package org.wangc6213.osmocombb4j.model

class MobileStationEntity {
    /** 测量开始频点号 */
    var bandArfcnFrom = 50u

    /** 测量结束频点号 */
    var bandArfcnTo = 59u

    /** 使用频点号 */
    var bandArfcn: Short = 53
    var ra: UByte = 0u
    var chanNr: Byte = 0
    var ta: Byte = 1
    var tsc: Byte = 0

    //    var lac: Short = 29649
    var lac: Short = 29649
    var tmsi: String = "43caa065"
    var rand: String = ""
    var laiBytes = byteArrayOf(0x64, 0xf0.toByte(), 0x00, 0x73, 0xd1.toByte())
}