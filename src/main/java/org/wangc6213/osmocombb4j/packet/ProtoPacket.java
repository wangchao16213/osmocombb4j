package org.wangc6213.osmocombb4j.packet;

public abstract class ProtoPacket {
    private byte[] rawData = new byte[]{};
    private byte[] payloadRawData = new byte[]{};

    public abstract byte[] buildRawData();

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    public byte[] getPayloadRawData() {
        return payloadRawData;
    }

    public void setPayloadRawData(byte[] payloadRawData) {
        this.payloadRawData = payloadRawData;
    }
}
