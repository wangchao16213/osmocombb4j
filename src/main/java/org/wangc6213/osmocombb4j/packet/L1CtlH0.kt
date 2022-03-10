package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

class L1CtlH0 : ProtoPacket() {
    var bandArfcn: UShort = 0u
    override fun buildRawData(): ByteArray {
        return ByteBuffer.allocate(2).putShort(bandArfcn.toShort()).array()
    }

}