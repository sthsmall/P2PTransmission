package Tracker.Business;

import Tracker.TrackerMG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler extends Thread{
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String ip;

    public ServerHandler(Socket accept) {
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
//        try {
//            while((str = reader.readLine()) != null) {
//                System.out.println("收到来自" + ip + "的消息：" + str);
//                //处理消息
//                String[] strs = str.split(" ");
//                if(strs[0].equals("register")) {
//                    //注册
//                    TrackerMG.getInstance().register(strs[1], ip);
//                }else if(strs[0].equals("unregister")) {
//                    //注销
//                    TrackerMG.getInstance().unregister(strs[1], ip);
//                }else if(strs[0].equals("login")) {
//                    //登录
//                    TrackerMG.getInstance().login(strs[1], ip);
//                }else if(strs[0].equals("exit")) {
//                    //退出
//                    TrackerMG.getInstance().unregister(strs[1], ip);
//                    break;
//                }else if(strs[0].equals("registerTorrentFile")) {
//                    //注册种子文件
//                    TrackerMG.getInstance().registerTorrentFile(strs[1]);
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
