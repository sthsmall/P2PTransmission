package service.Peer.FileTransmission.DownloadTask;

import domain.Torrent;
import service.Peer.FileTransmission.Downloader.DLofTorrentFile;
import utils.PeerMG;

import java.io.*;
import java.util.ArrayList;

public class DLTaskOfTotalFile extends  Thread implements DownloadTask {
    ArrayList<DLofTorrentFile> dlOfTorrentFiles = new ArrayList<>();
    @Override
    public void run() {
        //获取Torrent文件列表
        ArrayList<File> files = new ArrayList<>();
        File source = new File("./Torrent");
        for( File file : source.listFiles()){
            if(file.getName().endsWith(".torrent")){
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    PeerMG.getInstance().getHashToTorrent().put(file.getName(),(Torrent)objectInputStream.readObject());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        while(!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(1000);
                String lists[] = source.list();
                for(String s : lists){
                    DLofTorrentFile dLofTorrentFile = new DLofTorrentFile();
                    dlOfTorrentFiles.add(dLofTorrentFile);

                    dLofTorrentFile.addAndStartTask(new DLTaskOfTorrentFile(new File("./Torrent/"+s)));
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
