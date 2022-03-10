package org.wangc6213.osmocombb4j.packet

/**
 * RSL RLL header (GSM_08.58 Chapter 8.3)
 */
class AbisRslRllHdr {
    val abisRslCommonHdr: AbisRslCommonHdr = AbisRslCommonHdr()
    var ieChan: UByte = 0u
    var chanNr: UByte = 0u

    /**
     * /* Channel Number 9.3.1 */
    union abis_rsl_chan_nr {
    #if OSMO_IS_BIG_ENDIAN
    uint8_t cbits:5,
    tn:3;
    #elif OSMO_IS_LITTLE_ENDIAN
    uint8_t tn:3,
    cbits:5;
    #endif
    uint8_t chan_nr;
    } __attribute__ ((packed));
     */
    var abisRslChanNr: UShort = 0u
    var chanNrFields: AbisRslChanNr = AbisRslChanNr()
    var ieLinkId: UByte = 0u
    var linkId: UByte = 0u
    var linkIdFields: AbisRslLinkId = AbisRslLinkId()
    var abisRslLinkId: UShort = 0u
}