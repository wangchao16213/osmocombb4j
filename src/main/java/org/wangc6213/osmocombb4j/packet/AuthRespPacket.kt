package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil
import java.nio.ByteBuffer

/**
 * 授权响应
 * send: 00 1f 06 00 00 00 51 00 00 00 01 66 19 05 54 09 91 79 14 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b
 */
class AuthRespPacket(chanNr: Byte, val sres: String, ns: Int, nr: Int) :
    UpIframePacket(chanNr = chanNr, ns = ns, nr = nr) {
    val pd: Byte = 0x05
    val msgType: Byte = (0x14).toByte()


    init {
        length = 0x19
        val byteBuffer = ByteBuffer.allocate(20)
        byteBuffer.put(pd)
        byteBuffer.put(msgType)
        byteBuffer.put(HexUtil.decodeHex(sres))
        byteBuffer.put(byteArrayOf(0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b))
        data = byteBuffer.array()
    }

}