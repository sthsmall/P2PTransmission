package domain;

import lombok.Data;
import utils.TrackerMG;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

//种子中包含文件
@Data
public class TorrentFile  implements Serializable {
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
    private ArrayList<TorrentFile> children = new ArrayList<>();
    //文件
    private File file;

    public TorrentFile(File file){
        this.file = file;
        this.name = file.getName();
        this.hash = TrackerMG.getInstance().getFileHash(file);
        this.size = file.length();
    }

    public TorrentFile(File file,String path){
        this.file = file;
        this.name = file.getName();
//        this.hash = TrackerMG.getInstance().getFileHash(file);
        this.size = file.length();
        this.path = path;
        this.isDirectory = file.isDirectory();
    }

    public void addChildren(TorrentFile torrentFile){
        children.add(torrentFile);
    }

}
