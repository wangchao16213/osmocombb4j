package org.wangc6213.tests

import cn.hutool.core.util.HexUtil
import org.junit.Test
import org.wangc6213.osmocombb4j.packet.DlSmsCpDataPacket
import java.nio.charset.Charset


class Parent {
    var pa: Int

    constructor(a: Int) {
        pa = a
        println("执行了构造函数 ${pa}")
    }

    constructor(p1: Int, p2: Int) {
        pa = 20
        println("执行了构造函数 ${pa}")
    }
}

class Tests2 {
    @Test
    fun test1() {
        Parent(10)
        Parent(20, 30)
    }


    @Test
    fun test2() {
        var byteArray = mutableListOf<Byte>()
        var dump = """
            0020   53 84 23 7e 30 10 81 7e 8b af 79 d1 62 80 30 11
            0030   4f 60 6b 63 57 28 76 7b 5f 55 5f ae 4f e1 ff 0c
            0040   9a 8c 8b c1 78 01 00 37 00 35 00 34 00 31 00 30
            0050   00 32 30 02 8f 6c 53 d1 53 ef 80 fd 5b fc 81 f4
            0060   5e 10 53 f7 88 ab 76 d7 30 02 59 82 67 9c 8f d9
            0070   4e 0d 66 2f 4f 60 67 2c 4e ba 64 cd 4f 5c ff 0c
            0080   56 de 59 0d 00 4a 00 5a 53 ef 96 3b 6b 62 8b e5
            0090   75 28 62 37 76 7b 5f 55 4f 60 76 84 5f ae 4f e1
            00a0   30 02
        """.trimIndent()
        val lines = dump.split("\n")
        for (line in lines) {
            val bytesStr = line.split("   ")[1]
            bytesStr.split(" ").forEach {
                byteArray.add(Integer.parseInt(it, 16).toByte())
            }

        }
        val smsTextBytes = byteArray.toByteArray().copyOfRange(4,byteArray.size)

        println(String(smsTextBytes, Charset.forName("utf-16")));
    }


    @Test
    fun test3(){
        val str = "00 27 03 00 00 00 59 00 00 35 00 18 ef d0 36 00 00 00 0f 00 53 09 01 33 01 01 08 91 68 31 08 10 00 05 f0 00 26 24 0d 91 68 81 15 65 70 90 f8 00 08 12 21 60 81 63 23 23 12 55 4a 55 4a 55 4a 55 4a 55 4a 55 4a 55 4a 55 4a 55 4a 2b f5 ba c7 33 12"
        val array = HexUtil.decodeHex(str)
        val dlSmsCpDataPacket = DlSmsCpDataPacket(array)
        println(dlSmsCpDataPacket.msIsdn())
        println(dlSmsCpDataPacket.getSmsText())
    }
}
