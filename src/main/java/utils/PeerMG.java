package utils;

import service.Peer.FileTransmission.StatusOfTotalFile;
import service.Peer.Sender.AccessInfoToTrackerSender;
import service.Peer.page.Home;

import service.Peer.Model.PeerInfo;
import service.Peer.Sender.InfoToTrackerSender;
import service.Peer.TorrentFileTransmissionThread;
import domain.Torrent;
import domain.TorrentFile;
import service.Peer.page.Login;
import service.Peer.page.Register;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class PeerMG {

    public final static int InfoPort = 5204;
    public final static int FilePort = 9999;
    public static int FilePieceSize = 1024 * 1024;
    private String TrackerIP = "127.0.0.1";
    private HashMap<String, HashSet<PeerInfo>> hashToPeerInfo = new HashMap<>();
    private HashMap<String, ArrayList<StatusOfTotalFile>> hashALLToTotalFileStatus = new HashMap<>();
    private HashMap<String, StatusOfTotalFile> hashToTotalFileStatus = new HashMap<>();

    public HashMap<String, ArrayList<StatusOfTotalFile>> getHashALLToTotalFileStatus() {
        return hashALLToTotalFileStatus;
    }

    public HashMap<String, StatusOfTotalFile> getHashToTotalFileStatus() {
        return hashToTotalFileStatus;
    }

    private InfoToTrackerSender infoToTrackerSender;

    //登录界面
    private final Login login = new Login();
    //注册界面
    private final Register register = new Register();
    //主界面
    private final Home home = new Home();
    //使用单例模式
    private static PeerMG instance = new PeerMG();

    private PeerMG() {
    }

    public static PeerMG getInstance() {
        return instance;
    }

    public String getTrackerIP() {
        return TrackerIP;
    }

    public void setTrackerIP(String trackerIP) {
        TrackerIP = trackerIP;
    }



    //分析所有的文件状况
    private StatusOfTotalFile pieceInfoAnalyse(ArrayList<StatusOfTotalFile> statusOfTotalFiles){
        //暂定
        return null;
    }



    //与服务器建立连接
    public void ConnectToServer() {

    }

    public Login getLogin() {
        return login;
    }

    public Register getRegister() {
        return register;
    }

    public Home getHome() {
        return home;
    }

    //从文件制作种子文件
    public boolean MakeTorrentFromFile(ArrayList<File> files) {

        Torrent torrent = new Torrent();

        ArrayList<TorrentFile> torrentFiles = new ArrayList<TorrentFile>();

        for (File file : files) {
            String path = file.getName();
            if (file.isDirectory()) {
                torrentFiles.add(MakeTorrentFromFileCirculate(file, path));
            } else {
                torrentFiles.add(new TorrentFile(file, path));
            }
        }

        torrent.setFileList(torrentFiles);

        //将种子对象写入文件
        StorageTorrent(torrent);

        //将种子文件发送到服务器
        SendTorrent(torrent);

        return true;
    }


    //将种子对象写入文件
    private void StorageTorrent(Torrent torrent) {
        try {
            File file = new File("./src/temp.torrent");

            //利用对象输出流，将种子对象写入文件
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(torrent);
            objectOutputStream.close();

            //获取给文件的哈希值(该哈希值为文件的唯一标识)
            String hash = LargeFileHashCalculator.getHash(file);
            File fileNew = new File("./src/" + hash + ".torrent");
            //将文件重命名
            file.renameTo(fileNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //递归制作种子文件
    private TorrentFile MakeTorrentFromFileCirculate(File now, String path) {
        String newPath = path + "/" + now.getName();
        if (now.isDirectory()) {
            TorrentFile torrentFile = new TorrentFile(now, newPath);
            for (File file : now.listFiles()) {

                torrentFile.addChildren(MakeTorrentFromFileCirculate(file, newPath));
            }
            return torrentFile;
        } else {
            return new TorrentFile(now, newPath);
        }
    }

    //发送种子文件
    private boolean SendTorrent(Torrent torrent) {
        infoToTrackerSender.Send("Torrent");
        new TorrentFileTransmissionThread(torrent).start();
        return true;
    }

    public HashMap<String, HashSet<PeerInfo>> getHashToPeerInfo() {
        return hashToPeerInfo;
    }

    public void setHashToPeerInfo(HashMap<String, HashSet<PeerInfo>> hashToPeerInfo) {
        this.hashToPeerInfo = hashToPeerInfo;
    }


    //跳转到登录页面
    public void switchLogin(boolean flag) {
        //flag用来判断是由首界面还是注册界面跳转过来的
        if (flag) {
            //清空注册界面信息
            register.getID().setText("");
            register.getM1().setText("");
            register.getM2().setText("");
            register.setVisible(false);
        } else {
            home.setVisible(false);
        }
        login.setVisible(true);
    }

    //跳转到注册页面
    public void switchRegister() {
        //清空登录界面信息
        login.getID2().setText("");
        login.getPw().setText("");
        login.setVisible(false);
        register.setVisible(true);
    }

    //跳转到首页面
    public void switchHome(int score) {
        //清空登录界面信息
        login.getID2().setText("");
        login.getPw().setText("");
        login.setVisible(false);
        login.setVisible(false);
        home.setVisible(true);
    }

    //判断用户名和密码是否正确
    public void ToHome(String username, String password) {
        //按照协给tracker服务器发送登录消息
        String msg = "LOGIN|" + username + "|" + password;
        //启动发送访问信息线程
        new AccessInfoToTrackerSender(msg).start();
    }

    //检查用户名是否已经存在
    public void checkUsername(String username) {
        //按照协议给tracker服务器发送注册消息
        String msg = "CHECK|" + username;
        //启动发送访问信息线程
        new AccessInfoToTrackerSender(msg).start();
    }

    //显示登录失败信息
    public void loginFailed(String msg) {
        login.getWarning2().setText(msg);
    }

    //显示用户名是否重复
    public void usernameRepeated(boolean flag) {
        if (flag) {
            register.getUserWarning().setText("用户名已存在");
        } else {
            register.getUserWarning().setText("");
        }
    }

    //检查两次输入的密码是否一致
    public boolean checkPassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            register.getPasswordWarning().setText("两次密码输入不一致");
            return false;
        } else {
            register.getPasswordWarning().setText("");
        }
        return true;
    }

    //注册
    public void register(String username, String password) {
        //按照协议给tracker服务器发送注册消息
        String msg = "REGISTER|" + username + "|" + password;
        //启动发送访问信息线程
        new AccessInfoToTrackerSender(msg).start();
    }

    public void registerFailed() {
        register.getUserWarning().setText("用户名已存在");
    }

    public String getTrackerIp() {
        return TrackerIP;
    }

    public int getTrackerInfoPort() {
        return InfoPort;
    }
}
