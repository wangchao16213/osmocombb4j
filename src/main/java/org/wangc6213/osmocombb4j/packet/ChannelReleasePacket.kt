package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil

/**
 * 基站下行拆除链路请求
 * tip: 03 00 00 00 51 00 00 33 00 17 d3 1d 37 00 00 00 03 cc 0d 06 0d 00 2b 5f 47 78 32 a2 95 b9 af 32 fc 15 81 cf 20 54 18
 */
class ChannelReleasePacket(rawData: ByteArray) : DlIframePacket(rawData) {
    val pd: UByte
    val msgType: UByte
    val rrCause: UByte
    val baRangeBytes: ByteArray

    init {
        this.pd = super.data[0].toUByte()
        this.msgType = super.data[1].toUByte()
        this.rrCause = super.data[2].toUByte()
        this.baRangeBytes = super.data.copyOfRange(3, 9)
    }


    /**
     * 是否正常拆除链路
     */
    fun isNormalRRCause(): Boolean {
        if (rrCause.toInt() == 0) {
            return true
        }
        return false
    }
}