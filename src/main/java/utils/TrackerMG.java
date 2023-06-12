package utils;

import dao.UserMapper;
import domain.User;
import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import service.Peer.Model.PeerInfo;
import service.Tracker.InfoServerListener;
import domain.Torrent;
import service.Tracker.TorrentsMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Data
public class TrackerMG  {
    public final static int TorrentPort = 49999;
    //文件分片大小
    public final static int FilePieceSize = 1024*1024;

    private HashMap<PeerInfo,HashSet<String>> ipToTorrent = new HashMap<>();
    private HashMap<String,HashSet<PeerInfo>> TorrentToIp = new HashMap<>();
    private HashSet<String> hasTorrent = new HashSet<>();

    private LargeFileHashCalculator largeFileHashCalculator = new LargeFileHashCalculator();
    //使用单例模式
    private static TrackerMG instance = new TrackerMG();
    private TrackerMG() {}
    public static TrackerMG getInstance() {
        return instance;
    }

    private TorrentsMap torrentsMap = new TorrentsMap();

    public void init(){

    }

    public void start() {
        //初始化种子文件列表
        File file = new File("./src/TestFile/torrents/");

        File[] files =file.listFiles();
        ArrayList<Torrent> torrents = new ArrayList<>();
        try {

            for (File f : files){
                ObjectInputStream ob = new ObjectInputStream(new FileInputStream(f));
                torrents.add ((Torrent)ob.readObject());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //启动用户界面
        // new TrackerForm().start();
        //启动监听线程
        new InfoServerListener().start();

    }


    //获取文件的哈希值
    public String getFileHash(File file)  {
        try {
            return LargeFileHashCalculator.getHash(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //启动服务器信息监听线程
    public void startInfoServerListener() {
        new InfoServerListener().start();
    }

    //校验用户的用户名和密码
    public boolean checkUser(String username, String password) {
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.login(username, password);
        return user != null;
    }

    //用户注册
    public void registerUser(User user) {
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.register(user);
        session.commit();
        session.close();
    }

    //查找该用户的积分
    public int selectScore(String username){
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        String score = mapper.selectScore(username);
        return Integer.parseInt(score);
    }

    //查看用户名是否已存在
    public boolean checkUsername(String username){
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUser(username);
        return user != null;
    }

    //修改由用户信息
    public void updateUser(User user) {
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        mapper.updateUser(user);
        session.commit();
        session.close();
    }
}
