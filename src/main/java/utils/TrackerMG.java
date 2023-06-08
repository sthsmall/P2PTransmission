package utils;

import dao.UserMapper;
import domain.User;
import org.apache.ibatis.session.SqlSession;
import service.Tracker.InfoServerListener;
import domain.Torrent;
import service.Tracker.TorrentsMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class TrackerMG  {
    final static int ProtocolPort = 8888;

    final static int FilePort = 9999;
    //文件分片大小
    public final static int FilePieceSize = 1024*1024;

    //使用单例模式
    private static TrackerMG instance = new TrackerMG();
    private TrackerMG() {}
    public static TrackerMG getInstance() {
        return instance;
    }

    private TorrentsMap torrentsMap = new TorrentsMap();

    public void start() {
        //初始化种子文件列表
        File file = new File("./src/TestFile/torrents/");

        File files[]=file.listFiles();
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

    //校验用户的用户名和密码
    public boolean CheckUser(String username, String password) {
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.login(username, password);
        return user != null;
    }

    //用户注册
    public boolean RegisterUser(User user) {
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user1 = mapper.selectUser(user.getUserName());
        if (user1 != null) {
            return false;
        }
        mapper.register(user);
        session.commit();
        session.close();
        return true;
    }

}
