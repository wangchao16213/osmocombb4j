package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.utils.BytesUtils
import java.nio.ByteBuffer
import java.util.ArrayList
import java.util.Arrays

/**
 * 功率测量确认包
 * 功率测量确认包频点测量元素
 *
 *
 * PM MEAS: ARFCN=0, 48   dBm at baseband, -89  dBm at RF   pm1 = 48 16进制对应15
 * 测试真实基站的arfcn为53 中国移动
 */
class PmConfPacket : ProtoPacket {
    var hdr = L1ctlPacket(L1CtlProto.L1CTL_PM_CONF)
    private var pmInfos: MutableList<PmInfoPacket> = ArrayList()
    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(14)
        byteBuffer.put(hdr.buildRawDataWithoutPayload())
        return byteBuffer.array()
    }

    constructor(rawData: ByteArray) {
        hdr.len = BytesUtils.b2s(rawData, 0)
        hdr.msgType = rawData[2]
        hdr.flags = rawData[3]
        hdr.padding0 = rawData[4]
        hdr.padding1 = rawData[5]
        hdr.payloadRawData = rawData.copyOfRange(6, rawData.size)

        //收到的数据包长度-l1ctr头部长度 然后除4 然后获取pm的个数
        val pminfoLen = (rawData.size - 6) / 4
        for (i in 0..pminfoLen) {
            pmInfos.add(PmInfoPacket(Arrays.copyOfRange(hdr.payloadRawData, i * 4, 4 * i + 4)))
        }
    }

    constructor() {
        hdr.msgType = L1CtlProto.L1CTL_PM_CONF
    }

    override fun toString(): String {
        return "PmConfPacket{" +
                "hdr=" + hdr +
                ", pmInfos=" + pmInfos +
                '}'
    }

    fun getPmInfos(): List<PmInfoPacket> {
        return pmInfos
    }

    fun setPmInfos(pmInfos: MutableList<PmInfoPacket>) {
        this.pmInfos = pmInfos
    }
}