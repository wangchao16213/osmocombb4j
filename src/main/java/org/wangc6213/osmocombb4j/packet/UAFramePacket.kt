package org.wangc6213.osmocombb4j.packet

class UAFramePacket(val chanNr: Byte) : ProtoPacket() {


    override fun buildRawData(): ByteArray {
        val bytes = byteArrayOf(
            0x00,
            0x1f,
            0x06,
            0x00,
            0x00,
            0x00,
            chanNr,
            0x03,
            0x00,
            0x00,
            0x0f,
            0x73,
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
        return bytes
    }


}