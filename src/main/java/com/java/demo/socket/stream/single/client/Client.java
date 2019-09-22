package com.java.demo.socket.stream.single.client;

import com.java.demo.socket.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        Socket socket = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] buf = new byte[10240];
        BufferedReader sr = null;
        try {
            socket = new Socket("127.0.0.1", 10000);
            bis = new BufferedInputStream(new FileInputStream("d:/demo.txt"));
            bos = new BufferedOutputStream(socket.getOutputStream());
            int t = bis.available(), r = 0;
            while ((r = bis.read(buf)) > 0) {
                t -= r;
                logger.info("read {} bytes from file, {} bytes remain", r, t);
                bos.write(buf, 0 , r);
                bos.flush();
            }
            socket.isOutputShutdown(); // 发送完成
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = sr.readLine();
            if ("ok".equalsIgnoreCase(s)) {
                logger.info("transport the file successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(sr);
            StreamUtil.close(bos);
            StreamUtil.close(bis);
            StreamUtil.close(socket);
        }
        System.out.println("done");
    }
}
