package utils;

import lombok.Data;
import service.Peer.FileTransmission.ASK.ASKTrackerInfo;
import service.Peer.FileTransmission.DownloadTask.DLTaskOfTorrentFile;
import service.Peer.FileTransmission.Downloader.DLofTorrentFile;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
import service.Peer.Sender.AccessInfoToTrackerSender;
import service.Peer.page.*;

import service.Peer.Model.PeerInfo;
import service.Peer.FileTransmission.ASK.SendTorrentFileToTracker;
import domain.Torrent;
import domain.TorrentFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

@Data
public class PeerMG {

    public final static int InfoPort = 5204;
    public final static int FilePort = 49999;
    public final static int PeerStatusPort = 49999;
    public final static int TrackerTorrentPort = 1234;
    public final static int TrackerPort = 49999;
    public static int FilePieceSize = 1024 * 1024;
    public static int PieceReceivePort = 8899;
    private String TrackerIP = "192.168.122.116";
    //通过磁链获得Peer信息
    private HashMap<String, HashSet<PeerInfo>> hashToPeerInfo = new HashMap<>();
    //通过磁链获得分块信息
    private final HashMap<String, HashMap<String,Integer>> hashALLToTotalFileStatus = new HashMap<>();
    //通过磁链获得分块信息
    private final HashMap<String, Boolean> hashToTotalFileStatus = new HashMap<>();
    //获得torrent拥有情况
    private HashSet<String> NowDownloadingTorrents = new HashSet<>();
    //获得下载列表
    private  HashMap<String,Queue<String>> hashToDownloadList = new HashMap<>();

    public  HashMap<String,Queue<String>> getHashToDownloadList() {
        return hashToDownloadList;
    }

    //哈希值对应的种子
    private HashMap<String, Torrent> hashToTorrent = new HashMap<>();


    //每个子文件对应的文件信息
    private HashMap<String, StatusOfSingleFile> hashToStatusOfSingleFile = new HashMap<>();
    //每个Torrent文件对应的文件信息
    private HashMap<String, StatusOfTotalFile> hashToStatusOfTotalFile = new HashMap<>();

    private HashMap<String, File> HashToFile = new HashMap<>();

    public HashMap<String, Boolean> getHashToTotalFileStatus() {
        return hashToTotalFileStatus;
    }



    //登录界面
    private  final Login login = new Login();
    //注册界面
    private final Register register = new Register();
    //主界面
    private final Home home = new Home();
    //使用单例模式
    private static final PeerMG instance = new PeerMG();


    private PeerMG() {
    }





