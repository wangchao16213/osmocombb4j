package org.wangc6213.osmocombb4j.packet

class IndentityRespPakcet(miType: Byte, chanNr: Byte, nr: Int, ns: Int) :
    UpIframePacket(chanNr = chanNr, ns = ns, nr = nr) {
    val imsi = byteArrayOf()

    init {
        length = 0x2d
        println("收到mitype${miType.toInt()}")
        if (miType.toInt() == 1) {
            data = byteArrayOf(
                0x05,
                0x59,
                0x08,
                0x49,
                0x06,
                0x70,
                0x01,
                0x37,
                0x71,
                0x17,
                0x89.toByte(),
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
        } else {
            data = byteArrayOf(
                0x05,
                0x19,
                0x08,
                0x8a.toByte(),
                0x36,
                0x47,
                0x01,
                0x74,
                0x46,
                0x62,
                0x92.toByte(),
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
        }
    }
}