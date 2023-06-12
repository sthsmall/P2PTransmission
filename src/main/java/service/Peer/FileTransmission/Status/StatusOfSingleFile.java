package service.Peer.FileTransmission.Status;

import lombok.Data;
import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.File;
import java.util.*;

@Data
public class StatusOfSingleFile {
    //文件是否是文件夹
    private boolean isDirectory;

    public StatusOfSingleFile() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusOfSingleFile that = (StatusOfSingleFile) o;
        return Objects.equals(hash, that.hash) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, path);
    }

    //文件的名称
    private String name;
    //文件的大小
    private long size;
    //文件的哈希值
    private String hash;
    //文件的唯一路径
    private String path;
    //文件夹的子文件
    private ArrayList<StatusOfSingleFile> children = new ArrayList<>();
    //文件
    private File file;

    private Boolean[] pieceStatus;

    private HashMap<Integer, PeerInfo> pieceToPeerInfo = new HashMap<>();


    public StatusOfSingleFile(File file, String path){
        this.file = file;
        this.name = file.getName();
        //this.hash = TrackerMG.getInstance().getFileHash(file);
        this.size = file.length();
        this.path = path;
        this.isDirectory = file.isDirectory();
        this.pieceStatus = new Boolean[(int) Math.ceil((double) size / PeerMG.FilePieceSize)];

    }

    public void addChildren(StatusOfSingleFile statusOfSingleFile){
        children.add(statusOfSingleFile);
    }

    public HashMap<String, StatusOfSingleFile> getHashToStatusOfSingleFile() {
        HashMap<String, StatusOfSingleFile> hashToStatusOfSingleFile = new HashMap<>();
        for (StatusOfSingleFile child : children) {
            if (child.isDirectory) {
                Map.Entry<String, StatusOfSingleFile> stringStatusOfSingleFileEntry = recursionGetStatusOfSingleFile(child);
                hashToStatusOfSingleFile.put(stringStatusOfSingleFileEntry.getKey(), stringStatusOfSingleFileEntry.getValue());
            } else {
                hashToStatusOfSingleFile.put(child.hash, child);
            }
        }
        return hashToStatusOfSingleFile;
    }
    //递归遍历StatusOfSingleFile，将其转化为HashMap<String, StatusOfSingleFile>，其中String为
    // 非文件夹的文件的相对路径，StatusOfSingleFile为该文件的分片状态
    public Map.Entry<String ,StatusOfSingleFile> recursionGetStatusOfSingleFile(StatusOfSingleFile statusOfSingleFile){
        if(statusOfSingleFile.isDirectory){
            for(StatusOfSingleFile child : statusOfSingleFile.children){
                recursionGetStatusOfSingleFile(child);
            }
        }else{
            HashMap<String, StatusOfSingleFile> hashToStatusOfSingleFile = new HashMap<>();
            hashToStatusOfSingleFile.put(statusOfSingleFile.getPath(), statusOfSingleFile);
            return hashToStatusOfSingleFile.entrySet().iterator().next();
        }
        return null;
    }

    HashSet<String> hashToAllPiece = new HashSet<>();
    public HashSet<String> getHashToAllPiece() {

        for (StatusOfSingleFile child : children) {
            if (child.isDirectory) {
                recursion(child);
            } else {
                for(int i = 0; i < child.pieceStatus.length; i++){
                    if(child.pieceStatus[i] == true){
                        hashToAllPiece.add(child.hash +":"+ i);
                    }
                }
            }

        }
        return hashToAllPiece;
    }

    //递归遍历StatusOfSingleFile，将其转化为HashMap<String, StatusOfSingleFile>，其中String为
    // 非文件夹的文件的相对路径，StatusOfSingleFile为该文件的分片状态
    public void  recursion(StatusOfSingleFile statusOfSingleFile){
        if(statusOfSingleFile.isDirectory){
            for(StatusOfSingleFile child : statusOfSingleFile.children){
                recursion(child);
            }
        }else{
            for(int i = 0; i < statusOfSingleFile.pieceStatus.length; i++){
                if(statusOfSingleFile.pieceStatus[i] == true){
                    hashToAllPiece.add(statusOfSingleFile.hash +":"+ i);
                }
            }
        }
    }

    public void setPieceStatus(int index, boolean status){
        pieceStatus[index] = status;
    }


    public void addPiece(int pieceIndex) {
        pieceStatus[pieceIndex] = true;
    }
}
