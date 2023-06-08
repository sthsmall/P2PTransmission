package service.Peer.FileTransmission;

import domain.TorrentFile;
import lombok.Data;
import utils.TrackerMG;

import java.io.File;
import java.util.ArrayList;
@Data
public class SingleFileStatus {
    //文件是否是文件夹
    private boolean isDirectory;
    //文件的名称
    private String name;
    //文件的大小
    private long size;
    //文件的哈希值
    private String hash;
    //文件的唯一路径
    private String path;
    //文件夹的子文件
    private ArrayList<SingleFileStatus> children;
    //文件
    private File file;

    private Boolean[] pieceStatus;


    public SingleFileStatus(File file,String path){
        this.file = file;
        this.name = file.getName();
        this.hash = TrackerMG.getInstance().getFileHash(file);
        this.size = file.length();
        this.path = path;
    }

    public void addChildren(SingleFileStatus singleFileStatus){
        children.add(singleFileStatus);
    }
}
