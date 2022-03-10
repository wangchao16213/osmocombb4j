package org.wangc6213.osmocombb4j.model

/**
 * 复帧数据传输中变量,数据全部都模8(range:0~7)
 * 初始化阶段 NS和 NR的值为VS和VR的值
 */
class MutiFrameVar {
    /** 下次发送序列号  发送I帧后该值 + 1 */
    var vs: Int = 1

    /** 确认变量 */
    var va: Int = 0

    /** 发送序列号 */
    var ns: Int = 0

    /** 预期对方下一个I帧的发送序列号 */
    var vr: Int = 0

    /** 接收序列号 即下一个接收到的I帧的预期发送序列号 */
    var nr: Int = 0

    val k = 1


    fun increasVs() {
        if (vs + 1 < 8) vs += 1 else vs = 0
    }

    fun increaseVa() {
        if (va + 1 < 8) va += 1 else va = 0
    }

    fun increaseNs() {
        if (ns + 1 < 8) ns += 1 else ns = 0
    }

    fun increaseVr() {
        if (vr + 1 < 8) vr += 1 else vr = 0
    }

    fun increaseNr() {
        if (nr + 1 < 8) nr += 1 else nr = 0
    }

    /**
     * 已经正确接收I帧
     */
    fun alreadyRecvIFrame(nr: Int) {
        increaseVr()
        va = nr
    }

    fun alreadyRecvSFrame(nr: Int) {
        va = nr
    }

    /**
     * 是否允许发送
     */
    fun isAllowSend(): Boolean {
        return vs < va + k
    }

    override fun toString(): String {
        return "MutiFrameVar(vs=$vs, va=$va, ns=$ns, vr=$vr, nr=$nr, k=$k)"
    }


}