package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.common.L1ctlResetType
import java.nio.ByteBuffer

class RestReqPacket(resetType: Byte = L1ctlResetType.L1CTL_RES_T_FULL) : ProtoPacket() {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_RESET_REQ)
    private var type = L1ctlResetType.L1CTL_RES_T_FULL
    private val paddings = byteArrayOf(0x00, 0x00, 0x00)
    override fun buildRawData(): ByteArray {
        val hdrbytes = hdr.buildRawDataWithoutPayload()
        val byteBuffer = ByteBuffer.allocate(10)
        byteBuffer.put(hdrbytes)
        byteBuffer.put(type)
        byteBuffer.put(paddings)
        return byteBuffer.array()
    }

    init {
        hdr.msgType = L1CtlProto.L1CTL_RESET_REQ
        hdr.len = 8.toShort()
        type = resetType
    }
}