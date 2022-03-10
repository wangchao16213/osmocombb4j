package org.wangc6213.osmocombb4j.utils;

public class BytesUtils {
    public static Short b2s(byte[] bytes, int offset) {
        return (short) ((bytes[offset] & 0xff) << 8 | (bytes[offset + 1] & 0xff));
    }


    public static Integer b2i(byte[] bytes, int offset) {
        return (int) ((bytes[offset] & 0xff) << 24 | (bytes[offset + 1] & 0xff) << 16 | (bytes[offset + 2] & 0xff) << 8 | (bytes[offset + 3] & 0xff));
    }
}
