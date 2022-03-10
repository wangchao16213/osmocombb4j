package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil
import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils


/**
 * 下行寻呼响应
 * tip: 03 00 00 00 + 59 00 00 2f 00 1d 47 0e 3a 84 00 00 + 01 73 35 + 06 27 01 03 33 19 01 05 f4 37 c3 4c bc 2b 97 30 6e 94 2c 6f
 */
class DlPagingRespnsePacket(rawData: ByteArray) : ProtoPacket() {
    val hdr = L1ctlPacket(L1CtlProto.L1CTL_DATA_IND)
    val infoDlPacket: InfoDlPacket
    val address: Byte
    val control: Byte
    val length: Byte
    val pd: Byte
    val msgType: Byte
    val cipheringKeySeqNum: Byte
    val mobileStationClassmarkBytes: ByteArray
    val mobileIdentityBytes: ByteArray

    init {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)
        infoDlPacket = InfoDlPacket(rawData.copyOfRange(6, 18))
        address = rawData[18]
        control = rawData[19]
        length = rawData[20]
        pd = rawData[21]
        msgType = rawData[22]
        cipheringKeySeqNum = rawData[23]
        mobileStationClassmarkBytes = rawData.copyOfRange(24, 28)
        mobileIdentityBytes = rawData.copyOfRange(28, 34)
    }

    fun getTmsi():String {
        return HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(2,mobileIdentityBytes.size))
    }

    override fun buildRawData(): ByteArray {
        TODO("Not yet implemented")
    }
}