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
 * 在驻留授权复帧流程
 */
class CampingMutiFrameHandle(
    val afunixSocket: AFUNIXSocket,
    val mutiFrameVar: MutiFrameVar,
    val mobileStationEntity: MobileStationEntity
) {
    /**
     * 发送数据队列
     */
    val sendDataQueue: BlockingQueue<UpIframePacket> = ArrayBlockingQueue(1000)
    var loopFlag = true

    /**
     * 帧传输是否进入异常状态
     */
    var rejFlag = false


    fun doHandle() {
        while (loopFlag) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_DATA_IND) {
                val dataIndPacket = DataIndPacket(l1ctlPacket.rawData)
                /**
                 * 只接收下行方向address为0x03的I帧和0x01的S帧
                 */
                if (dataIndPacket.data[0].toInt() == 0x03 || dataIndPacket.data[0].toInt() == 0x01) {
                    if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.I_FRAME || dataIndPacket.getLapdmFrameType() == LapdmFrameType.S_FRAME) {
                        println("收到的帧:===${DebugHelper.getPrintFrameContent(dataIndPacket.data)}")
                        processDataQueue(dataIndPacket)
                    }
                }
            }
        }
    }

    private fun processDataQueue(dataIndPacket: DataIndPacket) {
        if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.S_FRAME) {
            mutiFrameVar.alreadyRecvSFrame(dataIndPacket.getNr())
            checkSend()
        } else if (dataIndPacket.getLapdmFrameType() == LapdmFrameType.I_FRAME) {
            /**
             * 收到I帧判断序号是否正确
             */
            if (dataIndPacket.getNs() == mutiFrameVar.vr) {
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
                        SframePacket(dataIndPacket.infoDl.chanNr.toByte(), mutiFrameVar.vr).buildRawData()
                    )
                    println(
                        "发送S确认帧:===${
                            DebugHelper.getPrintFrameContent(
                                SframePacket(
                                    dataIndPacket.infoDl.chanNr.toByte(),
                                    mutiFrameVar.vr
                                ).buildRawData()
                            )
                        }"
                    )
                }
                rejFlag = false
            } else {

                /**
                 * 如果进入异常状态并且接收到I帧的P位为1 发送一个F位为1的RR响应
                 */
                if (rejFlag) {
                }

                //发送拒绝帧 并且帧传输进入异常状态 忽略所有I帧   *处于异常状态中S拒绝帧只发送一次
                if (!rejFlag) {
                    PacketUtils.send(
                        afunixSocket,
                        SframePacket(
                            dataIndPacket.infoDl.chanNr.toByte(),
                            mutiFrameVar.vr, 1
                        ).buildRawData()
                    )
                    println(
                        "发送S拒绝帧:===${
                            DebugHelper.getPrintFrameContent(
                                SframePacket(
                                    dataIndPacket.infoDl.chanNr.toByte(),
                                    mutiFrameVar.vr, 1
                                ).buildRawData()
                            )
                        }"
                    )
                    rejFlag = true
                }
            }
        }
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
        } else if (dataIndPacket.isCrroctPacket(LapdmProto.MM_INFOMATION)) {
            /**
             * MM infomation 这里不做任何响应 只发送一个RR帧 并且vs + 1
             */
            //mutiFrameVar.increasVs()
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
            stopHandle()
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