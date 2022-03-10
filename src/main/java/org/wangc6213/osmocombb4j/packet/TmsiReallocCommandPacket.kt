package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil

class TmsiReallocCommandPacket(rawData: ByteArray) : DlIframePacket(rawData) {
    val pd: Byte
    val msgType: Byte
    val laiBytes: ByteArray
    val mobileIdentityBytes: ByteArray

    init {
        pd = super.data[0]
        msgType = super.data[1]
        laiBytes = super.data.copyOfRange(2, 7)
        val mobileIdentityLenth = rawData[7].toUInt()
        mobileIdentityBytes = rawData.copyOfRange(8, 8 + mobileIdentityLenth.toInt())
    }


    fun getTmsi(): String {
        return HexUtil.encodeHexStr(mobileIdentityBytes)
    }
}