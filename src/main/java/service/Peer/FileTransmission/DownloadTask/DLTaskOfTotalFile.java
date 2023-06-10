package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.TorrentFileTransmitter;

import java.io.File;
import java.util.ArrayList;

public class DLTaskOfTotalFile extends  Thread implements DownloadTask {
    @Override
    public void run() {
        //获取Torrent文件列表
        ArrayList<File> files = new ArrayList<>();
        File source = new File("./Torrent");
        String lists[] = source.list();
        for (String list : lists) {
            new TorrentFileTransmitter(new File("./Torrent/" + list)).start();
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
