package org.wangc6213.osmocombb4j.utils;

import cn.hutool.core.util.ArrayUtil;
import org.newsclub.net.unix.AFUNIXSocket;
import org.wangc6213.osmocombb4j.common.L1CtlProto;
import org.wangc6213.osmocombb4j.common.LapdmProto;
import org.wangc6213.osmocombb4j.packet.DataIndPacket;
import org.wangc6213.osmocombb4j.packet.L1ctlPacket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PacketUtils {
    public static L1ctlPacket readToL1Packet(InputStream inputStream) throws IOException {
        byte[] lenBytes = new byte[2];
        inputStream.read(lenBytes, 0, 2);

        /**
         * 获取消息体
         */
        short len = BytesUtils.b2s(lenBytes, 0);
        byte[] bodyBytes = new byte[len];
        inputStream.read(bodyBytes, 0, len);
        L1ctlPacket l1CtlProtoPacket = new L1ctlPacket();
        l1CtlProtoPacket.setLen(len);
        l1CtlProtoPacket.setMsgType((byte) (bodyBytes[0] & 0xff));
        l1CtlProtoPacket.setFlags((byte) (bodyBytes[1] & 0xff));
        l1CtlProtoPacket.setPadding0((byte) (bodyBytes[2] & 0xff));
        l1CtlProtoPacket.setPadding1((byte) (bodyBytes[3] & 0xff));
        l1CtlProtoPacket.setPayloadRawData(bodyBytes);
        byte[] rawdata = ByteBuffer.allocate(len + 2).put(lenBytes).put(bodyBytes).array();
        l1CtlProtoPacket.setRawData(rawdata);
        return l1CtlProtoPacket;
    }


    public static L1ctlPacket readToL1Packet(byte[] rawData) {
        /**
         * 获取消息体
         */
        short len = BytesUtils.b2s(rawData, 0);
        byte[] bodyBytes = Arrays.copyOfRange(rawData, 2, len);
        L1ctlPacket l1CtlProtoPacket = new L1ctlPacket();
        l1CtlProtoPacket.setLen(len);
        l1CtlProtoPacket.setMsgType((byte) (bodyBytes[0] & 0xff));
        l1CtlProtoPacket.setFlags((byte) (bodyBytes[1] & 0xff));
        l1CtlProtoPacket.setPadding0((byte) (bodyBytes[2] & 0xff));
        l1CtlProtoPacket.setPadding1((byte) (bodyBytes[3] & 0xff));
        l1CtlProtoPacket.setPayloadRawData(bodyBytes);
        l1CtlProtoPacket.setRawData(rawData);
        return l1CtlProtoPacket;
    }


    /**
     * 判断是否是Lapdm帧
     *
     * @param rawData     原始数据
     * @param lapdmProtoc 帧包msgType类型
     * @return
     */
    public static Boolean correctLapdmPacket(byte[] rawData, Integer lapdmProtoc) {
        if (rawData[2] == L1CtlProto.L1CTL_DATA_IND) {
            if (LapdmProto.AUTH_REQ == lapdmProtoc) {
                //rawData[22]
            }
        }
        return false;
    }


    /**
     * 发送包
     */
    public static void send(AFUNIXSocket afunixSocket, byte[] bytes) throws IOException {
        afunixSocket.getOutputStream().write(bytes);
        afunixSocket.getOutputStream().flush();
    }


    public static Integer[] parserNrAndNs(Integer param) {
        Integer[] result = new Integer[2];
        int ns = (param & 0x0e) >> 1;
        int nr = param >> 5;
        result[0] = nr;
        result[1] = ns;
        return result;
    }


    /**
     * I帧分段数据合并  新的ACL字节 + 暂存的所有数据 + 新的接收的数据
     *
     * @param dataIndPacket         当前接收到的数据包
     * @param sengmentDataIndPacket 分段暂存数据包
     */
    public static void segmentIframeDataMerge(DataIndPacket dataIndPacket, DataIndPacket sengmentDataIndPacket) {
        //Address Contorl Length字节数组
        byte[] aclBytes = Arrays.copyOfRange(dataIndPacket.getData(), 0, 3);
        //暂存的数据
        byte[] oldDataBytes = Arrays.copyOfRange(sengmentDataIndPacket.getData(), 3, sengmentDataIndPacket.getData().length);
        byte[] newDataBytes = Arrays.copyOfRange(dataIndPacket.getData(), 3, dataIndPacket.getData().length);
        sengmentDataIndPacket.setData(ArrayUtil.addAll(aclBytes, oldDataBytes, newDataBytes));
    }
}
