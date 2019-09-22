package com.java.demo.socket.stream.multi.client;

import com.java.demo.socket.util.ObjectUtil;
import com.java.demo.socket.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        Socket socket = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        BufferedReader sr = null;
        byte[] buf = new byte[10240];
        try {
            socket = new Socket("127.0.0.1", 10000);
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for (int i = 1; i < 4; i++) {
                bis = new BufferedInputStream(new FileInputStream(String.format("d:/a%s.txt", i)));
                try {
                    int t = bis.available(), r = 0;
                    byte[] bytes = ObjectUtil.intToArray(t);
                    bos = new BufferedOutputStream(socket.getOutputStream());
                    bos.write(bytes);
                    while ((r = bis.read(buf)) > 0) {
                        bos.write(buf, 0 , r);
                        bos.flush();
                    }
                    // 等待服务器响应
                    String s = sr.readLine();
                    if ("ok".equalsIgnoreCase(s)) {
                        logger.info("receive successful");
                    } else {
                        logger.info("receive fail");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    StreamUtil.close(bis);
                }
            }
            socket.shutdownOutput(); // 告知服务端，文件以及发送完毕
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(bos);
            StreamUtil.close(sr);
            StreamUtil.close(socket);
        }
    }
}
