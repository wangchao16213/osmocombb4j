package org.wangc6213.osmocombb4j.handle

import org.newsclub.net.unix.AFUNIXSocket
import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.common.LapdmFrameType
import org.wangc6213.osmocombb4j.common.LapdmProto
import org.wangc6213.osmocombb4j.model.MobileStationEntity
import org.wangc6213.osmocombb4j.model.MutiFrameVar
import org.wangc6213.osmocombb4j.packet.*
import org.wangc6213.osmocombb4j.utils.DebugHelper
import org.wangc6213.osmocombb4j.utils.PacketUtils
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

/**
 * 短信寻呼复帧流程
 */
class SmsMutiFrameHandle(
    val afunixSocket: AFUNIXSocket,
    val mobileStationEntity: MobileStationEntity
) {
    var mutiFrameVar = MutiFrameVar()

    /**
     * 发送数据队列
     */
    val sendDataQueue: BlockingQueue<UpIframePacket> = ArrayBlockingQueue(1000)
    var loopFlag = true

    /**
     * 帧传输是否进入异常状态
     */
    var rejFlag = false

    var segmentIframePacket: DataIndPacket? = null

    //是否处于分段状态
    var isSegment = false


    fun doHandle() {
        while (loopFlag) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_DATA_IND) {
                var dataIndPacket = DataIndPacket(l1ctlPacket.rawData)
                /**
                 * 只接收下行方向address为0x03和0x0f的I帧和0x01和0x0d的S帧
                 */
                if (dataIndPacket.data[0].toInt() == 0x03 || dataIndPacket.data[0].toInt() == 0x0f
                    || dataIndPacket.data[0].toInt() == 0x01 || dataIndPacket.data[0].toInt() == 0x0d
                ) {
                    if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.I_FRAME || dataIndPacket.getLapdmFrameType() == LapdmFrameType.S_FRAME || dataIndPacket.getLapdmFrameType() == LapdmFrameType.U_FRAME) {
                        println("处理收到的帧:===${DebugHelper.getPrintFrameContent(dataIndPacket.data)}")
                        processDataQueue(dataIndPacket)
                    }
                }
            }
        }
    }

    private fun processDataQueue(dataIndPacket1: DataIndPacket) {
        var dataIndPacket = dataIndPacket1
        //判断帧类型
        if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.S_FRAME) {
            mutiFrameVar.alreadyRecvSFrame(dataIndPacket.getNr())
            checkSend()
        } else if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.U_FRAME
            && (dataIndPacket.data[1].toUByte().toInt().and(0x3f) == 0x3f || dataIndPacket.data[1].toUByte().toInt()
                .and(0x2f) == 0x2f)
        ) {
            println("收到U帧")
            //该帧为SABM这里重新初始化复帧变量
            mutiFrameVar = MutiFrameVar()
            mutiFrameVar.vs = 0

            //回复U帧
            PacketUtils.send(afunixSocket, UAFramePacket(dataIndPacket.infoDl.chanNr.toByte()).buildRawData())
            println(
                "发送UA帧:===${
                    DebugHelper.getPrintFrameContent(
                        UAFramePacket(dataIndPacket.infoDl.chanNr.toByte()).buildRawData()
                    )
                }"
            )
        } else if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.I_FRAME) {
            /**
             * 收到I帧判断序号是否正确
             */
            if (dataIndPacket.getNs() == mutiFrameVar.vr) {
                //判断数据是否分段 0x02为数据分段要进行数据重组
                if (dataIndPacket.data[2].toUByte().toInt().and(0x03) == 0x03) {

                    if (segmentIframePacket == null) {
                        //第一次收到分段数据
                        segmentIframePacket = dataIndPacket
                    } else {
                        PacketUtils.segmentIframeDataMerge(dataIndPacket, segmentIframePacket)
                    }
                    //发送S确认帧 并且消除异常状态
                    PacketUtils.send(
                        afunixSocket,
                        SframePacket(
                            dataIndPacket.infoDl.chanNr.toByte(),
                            mutiFrameVar.vr,
                            address = dataIndPacket.data[0]
                        ).buildRawData()
                    )
                    //预期接收序列号+1
                    mutiFrameVar.increaseVr()
                    println(
                        "发送S确认帧:===${
                            DebugHelper.getPrintFrameContent(
                                SframePacket(
                                    dataIndPacket.infoDl.chanNr.toByte(),
                                    mutiFrameVar.vr, address = dataIndPacket.data[0]
                                ).buildRawData()
                            )
                        }"
                    )
                    rejFlag = false

                } else {
                    if (segmentIframePacket != null) {
                        //处于分段当中 并且接收到最后一个分段
                        PacketUtils.segmentIframeDataMerge(dataIndPacket, segmentIframePacket)
                        dataIndPacket = segmentIframePacket as DataIndPacket
                        segmentIframePacket = null
                    }
                    //接收确认
                    mutiFrameVar.alreadyRecvIFrame(dataIndPacket.getNr())

                    doRespIFrame(dataIndPacket)

                    println("检查发送队列")
                    if (sendDataQueue.size > 0) {
                        checkSend()
                    } else {
                        //发送S确认帧 并且消除异常状态
                        PacketUtils.send(
                            afunixSocket,
                            SframePacket(
                                dataIndPacket.infoDl.chanNr.toByte(),
                                mutiFrameVar.vr,
                                address = dataIndPacket.data[0]
                            ).buildRawData()
                        )
                        println(
                            "发送S确认帧:===${
                                DebugHelper.getPrintFrameContent(
                                    SframePacket(
                                        dataIndPacket.infoDl.chanNr.toByte(),
                                        mutiFrameVar.vr, address = dataIndPacket.data[0]
                                    ).buildRawData()
                                )
                            }"
                        )
                    }
                    rejFlag = false
                }
            } else {
                //发送拒绝帧 并且帧传输进入异常状态 忽略所有I帧   *处于异常状态中S拒绝帧只发送一次
                if (!rejFlag) {
                    PacketUtils.send(
                        afunixSocket,
                        SframePacket(
                            dataIndPacket.infoDl.chanNr.toByte(),
                            mutiFrameVar.vr, 1, address = dataIndPacket.data[0]
                        ).buildRawData()
                    )
                    println(
                        "发送S拒绝帧:===${
                            DebugHelper.getPrintFrameContent(
                                SframePacket(
                                    dataIndPacket.infoDl.chanNr.toByte(),
                                    mutiFrameVar.vr, 1, address = dataIndPacket.data[0]
                                ).buildRawData()
                            )
                        }"
                    )
                    rejFlag = true
                }
            }
        }
    }

    private fun processDataQueue2(dataIndPacket1: DataIndPacket) {
        var dataIndPacket = dataIndPacket1
        //判断帧类型

        when (dataIndPacket.getLapdmFrameType()) {
            LapdmFrameType.S_FRAME -> {
                mutiFrameVar.alreadyRecvSFrame(dataIndPacket.getNr())
                checkSend()
            }
            LapdmFrameType.U_FRAME -> {
                if (dataIndPacket.data[1].toUByte().toInt().and(0x3f) == 0x3f || dataIndPacket.data[1].toUByte().toInt()
                        .and(0x2f) == 0x2f
                ) {
                    println("收到U帧")
                    //该帧为SABM这里重新初始化复帧变量
                    mutiFrameVar = MutiFrameVar()
                    mutiFrameVar.vs = 0

                    //回复U帧
                    PacketUtils.send(afunixSocket, UAFramePacket(dataIndPacket.infoDl.chanNr.toByte()).buildRawData())
                }
            }
            LapdmFrameType.I_FRAME -> {
                processIframe(dataIndPacket)
            }
        }
    }

    fun processIframe(dataIndPacket: DataIndPacket) {
        /**
         * 收到I帧判断序号是否正确
         */
        if (isCorrectIframe(dataIndPacket)) {
            //判断数据是否分段 0x02为数据分段要进行数据重组
            if (dataIndPacket.data[2].toUByte().toInt().and(0x03) == 0x03) {

                if (segmentIframePacket == null) {
                    //第一次收到分段数据
                    segmentIframePacket = dataIndPacket
                } else {
                    PacketUtils.segmentIframeDataMerge(dataIndPacket, segmentIframePacket)
                }
                //发送S确认帧 并且消除异常状态
                PacketUtils.send(
                    afunixSocket,
                    SframePacket(
                        dataIndPacket.infoDl.chanNr.toByte(),
                        mutiFrameVar.vr,
                        address = dataIndPacket.data[0]
                    ).buildRawData()
                )
                //预期接收序列号+1
                mutiFrameVar.increaseVr()
                println(
                    "发送S确认帧:===${
                        DebugHelper.getPrintFrameContent(
                            SframePacket(
                                dataIndPacket.infoDl.chanNr.toByte(),
                                mutiFrameVar.vr, address = dataIndPacket.data[0]
                            ).buildRawData()
                        )
                    }"
                )
            } else {
                if (segmentIframePacket != null) {
                    //处于分段当中 并且接收到最后一个分段
                    PacketUtils.segmentIframeDataMerge(dataIndPacket, segmentIframePacket)
                    //dataIndPacket = segmentIframePacket as DataIndPacket
                    segmentIframePacket = null
                }
                //接收确认
                mutiFrameVar.alreadyRecvIFrame(dataIndPacket.getNr())

                doRespIFrame(dataIndPacket)

                println("检查发送队列")
                if (sendDataQueue.size > 0) {
                    checkSend()
                } else {
                    //发送S确认帧 并且消除异常状态
                    PacketUtils.send(
                        afunixSocket,
                        SframePacket(
                            dataIndPacket.infoDl.chanNr.toByte(),
                            mutiFrameVar.vr,
                            address = dataIndPacket.data[0]
                        ).buildRawData()
                    )
                    println(
                        "发送S确认帧:===${
                            DebugHelper.getPrintFrameContent(
                                SframePacket(
                                    dataIndPacket.infoDl.chanNr.toByte(),
                                    mutiFrameVar.vr, address = dataIndPacket.data[0]
                                ).buildRawData()
                            )
                        }"
                    )
                }
                rejFlag = false
            }
        }
    }


    /**
     * 是否正确序号的I帧
     */
    fun isCorrectIframe(dataIndPacket: DataIndPacket): Boolean {
        if (dataIndPacket.getNs() != mutiFrameVar.vr) {
            //发送拒绝帧 并且帧传输进入异常状态 忽略所有I帧   *处于异常状态中S拒绝帧只发送一次
            if (!rejFlag) {
                PacketUtils.send(
                    afunixSocket,
                    SframePacket(
                        dataIndPacket.infoDl.chanNr.toByte(),
                        mutiFrameVar.vr, 1, address = dataIndPacket.data[0]
                    ).buildRawData()
                )
                println(
                    "发送S拒绝帧:===${
                        DebugHelper.getPrintFrameContent(
                            SframePacket(
                                dataIndPacket.infoDl.chanNr.toByte(),
                                mutiFrameVar.vr, 1, address = dataIndPacket.data[0]
                            ).buildRawData()
                        )
                    }"
                )
                //进入异常状态
                rejFlag = true
            }
            return false
        }
        //解除异常状态
        rejFlag = false
        return true
    }

    fun doRespIFrame(dataIndPacket: DataIndPacket) {
        if (dataIndPacket.isCrroctPacket(LapdmProto.INDENTITY_REQ)) {
            val indentityReqPacket = IndentityReqPacket(dataIndPacket.rawData)
            val indentityRespPakcet = IndentityRespPakcet(
                indentityReqPacket.identityType,
                dataIndPacket.infoDl.chanNr.toByte(),
                0,
                0
            )
            sendDataQueue.put(indentityRespPakcet)

        } else if (dataIndPacket.isCrroctPacket(LapdmProto.AUTH_REQ)) {
            /**
             * 授权请求
             */
            val authReqPacket = AuthReqPacket(dataIndPacket.rawData)
            val sres = GetSresHandle(authReqPacket.randText, afunixSocket).getSres()
            val authRespPacket = AuthRespPacket(
                dataIndPacket.infoDl.chanNr.toByte(),
                sres,
                0,
                0
            )
            sendDataQueue.put(authRespPacket)
        } else if (dataIndPacket.isCrroctPacket(LapdmProto.LOC_UPD_ACCEPT)) {
            /**
             * 位置更新已接受
             */
            println("位置更新接受:" + DebugHelper.getPrintFrameContent(dataIndPacket.rawData))
            val locUpdAcceptPacket = LocUpdAcceptPacket(dataIndPacket.rawData)
            println("重分配的TMSI为:${locUpdAcceptPacket.getTmsi()}")
            mobileStationEntity.tmsi = locUpdAcceptPacket.getTmsi()
            val ulTmsiReallocCompletePacket = TmsiReallocCompletePacket(dataIndPacket.infoDl.chanNr.toByte(), 0, 0)
            sendDataQueue.put(ulTmsiReallocCompletePacket)
        } else if (dataIndPacket.isCrroctPacket(LapdmProto.TMSI_Reallocation_Command)) {
            println("TMSI重分配接受:" + DebugHelper.getPrintFrameContent(dataIndPacket.rawData))
            val tmsiReallocCommandPacket = TmsiReallocCommandPacket(dataIndPacket.rawData)
            println("重分配的TMSI为:${tmsiReallocCommandPacket.getTmsi()}")
            mobileStationEntity.tmsi = tmsiReallocCommandPacket.getTmsi()
            val ulTmsiReallocCompletePacket = TmsiReallocCompletePacket(dataIndPacket.infoDl.chanNr.toByte(), 0, 0)
            sendDataQueue.put(ulTmsiReallocCompletePacket)
        } else if (dataIndPacket.isCrroctPacket(LapdmProto.MM_INFOMATION)) {
            /**
             * MM infomation 这里不做任何响应 只发送一个RR帧 并且vs + 1
             */
            //mutiFrameVar.increasVs()
        } else if (dataIndPacket.isCrroctPacket(LapdmProto.SMS_RP_DATA)) {
            //下行短信通知
            val dlSmsCpDataPacket = DlSmsCpDataPacket(dataIndPacket.buildRawData())
            println("收到下行短信,内容为:${dlSmsCpDataPacket.getSmsText()}")
            val smsCpAckPacket =
                UlSmsCpAckPacket(dlSmsCpDataPacket.getTIO(), dataIndPacket.infoDl.chanNr.toByte(), 0, 0)
            sendDataQueue.put(smsCpAckPacket)

            val ulSmsCpDataPacket =
                UlSmsCpDataPacket(dlSmsCpDataPacket.getTIO(), dataIndPacket.infoDl.chanNr.toByte(), 0, 0)
            sendDataQueue.put(ulSmsCpDataPacket)

        } else if (dataIndPacket.isCrroctPacket(LapdmProto.CHANNEL_RELEASE)) {
            /**
             * 链路拆除
             */
            val channelReleasePacket = ChannelReleasePacket(dataIndPacket.rawData)
            if (channelReleasePacket.isNormalRRCause()) {
                println("链路已经正常拆除")
            } else {
                println("链路拆除异常 请查看RR原因:${channelReleasePacket.rrCause}")
            }
            //stopHandle()
        }
    }


    /**
     * 检查上次响应是否基站已确认
     */
    private fun checkSend() {
        println("检查队列是否可发送${mutiFrameVar}----${mutiFrameVar.isAllowSend()}")
        if (sendDataQueue.size > 0 && mutiFrameVar.isAllowSend()) {
            val iframePacket = sendDataQueue.take()
            iframePacket.fillControl(mutiFrameVar.vr, mutiFrameVar.vs)
            val bytes = iframePacket.buildRawData()
            println("发送队列帧:===${DebugHelper.getPrintFrameContent(bytes)}")
            PacketUtils.send(afunixSocket, bytes)
            mutiFrameVar.increasVs()
        }
    }

    /**
     * 停止处理
     */
    fun stopHandle() {
        loopFlag = false
    }
}