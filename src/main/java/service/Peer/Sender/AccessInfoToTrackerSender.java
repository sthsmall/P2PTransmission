package service.Peer.Sender;

import utils.PeerMG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Period;

/*
访问tracker服务器进行用户登录、注册等操作
*/
public class AccessInfoToTrackerSender extends Thread {
    //服务器ip
    private String ip = PeerMG.getInstance().getTrackerIP();
    //服务器端口
    private int port = PeerMG.InfoPort;
    //发送方套接字
    private Socket socket;
    //输入输出流
    private PrintWriter pw;
    private BufferedReader br;
    //要发送的协议信息
    private String msg;

    public AccessInfoToTrackerSender(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        //获取输入输出流
        try {
            socket = new Socket(ip, port);
            //获取输入输出流
            pw = new PrintWriter(socket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            //发送消息
            pw.println(this.msg);
            pw.flush();
            //接收消息
            String reply = br.readLine();
            String[] infos = reply.split("\\|");
            if ("LOGIN".equals(infos[0])) {
                //登录
                if ("YES".equals(infos[1])) {
                    //允许登录
                    int score = Integer.parseInt(infos[2]);
                    PeerMG.getInstance().switchHome(score);
                } else {
                    //登录失败
                    String reason = infos[2];
                    PeerMG.getInstance().loginFailed(reason);
                }
            } else if ("REGISTER".equals(infos[0])) {
                if ("YES".equals(infos[1])) {
                    PeerMG.getInstance().switchLogin(true);
                } else {
                    PeerMG.getInstance().registerFailed();
                }
            } else if ("CHECK".equals(infos[0])) {
                //检查用户名是否存在
                if ("YES".equals(infos[1])) {
                    //用户名存在
                    PeerMG.getInstance().usernameRepeated(true);
                } else {
                    //用户名不存在
                    PeerMG.getInstance().usernameRepeated(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (br != null) {
                    br.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }
}
