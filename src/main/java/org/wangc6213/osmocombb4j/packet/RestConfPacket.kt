package org.wangc6213.osmocombb4j.packet


import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.common.L1ctlResetType
import org.wangc6213.osmocombb4j.utils.BytesUtils
import java.nio.ByteBuffer

class RestConfPacket : ProtoPacket {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_RESET_CONF)
    private var type = L1ctlResetType.L1CTL_RES_T_FULL
    private var paddings = byteArrayOf(0x00, 0x00, 0x00)

    constructor() {
        hdr.msgType = L1CtlProto.L1CTL_RESET_CONF
    }

    constructor(rawData: ByteArray) {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        type = rawData[7]
        paddings = rawData.copyOfRange(8, rawData.size)
    }

    override fun buildRawData(): ByteArray {
        val hdrbytes = hdr.buildRawDataWithoutPayload()
        val byteBuffer = ByteBuffer.allocate(10)
        byteBuffer.put(hdrbytes)
        byteBuffer.put(type)
        byteBuffer.put(paddings)
        return byteBuffer.array()
    }
}