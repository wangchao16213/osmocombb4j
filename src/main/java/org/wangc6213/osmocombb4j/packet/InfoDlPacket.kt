package org.wangc6213.osmocombb4j.packet


import kotlin.experimental.or

/**
 * 总长度12个字节
 */
class InfoDlPacket(rawData: ByteArray) : ProtoPacket() {
    /**
     * GSM 08.58 channel number (9.3.1) 1byte
     */
    var chanNr: UByte = 0u

    /**
     * GSM 08.58 link identifier (9.3.2)
     */
    var linkId: UByte = 0u

    /**
     * the ARFCN and the band. FIXME: what about MAIO?
     */
    var bandArfcn: UShort = 0u
    var frameNr: UInt = 0u

    /**
     * 0 .. 63 in typical GSM notation (dBm+110) 1byte
     */
    var rxLevel: UByte = 0u

    /**
     * Signal/Noise Ration (dB) 1byte
     */
    var snr: UByte = 0u

    /**
     * GSM 08.58 link identifier (9.3.2)
     */
    var numBiterr: UByte = 0u

    /**
     * GSM 08.58 link identifier (9.3.2)
     */
    var fireCrc: UByte = 0u


    override fun buildRawData(): ByteArray {
        return ByteArray(0)
    }

    override fun toString(): String {
        return "InfoDlPacket{" +
                "chanNr=" + chanNr +
                ", linkId=" + linkId +
                ", bandArfcn=" + bandArfcn +
                ", frameNr=" + frameNr +
                ", rxLevel=" + rxLevel +
                ", snr=" + snr +
                ", numBiterr=" + numBiterr +
                ", fireCrc=" + fireCrc +
                '}'
    }

    init {
        chanNr = rawData[0].toUByte()
        linkId = rawData[1].toUByte()
        bandArfcn = rawData[2].or(rawData[3]).toUShort()
        frameNr = rawData[4].or(rawData[5]).or(rawData[6]).or(rawData[7]).toUInt()
        rxLevel = rawData[8].toUByte()
        snr = rawData[9].toUByte()
        numBiterr = rawData[10].toUByte()
        fireCrc = rawData[11].toUByte()
    }
}