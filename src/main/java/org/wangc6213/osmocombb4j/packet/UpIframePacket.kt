package org.wangc6213.osmocombb4j.packet


import java.nio.ByteBuffer

/**
 * 上行 通过构造函数创建的是由MS进行发起的 所以address是0x01(短消息除外)
 */
open class UpIframePacket() : ProtoPacket() {
    var len = 0x1f.toShort()
    val bytes1 = byteArrayOf(0x06, 0x00, 0x00, 0x00)
    open var chanNr: Byte = 0
    var bytes2 = byteArrayOf(0x00, 0x00, 0x00)
    var address: Byte = 0x00
    var control: Byte = (0x00).toByte()
    var length: Byte = 0x00
    var data: ByteArray = byteArrayOf()

    constructor(sapi: Int = 0, chanNr: Byte, ns: Int, nr: Int) : this() {
        if (sapi == 0) {
            address = 0x01
        } else if (sapi == 3) {
            //短消息
            address = 0x0d
            bytes2 = byteArrayOf(0x03, 0x00, 0x00)
        }
        this.chanNr = chanNr

        //收到的NR作为发送序列号
        val nsBstr = fillNvarBStr(Integer.toBinaryString(if (ns > 7) 0 else ns))
        val nrBstr = fillNvarBStr(Integer.toBinaryString(nr))
        control = (nrBstr + "0" + nsBstr + "0").toInt(2).toByte()
    }


    fun fillControl(nr: Int, ns: Int) {
        //收到的NR作为发送序列号
        val nsBstr = fillNvarBStr(Integer.toBinaryString(if (ns > 7) 0 else ns))
        val nrBstr = fillNvarBStr(Integer.toBinaryString(nr))
        control = (nrBstr + "0" + nsBstr + "0").toInt(2).toByte()
    }

    /**
     * 填充NS或者NR为三位二进制字符串
     */
    fun fillNvarBStr(nvarBStr: String): String {
        var result = nvarBStr
        if (result.length < 3) {
            val strlenth = 3 - result.length
            for (i in 0 until strlenth) {
                result = "0$result"
            }
        }
        return result
    }


    /**
     * 获取当前序列号
     */
    fun getNs(): Int {
        return control.toInt().and(0x0e).shr(1)
    }

    fun getNr(): Int {
        return control.toInt().shr(5)
    }

    override fun buildRawData(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(0x1f + 2)
        byteBuffer.putShort(len)
        byteBuffer.put(bytes1)
        byteBuffer.put(chanNr)
        byteBuffer.put(bytes2)
        byteBuffer.put(address)
        byteBuffer.put(control)
        byteBuffer.put(length)
        byteBuffer.put(data)
        return byteBuffer.array()
    }
}