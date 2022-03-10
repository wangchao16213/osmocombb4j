package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.packet.ProtoPacket
import java.nio.ByteBuffer

/**
 * SABM帧 Location Updating Request 上行
 */
class UlLocUpdReqPacket(val chanNr: Byte, val tmsi: String, val laiBytes: ByteArray) : ProtoPacket() {
    private val len: Short = 31
    private val padding1 = byteArrayOf(0x06, 0x00, 0x03, 0x04)

    /**
     * 都是一字节
     */
    private val padding2 = byteArrayOf(
        0x00,
        0x02,
        0x00,
        0x01,
        0x3f,
        0x3d,
        0x05,
        0x08,
        0x02
    )
    private val fillbytes = byteArrayOf(0x2b, 0x2b, 0x2b, 0x2b, 0x2b)
    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(len + 2)
        byteBuffer.putShort(len)
        byteBuffer.put(padding1)
        byteBuffer.put(chanNr)
        byteBuffer.put(padding2)
        byteBuffer.put(laiBytes)
        byteBuffer.put(
            byteArrayOf(
                0x33,
                0x05,
                0xf4.toByte()
            )
        )
        val tmsiItemList = tmsi.chunked(2)
        for (tmsiItem in tmsiItemList) {
            byteBuffer.put(Integer.parseInt(tmsiItem,16).toByte())
        }
        byteBuffer.put(fillbytes)
        return byteBuffer.array()
    }
}