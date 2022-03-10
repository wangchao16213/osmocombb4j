package org.wangc6213.osmocombb4j.packet;

import cn.hutool.core.util.HexUtil;
import org.wangc6213.osmocombb4j.common.L1CtlProto;
import org.wangc6213.osmocombb4j.utils.BytesUtils;

import java.util.Arrays;

/**
 * UA帧 Location Updating Request 下行
 * tip 03 00 00 00 + 51 00 00 33 00 17 d0 20 37 00 00 00 + 01 73 3d 05 08 32 64 f0 00 73 d1 33 05 f4 3c cb 61 05 2b bd 83 31 a1
 */
public class DlLocUpdReqPacket extends ProtoPacket {
    private L1ctlPacket hdr = new L1ctlPacket(L1CtlProto.L1CTL_DATA_IND);
    private InfoDlPacket infoDlPacket;
    private Byte address;
    private Byte control;
    /**
     * length:6位 ;Last segment:1位 ; Final octet:1位
     */
    private Byte length;
    private Byte pd;
    private Byte messageType;
    private Byte cipheringKeySequeuenceNumber;
    private byte[] laiBytes;
    private Byte classMark;
    private Byte mobileIdentityLength;
    private Byte unsed;
    private Integer tmsi;
    private Byte[] fillFrameBytes;
    private byte[] tmsiBytes;
    /**
     * 都是一字节
     */
    private Short[] padding2 = new Short[]{0x00, 0x02, 0x00, 0x01, 0x3f, 0x3d, 0x05, 0x08, 0x32, 0x64, 0xf0, 0x00, 0x73, 0x7c, 0x33, 0x05, 0xf4};

    public DlLocUpdReqPacket(byte[] rawData) {
        hdr.setLen(BytesUtils.b2s(rawData, 0));
        hdr.setMsgType(rawData[2]);
        hdr.setFlags(rawData[3]);
        hdr.setPadding0(rawData[4]);
        hdr.setPadding1(rawData[5]);
        this.infoDlPacket = new InfoDlPacket(Arrays.copyOfRange(rawData, 6, 18));
        this.address = rawData[18];
        this.control = rawData[19];
        this.length = rawData[20];
        this.pd = rawData[21];
        this.messageType = rawData[22];
        this.cipheringKeySequeuenceNumber = rawData[23];
        this.laiBytes = Arrays.copyOfRange(rawData, 24, 29);
        this.classMark = rawData[29];
        this.mobileIdentityLength = rawData[30];
        this.unsed = rawData[31];
        this.tmsi = BytesUtils.b2i(rawData, 32);
        this.tmsiBytes = Arrays.copyOfRange(rawData, 32, 36);
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

    public InfoDlPacket getInfoDlPacket() {
        return infoDlPacket;
    }

    public void setInfoDlPacket(InfoDlPacket infoDlPacket) {
        this.infoDlPacket = infoDlPacket;
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

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public Byte getCipheringKeySequeuenceNumber() {
        return cipheringKeySequeuenceNumber;
    }

    public void setCipheringKeySequeuenceNumber(Byte cipheringKeySequeuenceNumber) {
        this.cipheringKeySequeuenceNumber = cipheringKeySequeuenceNumber;
    }

    public byte[] getLaiBytes() {
        return laiBytes;
    }

    public void setLaiBytes(byte[] laiBytes) {
        this.laiBytes = laiBytes;
    }

    public Byte getClassMark() {
        return classMark;
    }

    public void setClassMark(Byte classMark) {
        this.classMark = classMark;
    }

    public Byte getMobileIdentityLength() {
        return mobileIdentityLength;
    }

    public void setMobileIdentityLength(Byte mobileIdentityLength) {
        this.mobileIdentityLength = mobileIdentityLength;
    }

    public Byte getUnsed() {
        return unsed;
    }

    public void setUnsed(Byte unsed) {
        this.unsed = unsed;
    }

    public Byte[] getFillFrameBytes() {
        return fillFrameBytes;
    }

    public void setFillFrameBytes(Byte[] fillFrameBytes) {
        this.fillFrameBytes = fillFrameBytes;
    }

    public Short[] getPadding2() {
        return padding2;
    }

    public void setPadding2(Short[] padding2) {
        this.padding2 = padding2;
    }

    public Integer getTmsi() {
        return tmsi;
    }

    public void setTmsi(Integer tmsi) {
        this.tmsi = tmsi;
    }

    public String getTmsiStr() {
        return HexUtil.encodeHexStr(tmsiBytes);
    }
}
