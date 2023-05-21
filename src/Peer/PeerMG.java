package Peer;

import Tracker.Model.Torrent;
import Tracker.Model.TorrentFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PeerMG {
    //使用单例模式
    private static PeerMG instance = new PeerMG();
    private PeerMG() {}
    public static PeerMG getInstance() {
        return instance;
    }

    //从文件制作种子文件
    public boolean MakeTorrentFromFile(ArrayList<File> files) {

        Torrent torrent = new Torrent();

        ArrayList<TorrentFile> torrentFiles = new ArrayList<TorrentFile>();

        for(File file : files ){
            String path = file.getName();
            if(file.isDirectory()){
                torrentFiles.add(MakeTorrentFromFileCirculate(file,path));
            }else{
                torrentFiles.add(new TorrentFile(file,path));
            }
        }

        torrent.setFileList(torrentFiles);
        return true;
    }
    //递归制作种子文件
    public TorrentFile MakeTorrentFromFileCirculate(File now,String path){
        String newPath = path + "/" + now.getName();
        if(now.isDirectory()){

            TorrentFile torrentFile = new TorrentFile(now,newPath);
            for(File file : now.listFiles()){
                torrentFile.addChildren(MakeTorrentFromFileCirculate(file,newPath));
            }
            return torrentFile;
        }else {
            return new TorrentFile(now,newPath);
        }
    }


}
