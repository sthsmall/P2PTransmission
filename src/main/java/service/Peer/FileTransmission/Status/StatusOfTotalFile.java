package service.Peer.FileTransmission.Status;

import utils.PeerMG;

import java.io.Serializable;
import java.util.*;

public class StatusOfTotalFile implements Serializable {
    int len = PeerMG.FilePieceSize;
    HashMap<String,StatusOfSingleFile> hashToStatusOfSingleFile;

    Queue<Map.Entry<String,StatusOfSingleFile>> downloadList;

    StatusOfSingleFile statusOfSingleFile;

    HashSet<String> hashToAllPiece = new HashSet<>();

    public StatusOfTotalFile(){

    }

    public StatusOfTotalFile(StatusOfSingleFile statusOfSingleFile){
        this.statusOfSingleFile = statusOfSingleFile;
    }

    public void aboutStatus(){
        hashToStatusOfSingleFile = statusOfSingleFile.getHashToStatusOfSingleFile();
    }

    public void setDownloadList( Queue<Map.Entry<String,StatusOfSingleFile>> downloadList ){
        this.downloadList = downloadList;
    }

    //启动一个线程，持续刷新下载列表


    //返回下载列表


    public HashSet<String> getHashToAllPiece() {
        return statusOfSingleFile.getHashToAllPiece();
    }

    public Iterable<Map.Entry<String,StatusOfSingleFile>> getHashToDownloadList() {
        return downloadList;
    }

    public void setFileStruct(StatusOfSingleFile fileStruct) {
        statusOfSingleFile = fileStruct;
    }
}