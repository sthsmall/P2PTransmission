package Tracker.Model;

import java.io.Serializable;
import java.util.ArrayList;

//种子类
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public ArrayList<TorrentFile> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<TorrentFile> fileList) {
        this.fileList = fileList;
    }
}
