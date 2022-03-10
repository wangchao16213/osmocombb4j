package org.wangc6213.tests

import cn.hutool.core.util.HexUtil
import cn.hutool.http.HttpRequest
import cn.hutool.setting.Setting
import org.junit.Test
import org.wangc6213.osmocombb4j.packet.LocUpdAcceptPacket
import org.wangc6213.tests.models.LogDumpModel


class Tests {
    @Test
    fun test1() {
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
        var slots = 217
        var randomvalue = 361067946
        slots += (randomvalue % 32)
        println(slots)
        var v1 = slots.shr(8).or((0).shl(7))
        var offset = (v1.and(0x7f).shl(8)).or(slots)
        println(offset)
    }

    @Test
    fun test2() {
        for (i in 0..100) {
            println((0..31).random())
        }
    }


    @Test
    fun test3() {
        var a: Byte = 1
        var b: Byte = 55
        var aa: ByteArray = byteArrayOf(a, b)

        println(HexUtil.encodeHexStr(aa).chunked(2).joinToString(" "))
    }

    @Test
    fun test4() {
        var a: Byte = 1
        var b: Byte = 55
        var aa: ByteArray = byteArrayOf(a, b)

        println(HexUtil.encodeHexStr(aa).chunked(2).joinToString(" "))
    }


    @Test
    fun test6() {
        var a: Byte = 1
        var b: Byte = 55
        var aa: ByteArray = byteArrayOf(a, b)

        println(HexUtil.encodeHexStr(aa).chunked(2).joinToString(" "))
    }


    @Test
    fun test7() {
        //DmEstReqPacket(0x41.toByte(), 0x04.toByte().toUByte(), 51.toShort().toUShort()).buildRawData()
    }

    @Test
    fun test8() {
        var rand = "9411187dfc257fb3cc9615baf0a9935f"
        println(HexUtil.decodeHex(rand))
        println(HexUtil.decodeHex(rand))
        var list = rand.chunked(2)
//        list.forEach {
//            println(HexUtil.decodeHex())
//        }

    }


    @Test
    fun test9() {
        val nr: UInt = 7u
        var nrBstr = Integer.toBinaryString(nr.toInt())
        println(nrBstr)
    }


    @Test
    fun test10() {
        val a = 34 and 0x0e
        val b = a shr 1
        println(b)
    }


    @Test
    fun test11() {
        var a: UByte = (0x81).toUByte()
        println(a.toInt().shr(5))
    }


    @Test
    fun test12() {
        val log = """
收到的帧:===03 20 0d 05 18 01 2b 07 67 42 aa b1 bf 34 70 17 64 0e ab 44 7f 73 60
发送队列帧:===00 1f 06 00 00 00 51 00 00 00 01 22 2d 05 19 08 8a 36 47 01 74 46 62 92 2b 2b 2b 2b 2b 2b 2b 2b 2b
收到的帧:===03 42 0d 05 18 02 2b 6a a5 ef 3b 0c 13 45 cd f3 15 85 cf 23 14 19 cf
发送队列帧:===00 1f 06 00 00 00 51 00 00 00 01 44 2d 05 19 08 8a 36 47 01 74 46 62 92 2b 2b 2b 2b 2b 2b 2b 2b 2b
收到的帧:===03 64 0d 05 18 01 2b 90 4a ec 37 0d d6 85 9e e3 28 49 de b6 d8 76 da
发送队列帧:===00 1f 06 00 00 00 51 00 00 00 01 66 2d 05 19 08 8a 36 47 01 74 46 62 92 2b 2b 2b 2b 2b 2b 2b 2b 2b
收到的帧:===03 86 0d 06 0d 00 2b 99 b0 6a f4 2f 07 5c 02 b9 c1 b2 d0 75 9c 27 29
发送S确认帧:===00 1f 06 00 00 00 51 00 00 00 03 81 01 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b 2b
        """.trimIndent()

        log.split("\n").forEach {
            val logDumpModel = LogDumpModel(it)
            println(logDumpModel)
        }


    }


    @Test
    fun test13() {
        val rawData: ByteArray = byteArrayOf(
            0x00,
            0x27,
            0x03,
            0x00,
            0x00,
            0x00,
            0x71,
            0x00,
            0x00,
            0x2f,
            0x00,
            0x09,
            0x58,
            0xe9.toByte(),
            0x39,
            0x00,
            0x00,
            0x00,
            0x03,
            0xca.toByte(),
            0x39,
            0x05,
            0x02,
            0x64,
            0xf0.toByte(),
            0x00,
            0x73,
            0xd1.toByte(),
            0x17,
            0x05,
            0xf4.toByte(),
            0x49,
            0xc2.toByte(),
            0xfb.toByte(),
            0xa4.toByte(),
            0x2b,
            0x13,
            0x39,
            0xcd.toByte(),
            0xd2.toByte(),
            0xd5.toByte()
        )
        var locUpdAcceptPacket = LocUpdAcceptPacket(rawData)
        println(locUpdAcceptPacket)
    }


    @Test
    fun test14() {
        //b1409749 B1409749
        val setting = Setting("bb.setting")
        val result =
            HttpRequest.get(setting.getStr("baseapi") + "/setrand/03d7b5634c0f97047831354663c2bdc7").execute().body()
    }

    @Test
    fun test15() {
        println(0x09.toUByte().toInt().and(0x70).shr(4))
    }
}
