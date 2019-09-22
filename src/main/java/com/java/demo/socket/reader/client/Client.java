package com.java.demo.socket.reader.client;

import com.java.demo.socket.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        BufferedReader ir = null; // 从键盘读取字符串
        BufferedReader sr = null; // 从socket读取字符串
        String cmd = null;
        PrintWriter pw = null;
        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 10000);
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            System.out.printf("Say 'bye' to exit");
            ir = new BufferedReader(new InputStreamReader(System.in));
            while ((cmd = ir.readLine()) != null) {
                pw.println(cmd);
                String s = sr.readLine();
                System.out.println(String.format("Server say %s", s));
                if ("bye".equalsIgnoreCase(cmd)) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Error on connection", e);
        } finally {
            StreamUtil.close(ir);
            StreamUtil.close(sr);
            StreamUtil.close(socket);
        }
        System.out.println("bye");
    }
}
