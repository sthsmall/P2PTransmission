package service.Peer.FileTransmission.DownloadTask;

import domain.Torrent;
import service.Peer.FileTransmission.ASK.ASKPeerForFileStatuser;
import service.Peer.FileTransmission.ASK.ASKTrackerForPeerInfoer;
import service.Peer.FileTransmission.Downloader.DLofPiece;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
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
        //获得文件结构
        StatusOfSingleFile fileStruct = torrent.getFileStruct();

        //创建实际文件结构

        File filek = new File("./src/Download/"+torrent.getName());

        filek.mkdir();
        File tempFile = null;
        //遍历文件状态将每个文件状态加入到总文件状态中
        for(StatusOfSingleFile s : fileStruct.getChildren()){
            for(StatusOfSingleFile ss : s.getChildren()){
                if(ss.isDirectory()){
                    tempFile = new File("./src/Download/"+torrent.getName()+"/"+ss.getPath());
                    tempFile.mkdirs();
                    cire(ss);
                }else {
                    tempFile = new File("./src/Download/"+torrent.getName()+"/"+ss.getPath());
                    try {
                        if(!tempFile.exists()) {
                            System.out.println(tempFile.createNewFile());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //将文件状态加入到总文件状态中
                    PeerMG.getInstance().getHashToStatusOfSingleFile().put(ss.getPath(),ss);
                }
            }
        }


        //将下载任务加入到下载队列中
        PeerMG.getInstance().getNowDownloadingTorrents().add(hash);

        //创建本Torrent文件下载状态
        StatusOfTotalFile statusOfTotalFile = new StatusOfTotalFile();

        statusOfTotalFile.setFileStruct(fileStruct);

        //将本Torrent文件下载状态加入到总下载状态中
        PeerMG.getInstance().getHashToStatusOfTotalFile().put(file.getName(),statusOfTotalFile);

        //创建心跳线程
        ASKTrackerForPeerInfoer askTrackerForPeerInfoer = new ASKTrackerForPeerInfoer(file);
        askTrackerForPeerInfoer.start();

        ASKPeerForFileStatuser askPeerForFileStatuser = new ASKPeerForFileStatuser(file);
        askPeerForFileStatuser.start();

        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(3000);
                Queue<String> Piece = PeerMG.getInstance().getHashToDownloadList().get(hash);
                if(Piece == null || Piece.size() == 0){


                    System.out.println("No Piece to download");
                    Thread.sleep(1000);
                    continue;
                }
                DLofPiece dlOfPiece = null;

                int k = 0;
                for(String path: Piece){
                    if(k++ > 30){
                        break;
                    }
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
                file.mkdirs();
                cire(s);
            }else {
                PeerMG.getInstance().getHashToStatusOfSingleFile().put(s.getPath(),s);
                File file = new File("./src/Download/"+torrent.getName()+"/"+s.getPath());
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
