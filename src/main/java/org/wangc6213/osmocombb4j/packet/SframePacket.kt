package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

/**
 * S帧
 * send: 00 1f 06 00 00 00 51 00 00 00 03 61 01 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b
 */
class SframePacket(val chanNr: Byte, nr: Int, val type: Int = 0, val address: Byte = 0x03) : ProtoPacket() {
    val REJ: Int = 1
    val RR: Int = 0
    var bytes1 = byteArrayOf()
    var control: Byte = 0
    val bytes2 = byteArrayOf(
        0x01,
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
        0x2b,
        0x2b
    )

    init {
        this.bytes1 = byteArrayOf(0x00, 0x1f, 0x06, 0x00, 0x00, 0x00, chanNr, 0x00, 0x00, 0x00, address)
        val nrBstr = Integer.toBinaryString(nr)
        if (type == REJ) {
            this.control = "${nrBstr}11001".toInt(2).toByte()
        } else {
            this.control = "${nrBstr}00001".toInt(2).toByte()
        }
    }


    override fun buildRawData(): ByteArray {
        val byteArray = ByteBuffer.allocate(0x1f + 2)
        byteArray.put(bytes1)
        byteArray.put(control)
        byteArray.put(bytes2)
        return byteArray.array()
    }


}