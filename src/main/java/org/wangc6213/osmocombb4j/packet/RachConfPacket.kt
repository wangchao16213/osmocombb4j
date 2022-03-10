package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils

class RachConfPacket : ProtoPacket {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_PM_CONF)
    private var dlPacket = InfoDlPacket(byteArrayOf())


    constructor(rawData: ByteArray) {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        dlPacket = InfoDlPacket(hdr.payloadRawData)

    }



    override fun buildRawData(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "RachConfPacket(hdr=$hdr, dlPacket=$dlPacket)"
    }

}