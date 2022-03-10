package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.common.L1CTL_FBSB_F_FB
import java.nio.ByteBuffer

/**
 * """
 * 频率频率校正同步请求
 * tip: 00 11 + 01 00 00 00 + 00 35 + 00 64 + 27 10 + 03 20 + 03 + 07 + 00 + 00 + 35
 * """
 */
class FbsbReqPacket(bandArfcn: Short, var ccchModel: UByte = 0u) : ProtoPacket() {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_FBSB_REQ)
    private val bandArfcn: UShort
    private val timeout: UShort
    private val freqErrThresh1: UShort
    private val freqErrThresh2: UShort
    private val numFreqerrAvg: UByte
    private val flags: UByte
    var syncInfoIdx: UByte
    private val rxlevExp: UByte
    override fun buildRawData(): ByteArray {
        val hdrBytes = hdr.buildRawDataWithoutPayload()
        val byteBuffer = ByteBuffer.allocate(19)
        byteBuffer.put(hdrBytes)
        byteBuffer.putShort(bandArfcn.toShort())
        byteBuffer.putShort(timeout.toShort())
        byteBuffer.putShort(freqErrThresh1.toShort())
        byteBuffer.putShort(freqErrThresh2.toShort())
        byteBuffer.put(numFreqerrAvg.toByte())
        byteBuffer.put(flags.toByte())
        byteBuffer.put(syncInfoIdx.toByte())
        byteBuffer.put(ccchModel.toByte())
        byteBuffer.put(rxlevExp.toByte())
        return byteBuffer.array()
    }

    override fun toString(): String {
        return "FbsbReqPacket(ccchModel=$ccchModel, hdr=$hdr, bandArfcn=$bandArfcn, timeout=$timeout, freqErrThresh1=$freqErrThresh1, freqErrThresh2=$freqErrThresh2, numFreqerrAvg=$numFreqerrAvg, flags=$flags, syncInfoIdx=$syncInfoIdx, rxlevExp=$rxlevExp)"
    }

    init {
        hdr.len = 17.toShort()
        this.bandArfcn = bandArfcn.toUShort()
        timeout = 100u
        freqErrThresh1 = 10000u
        freqErrThresh2 = 800u
        numFreqerrAvg = 3u
        flags = L1CTL_FBSB_F_FB.L1CTL_FBSB_F_FB01SB.toUByte()
        syncInfoIdx = 3u
        rxlevExp = 53u
    }


}