package org.wangc6213.osmocombb4j.packet


import cn.hutool.core.util.HexUtil
import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.common.LapdmFrameType
import org.wangc6213.osmocombb4j.common.LapdmProto
import org.wangc6213.osmocombb4j.utils.BytesUtils
import java.nio.ByteBuffer

/**
 * L1ctl.c 212
 * 信道接收数据标识
 * data on the CCCH was found. This is following the header
 * tip 03 00 00 00 90 00 00 35 00 1f d4 7e 36 00 00 00 25 06 21 00 05 f4 e7 fa 42 1a 23 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b
 */
class DataIndPacket(rawData: ByteArray) : ProtoPacket() {
    var hdr = L1ctlPacket(L1CtlProto.L1CTL_DATA_IND)
    var infoDl: InfoDlPacket

    /** 23个字节  */
    var data: ByteArray
    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(2 + 4 + 12 + data.size)
        byteBuffer.put(rawData.copyOfRange(0,2 + 4 + 12))
        byteBuffer.put(data)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return "DataIndPacket{" +
                "hdr=" + hdr.toString() +
                ", infoDl=" + infoDl.toString() +
                ", data=" + data.contentToString() +
                '}'
    }

    init {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        setRawData(rawData)
        infoDl = InfoDlPacket(rawData.copyOfRange(6, 18))
        data = rawData.copyOfRange(18, rawData.size)
    }


    /**
     * 是否下行UA帧 for 位置更新
     */
    fun isDLLocUpdReq(): Boolean {
        //是否是UA帧 并且 是下行位置更新
        if ((this.data[1] == (0x73).toByte() || this.data[1] == (0x3f).toByte()) && this.data[3] == (0x05).toByte() && this.data[4] == (0x08).toByte()) {
            return true
        }
        return false
    }

    /**
     * 是否下行寻呼响应
     */
    fun isDlPagingResp(): Boolean {
        if ((this.data[1] == (0x73).toByte() || this.data[1] == (0x3f).toByte()) && this.data[3] == (0x06).toByte() && this.data[4] == (0x27).toByte()) {
            return true
        }
        return false
    }

    fun isCrroctPacket(lapdmPacketType: Int): Boolean {
        if (LapdmProto.AUTH_REQ == lapdmPacketType) {
            //排除MM infomation帧(该帧类型为0x32)
            if (data[4].toUByte().toInt().and(0x12) == 0x12 && (data[4].toUByte().toInt().and(0x32) != 0x32)) {
                return true
            }
        } else if (LapdmProto.INDENTITY_REQ == lapdmPacketType) {
            if (data[4].toUByte().toInt().and(0x18) == 0x18) {
                return true
            }
        } else if (LapdmProto.LOC_UPD_ACCEPT == lapdmPacketType) {
            //排除MM infomation帧(该帧类型为0x32)
            if (data[4].toUByte().toInt().and(0x02) == 0x02 && (data[4].toUByte().toInt().and(0x32) != 0x32)) {
                return true
            }

        } else if (LapdmProto.CHANNEL_RELEASE == lapdmPacketType) {
            if (data[3].toUByte().toInt().and(0x06) == 0x06 && data[4] == (0x0d).toByte()) {
                return true
            }
        } else if (LapdmProto.MM_INFOMATION == lapdmPacketType) {
            if (data[4].toUByte().toInt().and(0x32) == 0x32) {
                return true
            }
        } else if (LapdmProto.TMSI_Reallocation_Command == lapdmPacketType) {
            if (data[4].toUByte().toInt().and(0x1a) == 0x1a) {
                return true
            }
        } else if (LapdmProto.SMS_RP_DATA == lapdmPacketType) {
            if (data[3].toUByte().toInt().and(0x09) == 0x09 && data[4] == (0x01).toByte()) {
                return true
            }
        } else if (LapdmProto.SMS_CP_ACK == lapdmPacketType) {
            if (data[3].toUByte().toInt().and(0x09) == 0x09 && data[4] == (0x04).toByte()) {
                return true
            }
        }
        return false
    }

    /**
     * 判断帧类型
     */
    fun getLapdmFrameType(): Int {
        if (data[1].toInt().and(0x03) == 0x03) {
            return LapdmFrameType.U_FRAME
        }
        if (data[1].toInt().and(0x01) == 0x01) {
            return LapdmFrameType.S_FRAME
        }
        if (data[1].toInt().and(0x01) == 0x00) {
            return LapdmFrameType.I_FRAME
        }
        return LapdmFrameType.UNKOWN_FRAME
    }

    /**
     * 获取当前序列号
     */
    fun getNs(): Int {
        //这里需要转换成Ubyte否则会产生负数
        return data[1].toUByte().toInt().and(0x0e).shr(1)
    }

    /**
     * 判断是否是SAPI1
     */
    fun isSAPI1(): Boolean {
        //这里需要转换成Ubyte否则会产生负数
        if (data[0].toUByte().toInt().and(0x04) == 0x04) {
            return true
        }
        return false
    }

    fun printForLapdm() {
        //println("msgType:${data[4]},msgTypeBinary:${Integer.toBinaryString(data[4].toInt())}")
        println("LAPDM调试已接收:" + HexUtil.encodeHexStr(data).chunked(2).joinToString(" "))
        println("帧类型:${getLapdmFrameType()}")
    }

    fun getNr(): Int {
        //这里需要转换成Ubyte否则会产生负数
        return data[1].toUByte().toInt().shr(5)
    }
}