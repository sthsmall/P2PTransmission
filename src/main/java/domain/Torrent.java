package domain;

import lombok.Data;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;

import java.io.*;
import java.util.ArrayList;

//种子文件
@Data
public class Torrent implements Serializable {
    //种子文件的名称
    private String name;//
    //种子文件的大小
    private long size;
    //种子文件的哈希值
    private String hash;
    //种子文件的创建者
    private String creator;
    //种子文件的创建时间
    private String createTime;
    //种子文件的描述
    private String description;
    //种子文件的下载地址
    private String downloadUrl;
    //种子的文件列表
    private ArrayList<TorrentFile> fileList;

    public static Torrent createTorrentFromFile(File file) throws IOException, ClassNotFoundException {
        Torrent torrent;
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        torrent = (Torrent) objectInputStream.readObject();
        objectInputStream.close();
        return torrent;
    }


    public StatusOfSingleFile getFileStruct(){
        StatusOfSingleFile statusOfSingleFile = new StatusOfSingleFile();
        for(TorrentFile torrentFile: fileList){
            String path = torrentFile.getPath();
            if(torrentFile.isDirectory()){
                statusOfSingleFile.addChildren(CirculateOfGetFileStruct(torrentFile));
            }else{
                statusOfSingleFile.addChildren(new StatusOfSingleFile(torrentFile.getFile(),path));
            }
        }
        return statusOfSingleFile;
    }

    private StatusOfSingleFile CirculateOfGetFileStruct (TorrentFile torrentFile){
        StatusOfSingleFile statusOfSingleFile = new StatusOfSingleFile(torrentFile.getFile(),torrentFile.getPath());

        for(TorrentFile torrentFile1: torrentFile.getChildren()){
            if(torrentFile1.isDirectory()){
                statusOfSingleFile.addChildren(CirculateOfGetFileStruct(torrentFile1));
            }else{
                statusOfSingleFile.addChildren(new StatusOfSingleFile(torrentFile1.getFile(),torrentFile1.getPath()));
            }
        }
        return statusOfSingleFile;
    }


}
