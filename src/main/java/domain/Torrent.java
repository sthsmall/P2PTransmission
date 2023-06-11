package domain;

import lombok.Data;
import service.Peer.FileTransmission.SingleFileStatus;

import java.io.File;
import java.io.Serializable;
import java.security.Signature;
import java.util.ArrayList;

//种子文件
@Data
public class Torrent implements Serializable {
    //种子文件的名称
    private String name;
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


    public ArrayList<SingleFileStatus> getFileStruct(){
        ArrayList<SingleFileStatus> sfs = new ArrayList<>();
        for(TorrentFile torrentFile: fileList){
            String path = torrentFile.getPath();
            if(torrentFile.isDirectory()){
                sfs.add(CirculateOfGetFileStruct(torrentFile));
            }else{
                sfs.add(new SingleFileStatus(torrentFile.getFile(),path));
            }
        }
        return sfs;
    }
    public SingleFileStatus CirculateOfGetFileStruct (TorrentFile torrentFile){
        SingleFileStatus singleFileStatus = new SingleFileStatus(torrentFile.getFile(),torrentFile.getPath());

        for(TorrentFile torrentFile1: torrentFile.getChildren()){
            if(torrentFile1.isDirectory()){
                singleFileStatus.addChildren(CirculateOfGetFileStruct(torrentFile1));
            }else{
                singleFileStatus.addChildren(new SingleFileStatus(torrentFile1.getFile(),torrentFile1.getPath()));
            }
        }
        return singleFileStatus;
    }


}
