package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil
import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils

/**
 * 寻呼消息
 * tip: 03 00 00 00 90 00 00 32 00 27 e4 e0 2b 6e 0c 00 25 06 21 00 05 f4 d1 d3 1e b9 23 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b
 */
class PagingRequestPacket(rawData: ByteArray) : ProtoPacket() {
    val hdr: L1ctlPacket = L1ctlPacket(L1CtlProto.L1CTL_DATA_IND)
    val infoDlPacket: InfoDlPacket
    val l2PseudoLength: Byte
    val pd: UByte
    val msgType: UByte
    val channelNeeded: Byte
    val mobileIdentityBytes: ByteArray

    init {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        this.infoDlPacket = InfoDlPacket(rawData.copyOfRange(6, 18))
        l2PseudoLength = rawData[18]
        pd = rawData[19].toUByte()
        msgType = rawData[20].toUByte()
        channelNeeded = rawData[21]
        mobileIdentityBytes = rawData.copyOfRange(22, rawData.size)

        println("Paging类型为:" + getPagingType())
    }

    override fun buildRawData(): ByteArray {
        TODO("Not yet implemented")
    }

    /**
     * 获取寻呼类型
     */
    fun getPagingType(): Int {
        if (msgType == (0x21).toUByte()) {
            return 1
        } else if (msgType == (0x22).toUByte()) {
            return 2
        }
        return 3
    }


    fun getTmsis(): MutableList<String> {
        val list = mutableListOf<String>()

        var paginType = getPagingType()

        if (paginType == 1) {
            //Type 1 包含一个TMSI
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(2, 6)))
        } else if (paginType == 2) {
            /**
             * Type 2 最多包含3个TMSI  TMSI1 + TMSI2 + 17 05 f4 + TMSI3
             */
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(2, 6)))
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(6, 10)))
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(13, 17)))
        } else if (paginType == 3) {
            /**
             * Type 3 最多包含4个TMSI
             */
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(2, 6)))
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(6, 10)))
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(10, 14)))
            list.add(HexUtil.encodeHexStr(mobileIdentityBytes.copyOfRange(14, 18)))
        }

        return list
    }


    fun isMatch(tmsi: String): Boolean {
        return getTmsis().contains(tmsi)
    }
}