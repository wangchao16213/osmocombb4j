package org.wangc6213.osmocombb4j.packet;

import org.wangc6213.osmocombb4j.utils.BytesUtils;

public class PmInfoPacket {
    private Short bandArfcn;
    private Byte pm1;
    private Byte pm2;
    /** -110 */
    private int pm1Readable;


    public PmInfoPacket(byte[] pmInfoRawData) {
        bandArfcn = BytesUtils.b2s(pmInfoRawData, 0);
        pm1 = (byte) (pmInfoRawData[2] & 0xff);
        pm2 = (byte) (pmInfoRawData[3] & 0xff);
    }

    @Override
    public String toString() {
        return "PmInfo{" +
                "bandArfcn=" + bandArfcn +
                ", pm1=" + pm1 +
                ", pm2=" + pm2 +
                ", pm1Readable=" + pm1Readable +
                '}';
    }


    public Short getBandArfcn() {
        return bandArfcn;
    }

    public void setBandArfcn(Short bandArfcn) {
        this.bandArfcn = bandArfcn;
    }

    public Byte getPm1() {
        return pm1;
    }

    public void setPm1(Byte pm1) {
        this.pm1 = pm1;
    }

    public Byte getPm2() {
        return pm2;
    }

    public void setPm2(Byte pm2) {
        this.pm2 = pm2;
    }

    public int getPm1Readable() {
        return pm1Readable;
    }

    public void setPm1Readable(int pm1Readable) {
        this.pm1Readable = pm1Readable;
    }
}
