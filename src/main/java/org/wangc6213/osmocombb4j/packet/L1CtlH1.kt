package org.wangc6213.osmocombb4j.packet

import java.nio.ByteBuffer

class L1CtlH1 : ProtoPacket() {
    var hsn: UByte = 0u
    var maio: UByte = 0u

    //    var n: UByte = 0u
//    var padding = byteArrayOf(0x00)
    var ma: Array<Short> = Array(64, { 0 })
    override fun buildRawData(): ByteArray {
        //TODO 为什么不应该是132
        val byteBuffer = ByteBuffer.allocate(130)
        byteBuffer.put(hsn.toByte())
        byteBuffer.put(maio.toByte())
        //TODO 这里有点问题 需要确定下数据结构
//        byteBuffer.put(n.toByte())
//        byteBuffer.put(padding)
        ma.forEach {
            byteBuffer.putShort(it)
        }
        return byteBuffer.array()
    }

}