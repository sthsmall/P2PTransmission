package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.ASKPeerForFileStatuser;
import service.Peer.FileTransmission.ASKTrackerForPeerInfoer;
import service.Peer.FileTransmission.Downloader.DLofSingleFile;
import service.Peer.FileTransmission.StatusOfSingleFile;
import service.Peer.FileTransmission.StatusOfTotalFile;
import service.Peer.Model.PeerInfo;
import service.Peer.Sender.InfoToTrackerSender;
import utils.PeerMG;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

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
        ArrayList<DLofSingleFile> dlOfSingleFiles = new ArrayList<>();
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
                DLofSingleFile dlOfSingleFile = null;
                for(Map.Entry<String ,StatusOfSingleFile> entry : statusOfTotalFile.getHashToDownloadList().entrySet()){
                    dlOfSingleFile = new DLofSingleFile();
                    dlOfSingleFile.addAndStartTask(new DLTaskOfSingleFile(entry.getKey(),entry.getValue()));
                }
                dlOfSingleFiles.add(dlOfSingleFile);

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
