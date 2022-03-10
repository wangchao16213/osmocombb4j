package org.wangc6213.osmocombb4j.packet;

import org.wangc6213.osmocombb4j.common.L1CtlProto;

import java.nio.ByteBuffer;

/**
 * CCCH_MODE REQ
 * tip: 00 08 + 10 00 00 00 + 01 00 00 00
 */
public class CCCHModeReqPacket extends ProtoPacket {
    private L1ctlPacket hdr = new L1ctlPacket(L1CtlProto.L1CTL_CCCH_MODE_REQ);
    private Byte ccchMode = 0;
    private Byte padidng0 = 0;
    private Byte padidng1 = 0;
    private Byte padidng2 = 0;

    public CCCHModeReqPacket(Byte ccchMode) {
        hdr.setLen((short) 8);
        this.ccchMode = ccchMode;
    }

    public CCCHModeReqPacket() {
        hdr.setLen((short) 8);
    }

    @Override
    public byte[] buildRawData() {
        byte[] hdrBytes = hdr.buildRawDataWithoutPayload();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(hdrBytes);
        byteBuffer.put(ccchMode);
        byteBuffer.put(padidng0);
        byteBuffer.put(padidng1);
        byteBuffer.put(padidng2);
        return byteBuffer.array();
    }


}
