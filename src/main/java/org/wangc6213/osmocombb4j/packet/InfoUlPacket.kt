package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

/**
 * 上联口信息包 uplink info 总长度4字节
 */
class InfoUlPacket {
    /**
     * 1bytes GSM 08.58 channel number (9.3.1)
     */
    var chanNr: UByte = 0u

    /**
     * 1bytes GSM 08.58 link identifier (9.3.2)
     */
    var linkId: UByte = 0u
    val paddings = byteArrayOf(0x00, 0x00)

    fun buildRawData(): ByteArray {
        var rawdata = ByteBuffer.allocate(4)
        rawdata.put(chanNr.toByte())
        rawdata.put(linkId.toByte())
        rawdata.put(paddings)
        return rawdata.array()
    }


}