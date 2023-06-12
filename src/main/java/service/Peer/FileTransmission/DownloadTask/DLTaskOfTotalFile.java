package service.Peer.FileTransmission.DownloadTask;

import domain.Torrent;
import domain.TorrentFile;
import service.Peer.FileTransmission.Downloader.DLofTorrentFile;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
import utils.PeerMG;

import java.io.*;
import java.util.ArrayList;

public class DLTaskOfTotalFile extends  Thread implements DownloadTask {
    ArrayList<DLofTorrentFile> dlOfTorrentFiles = new ArrayList<>();

    @Override
    public void run() {
        //获取Torrent文件列表
        ArrayList<File> files = new ArrayList<>();



//        try {
////                    PeerMG.getInstance().getHashToFile().put(file.getName(),file);
////                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
////                    Torrent torrent = (Torrent)objectInputStream.readObject();
////                    PeerMG.getInstance().getHashToTorrent().put(file.getName(),torrent);
//
//
//
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


        while(!Thread.currentThread().isInterrupted()){

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




}
