package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils

/**
 * 下行 通过构造函数创建的是由MS进行发起的 所以address是0x01(短消息除外)
 * tip:03 00 00 00 51 00 00 33 00 17 d2 51 34 00 00 00 03 a8 39 05 02 64 f0 00 73 d1 17 05 f4 5e cb 49 2d 2b 1a d7 4b 1e b7
 */
open class DlIframePacket(rawData: ByteArray) : ProtoPacket() {
    val hdr = L1ctlPacket(L1CtlProto.L1CTL_DATA_REQ)
    val infoDlPacket: InfoDlPacket
    open var chanNr: Byte = 0
    var address: UByte = 0x00u
    var control: UByte = (0x00).toUByte()
    var length: UByte = 0x00u
    var data: ByteArray = byteArrayOf()

    init {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        this.infoDlPacket = InfoDlPacket(rawData.copyOfRange(6, 18))

        this.address = rawData[18].toUByte()
        this.control = rawData[19].toUByte()
        this.length = rawData[20].toUByte()
        this.data = rawData.copyOfRange(21, rawData.size)


    }

    /**
     * 获取当前序列号
     */
    fun getNs(): Int {
        return control.toInt().and(0x0e).shr(1)
    }

    fun getNr(): Int {
        return control.toInt().shr(5)
    }

    override fun buildRawData(): ByteArray {
        return byteArrayOf()
    }
}