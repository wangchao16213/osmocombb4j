package org.wangc6213.osmocombb4j.packet


import java.nio.ByteBuffer

/**
 * 上行 TMSI重分配完成响应
 * tip: 00 1f 06 00 00 00 51 00 00 00 01 aa 09 05 5b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b
 *
 */
class TmsiReallocCompletePacket(chanNr: Byte, nr: Int, ns: Int) : UpIframePacket(chanNr = chanNr, ns = ns, nr = nr) {
    val pd: Byte = 0x05
    val msgType: Byte = (0x1b).toByte()

    init {
        length = 0x09
        val byteBuffer = ByteBuffer.allocate(20)
        byteBuffer.put(pd)
        byteBuffer.put(msgType)
        byteBuffer.put(
            byteArrayOf(
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b,
                0x2b
            )
        )
        data = byteBuffer.array()
    }
}