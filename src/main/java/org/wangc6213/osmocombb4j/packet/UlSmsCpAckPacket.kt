package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

class UlSmsCpAckPacket(tio: Int, chanNr: Byte, ns: Int, nr: Int) :
    UpIframePacket(sapi = 3, chanNr = chanNr, ns = ns, nr = nr) {
    val pd: Byte
    val msgType: Byte = 0x04
    val fillBytes: ByteArray = byteArrayOf(
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
        0x2b,
    )

    init {
        val tiovalue = tio
        var tiovalueStr = Integer.toBinaryString(tiovalue)
        if (tiovalueStr.length < 3) {
            val strlenth = 3 - tiovalueStr.length
            for (i in 0 until strlenth) {
                tiovalueStr = "0$tiovalueStr"
            }
        }
        pd = Integer.parseInt("1${tiovalueStr}1001", 2).toByte()
        length = 0X09
        val byteBuffer = ByteBuffer.allocate(20)
        byteBuffer.put(pd)
        byteBuffer.put(msgType)
        byteBuffer.put(fillBytes)
        data = byteBuffer.array()

    }
}