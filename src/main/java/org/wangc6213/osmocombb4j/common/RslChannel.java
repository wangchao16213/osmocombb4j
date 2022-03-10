package org.wangc6213.osmocombb4j.common;

public class RslChannel {
    /* Chapter 9.3.1 */
    public static final int RSL_CHAN_NR_MASK = 0xf8;
    public static final int RSL_CHAN_NR_1 = 0x08;    /*< bit to add for 2nd,... lchan */
    public static final int RSL_CHAN_Bm_ACCHs = 0x08;
    public static final int RSL_CHAN_Lm_ACCHs = 0x10;    /* ..  0x18 */
    public static final int RSL_CHAN_SDCCH4_ACCH = 0x20;    /* ..  0x38 */
    public static final int RSL_CHAN_SDCCH8_ACCH = 0x40;    /* ... 0x78 */
    public static final int RSL_CHAN_BCCH = 0x80;
    public static final int RSL_CHAN_RACH = 0x88;
    public static final int RSL_CHAN_PCH_AGCH = 0x90;
    public static final int RSL_CHAN_OSMO_PDCH = 0xc0;/*< non-standard, for dyn TS */
    public static final int RSL_CHAN_OSMO_CBCH4 = 0xc8;    /*< non-standard, for CBCH/SDCCH4 */
    public static final int RSL_CHAN_OSMO_CBCH8 = 0xd0;    /*< non-standard, for CBCH/SDCCH8 */

    /* non-standard, Osmocom specific Bm/Lm equivalents for VAMOS */
    public static final int RSL_CHAN_OSMO_VAMOS_Bm_ACCHs = 0xe8;    /* VAMOS TCH/F */
    public static final int RSL_CHAN_OSMO_VAMOS_Lm_ACCHs = 0xf0;    /* VAMOS TCH/H */
    public static final int RSL_CHAN_OSMO_VAMOS_MASK = 0xe0;    /* VAMOS TCH/{F,H} */

}
