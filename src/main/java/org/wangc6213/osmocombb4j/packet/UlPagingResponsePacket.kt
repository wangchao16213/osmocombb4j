package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

/**
 * 寻呼响应
 * tip:00 1f 06 00 03 04 79 00 02 00 01 3f 35 06 27 00 03 33 19 01 05 f4 37 c3 4c bc 2b 2b 2b 2b 2b 2b 2b
 */
class UlPagingResponsePacket(val chanNr: Byte, tmsi: String) : ProtoPacket() {
    val bytes1 =
        byteArrayOf(0x00, 0x1f, 0x06, 0x00, 0x03, 0x04, chanNr, 0x00, 0x02, 0x00, 0x01, 0x3f, 0x35)
    val pd: Byte = 0x06
    val msgType: Byte = 0x27
    val cipheringKeySeqNum: Byte = 0x00
    val mobileStattionClassmarkBytes = byteArrayOf(0x03, 0x33, 0x19, 0x01)
    val bytes2 = byteArrayOf(0x05, 0xf4.toByte())
    var tmsiBytes = byteArrayOf()
    val fillBytes = byteArrayOf(0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b)


    init {
        val tmsiBytesBuffer = ByteBuffer.allocate(4)
        val tmsiItemList = tmsi.chunked(2)
        for (tmsiItem in tmsiItemList) {
            tmsiBytesBuffer.put(Integer.parseInt(tmsiItem, 16).toByte())
        }
        tmsiBytes = tmsiBytesBuffer.array()
    }


    override fun buildRawData(): ByteArray {
        val bytesBuffer = ByteBuffer.allocate(0x1f + 2)
        bytesBuffer.put(bytes1)
        bytesBuffer.put(pd)
        bytesBuffer.put(msgType)
        bytesBuffer.put(cipheringKeySeqNum)
        bytesBuffer.put(mobileStattionClassmarkBytes)
        bytesBuffer.put(bytes2)
        bytesBuffer.put(tmsiBytes)
        bytesBuffer.put(fillBytes)
        return bytesBuffer.array()
    }
}