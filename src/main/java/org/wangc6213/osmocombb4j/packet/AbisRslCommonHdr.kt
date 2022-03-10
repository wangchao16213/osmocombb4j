package org.wangc6213.osmocombb4j.packet


/*! RSL common header */
class AbisRslCommonHdr {
    /*!< message discriminator (ABIS_RSL_MDISC_*) */
    var msgDiscr: UByte = 0u

    /*!< message type (\ref abis_rsl_msgtype) */
    var msgType: UByte = 0u
}