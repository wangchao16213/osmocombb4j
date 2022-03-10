package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

/**
 * send: 00 1f 06 0a 01 51 51 40 18 01 05 01 01 03 49 06 15 00 40 01 c0 00 00 00 00 00 00 00 00 00 00 00 00
 */
class MeasReportPacket(chanNr: Byte, ta: Byte) : ProtoPacket() {
    val len: UShort = 31u
    val unknowBytes: ByteArray = byteArrayOf(0x06, 0x0a, 0x01)
    val bytedatas: ByteArray = byteArrayOf(
        chanNr,
        chanNr,
        0x40,
        0x18,
        ta,
        0x05,
        0x00,
        0x01,
        0x03,
        0x49,
        0x06,
        0x15,
        0x00,
        0x40,
        0x01,
        0xc0.toByte(),
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00
    )

    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(33)
        byteBuffer.putShort(len.toShort())
        byteBuffer.put(unknowBytes)
        byteBuffer.put(bytedatas)
        return byteBuffer.array()
    }

}