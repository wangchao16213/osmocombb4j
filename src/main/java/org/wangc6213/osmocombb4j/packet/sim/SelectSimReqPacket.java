package org.wangc6213.osmocombb4j.packet.sim;

import org.wangc6213.osmocombb4j.common.L1CtlProto;
import org.wangc6213.osmocombb4j.packet.L1ctlPacket;
import org.wangc6213.osmocombb4j.packet.ProtoPacket;

import java.nio.ByteBuffer;

public class SelectSimReqPacket extends ProtoPacket {
    private L1ctlPacket hdr = new L1ctlPacket(L1CtlProto.L1CTL_SIM_REQ);
    private Byte clazz = (byte) 0xa0;
    private Byte ins = (byte) 0xa4;
    private Byte p1 = 0x00;
    private Byte p2 = 0x00;
    private Byte p3 = 0x02;
    private Short fileId;


    public SelectSimReqPacket() {
        hdr.setLen((short) 0x0b);
        // 0x3f00
        this.fileId = 16128;
    }


    @Override
    public byte[] buildRawData() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(0x0b + 2);
        byteBuffer.put(hdr.buildRawDataWithoutPayload());
        byteBuffer.put(clazz);
        byteBuffer.put(ins);
        byteBuffer.put(p1);
        byteBuffer.put(p2);
        byteBuffer.put(p3);
        byteBuffer.putShort(fileId);
        return byteBuffer.array();
    }
}
