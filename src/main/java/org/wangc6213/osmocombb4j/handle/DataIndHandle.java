package org.wangc6213.osmocombb4j.handle;

import org.wangc6213.osmocombb4j.packet.DataIndPacket;

public class DataIndHandle {
    private DataIndPacket dataIndPacket;

    public DataIndHandle(DataIndPacket dataIndPacket) {
        this.dataIndPacket = dataIndPacket;
    }

    public void doHandle() {
        byte[] data = dataIndPacket.getData();

        if (((data[1] >> 3) & 0xff) == 0x03){
            /**
             * System information 1~8
             */
        }else if (((data[1] >> 3) & 0xff) == 0x00){
            /**
             * System information  2bis 2ter 5bis 5ter 9 13
             */
        }
    }
}
