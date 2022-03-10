package org.wangc6213.tests.models;

import org.wangc6213.osmocombb4j.utils.PacketUtils;

public class LogDumpModel {
    private String direct;
    private String frameType;
    private Integer nr;
    private Integer ns;
    private String data;

    public LogDumpModel(String logline) {
        if (logline.startsWith("收到")) {
            this.direct = "收";
        } else {
            this.direct = "发";
        }

        String[] logItemArray = logline.split(":===");


        data = logItemArray[1];

        if (logItemArray[0].startsWith("收到")) {
            frameType = logItemArray[1].substring(0, 2).equals("03") ? "I" : "S";
        } else {
            frameType = logItemArray[0].contains("发送S") ? "S" : "I";
        }


        if (logline.startsWith("收到")) {
            Integer[] rs = PacketUtils.parserNrAndNs(Integer.parseInt(data.split(" ")[1],16));
            this.nr = rs[0];
            this.ns = rs[1];

        } else {
            Integer[] rs = PacketUtils.parserNrAndNs(Integer.parseInt(data.split(" ")[11],16));
            this.nr = rs[0];
            this.ns = rs[1];
        }



    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LogDumpModel{" +
                "direct='" + direct + '\'' +
                ", frameType='" + frameType + '\'' +
                ", nr=" + nr +
                ", ns=" + ns +
                ", data='" + data + '\'' +
                '}';
    }
}
