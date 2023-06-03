package Peer;

import Peer.Business.ProtocolCommunicator;
import Peer.Business.TorrentFileTransmissionThread;
import Tracker.Business.LargeFileHashCalculator;
import Tracker.Model.Torrent;
import Tracker.Model.TorrentFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PeerMG {

    public final static int ProtocolPort = 8888;
    public final static int FilePort = 9999;

    //使用单例模式
    private static PeerMG instance = new PeerMG();
    private PeerMG() {}
    public static PeerMG getInstance() {
        return instance;
    }
    //计算文件的哈希值
    LargeFileHashCalculator largeFileHashCalculator = new LargeFileHashCalculator();
    private ProtocolCommunicator protocolCommunicator;

    //与服务器建立连接
    public void ConnectToServer(){
        protocolCommunicator = new ProtocolCommunicator();
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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(torrent);
            objectOutputStream.close();
            String hash = largeFileHashCalculator.getHash(file);
            File fileNew = new File("./src/"+hash+".torrent");
            file.renameTo(fileNew);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //递归制作种子文件
    private TorrentFile MakeTorrentFromFileCirculate(File now,String path){
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

    //发送种子文件
    private boolean SendTorrent(Torrent torrent){
        protocolCommunicator.Send("Torrent");
        new TorrentFileTransmissionThread(torrent).start();
        return true;
    }

}
