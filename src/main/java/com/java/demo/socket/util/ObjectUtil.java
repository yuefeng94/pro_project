package com.java.demo.socket.util;

import java.util.Collection;

public class ObjectUtil {
    public static byte[] intToArray(int a) {
        return new byte[] {(byte) ((a >> 24) & 0xff), (byte)((a >> 16) & 0xff), (byte)((a >> 8) & 0xff), (byte) (a & 0xff)};
    }

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16 | (b[0] & 0xff) << 24;
    }

    public Boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            String data = (String) object;
            if ("".equals(data)) {
                return true;
            }
        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            if (collection.isEmpty() || collection.size() == 0) {
                return true;
            }
        }
        return false;
    }
}
