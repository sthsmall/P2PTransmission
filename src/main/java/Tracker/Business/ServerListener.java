package Tracker.Business;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener extends Thread{
    //监听端口
    private int port = 5204;

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                new ServerHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
