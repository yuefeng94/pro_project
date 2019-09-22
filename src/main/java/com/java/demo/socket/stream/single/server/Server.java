package com.java.demo.socket.stream.single.server;

import com.java.demo.socket.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    public void start() throws IOException {
        serverSocket = new ServerSocket(10000);
        executorService.execute(this);
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            while ((socket = serverSocket.accept()) != null) {
                executorService.execute(new Processor(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(socket);
        }
    }

    public void close() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        Server server = new Server();
        BufferedReader ir = null;
        try {
            server.start();
            ir = new BufferedReader(new InputStreamReader(System.in));
            String cmd = null;
            while ((cmd = ir.readLine()) != null) {
                if ("exit".equalsIgnoreCase(cmd)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(ir);
            server.close();
        }
    }
}
