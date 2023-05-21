package Tracker.Model;

import java.io.Serializable;
import java.util.ArrayList;

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    //种子的文件列表
    private ArrayList<TorrentFile> fileList;

    public Torrent(){

    }

    public String getHash() {
        return hash;
    }

    public void setFileList(ArrayList<TorrentFile> torrentFiles) {
        this.fileList = torrentFiles;
    }

    public String getName() {
        return name;
    }
}
