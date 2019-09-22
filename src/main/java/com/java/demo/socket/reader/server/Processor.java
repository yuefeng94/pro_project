package com.java.demo.socket.reader.server;

import com.java.demo.socket.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Processor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Processor.class);

    private Socket socket = null;

    public Processor (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            while (!Thread.interrupted()) {
                String s = br.readLine();
                System.out.println(String.format("%s say %s", socket.getRemoteSocketAddress(), s));
                pw.println(s);
                if ("bye".equalsIgnoreCase(s)) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Error on process command", e);
        } finally {
            StreamUtil.close(br);
            StreamUtil.close(pw);
            StreamUtil.close(socket);
        }
    }
}
