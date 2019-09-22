package com.java.demo.socket.stream.multi.server;

import com.java.demo.socket.util.ObjectUtil;
import com.java.demo.socket.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Processor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Processor.class);

    private Socket socket;
    public Processor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        PrintWriter pw = null;
        int i = 0;
        byte[] h = new byte[4]; // 从socket输入流中读取头4个字段，并转换int,已明确要接收的数据长度
        byte[] buf = new byte[10240];
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream(), true);
            while (!socket.isInputShutdown() && bis.read(h) == 4) {
                int t = ObjectUtil.byteArrayToInt(h), r = 0;
                if (t < 1) {
                    pw.println("error");
                    break;
                }
                bos = new BufferedOutputStream(new FileOutputStream(String.format("d:/d%s.txt", i)));
                // 客户端在发送每个文件后，会等待服务端的响应，所以这里不会越界
                try {
                    while (t > 0 && (r = bis.read(buf)) > 0) {
                        t -= r;
                        bos.write(buf, 0 , r);
                        bos.flush();
                    }
                    pw.println("ok");
                    i++;
                } finally {
                    StreamUtil.close(bos);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(bis);
            StreamUtil.close(pw);
            StreamUtil.close(socket);
        }
    }
}
