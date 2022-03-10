package org.wangc6213.osmocombb4j.packet


import org.wangc6213.osmocombb4j.common.L1CtlProto
import java.nio.ByteBuffer

/**
 * send: 00 90 05 00 00 00 51 00 00 00 04 00 00 33 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 05
 */
class DmEstReqPacket(chanNr: Byte, tsc: UByte, bandArfcn: UShort) : ProtoPacket() {
    /** 6 byte */
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_DM_EST_REQ)
    /** 4 byte */
    private val infoUl = InfoUlPacket()
    private var tsc: UByte = tsc
    private var h: UByte = 0u
    /** 2 byte */
    private var h0 = L1CtlH0()
    /** 4 byte */
    private var h1 = L1CtlH1()
    private var tchMode: Byte = 0
    private var audioMode: Byte = 5
    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(146)
        byteBuffer.put(hdr.buildRawDataWithoutPayload())
        byteBuffer.put(infoUl.buildRawData())
        byteBuffer.put(tsc.toByte())
        byteBuffer.put(h.toByte())
        byteBuffer.put(h0.buildRawData())
        byteBuffer.put(h1.buildRawData())
        byteBuffer.put(tchMode)
        byteBuffer.put(audioMode)
        return byteBuffer.array()
    }

    init {
        hdr.len = 144
        infoUl.chanNr = chanNr.toUByte()
        infoUl.linkId = 0u
        h0.bandArfcn = bandArfcn
    }


}