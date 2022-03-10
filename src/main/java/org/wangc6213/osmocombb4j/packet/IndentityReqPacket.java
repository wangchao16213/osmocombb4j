package org.wangc6213.osmocombb4j.packet;

import org.wangc6213.osmocombb4j.exception.UnKownPacketException;
import org.wangc6213.osmocombb4j.utils.BytesUtils;

import java.util.Arrays;

/**
 * Indentity Request
 */
public class IndentityReqPacket extends ProtoPacket {
    private L1ctlPacket hdr;
    private InfoDlPacket infoDl;
    private Byte address;
    private Byte control;
    private Byte length;
    private Byte pd;
    private Byte msgType;
    private Byte identityType;



    /** nr和ns不计入包中 */
    private Integer nr;
    private Integer ns;

    public IndentityReqPacket(byte[] rawData) throws UnKownPacketException {
        /**
         * 判断是否是授权请求
         */
        if (rawData.length < 23 || (rawData[22] != 0x18 && rawData[22] != 0x58)) {
            throw new UnKownPacketException();
        } else {
            this.hdr = new L1ctlPacket();
            this.hdr.setLen(BytesUtils.b2s(rawData, 0));
            this.hdr.setMsgType(rawData[2]);
            this.hdr.setFlags(rawData[3]);
            this.hdr.setPadding0(rawData[4]);
            this.hdr.setPadding1(rawData[5]);
            this.infoDl = new InfoDlPacket(Arrays.copyOfRange(rawData, 6, 18));
            this.address = rawData[18];
            this.control = rawData[19];
            this.length = rawData[20];
            this.pd = rawData[21];
            this.msgType = rawData[22];
            this.identityType = rawData[23];
            this.nr = this.control >> 5;
            this.ns = (this.control & 0x0e) >> 1;
        }
    }

    @Override
    public byte[] buildRawData() {
        return new byte[0];
    }

    public L1ctlPacket getHdr() {
        return hdr;
    }

    public void setHdr(L1ctlPacket hdr) {
        this.hdr = hdr;
    }

    public InfoDlPacket getInfoDl() {
        return infoDl;
    }

    public void setInfoDl(InfoDlPacket infoDl) {
        this.infoDl = infoDl;
    }

    public Byte getAddress() {
        return address;
    }

    public void setAddress(Byte address) {
        this.address = address;
    }

    public Byte getControl() {
        return control;
    }

    public void setControl(Byte control) {
        this.control = control;
    }

    public Byte getLength() {
        return length;
    }

    public void setLength(Byte length) {
        this.length = length;
    }

    public Byte getPd() {
        return pd;
    }

    public void setPd(Byte pd) {
        this.pd = pd;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public Byte getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Byte identityType) {
        this.identityType = identityType;
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

    @Override
    public String toString() {
        return "IndentityReqPacket{" +
                "hdr=" + hdr +
                ", infoDl=" + infoDl +
                ", address=" + address +
                ", control=" + control +
                ", length=" + length +
                ", pd=" + pd +
                ", msgType=" + msgType +
                ", identityType=" + identityType +
                ", nr=" + nr +
                ", ns=" + ns +
                '}';
    }
}
