package com.java.demo.socket.stream.single.server;

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
        byte[] buf = new byte[10240];
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream("d:/demo11.txt"));
            pw = new PrintWriter(socket.getOutputStream(), true);
            int r = 0, t = 0;
            while (!socket.isInputShutdown() && (r = bis.read(buf)) > 0) {
                t += r;
                logger.info("received {} / {} bytes", r, t);
                bos.write(buf,0 , r);
                bos.flush();
            }
            logger.info("received {} bytes and done", t);
            pw.println("ok");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(bis);
            StreamUtil.close(bos);
            StreamUtil.close(pw);
            StreamUtil.close(socket);
        }
    }
}
