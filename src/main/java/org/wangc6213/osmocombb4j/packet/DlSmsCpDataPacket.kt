package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil
import org.wangc6213.osmocombb4j.utils.DebugHelper
import java.nio.charset.Charset

/**
 * 下行接收短信
 */
class DlSmsCpDataPacket(rawData: ByteArray) : DlIframePacket(rawData) {
    val pd: Byte
    val msgType: Byte
    val cpDataLength: Byte
    val rpMessageType: Byte
    val rpMessageRef: Byte
    val rpOriginatorAddressLength: UByte
    val rpExtension: Byte
    val calledPartyBcdNumBytes: ByteArray
    val rpDestAddress: Byte
    val rpUserDataLength: UByte

    //SMS DELIVER/SUBMIT
    val tpRp: Byte
    val tpOrientationAddressLength: UByte
    val tpExtension: Byte

    //发送者手机号字节
    val msIsdnBytes: ByteArray
    val tpPid: Byte
    val tpDcs: Byte
    val tpServiceCentreTimeStampBytes: ByteArray
    val tpUserDataLength: UByte
    val tpUserData: ByteArray

    init {

        println("下行短信包:${DebugHelper.getPrintFrameContent(rawData)}")

        pd = super.data[0]
        msgType = super.data[1]
        cpDataLength = super.data[2]

        /*** GSM A-I/F RP - RP-DATA (Network to MS) ***/
        rpMessageType = super.data[3]
        rpMessageRef = super.data[4]
        rpOriginatorAddressLength = super.data[5].toUByte()
        rpExtension = super.data[6]
        calledPartyBcdNumBytes = data.copyOfRange(7, 7 + rpOriginatorAddressLength.toInt() - 1)
        rpDestAddress = data[7 + rpOriginatorAddressLength.toInt() - 1]
        rpUserDataLength = data[7 + rpOriginatorAddressLength.toInt()].toUByte()

        /*** GSM SMS TPDU (GSM 03.40) SMS-DELIVER ***/
        val smsTpuData = data.copyOfRange(7 + rpOriginatorAddressLength.toInt() + 1, data.size)
        tpRp = smsTpuData[0]
        tpOrientationAddressLength = smsTpuData[1].toUByte()
        tpExtension = smsTpuData[2]
        var index = (tpOrientationAddressLength.toInt() / 2) + (tpOrientationAddressLength.toInt() % 2)
        msIsdnBytes = smsTpuData.copyOfRange(3, 3 + index)


        var startIndex = 3 + index

        tpPid = smsTpuData[startIndex]
        tpDcs = smsTpuData[startIndex + 1]
        tpServiceCentreTimeStampBytes = smsTpuData.copyOfRange(startIndex + 2, startIndex + 9)
        tpUserDataLength = smsTpuData[startIndex + 9].toUByte()
        tpUserData = smsTpuData.copyOfRange(startIndex + 10, startIndex + 10 + tpUserDataLength.toInt())


    }


    /**
     * 获取短信明文
     */
    fun getSmsText(): String {
        return String(tpUserData, Charset.forName("utf-16"))
    }

    /**
     * 获取发送者手机号
     */
    fun msIsdn(): String {
        var result = ""
        HexUtil.encodeHexStr(msIsdnBytes).chunked(2).forEach { result += it.reversed() }
        return result.substring(0, result.length - 1)
    }

    fun getTIO(): Int {
        return pd.toUByte().toInt().and(0x70).shr(4)
    }

}