package org.wangc6213.osmocombb4j.packet;

import java.nio.ByteBuffer;

public class ClassmarkChangePacket extends ProtoPacket {
    private byte[] bytes1 = new byte[]{0x00, 0x1f, 0x06, 0x00, 0x00, 0x00};
    private Byte chanNr;
    private byte[] bytes2 = new byte[]{0x00, 0x00, 0x00};
    private byte[] dataBytes = new byte[]{0x01, 0x00, 0x35, 0x06, 0x16, 0x03, 0x33, 0x19, (byte) 0x81, 0x20, 0x05, 0x60, 0x14, (byte) 0xc0, 0x00, 0x00, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b, 0x2b};

    public ClassmarkChangePacket(Byte chanNr) {
        this.chanNr = chanNr;
    }

    @Override
    public byte[] buildRawData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(0x1f + 2);
        byteBuffer.put(bytes1);
        byteBuffer.put(chanNr);
        byteBuffer.put(bytes2);
        byteBuffer.put(dataBytes);
        return byteBuffer.array();
    }
}
