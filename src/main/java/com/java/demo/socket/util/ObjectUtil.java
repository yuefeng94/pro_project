package com.java.demo.socket.util;

public class ObjectUtil {
    public static byte[] intToArray(int a) {
        return new byte[] {(byte) ((a >> 24) & 0xff), (byte)((a >> 16) & 0xff), (byte)((a >> 8) & 0xff), (byte) (a & 0xff)};
    }

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16 | (b[0] & 0xff) << 24;
    }
}