    //分析所有的文件状况
    public Queue<String> strategyOfDownload(HashMap<String,Integer> status){
        PriorityQueue<Map.Entry<String,Integer>> priorityQueue = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String,Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });

        for (Map.Entry<String,Integer> entry : status.entrySet()){
            priorityQueue.add(entry);
        }
        Queue<String> arrayList = new LinkedList<>();
        for (Map.Entry<String,Integer> entry : priorityQueue){
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }


    //用户信息更改界面
    private final Edit edit = new Edit();

    private final Link link = new Link();
    private final int LOGIN = 0;
    private final int REGISTER = 1;
    private final int HOME = 2;
    private final int EDIT = 3;

    //与服务器建立连接
    public void ConnectToServer() {

    }

    //从文件制作种子文件
    public File MakeTorrentFromFile(ArrayList<File> files,String name) {

        Torrent torrent = new Torrent();
        torrent.setName(name);
        ArrayList<TorrentFile> torrentFiles = new ArrayList<TorrentFile>();


        for (File file : files) {
            String path = file.getName();
            if (file.isDirectory()) {
                TorrentFile torrentFile = new TorrentFile(file, path);
                MakeTorrentFromFileCirculate(file,torrentFile);
                torrentFiles.add(torrentFile);
            } else {
                torrentFiles.add(new TorrentFile(file, path));
            }
        }

        torrent.setFileList(torrentFiles);

        //将种子对象写入文件
        File file = StorageTorrent(torrent);

        //将种子对象加入
        PeerMG.getInstance().getHashToTorrent().put(file.getName(), torrent);

        //将种子文件发送到服务器
        SendTorrent(file);
        return file;
    }


    //将种子对象写入文件
    public File StorageTorrent(Torrent torrent) {
        File f = null;
        try {
            File file = new File("./src/Torrents/temp.torrent");

            //利用对象输出流，将种子对象写入文件
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(torrent);
            objectOutputStream.close();

            //获取给文件的哈希值(该哈希值为文件的唯一标识)
            String hash = LargeFileHashCalculator.getHash(file);
            File fileNew = new File("./src/Torrents/" + hash + ".torrent");
            //将文件重命名
            file.renameTo(fileNew);
            f = fileNew;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("hahaha"+f.getName());
        return f;
    }

    //递归制作种子文件
    private void MakeTorrentFromFileCirculate(File now,TorrentFile torrentFile) {
        String newPath = null;

        for(File file : now.listFiles()){
            if(file.isDirectory()){
                newPath = torrentFile.getPath() + "/" + file.getName();
                TorrentFile torrentFile1 = new TorrentFile(file, newPath);
                MakeTorrentFromFileCirculate(file,torrentFile1);
                torrentFile.addChildren(torrentFile1);
            }else{
                newPath = torrentFile.getPath() + "/" + file.getName();
                torrentFile.addChildren(new TorrentFile(file, newPath));
            }
        }

    }



    //发送种子文件
    private boolean SendTorrent(File torrent) {
        new SendTorrentFileToTracker(torrent).start();
        return true;
    }



    //跳转到登录页面
    public void switchLogin(boolean isRegister) {
        //flag用来判断是由首界面还是注册界面跳转过来的
        if (isRegister) {
            //清空注册界面信息
            clear(REGISTER);
            register.setVisible(false);
        } else {

            home.setVisible(false);
        }
        login.setVisible(true);
    }

    //跳转到注册页面
    public void switchRegister() {
        //清空登录界面信息
        clear(LOGIN);
        login.setVisible(false);
        register.setVisible(true);
    }

    //跳转到首页面
    public void switchHome(String username, int score) {
        //清空登录界面信息
        clear(LOGIN);
        login.setVisible(false);
        //在首页面显示用户名和积分
        home.getID().setText(username);
        home.getScore().setText(String.valueOf(score));
        PeerMG.getInstance().init();
        home.percent();
        home.setVisible(true);
    }

    //跳转至用户信息更改界面
    public void switchEdit(String username) {
        //首先清空用户信息更改界面
        clear(EDIT);
        //初始化用户信息更改界面
        edit.getNewUsernameField().setText(username);
        edit.setVisible(true);
    }

    //清空登录界面信息
    public void clear(int flag) {
        switch (flag) {
            case LOGIN:
                login.getID2().setText("");
                login.getPw().setText("");
                login.getWarning1().setText("");
                login.getWarning2().setText("");
                break;
            case REGISTER:
                register.getID().setText("");
                register.getM1().setText("");
                register.getM2().setText("");
                register.getUserWarning().setText("");
                register.getPasswordWarning().setText("");
                break;
            case EDIT:
                edit.getNewUsernameField().setText("");
                edit.getNewPwField().setText("");
                edit.getNewPeAckField().setText("");
                edit.getUsernameWarning().setText("");
                edit.getNewPwWaring1().setText("");
                edit.getNewPwWaring2().setText("");
                break;
        }
    }

    //欲进入主页面
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
    public boolean checkPassword(String password1, String password2, boolean isRegister) {
        if (isRegister) {
            if (!password1.equals(password2)) {
                register.getPasswordWarning().setText("两次密码输入不一致");
                return false;
            } else {
                register.getPasswordWarning().setText("");
            }
        } else {
            if (!password1.equals(password2)) {
                edit.getNewPwWaring2().setText("两次密码输入不一致");
                return false;
            } else {
                edit.getNewPwWaring2().setText("");
            }
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

    //更新用户信息
    public void update(String username, String password) {
        //按照协议给tracker服务器发送注册消息
        String msg = "UPDATE|" + username + "|" + password;
        //启动发送访问信息线程
        new AccessInfoToTrackerSender(msg).start();
    }

    //显示注册失败原因
    public void registerFailed() {
        register.getUserWarning().setText("用户名已存在");
    }

    //关闭用户信息更改界面
    public void closeEdit() {
        //清空用户信息更改界面信息
        clear(EDIT);
        edit.setVisible(false);
    }


    public void closeLink() {
        link.setVisible(false);
    }

    public void openLink() {
        link.setVisible(true);
    }

    public DLTaskOfTorrentFile AddDownLoad(String hash,boolean isfull){
        ArrayList<String> torrentL = home.getTorrentList();
        Torrent torrent = PeerMG.getInstance().getHashToTorrent().get(hash);
        //将种子文件添加到下载任务
        File file = PeerMG.getInstance().getHashToFile().get(hash);
        //创建下载任务
        DLTaskOfTorrentFile dll = new DLTaskOfTorrentFile(file);
        //将下载任务添加到下载任务列表
        DLofTorrentFile.getInstance().addAndStartTask(dll);
        //将下载任务添加到主界面
        torrentL.add(torrent.getName());

        getHome().addOneDownloadTask(hash,isfull);
        return dll;

    }

    public static PeerMG getInstance() {
        return instance;
    }

    public void addDownloadTorrent(Torrent torrent) {

    }

    public void init() {
        ASKTrackerInfo askTrackerInfo = new ASKTrackerInfo();
        askTrackerInfo.start();
    }

    public HashSet<String> getNowDownloadingTorrents() {
        return NowDownloadingTorrents;
    }
}
