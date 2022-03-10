package org.wangc6213.osmocombb4j.handle

import cn.hutool.core.util.HexUtil
import cn.hutool.http.HttpRequest
import cn.hutool.json.JSONObject
import cn.hutool.setting.Setting
import org.newsclub.net.unix.AFUNIXSocket
import org.wangc6213.osmocombb4j.common.L1CtlProto
import org.wangc6213.osmocombb4j.packet.sim.SelectSimReqPacket
import org.wangc6213.osmocombb4j.utils.PacketUtils
import java.nio.ByteBuffer
import java.util.*

class GetSresHandle(var rand: String, var afunixSocket: AFUNIXSocket) {
    private var sres: String = ""
    private fun simreq() {
        val selectSimReqPacket = SelectSimReqPacket()
        afunixSocket.outputStream.write(selectSimReqPacket.buildRawData())
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                break
            }

        }
    }

    private fun simreq2() {
        val bytes = byteArrayOf(0x00, 0x09, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xc0.toByte(), 0x00, 0x00, 0x17)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                break
            }
        }
    }

    private fun simreq3() {
        val bytes =
            byteArrayOf(0x00, 0x0b, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xa4.toByte(), 0x00, 0x00, 0x02, 0x7f, 0x20)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                break
            }
        }
    }


    private fun simreq4() {
        val bytes = byteArrayOf(0x00, 0x09, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xc0.toByte(), 0x00, 0x00, 0x17)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                break
            }
        }
    }

    private fun simreq5() {
        val bb = ByteBuffer.allocate(11 + 16)
        val bytes = byteArrayOf(
            0x00, 0x19, 0x16, 0x00, 0x00, 0x00,
            0xa0.toByte(),
            0x88.toByte(), 0x00, 0x00, 0x10
        )
        bb.put(bytes)
        bb.put(HexUtil.decodeHex(rand))
        afunixSocket.outputStream.write(bb.array())
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                break
            }
        }
    }


    private fun simreq6() {
        val bytes = byteArrayOf(0x00, 0x09, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xc0.toByte(), 0x00, 0x00, 0x0c)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                sres = HexUtil.encodeHexStr(Arrays.copyOfRange(l1ctlPacket.rawData, 6, 10)).chunked(2).joinToString("")
                break
            }
        }
    }

    fun simreq7() {
        val bytes =
            byteArrayOf(0x00, 0x0b, 0x16, 0x00, 0x00, 0x00, 0xa0.toByte(), 0xa4.toByte(), 0x00, 0x00, 0x02, 0x6f, 0x20)
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
        while (true) {
            val l1ctlPacket = PacketUtils.readToL1Packet(afunixSocket.inputStream)
            if (l1ctlPacket.rawData[2] == L1CtlProto.L1CTL_SIM_CONF) {
//                println(HexUtil.encodeHexStr(l1ctlPacket.rawData).chunked(2).joinToString(" "))
                break
            }
        }
    }


    fun getSres(): String {
        println("当前rand为:${rand},开始进行计算SRES")
//        val setting = Setting("bb.setting")
//
//        val result = HttpRequest.get(setting.getStr("baseapi") + "/setrand/${rand}").execute().body()
//        if (JSONObject(result).getInt("code") == 0){
//            println("setrand访问失败")
//        }
//        while (true){
//            val result2 = HttpRequest.get(setting.getStr("baseapi") + "/getSres").execute().body()
//            if (JSONObject(result2).getInt("code") == 1){
//                HttpRequest.get(setting.getStr("baseapi") + "/reset").execute().body()
//                sres = JSONObject(result2).getStr("data").lowercase()
//                break
//            }
//        }
        simreq()
        simreq2()
        simreq3()
        simreq4()
        simreq5()
        simreq6()
        simreq7()
        println("sres计算完毕,值为:${sres}")
        return sres
    }
}