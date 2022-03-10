package org.wangc6213.osmocombb4j.packet;

import java.nio.ByteBuffer;

public class L1ctlPacket extends ProtoPacket {
    private Short len;
    private Byte msgType = 0;
    private Byte flags = 0;
    private Byte padding0 = 0;
    private Byte padding1 = 0;


    /**
     * 获取字节流 不包含载体
     *
     * @return
     */
    public byte[] buildRawDataWithoutPayload() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        byteBuffer.putShort(len);
        byteBuffer.put(msgType);
        byteBuffer.put(flags);
        byteBuffer.put(padding0);
        byteBuffer.put(padding1);
        return byteBuffer.array();
    }


    public L1ctlPacket(Byte msgType) {
        this.msgType = msgType;
    }

    public L1ctlPacket(){

    }

    public Short getLen() {
        return len;
    }

    public void setLen(Short len) {
        this.len = len;
    }

    public Byte getFlags() {
        return flags;
    }

    public void setFlags(Byte flags) {
        this.flags = flags;
    }

    public Byte getPadding0() {
        return padding0;
    }

    public void setPadding0(Byte padding0) {
        this.padding0 = padding0;
    }

    public Byte getPadding1() {
        return padding1;
    }

    public void setPadding1(Byte padding1) {
        this.padding1 = padding1;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    @Override
    public byte[] buildRawData() {
        return new byte[0];
    }

    @Override
    public String toString() {
        return "L1ctlPacket{" +
                "len=" + len +
                ", msgType=" + msgType +
                ", flags=" + flags +
                ", padding0=" + padding0 +
                ", padding1=" + padding1 +
                '}';
    }
}
