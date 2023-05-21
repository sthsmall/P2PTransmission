package Tracker;

import Tracker.Business.*;
import Tracker.Model.*;
import Tracker.Presentation.*;

import java.io.File;

public class TrackerMG  {
    final static int ProtocolPort = 8888;
    final static int FilePort = 9999;

    //使用单例模式
    private static TrackerMG instance = new TrackerMG();
    private TrackerMG() {}
    public static TrackerMG getInstance() {
        return instance;
    }


    private TorrentsMap torrentsMap = new TorrentsMap();



    public void start() {
        //初始化种子文件列表


        //启动用户界面
        new TrackerForm().start();
        //启动监听线程
        new ServerListener().start();
        //启动心跳线程
        new HeartBeat().start();



    }

    //获取文件的哈希值
    public String getFileHash(File file) {
        return new LargeFileHashCalculator(file).getHash();
    }

}
