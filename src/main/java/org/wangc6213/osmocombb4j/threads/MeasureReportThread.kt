package org.wangc6213.osmocombb4j.threads

import org.newsclub.net.unix.AFUNIXSocket
import org.wangc6213.osmocombb4j.packet.MeasReportPacket
import java.util.*

class MeasureReportThread(val afunixSocket: AFUNIXSocket, val chanNr: Byte, val ta: Byte) : Thread() {

    override fun run() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val testMeasReportPacket = MeasReportPacket(chanNr, ta)
                afunixSocket.outputStream.write(testMeasReportPacket.buildRawData())
                afunixSocket.outputStream.flush()
                //println("已经进行测量报告")
            }
        }, Date(), 1000)
//        val testMeasReportPacket = MeasReportPacket(chanNr, ta)
//        afunixSocket.outputStream.write(testMeasReportPacket.buildRawData())
//        afunixSocket.outputStream.flush()
//        println("已经进行测量报告")
    }
}
