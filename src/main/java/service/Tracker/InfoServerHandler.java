package service.Tracker;

import domain.User;
import utils.PeerMG;
import utils.TrackerMG;

import javax.sound.midi.Track;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class InfoServerHandler extends Thread {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String ip;

    public InfoServerHandler(Socket accept) {
        this.socket = accept;
        this.ip = socket.getInetAddress().getHostAddress();
        //初始化流
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String str = "";
        //读取客户端发送的消息
        try {
            while ((str = reader.readLine()) != null) {
                //协议操作类型
                String[] msgs = str.split("\\|");
                System.out.println(msgs[0]);
                if ("LOGIN".equals(msgs[0])) {
                    //登录
                    String username = msgs[1];
                    String password = msgs[2];
                    //调用检查用户信息方法
                    if (TrackerMG.getInstance().checkUser(username, password)) {
                        //数据库中存在该用户，允许登录,同时将该用户积分发送携带过去
                        int score = TrackerMG.getInstance().selectScore(username);
                        String reply = "LOGIN|" + "YES|" + score;
                        writer.println(reply);
                    } else {
                        //用户名或者密码错误
                        String reason = "用户名或者密码错误";
                        String reply = "LOGIN|" + "NO|" + reason;
                        writer.println(reply);
                    }
                } else if ("REGISTER".equals(msgs[0])) {
                    //注册
                    String username = msgs[1];
                    String password = msgs[2];
                    String reply = "REGISTER|" + "NO";
                    //调用检查用户名是否存在方法
                    if (!TrackerMG.getInstance().checkUsername(username)) {
                        //调用注册方法
                        TrackerMG.getInstance().registerUser(new User(username, password));
                        //注册成功
                        reply = "REGISTER|" + "YES";
                    }
                    writer.println(reply);
                } else if ("CHECK".equals(msgs[0])) {
                    //检查用户名是否存在
                    String username = msgs[1];
                    if (TrackerMG.getInstance().checkUsername(username)) {
                        //用户名存在
                        String reply = "CHECK|" + "YES";
                        writer.println(reply);
                        System.out.println(11);
                    } else {
                        //用户名不存在
                        String reply = "CHECK|" + "NO";
                        writer.println(reply);
                        System.out.println(111);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
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
