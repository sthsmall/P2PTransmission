package service.Tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InfoServerHandler extends Thread{
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String ip;

    public InfoServerHandler(Socket accept) {
        this.socket = accept;
        this.ip = socket.getInetAddress().getHostAddress();
        //初始化流
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String str = null;
        //读取客户端发送的消息
        try {
            while ((str = reader.readLine()) != null){
                //协议操作类型
                String [] msgs = str.split(" ");
                if("LOGIN".equals(msgs[0])){
                    //登录
                    String username = msgs[1];
                    String password = msgs[2];
                    //调用登录方法


                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
