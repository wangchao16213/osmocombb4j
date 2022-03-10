package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import java.nio.ByteBuffer

/**
 * 参数请求
 * tip: 00 0c 13 00 00 00 00 00 00 00 00 05 00 00
 */
class ParamReqPacket : ProtoPacket() {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_PARAM_REQ)
    private val infoUl = InfoUlPacket()

    /**
     * 1字节 有符号
     */
    private val ta: Byte = 0
    private val txPower: Byte = 5
    private val paddings = byteArrayOf(0x00, 0x00)
    override fun buildRawData(): ByteArray {
        val hdrData = hdr.buildRawDataWithoutPayload()
        val byteBuffer = ByteBuffer.allocate(14)
        byteBuffer.put(hdrData)
        byteBuffer.put(infoUl.chanNr.toByte())
        byteBuffer.put(infoUl.linkId.toByte())
        byteBuffer.put(infoUl.paddings)
        byteBuffer.put(ta)
        byteBuffer.put(txPower)
        byteBuffer.put(paddings)
        return byteBuffer.array()
    }

    init {
        hdr.len = 12.toShort()
    }
}