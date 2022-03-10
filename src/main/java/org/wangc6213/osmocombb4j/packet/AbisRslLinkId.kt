package org.wangc6213.osmocombb4j.packet

/* Link Identifier 9.3.2 */
/**
 * /* Link Identifier 9.3.2 */
union abis_rsl_link_id {
#if OSMO_IS_BIG_ENDIAN
uint8_t cbits:2,
na:1,
reserved:2,
sapi:3;
#elif OSMO_IS_LITTLE_ENDIAN
uint8_t sapi:3,
reserved:2,
na:1,
cbits:2;
#endif
uint8_t link_id;
} __attribute__ ((packed));
 */
class AbisRslLinkId {
    var sapi:UByte = 0u
    var reserved:UByte = 0u
    var na:UByte = 0u
    var cbits:UByte = 0u
}