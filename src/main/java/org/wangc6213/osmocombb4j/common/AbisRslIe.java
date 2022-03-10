package org.wangc6213.osmocombb4j.common;

public class AbisRslIe {
    public static final Short RSL_IE_CHAN_NR = 0x01;
    public static final Short RSL_IE_LINK_IDENT = 0x02;
    public static final Short RSL_IE_ACT_TYPE = 0x03;
    public static final Short RSL_IE_BS_POWER = 0x04;
    public static final Short RSL_IE_CHAN_IDENT = 0x05;
    public static final Short RSL_IE_CHAN_MODE = 0x06;
    public static final Short RSL_IE_ENCR_INFO = 0x07;
    public static final Short RSL_IE_FRAME_NUMBER = 0x08;
    public static final Short RSL_IE_HANDO_REF = 0x09;
    public static final Short RSL_IE_L1_INFO = 0x0a;
    public static final Short RSL_IE_L3_INFO = 0x0b;
    public static final Short RSL_IE_MS_IDENTITY = 0x0c;
    public static final Short RSL_IE_MS_POWER = 0x0d;
    public static final Short RSL_IE_PAGING_GROUP = 0x0e;
    public static final Short RSL_IE_PAGING_LOAD = 0x0f;
    public static final Short RSL_IE_PYHS_CONTEXT = 0x10;
    public static final Short RSL_IE_ACCESS_DELAY = 0x11;
    public static final Short RSL_IE_RACH_LOAD = 0x12;
    public static final Short RSL_IE_REQ_REFERENCE = 0x13;
    public static final Short RSL_IE_RELEASE_MODE = 0x14;
    public static final Short RSL_IE_RESOURCE_INFO = 0x15;
    public static final Short RSL_IE_RLM_CAUSE = 0x16;
    public static final Short RSL_IE_STARTNG_TIME = 0x17;
    public static final Short RSL_IE_TIMING_ADVANCE = 0x18;
    public static final Short RSL_IE_UPLINK_MEAS = 0x19;
    public static final Short RSL_IE_CAUSE = 0x1a;
    public static final Short RSL_IE_MEAS_RES_NR = 0x1b;
    public static final Short RSL_IE_MSG_ID = 0x1c;
    /* reserved */
    public static final Short RSL_IE_SYSINFO_TYPE = 0x1e;
    public static final Short RSL_IE_MS_POWER_PARAM = 0x1f;
    public static final Short RSL_IE_BS_POWER_PARAM = 0x20;
    public static final Short RSL_IE_PREPROC_PARAM = 0x21;
    public static final Short RSL_IE_PREPROC_MEAS = 0x22;
    public static final Short RSL_IE_IMM_ASS_INFO = 0x23;        /* Phase 1 (3.6.0), later Full below */
    public static final Short RSL_IE_SMSCB_INFO = 0x24;
    public static final Short RSL_IE_MS_TIMING_OFFSET = 0x25;
    public static final Short RSL_IE_ERR_MSG = 0x26;
    public static final Short RSL_IE_FULL_BCCH_INFO = 0x27;
    public static final Short RSL_IE_CHAN_NEEDED = 0x28;
    public static final Short RSL_IE_CB_CMD_TYPE = 0x29;
    public static final Short RSL_IE_SMSCB_MSG = 0x2a;
    public static final Short RSL_IE_FULL_IMM_ASS_INFO = 0x2b;
    public static final Short RSL_IE_SACCH_INFO = 0x2c;
    public static final Short RSL_IE_CBCH_LOAD_INFO = 0x2d;
    public static final Short RSL_IE_SMSCB_CHAN_INDICATOR = 0x2e;
    public static final Short RSL_IE_GROUP_CALL_REF = 0x2f;
    public static final Short RSL_IE_CHAN_DESC = 0x30;
    public static final Short RSL_IE_NCH_DRX_INFO = 0x31;
    public static final Short RSL_IE_CMD_INDICATOR = 0x32;
    public static final Short RSL_IE_EMLPP_PRIO = 0x33;
    public static final Short RSL_IE_UIC = 0x34;
    public static final Short RSL_IE_MAIN_CHAN_REF = 0x35;
    public static final Short RSL_IE_MR_CONFIG = 0x36;
    public static final Short RSL_IE_MR_CONTROL = 0x37;
    public static final Short RSL_IE_SUP_CODEC_TYPES = 0x38;
    public static final Short RSL_IE_CODEC_CONFIG = 0x39;
    public static final Short RSL_IE_RTD = 0x3a;
    public static final Short RSL_IE_TFO_STATUS = 0x3b;
    public static final Short RSL_IE_LLP_APDU = 0x3c;
    /* Siemens vendor-specific */
    public static final Short RSL_IE_SIEMENS_MRPCI = 0x40;
    public static final Short RSL_IE_SIEMENS_PREF_AREA_TYPE = 0x43;
    public static final Short RSL_IE_SIEMENS_ININ_CELL_HO_PAR = 0x45;
    public static final Short RSL_IE_SIEMENS_TRACE_REF_NR = 0x46;
    public static final Short RSL_IE_SIEMENS_INT_TRACE_IDX = 0x47;
    public static final Short RSL_IE_SIEMENS_L2_HDR_INFO = 0x48;
    public static final Short RSL_IE_SIEMENS_HIGHEST_RATE = 0x4e;
    public static final Short RSL_IE_SIEMENS_SUGGESTED_RATE = 0x4f;

    /* Osmocom specific */
    public static final Short RSL_IE_OSMO_REP_ACCH_CAP = 0x60;
    public static final Short RSL_IE_OSMO_TRAINING_SEQUENCE = 0x61;
    public static final Short RSL_IE_OSMO_TEMP_OVP_ACCH_CAP = 0x62;

    /* ip.access */
    public static final Short RSL_IE_IPAC_SRTP_CONFIG = 0xe0;
    public static final Short RSL_IE_IPAC_PROXY_UDP = 0xe1;
    public static final Short RSL_IE_IPAC_BSCMPL_TOUT = 0xe2;
    public static final Short RSL_IE_IPAC_REMOTE_IP = 0xf0;
    public static final Short RSL_IE_IPAC_REMOTE_PORT = 0xf1;
    public static final Short RSL_IE_IPAC_RTP_PAYLOAD = 0xf2;
    public static final Short RSL_IE_IPAC_LOCAL_PORT = 0xf3;
    public static final Short RSL_IE_IPAC_SPEECH_MODE = 0xf4;
    public static final Short RSL_IE_IPAC_LOCAL_IP = 0xf5;
    public static final Short RSL_IE_IPAC_CONN_STAT = 0xf6;
    public static final Short RSL_IE_IPAC_HO_C_PARMS = 0xf7;
    public static final Short RSL_IE_IPAC_CONN_ID = 0xf8;
    public static final Short RSL_IE_IPAC_RTP_CSD_FMT = 0xf9;
    public static final Short RSL_IE_IPAC_RTP_JIT_BUF = 0xfa;
    public static final Short RSL_IE_IPAC_RTP_COMPR = 0xfb;
    public static final Short RSL_IE_IPAC_RTP_PAYLOAD2 = 0xfc;
    public static final Short RSL_IE_IPAC_RTP_MPLEX = 0xfd;
    public static final Short RSL_IE_IPAC_RTP_MPLEX_ID = 0xfe;
}
