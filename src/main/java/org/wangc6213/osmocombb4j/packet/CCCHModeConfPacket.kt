package org.wangc6213.osmocombb4j.packet


import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils

/**
 * 11 00 00 00 01 00 00 00
 */
class CCCHModeConfPacket(rawData: ByteArray) : ProtoPacket() {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_CCCH_MODE_CONF)
    private var ccchMode: Byte = 0
    private var padidng0: Byte = 0
    private var padidng1: Byte = 0
    private var padidng2: Byte = 0
    override fun buildRawData(): ByteArray {
        return ByteArray(0)
    }

    init {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        ccchMode = rawData[6]
        padidng0 = rawData[7]
        padidng1 = rawData[8]
        padidng2 = rawData[9]
    }
}