package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.StatusOfTotalFile;
import service.Peer.Model.PeerInfo;
import service.Peer.Sender.InfoToTrackerSender;
import utils.PeerMG;

import java.io.File;
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
        //创建心跳线程
        InfoToTrackerSender infoToTrackerSender = new InfoToTrackerSender(file);
        infoToTrackerSender.run();
        HashSet<PeerInfo> peerInfos = PeerMG.getInstance().getHashToPeerInfo().get(hash);
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
