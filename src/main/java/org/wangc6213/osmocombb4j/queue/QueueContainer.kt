package org.wangc6213.osmocombb4j.queue

import java.util.concurrent.BlockingQueue
import org.wangc6213.osmocombb4j.packet.L1ctlPacket
import java.util.concurrent.LinkedBlockingQueue
import kotlin.Throws
import java.lang.InterruptedException

class QueueContainer {
    /** 物理层控制协议队列 */
    private val l1ctlQueue: BlockingQueue<L1ctlPacket> = LinkedBlockingQueue()

    /** l1层事件触发队列 */
    private val l1eventQueue: BlockingQueue<Int> = LinkedBlockingQueue()

    @Throws(InterruptedException::class)
    @Synchronized
    fun pushL1ctlToQ(l1ctlPacket: L1ctlPacket) {
        l1ctlQueue.put(l1ctlPacket)
    }

    @Throws(InterruptedException::class)
    @Synchronized
    fun takeL1ctlPacket(): L1ctlPacket {
        return l1ctlQueue.take()
    }


    @Throws(InterruptedException::class)
    @Synchronized
    fun pushL1EventToQ(event: Int) {
        l1eventQueue.put(event)
    }

    @Throws(InterruptedException::class)
    @Synchronized
    fun takeL1Event(): Int {
        return l1eventQueue.take()
    }
}