package org.wangc6213.tests.test2

import cn.hutool.core.util.ArrayUtil
import cn.hutool.core.util.HexUtil
import org.wangc6213.osmocombb4j.packet.DataIndPacket
import org.wangc6213.osmocombb4j.packet.DlSmsCpDataPacket
import org.wangc6213.osmocombb4j.utils.DebugHelper
import org.wangc6213.osmocombb4j.utils.PacketUtils
import java.util.*

class TestMultiFrame {

    var bytesList =
        mutableListOf(
            HexUtil.decodeHex("00 27 03 00 00 00 59 00 00 35 00 18 ef d0 36 00 00 00 0f 00 53 09 01 33 01 01 08 91 68 31 08 10 00 05 f0 00 26 24 0d 91 68"),
            HexUtil.decodeHex("00 27 03 00 00 00 59 00 00 35 00 18 ef d0 36 00 00 00 0f 02 53 81 15 65 70 90 f8 00 08 12 21 60 81 63 23 23 12 55 4a 55 4a"),
            HexUtil.decodeHex("00 27 03 00 00 00 59 00 00 35 00 18 ef d0 36 00 00 00 0f 04 39 55 4a 55 4a 55 4a 55 4a 55 4a 55 4a 55 4a 2b f5 ba c7 33 12")
        )

    var segmentIframePacket: DataIndPacket? = null

    fun doHandle() {
        for (rawData in bytesList) {
            val l1ctlPacket = PacketUtils.readToL1Packet(rawData)
            var dataIndPacket = DataIndPacket(l1ctlPacket.rawData)
            processData(dataIndPacket)
        }
    }

    fun processData(dataIndPacket1: DataIndPacket) {
        var dataIndPacket = dataIndPacket1
        if (dataIndPacket.data[2].toUByte().toInt().and(0x03) == 0x03) {
            if (segmentIframePacket == null) {
                //第一次收到分段数据
                segmentIframePacket = dataIndPacket
            } else {
                segmentIframeDataMerge(dataIndPacket)
                println(DebugHelper.getPrintFrameContent(segmentIframePacket!!.data))
            }
        } else {
            if (segmentIframePacket != null) {
                //处于分段当中 并且接收到最后一个分段
                segmentIframeDataMerge(dataIndPacket)
                dataIndPacket = segmentIframePacket as DataIndPacket
                println(DebugHelper.getPrintFrameContent(segmentIframePacket!!.data))
                segmentIframePacket = null

            }
            println(DebugHelper.getPrintFrameContent(dataIndPacket.data))
            val dlSmsCpDataPacket = DlSmsCpDataPacket(dataIndPacket.buildRawData())
            println(dlSmsCpDataPacket.getSmsText())
        }
    }

    fun segmentIframeDataMerge(dataIndPacket: DataIndPacket) {
        //Address Contorl Length字节数组
        val aclBytes = dataIndPacket.data.copyOfRange(0, 3)
        //暂存的数据
        val oldDataBytes = Arrays.copyOfRange(segmentIframePacket!!.data, 3, segmentIframePacket!!.data.size)
        val newDataBytes = dataIndPacket.data.copyOfRange(3, dataIndPacket.data.size)
        segmentIframePacket!!.data = ArrayUtil.addAll(aclBytes, oldDataBytes, newDataBytes)
    }


}


fun main() {
    TestMultiFrame().doHandle()
}