package com.java.demo.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtil {

    private static final Logger log = LoggerFactory.getLogger(StreamUtil.class);
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            log.error("{Error on close {}", closeable.getClass().getName(), e);
        }
    }

}
