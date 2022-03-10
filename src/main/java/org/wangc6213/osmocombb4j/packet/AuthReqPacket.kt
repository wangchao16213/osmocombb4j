package org.wangc6213.osmocombb4j.packet

import cn.hutool.core.util.HexUtil
import java.util.Arrays
import org.wangc6213.osmocombb4j.exception.UnKownPacketException
import org.wangc6213.osmocombb4j.utils.BytesUtils

/**
 * 授权请求帧
 * 03 00 00 00 41 00 00 2f 00 18 a9 d7 3a d0 00 00 03 44 4d 05 12 00 87 0f 70 4d c6 a0 22 73 79 11 ba 1e d7 8e d0 53 2b
 */
class AuthReqPacket(rawData: ByteArray) : ProtoPacket() {
    var hdr: L1ctlPacket
    var infoDl: InfoDlPacket
    var address: Byte
    var control: UByte
    var length: Byte
    var pd: Byte
    var msgType: Byte
    var cipheringKeySequeuenceNumber: Byte
    var rand: ByteArray
    var nr: UInt
    var ns: UInt
    val randText: String
        get() = HexUtil.encodeHexStr(rand)

    override fun buildRawData(): ByteArray {
        return ByteArray(0)
    }




    override fun toString(): String {
        return "AuthReqPacket{" +
                "hdr=" + hdr +
                ", infoDl=" + infoDl +
                ", address=" + address +
                ", control=" + control +
                ", length=" + length +
                ", pd=" + pd +
                ", msgType=" + msgType +
                ", cipheringKeySequeuenceNumber=" + cipheringKeySequeuenceNumber +
                ", rand=" + Arrays.toString(rand) +
                ", nr=" + nr +
                ", ns=" + ns +
                '}'
    }

    init {
        /**
         * 判断是否是授权请求  AUTH_REQUEST 0X010010
         */
        if (rawData.size < 23 || (rawData[22].toUByte().toInt().and(0x12) != 0x12)) {
            throw UnKownPacketException()
        } else {
            hdr = L1ctlPacket()
            hdr.len = BytesUtils.b2s(rawData, 0)
            hdr.msgType = rawData[2]
            hdr.flags = rawData[3]
            hdr.padding0 = rawData[4]
            hdr.padding1 = rawData[5]
            infoDl = InfoDlPacket(rawData.copyOfRange(6, 18))
            address = rawData[18]
            control = rawData[19].toUByte()
            length = rawData[20]
            pd = rawData[21]
            msgType = rawData[22]
            cipheringKeySequeuenceNumber = rawData[23]
            rand = rawData.copyOfRange(24, 24 + 16)
            nr = control.toUInt().shr(5)
            ns = control.toUInt().shl(28).shr(29)
        }
    }
}