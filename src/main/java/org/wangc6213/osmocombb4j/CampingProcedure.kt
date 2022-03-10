package org.wangc6213.osmocombb4j

import cn.hutool.core.util.HexUtil
import org.newsclub.net.unix.AFUNIXSocket
import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.common.L1ctlResetType
import org.wangc6213.osmocombb4j.handle.CampingMutiFrameHandle
import org.wangc6213.osmocombb4j.model.MobileStationEntity
import org.wangc6213.osmocombb4j.model.MutiFrameVar
import org.wangc6213.osmocombb4j.packet.*
import org.wangc6213.osmocombb4j.packet.ImmediateAssignmentPacket
import org.wangc6213.osmocombb4j.threads.MeasureReportThread
import org.wangc6213.osmocombb4j.utils.PacketUtils

class CampingProcedure(var afunixSocket: AFUNIXSocket, val mobileStationEntity: MobileStationEntity) {

    /**
     * 设备复位
     */
    fun rest() {
        afunixSocket.outputStream.write(RestReqPacket().buildRawData())
        afunixSocket.outputStream.flush()

        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println("在复位操作中当前循环类型:${l1ctlPacket.msgType}")
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_RESET_CONF) {
                println("复位成功")
                break
            }
        }
    }


    /**
     * 设备复位不需要等待
     */
    fun rest2() {
        afunixSocket.outputStream.write(RestReqPacket().buildRawData())
        afunixSocket.outputStream.flush()
    }


    /**
     * 设备复位 类型2
     */
    fun rest3() {
        afunixSocket.outputStream.write(RestReqPacket(L1ctlResetType.L1CTL_RES_T_SCHED).buildRawData())
        afunixSocket.outputStream.flush()
    }

    /**
     * 进行突发请求
     */
    fun doFbsbReq() {
        val bytes = FbsbReqPacket(mobileStationEntity.bandArfcn).buildRawData()
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_FBSB_CONF) {
                val fbsbConfPacket = FbsbConfPacket(l1ctlPacket.rawData)
                if (fbsbConfPacket.result.toInt() == 0) {
                    println("频率校正成功")
                    println(fbsbConfPacket)
                    break
                }

            }
        }
    }

    /**
     * 进行测量
     */
    fun doPmReq() {
        val bytes = PmReqPacket(
            mobileStationEntity.bandArfcnFrom.toUShort(),
            mobileStationEntity.bandArfcnTo.toUShort()
        ).buildRawData()
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_PM_CONF) {
                val pmConfPacket = PmConfPacket(l1ctlPacket.rawData)
                println(pmConfPacket)
                break
            }
        }
    }

    fun doChannelReq() {
        var chreqPacket = FbsbReqPacket(mobileStationEntity.bandArfcn, 1u)
        //TODO 这里为啥要设置成0
        chreqPacket.syncInfoIdx = 0u
        println(chreqPacket)
        afunixSocket.outputStream.write(chreqPacket.buildRawData())
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_FBSB_CONF) {
                val fbsbConfPacket = FbsbConfPacket(l1ctlPacket.rawData)
                if (fbsbConfPacket.result.toInt() == 0) {
                    println("CHANNEL REQUEST SUCCESS")
                    println(fbsbConfPacket)
                    println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                    break
                }

            }
        }
    }

    fun doCCCHModelChange(ccchModel: Byte) {
        var ccchModeReqPacket = CCCHModeReqPacket(ccchModel)
        afunixSocket.outputStream.write(ccchModeReqPacket.buildRawData())
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_CCCH_MODE_CONF) {
                var ccchModeConfPacket = CCCHModeConfPacket(l1ctlPacket.rawData)
                println("CCCH_MODE已经切换到1")
                println(ccchModeConfPacket)
                break
            }
        }
    }


    /**
     * 随机访问 ACCH信道
     */
    fun randomAccess() {
        val paramRestReqPacket = ParamReqPacket()
        val rachReqPacket = RachReqPacket()
        println(HexUtil.encodeHexStr(paramRestReqPacket.buildRawData()).chunked(2).joinToString(" "))
        println(HexUtil.encodeHexStr(rachReqPacket.buildRawData()).chunked(2).joinToString(" "))
        mobileStationEntity.ra = rachReqPacket.ra
        println("已随机生成RA:${rachReqPacket.ra}")
        afunixSocket.outputStream.write(paramRestReqPacket.buildRawData())
        afunixSocket.outputStream.flush()
        afunixSocket.outputStream.write(rachReqPacket.buildRawData())
        afunixSocket.outputStream.flush()
    }

    /**
     * 获取指配置信令
     */
    fun reciveImmediateAssignment() {
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_DATA_IND) {
                val dataIndPacket = DataIndPacket(l1ctlPacket.rawData)
                if (dataIndPacket.data[1] == (0x06).toByte()
                    && dataIndPacket.data[2] == (0x3f).toByte()
                ) {
                    println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                    val immediateAssignmentPacket = ImmediateAssignmentPacket(dataIndPacket.data)
                    println("从信道接受指配信令,requestReference:${immediateAssignmentPacket.requestReference} RA:${immediateAssignmentPacket.getRa()}")
                    /**
                     * 获取AGCH信道中立即指配信令
                     */
                    if (mobileStationEntity.ra == immediateAssignmentPacket.getRa()) {
                        println("RA${immediateAssignmentPacket.getRa()}匹配成功,正在专用信道已连接")
                        mobileStationEntity.chanNr = immediateAssignmentPacket.getChanNr()
                        mobileStationEntity.ta = immediateAssignmentPacket.timingAdvance
                        mobileStationEntity.tsc = immediateAssignmentPacket.getTsc()
                        //进行复位 使用类型3
                        rest3()
                        val paramReqPacket = ParamReqPacket()
                        val dmEstReqPacket = DmEstReqPacket(
                            mobileStationEntity.chanNr,
                            mobileStationEntity.tsc.toUByte(),
                            mobileStationEntity.bandArfcn.toUShort()
                        )
                        //上报位置请求
                        var locUpdReqPacket =
                            UlLocUpdReqPacket(
                                mobileStationEntity.chanNr,
                                mobileStationEntity.tmsi,
                                mobileStationEntity.laiBytes
                            )
                        afunixSocket.outputStream.write(paramReqPacket.buildRawData())
                        afunixSocket.outputStream.flush()
                        println(
                            "已发送:" + HexUtil.encodeHexStr(paramReqPacket.buildRawData()).chunked(2).joinToString(" ")
                        )
                        afunixSocket.outputStream.write(dmEstReqPacket.buildRawData())
                        afunixSocket.outputStream.flush()
                        println(
                            "已发送:" + HexUtil.encodeHexStr(dmEstReqPacket.buildRawData()).chunked(2).joinToString(" ")
                        )
                        afunixSocket.outputStream.write(locUpdReqPacket.buildRawData())
                        afunixSocket.outputStream.flush()
                        println(
                            "已发送:" + HexUtil.encodeHexStr(locUpdReqPacket.buildRawData()).chunked(2).joinToString(" ")
                        )
                        break
                    }

                }
            }
        }
    }


    /**
     * 开启向基站定时报告状态
     */
    fun scheduleMeasureReprt() {
        MeasureReportThread(afunixSocket, mobileStationEntity.chanNr, mobileStationEntity.ta).start()
    }


    /**
     * 发送位置更新后开始接收下行位置更新UA帧
     */
    fun recvDlLocReqPacket() {
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.msgType == L1CtlProto.L1CTL_DATA_IND) {
                val dataIndPacket = DataIndPacket(l1ctlPacket.rawData)
                if (dataIndPacket.isDLLocUpdReq()) {
                    var dlLocUpdReqPacket = DlLocUpdReqPacket(l1ctlPacket.rawData)
                    if (dlLocUpdReqPacket.tmsiStr == mobileStationEntity.tmsi) {
                        //匹配成功
                        println("已匹配下行位置更新帧,发送Classmark change")
                        val classmarkChangePacket = ClassmarkChangePacket(mobileStationEntity.chanNr)
                        afunixSocket.outputStream.write(classmarkChangePacket.buildRawData())
                        afunixSocket.outputStream.flush()
                        println(
                            "已发送:" + HexUtil.encodeHexStr(classmarkChangePacket.buildRawData()).chunked(2)
                                .joinToString(" ")
                        )
                        break
                    }
                }
            }
        }
    }


    /**
     * 接收IndReq和授权请求
     */
    fun recvIndReqAndAuthReqMutiFramePacket() {
        CampingMutiFrameHandle(afunixSocket, mutiFrameVar = MutiFrameVar(), mobileStationEntity).doHandle()
    }

    fun start() {
        rest()
        doPmReq()
        rest()
        //第一次突发
        rest2()
        doFbsbReq()
        //等2秒
        Thread.sleep(2000)
        rest2()
        doCCCHModelChange(1)
        //等5秒
        Thread.sleep(5000)
        rest2()
        doChannelReq()
        randomAccess()
        //接受指定配置
        reciveImmediateAssignment()
        //定时报告状态
        scheduleMeasureReprt()
        recvDlLocReqPacket()
        recvIndReqAndAuthReqMutiFramePacket()
        println("MS入网操作完成")
    }
}