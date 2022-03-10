package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.packet.ProtoPacket
import org.wangc6213.osmocombb4j.packet.L1ctlPacket
import org.wangc6213.osmocombb4j.common.L1CtlProto
import java.nio.ByteBuffer

class PmReqPacket(val bandArfcnFrom: UShort = 0u, val bandArfcnTo: UShort = 10u) : ProtoPacket() {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_PM_REQ)
    private val type: UByte = 1u
    private val padding0: UByte = 0u
    private val padding1: UByte = 0u
    private val padding2: UByte = 0u

    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(14)
        byteBuffer.put(hdr.buildRawDataWithoutPayload())
        byteBuffer.put(type.toByte())
        byteBuffer.put(padding0.toByte())
        byteBuffer.put(padding1.toByte())
        byteBuffer.put(padding2.toByte())
        byteBuffer.putShort(bandArfcnFrom.toShort())
        byteBuffer.putShort(bandArfcnTo.toShort())
        return byteBuffer.array()
    }

    init {
        hdr.msgType = L1CtlProto.L1CTL_PM_REQ
        hdr.len = 12.toShort()
    }
}