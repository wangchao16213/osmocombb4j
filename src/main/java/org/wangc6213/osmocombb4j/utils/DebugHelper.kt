package org.wangc6213.osmocombb4j.utils

import cn.hutool.core.util.HexUtil
import org.newsclub.net.unix.AFUNIXSocket
import org.wangc6213.osmocombb4j.common.L1CtlProto

object DebugHelper {
    fun printL1ctlMsgType(msgType: Int) {
        L1CtlProto::class.java.declaredFields.forEach { field -> val field1 = field }
        if (L1CtlProto.L1CTL_FBSB_CONF.toInt() == msgType) {
            println("频率校正请求")
        }
    }


    fun sendData(afunixSocket: AFUNIXSocket, bytes: ByteArray) {
        afunixSocket.outputStream.write(bytes)
        afunixSocket.outputStream.flush()
    }

    fun getPrintFrameContent(bytes: ByteArray): String {
        return HexUtil.encodeHexStr(bytes).chunked(2).joinToString(" ")
    }
}