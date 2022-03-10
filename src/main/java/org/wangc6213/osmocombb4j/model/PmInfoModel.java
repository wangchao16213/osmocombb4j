package org.wangc6213.osmocombb4j.model;

import org.wangc6213.osmocombb4j.utils.BytesUtils;

public class PmInfoModel {
    private Short band_arfcn;
    private Byte pm1;
    private Byte pm2;
    /** -110 */
    private int pm1Readable;

    public PmInfoModel(){

    }

    public PmInfoModel(Short band_arfcn, Byte pm1, Byte pm2) {
        this.band_arfcn = band_arfcn;
        this.pm1 = pm1;
        this.pm2 = pm2;
    }

    @Override
    public String toString() {
        return "PmInfo{" +
                "band_arfcn=" + band_arfcn +
                ", pm1=" + pm1 +
                ", pm2=" + pm2 +
                ", pm1Readable=" + pm1Readable +
                '}';
    }
}
