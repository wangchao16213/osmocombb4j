package org.wangc6213.osmocombb4j.packet

import org.wangc6213.osmocombb4j.common.L1CtlProto
import java.nio.ByteBuffer
import kotlin.random.Random

/**
 * 随机访问请求
 * tip: 00 0c 04 00 00 00 00 00 00 00 09 00 00 10
 */
class RachReqPacket(var isFirst: Boolean = true) : L1ctlPacket() {
    private val hdr = L1ctlPacket(L1CtlProto.L1CTL_RACH_REQ)
    private val infoUl = InfoUlPacket()
    var ra: UByte = (0..31).random().toUByte()
    private val combined: UByte = 0u

    /**
     * offset的计算方法:
     * tx_integer为32
     * ccch_conf为是否combien
     * slots初始值 第一次随机访问是0 后面随机访问是217
     * slots = (random() % tx_integer) + slots
     * v1 = (slots >> 8)| (ccch_conf == 1) << 7
     * offset = ((v1 & 0x7f) << 8) | slots
     *
     */
    private var offset: UShort = 0u

    fun initOffset() {
        var slots = 0
        if (!isFirst) {
            slots = 217
        }
        val randomvalue = Random.nextInt()
        slots += (randomvalue % 32)
        val v1 = slots.shr(8).or((0).shl(7))
        val offset1 = (v1.and(0x7f).shl(8)).or(slots)
        this.offset = offset1.toUShort()
    }

    override fun buildRawData(): ByteArray {
        val hdrBytes = hdr.buildRawDataWithoutPayload()
        val byteBuffer = ByteBuffer.allocate(14)
        byteBuffer.put(hdrBytes)
        byteBuffer.put(infoUl.buildRawData())
        byteBuffer.put(ra.toByte())
        byteBuffer.put(combined.toByte())
        byteBuffer.putShort(offset.toShort())
        return byteBuffer.array()
    }

    init {
        hdr.len = 0x0c
        initOffset()
    }


}