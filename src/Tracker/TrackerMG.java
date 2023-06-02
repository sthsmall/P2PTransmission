package Tracker;

import Tracker.Business.*;
import Tracker.Model.*;
import Tracker.Presentation.*;

import java.io.*;
import java.security.NoSuchAlgorithmException;
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
    //计算文件的哈希值
    LargeFileHashCalculator largeFileHashCalculator = new LargeFileHashCalculator();

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
        new TrackerForm().start();
        //启动监听线程
        new ServerListener().start();
        //启动心跳线程
        new HeartBeat().start();



    }

    //获取文件的哈希值
    public String getFileHash(File file)  {
        try {
            return largeFileHashCalculator.getHash(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



}
