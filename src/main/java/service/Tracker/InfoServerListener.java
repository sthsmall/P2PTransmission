package service.Tracker;

import java.io.IOException;
import java.net.ServerSocket;

//对监听
public class InfoServerListener extends Thread{
    //监听端口
    private final int port = 5204;

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                new InfoServerHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
