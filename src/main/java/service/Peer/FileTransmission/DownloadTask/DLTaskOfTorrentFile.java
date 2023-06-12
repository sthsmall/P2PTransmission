package service.Peer.FileTransmission.DownloadTask;

import domain.Torrent;
import domain.TorrentFile;
import service.Peer.FileTransmission.ASK.ASKPeerForFileStatuser;
import service.Peer.FileTransmission.ASK.ASKTrackerForPeerInfoer;
import service.Peer.FileTransmission.Downloader.DLofPiece;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
import utils.LargeFileHashCalculator;
import utils.PeerMG;

import java.io.*;
import java.util.*;

public class DLTaskOfTorrentFile extends Thread implements DownloadTask{
    File file;
    String hash;
    Torrent torrent;

    public DLTaskOfTorrentFile(File file) {
        this.file = file;
        this.hash = file.getName();

    }
    @Override
    public void run() {
        ArrayList<DLofPiece> dlOfPieces = new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            this.torrent = (Torrent) objectInputStream.readObject();
            PeerMG.getInstance().getHashToTorrent().put(hash,torrent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //创建文件状态
        StatusOfSingleFile fileStruct = torrent.getFileStruct();
        File filek = new File("./src/Download/"+torrent.getName());
        filek.mkdir();
        File tempFile = null;
        //遍历文件状态将每个文件状态加入到总文件状态中
        for(StatusOfSingleFile s : fileStruct.getChildren()){
            for(StatusOfSingleFile ss : s.getChildren()){
                if(ss.isDirectory()){
                    tempFile = new File("./src/Download/"+torrent.getName()+"/"+ss.getPath());
                    tempFile.mkdir();
                    cire(ss);
                }else {
                    tempFile = new File("./src/Download/"+torrent.getName()+"/"+ss.getPath());
                    try {
                        tempFile.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    PeerMG.getInstance().getHashToStatusOfSingleFile().put(ss.getPath(),ss);
                }
            }
        }

        StatusOfTotalFile statusOfTotalFile = new StatusOfTotalFile();
        statusOfTotalFile.setFileStruct(fileStruct);
        PeerMG.getInstance().getHashToStatusOfTotalFile().put(file.getName(),statusOfTotalFile);

        //创建心跳线程
        ASKTrackerForPeerInfoer askTrackerForPeerInfoer = new ASKTrackerForPeerInfoer(file);
        askTrackerForPeerInfoer.start();
        ASKPeerForFileStatuser askPeerForFileStatuser = new ASKPeerForFileStatuser(file);
        askPeerForFileStatuser.start();
        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(1000);
                Queue<String> Piece = PeerMG.getInstance().getHashToDownloadList().get(hash);
                if(Piece == null){
                    Thread.sleep(1000);
                    continue;
                }
                DLofPiece dlOfPiece = null;
                for(String path: Piece){
                    Thread.sleep(1000);
                    dlOfPiece = new DLofPiece();
                    dlOfPiece.addAndStartTask(new DLTaskOfPiece(hash, path));
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public void startDownload() {
        new Thread(this).start();
    }

    @Override
    public void pauseDownload() {
        new Thread(this).interrupt();
    }

    @Override
    public void cancelDownload() {
        new Thread(this).interrupt();
    }

    //递归Torrent文件结构，将文件路径和状态对应存入HashMap
    private void cire(StatusOfSingleFile statusOfSingleFile){
        for (StatusOfSingleFile s : statusOfSingleFile.getChildren()){
            if(s.isDirectory()){
                File file = new File("./src/Download/"+torrent.getName()+"/"+s.getPath());
                file.mkdir();
                cire(s);
            }else {
                PeerMG.getInstance().getHashToStatusOfSingleFile().put(s.getPath(),s);
            }
        }
    }
}
