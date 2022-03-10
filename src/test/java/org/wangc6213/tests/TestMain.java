package org.wangc6213.tests;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;
import org.wangc6213.osmocombb4j.common.RslChannel;
import org.wangc6213.osmocombb4j.packet.DmEstReqPacket;
import org.wangc6213.osmocombb4j.packet.IndentityRespPakcet;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class TestMain {
    @Test
    public void test1() {
        byte[] bytes = new byte[]{0x00, 0x64};
        System.out.println(bytes);
    }


    @Test
    public void test2() {
        int chan_nr = 0x80;
        if ((chan_nr & 0xf8) == RslChannel.RSL_CHAN_Bm_ACCHs) {

        } else if ((chan_nr & 0xf0) == RslChannel.RSL_CHAN_Lm_ACCHs) {

        } else if ((chan_nr & 0xe0) == RslChannel.RSL_CHAN_SDCCH4_ACCH) {

        } else if ((chan_nr & 0xc0) == RslChannel.RSL_CHAN_SDCCH8_ACCH) {

        } else if ((chan_nr & 0xf8) == RslChannel.RSL_CHAN_BCCH) {

        } else if ((chan_nr & 0xf8) == RslChannel.RSL_CHAN_RACH) {

        } else if ((chan_nr & 0xf8) == RslChannel.RSL_CHAN_PCH_AGCH) {

        }

    }

    @Test
    public void test3() {
        byte abc = -128;
        int d = abc & 0xff;
        System.out.println(d);
    }


    @Test
    public void test4() {
        int[] a = new int[]{3, 1, 2};
        int[] b = a;
        b[0] = 30;
        System.out.println(a[0]);
    }


//    int rsl_dec_chan_nr(uint8_t chan_nr, uint8_t *type, uint8_t *subch, uint8_t *timeslot)
//    {
//	*timeslot = chan_nr & 0x7;
//
//        if ((chan_nr & 0xf8) == RSL_CHAN_Bm_ACCHs) {
//		*type = RSL_CHAN_Bm_ACCHs;
//		*subch = 0;
//        } else if ((chan_nr & 0xf0) == RSL_CHAN_Lm_ACCHs) {
//		*type = RSL_CHAN_Lm_ACCHs;
//		*subch = (chan_nr >> 3) & 0x1;
//        } else if ((chan_nr & 0xe0) == RSL_CHAN_SDCCH4_ACCH) {
//		*type = RSL_CHAN_SDCCH4_ACCH;
//		*subch = (chan_nr >> 3) & 0x3;
//        } else if ((chan_nr & 0xc0) == RSL_CHAN_SDCCH8_ACCH) {
//		*type = RSL_CHAN_SDCCH8_ACCH;
//		*subch = (chan_nr >> 3) & 0x7;
//        } else if ((chan_nr & 0xf8) == RSL_CHAN_BCCH) {
//		*type = RSL_CHAN_BCCH;
//		*subch = 0;
//        } else if ((chan_nr & 0xf8) == RSL_CHAN_RACH) {
//		*type = RSL_CHAN_RACH;
//		*subch = 0;
//        } else if ((chan_nr & 0xf8) == RSL_CHAN_PCH_AGCH) {
//		*type = RSL_CHAN_PCH_AGCH;
//		*subch = 0;
//        } else
//            return -EINVAL;
//
//        return 0;
//    }

    @Test
    public void test6() {
        Short a = 0xf0;
        byte b = (byte) (a & 0xff);
        System.out.println(b);
    }

    @Test
    public void test7() {
        String a = "00 90 05 00 00 00 51 00 00 00 04 00 00 33 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 05";
        String[] array = a.split(" ");
        System.out.println(array.length);
    }


    @Test
    public void test8() {
        Integer nr = 3;
        Integer ns = 2;
        System.out.println(Integer.toBinaryString(nr));
        System.out.println(Integer.toBinaryString(ns));
        String nrBstr = Integer.toBinaryString(nr);
        if (nrBstr.length() < 3) {
            nrBstr = "0" + nrBstr;
        }

        String nsBstr = Integer.toBinaryString(ns);
        if (nsBstr.length() < 3) {
            nsBstr = "0" + nsBstr;
        }

        System.out.println(Integer.parseInt(nrBstr, 2));
        System.out.println(Integer.parseInt(nsBstr, 2));
        System.out.println(nrBstr + "0" + nsBstr + "0");
        System.out.println(Integer.parseInt(nrBstr + "0" + nsBstr + "0", 2));


    }


    @Test
    public void test9() {
        IndentityRespPakcet indentityRespPakcet = new IndentityRespPakcet((byte) 1, (byte) 0x00, 1, 1);
        indentityRespPakcet.buildRawData();
    }
//    File file = new File("E:\\workspace\\mythril\\mythril");

    @Test
    public void test11(){
        File file = new File("E:\\workspace\\mythril\\mythril");
        VarNum varNum = new VarNum();
        test10(file,varNum);
        System.out.println(varNum.count);
    }

    public void test10(File file, VarNum varNum) {
        if (!file.isFile()) {
            File[] subFiles = file.listFiles();
            for (File file1:subFiles){
                test10(file1, varNum);
            }
        } else {
            int lines = FileUtil.readLines(file, "utf-8").size();
            varNum.count = varNum.count + lines;
        }
    }

    class VarNum {
        public Integer count = 0;
    }


    @Test
    public void test12(){
        int a = (34 & 0x0e);
        int b = a >> 1;
        System.out.println(b);
    }





    @Test
    public void test13() throws Exception{
        /*
         * 向服务器端发送数据
         */
        // 1.定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("43.155.100.191");
        int port = 1701;
        byte[] data = "用户名：admin;密码：123".getBytes();
        System.out.println("aaaa");
        // 2.创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        System.out.println("bbbb");
        // 3.创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();
        // 4.向服务器端发送数据报
        socket.send(packet);
        System.out.println("cccc");
        /*
         * 接收服务器端响应的数据
         */
        // 1.创建数据报，用于接收服务器端响应的数据
        byte[] data2 = new byte[1];
        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
        // 2.接收服务器响应的数据
        socket.receive(packet2);
        // 3.读取数据
        String reply = new String(data2, 0, packet2.getLength());
        System.out.println("我是客户端，服务器说：" + reply);
        // 4.关闭资源
        socket.close();
    }

}
