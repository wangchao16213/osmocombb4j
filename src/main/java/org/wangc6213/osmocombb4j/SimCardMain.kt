package org.wangc6213.osmocombb4j

import cn.hutool.core.util.HexUtil
import org.newsclub.net.unix.AFUNIXSocket
import org.wangc6213.osmocombb4j.packet.sim.SelectSimReqPacket
import org.wangc6213.osmocombb4j.utils.PacketUtils

class SimCardMain(var afunixSocket: AFUNIXSocket) {
    fun simreq() {
        val selectSimReqPacket = SelectSimReqPacket()
        afunixSocket.outputStream.write(selectSimReqPacket.buildRawData())
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }
    }

    fun simreq2() {
        val bytes = byteArrayOf(0x00, 0x09, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xc0.toByte(), 0x00, 0x00, 0x17)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }
    }

    fun simreq3() {
        val bytes =
            byteArrayOf(0x00, 0x0b, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xa4.toByte(), 0x00, 0x00, 0x02, 0x7f, 0x20)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }
    }


    fun simreq4() {
        val bytes = byteArrayOf(0x00, 0x09, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xc0.toByte(), 0x00, 0x00, 0x17)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }
    }

    fun simreq5() {
        val bytes = byteArrayOf(
            0x00, 0x19, 0x16, 0x00, 0x00, 0x00,
            0xa0.toByte(),
            0x88.toByte(), 0x00, 0x00, 0x10,
            0x87.toByte(), 0x0f, 0x70, 0x4d, 0xc6.toByte(), 0xa0.toByte(), 0x22, 0x73, 0x79, 0x11,
            0xba.toByte(), 0x1e, 0xd7.toByte(), 0x8e.toByte(), 0xd0.toByte(), 0x53
        )
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }
    }


    fun simreq6() {
        val bytes = byteArrayOf(0x00, 0x09, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xc0.toByte(), 0x00, 0x00, 0x0c)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }
    }

    fun simreq7(){
        val bytes = byteArrayOf(0x00,0x0b,0x16,0x00,0x00,0x00, 0xa0.toByte(), 0xa4.toByte(),0x00,0x00,0x02,0x6f,0x20)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
            break
        }

    }

    fun start() {
        simreq()
        simreq2()
        simreq3()
        simreq4()
        simreq5()
        simreq6()
        simreq7()
    }
}

