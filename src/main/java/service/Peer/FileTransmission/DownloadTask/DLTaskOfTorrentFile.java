package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.ASKPeerForFileStatuser;
import service.Peer.FileTransmission.ASKTrackerForPeerInfoer;
import service.Peer.FileTransmission.StatusOfTotalFile;
import service.Peer.Model.PeerInfo;
import service.Peer.Sender.InfoToTrackerSender;
import utils.PeerMG;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class DLTaskOfTorrentFile extends Thread implements DownloadTask{
    File file;
    String hash;
    StatusOfTotalFile statusOfTotalFile;
    public DLTaskOfTorrentFile(File file) {
        this.file = file;
        hash = file.getName();
        statusOfTotalFile = new StatusOfTotalFile();
    }
    @Override
    public void run() {
        ArrayList<DLTaskOfSingleFile> dlTaskOfSingleFiles = new ArrayList<>();
        //创建心跳线程
        InfoToTrackerSender infoToTrackerSender = new InfoToTrackerSender(file);
        infoToTrackerSender.run();
        ASKTrackerForPeerInfoer askTrackerForPeerInfoer = new ASKTrackerForPeerInfoer(file);
        askTrackerForPeerInfoer.start();
        ASKPeerForFileStatuser askPeerForFileStatuser = new ASKPeerForFileStatuser(file);
        askPeerForFileStatuser.start();
        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(1000);
                StatusOfTotalFile statusOfTotalFile = PeerMG.getInstance().getHashToDownloadList().get(hash);


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
