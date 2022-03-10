package org.wangc6213.osmocombb4j.packet

import kotlin.experimental.and
import kotlin.experimental.or

class ImmediateAssignmentPacket(val rawdata: ByteArray) : ProtoPacket() {
    /** GSM04.08    10.5.2.19  1byte */
    private var l2PseudoLength: Byte = 0

    /** GSM04.08    10.2  1/2byte */
    private var pd: Byte = 0

    /** GSM04.08    10.3.1  1/2byte */
    private var skipIndicator: Byte = 0

    /** GSM04.08    10.4  1byte */
    private var messageType: Byte = 0

    /** GSM04.08    10.5.2.26  1/2byte */
    private var pageMode: Byte = 0

    /** GSM04.08    10.5.2.25b  1/2byte */
    private var dedicatedModeOrTBF: Byte = 0

    //TODO 这里需要判断信道 该消息体出现在 SACCH
    /** GSM04.08    10.5.2.5  3byte */
    private var channelDescription: Int = 0
    //TODO 这里需要判断信道 该消息体出现在 PDCH
    /** GSM04.08    10.5.2.25a  3byte */
    private var packetChannelDescription: Int = 0

    /** GSM04.08    10.5.2.30  3byte */
    var requestReference: Int = 0

    /** GSM04.08    10.5.2.40  1byte */
    var timingAdvance: Byte = 0

    /** GSM04.08    10.5.2.21  1~9byte */
    private var mobileAllocation: ByteArray = byteArrayOf()


    init {
        l2PseudoLength = rawdata[0]
        pd = rawdata[1].toUInt().shr(4).toByte()
        skipIndicator = rawdata[1].and(0x0f)
        messageType = rawdata[2]
        pageMode = rawdata[3].toUInt().shr(4).toByte()
        dedicatedModeOrTBF = rawdata[3].and(0x0f)
        //TODO channelDescription和 packetChannelDescription需要判断消息体
        channelDescription = rawdata[4].or(rawdata[5]).or(rawdata[6]).toInt()
        //packetChannelDescription = rawdata[7].or(rawdata[8]).or(rawdata[9]).toInt()
        requestReference = rawdata[7].or(rawdata[8]).or(rawdata[9]).toInt()
        timingAdvance = rawdata[10]
        if (rawdata.size > 10) {
            mobileAllocation = rawdata.copyOfRange(11, rawdata.size)
        }
    }

    fun getRa(): UByte {
        //TODO 获取RA
        return rawdata[7].toUByte()
    }

    fun getChanNr(): Byte {
        return rawdata[4].toByte()
    }

    fun getTsc(): Byte {
        return rawdata[5].toUInt().shr(5).toByte()
    }


    override fun buildRawData(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "ImmediateAssignmentPacket(l2PseudoLength=$l2PseudoLength, pd=$pd, skipIndicator=$skipIndicator, messageType=$messageType, pageMode=$pageMode, dedicatedModeOrTBF=$dedicatedModeOrTBF, channelDescription=$channelDescription, packetChannelDescription=$packetChannelDescription, requestReference=$requestReference, timingAdvance=$timingAdvance, mobileAllocation=${mobileAllocation.contentToString()})"
    }
}