package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.ASK.ASKPeerForFileStatuser;
import service.Peer.FileTransmission.ASK.ASKTrackerForPeerInfoer;
import service.Peer.FileTransmission.Downloader.DLofPiece;
import utils.PeerMG;

import java.io.File;
import java.util.*;

public class DLTaskOfTorrentFile extends Thread implements DownloadTask{
    File file;
    String hash;


    public DLTaskOfTorrentFile(File file ) {
        this.file = file;
        hash = file.getName();

    }
    @Override
    public void run() {
        ArrayList<DLofPiece> dlOfPieces = new ArrayList<>();
        //创建心跳线程
        ASKTrackerForPeerInfoer infoToTrackerSender = new ASKTrackerForPeerInfoer(file);
        infoToTrackerSender.run();
        ASKTrackerForPeerInfoer askTrackerForPeerInfoer = new ASKTrackerForPeerInfoer(file);
        askTrackerForPeerInfoer.start();
        ASKPeerForFileStatuser askPeerForFileStatuser = new ASKPeerForFileStatuser(file);
        askPeerForFileStatuser.start();
        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(1000);
                Queue<String> Piece = PeerMG.getInstance().getHashToDownloadList().get(hash);

                DLofPiece dlOfPiece = null;

                for(String path: Piece){
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


}
