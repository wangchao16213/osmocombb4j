package org.wangc6213.osmocombb4j

import org.newsclub.net.unix.AFUNIXSocket
import org.newsclub.net.unix.AFUNIXSocketAddress
import org.wangc6213.osmocombb4j.model.MobileStationEntity
import java.io.File

fun main() {
    val socketFile = File("/tmp/osmocom_l2")
    val sock = AFUNIXSocket.newInstance()
    val mobileStationEntity = MobileStationEntity()
    mobileStationEntity.bandArfcn = 47
    mobileStationEntity.tmsi = "43caa065"
    try {
        sock.connect(AFUNIXSocketAddress.of(socketFile))
        if (sock.isConnected) {
            println("bb连接成功,开始启动信令中继系统")
            CampingProcedure(sock, mobileStationEntity).start()
            SMSProcedure(sock, mobileStationEntity).start()
            //println(GetSresHandle("f25a0454515ffc8b90bd26df51d61682", sock).getSres())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}