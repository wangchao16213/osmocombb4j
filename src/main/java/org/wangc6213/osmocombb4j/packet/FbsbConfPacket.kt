package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils
import kotlin.experimental.or

/**
 * 频率校正确认
 * tip: 02 00 00 00 + 00 + 00 00 + 35 00 1f d4 + 02 + 00 + 10 + 00 + 00 + 18 ee + 00 + 08
 */
class FbsbConfPacket(rawData: ByteArray) {
    var hdr = L1ctlPacket(L1CtlProto.L1CTL_FBSB_CONF)
    var infoDl: InfoDlPacket
    var initialFreqErr: Short = 0
    var result: UByte = 0u
    var bsic: UByte = 0u
    override fun toString(): String {
        return "FbsbConfPacket{" +
                "hdr=" + hdr +
                ", infoDl=" + infoDl +
                ", initialFreqErr=" + initialFreqErr +
                ", result=" + result +
                ", bsic=" + bsic +
                '}'
    }

    init {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        infoDl = InfoDlPacket(rawData.copyOfRange(6, 18))
        initialFreqErr = rawData[18].or(rawData[19]).toShort()
        result = rawData[20].toUByte()
        bsic = rawData[21].toUByte()
    }
}