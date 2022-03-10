package org.wangc6213.osmocombb4j.common;

public class L1CtlProto {
    /**
     * 空指令
     */
    public final static byte L1CTL_NONE = 0;
    /**
     * 频率校正同步请求
     */
    public final static byte L1CTL_FBSB_REQ = 1;
    /**
     * 频率校正同步请求
     */
    public final static byte L1CTL_FBSB_CONF = 2;
    /**
     * 数据传输标识
     */
    public final static byte L1CTL_DATA_IND = 3;
    public final static byte L1CTL_RACH_REQ = 4;
    public final static byte L1CTL_DM_EST_REQ = 5;
    public final static byte L1CTL_DATA_REQ = 6;
    public final static byte L1CTL_RESET_IND = 7;
    /**
     * 功率测量请求
     */
    public final static byte L1CTL_PM_REQ = 8;
    /**
     * 功率测量确认
     */
    public final static byte L1CTL_PM_CONF = 9;
    public final static byte L1CTL_ECHO_REQ = 10;
    public final static byte L1CTL_ECHO_CONF = 11;
    /**
     * 随机访问信道确认
     */
    public final static byte L1CTL_RACH_CONF = 12;
    /**
     * 设备复位请求
     */
    public final static byte L1CTL_RESET_REQ = 13;
    /**
     * 设备复位确认
     */
    public final static byte L1CTL_RESET_CONF = 14;
    public final static byte L1CTL_DATA_CONF = 15;
    public final static byte L1CTL_CCCH_MODE_REQ = 16;
    public final static byte L1CTL_CCCH_MODE_CONF = 17;
    public final static byte L1CTL_DM_REL_REQ = 18;
    public final static byte L1CTL_PARAM_REQ = 19;
    public final static byte L1CTL_DM_FREQ_REQ = 20;
    public final static byte L1CTL_CRYPTO_REQ = 21;
    public final static byte L1CTL_SIM_REQ = 22;
    public final static byte L1CTL_SIM_CONF = 23;
    public final static byte L1CTL_TCH_MODE_REQ = 24;
    public final static byte L1CTL_TCH_MODE_CONF = 25;
    public final static byte L1CTL_NEIGH_PM_REQ = 26;
    public final static byte L1CTL_NEIGH_PM_IND = 27;
    public final static byte L1CTL_TRAFFIC_REQ = 28;
    public final static byte L1CTL_TRAFFIC_CONF = 29;
    public final static byte L1CTL_TRAFFIC_IND = 30;
    public final static byte L1CTL_BURST_IND = 31;
    public final static byte L1CTL_TBF_CFG_REQ = 32;
    public final static byte L1CTL_TBF_CFG_CONF = 33;
    public final static byte L1CTL_DATA_TBF_REQ = 34;
    public final static byte L1CTL_DATA_TBF_CONF = 35;
    public final static byte L1CTL_EXT_RACH_REQ = 36;


}
