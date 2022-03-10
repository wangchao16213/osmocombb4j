package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil


/**
 * 基站位置更新接受
 */
class LocUpdAcceptPacket(rawData: ByteArray) : DlIframePacket(rawData) {
    val pd: UByte
    val msgType: UByte
    val laiBytes: ByteArray
    val mobileIdentityBytes: ByteArray

    init {
        this.pd = super.data[0].toUByte()
        this.msgType = super.data[1].toUByte()
        this.laiBytes = super.data.copyOfRange(2, 7)
        this.mobileIdentityBytes = super.data.copyOfRange(7, 14)
    }

    fun getTmsi(): String {
        return HexUtil.encodeHexStr(this.mobileIdentityBytes.copyOfRange(3, mobileIdentityBytes.size))
    }
}