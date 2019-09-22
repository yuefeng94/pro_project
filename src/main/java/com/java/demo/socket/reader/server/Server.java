package com.java.demo.socket.reader.server;

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

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private ServerSocket serverSocket = null;

    public void start() throws IOException {
        serverSocket = new ServerSocket(10000);
        threadPool.execute(this);
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            while ((socket = serverSocket.accept()) != null) {
                log.info("Client {} connected", socket.getRemoteSocketAddress());
                // 创建新线程去处理新的socket连接，并与客户端保持通信
                threadPool.execute(new Processor(socket));
            }
        } catch (IOException e) {
            log.error("Error on process connection ", e);
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
        threadPool.shutdown();
    }

    public static void main(String[] args) {
        Server server = new Server();
        BufferedReader ir = null;
        try {
            server.start(); // 启动服务器，保持监听状态
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
