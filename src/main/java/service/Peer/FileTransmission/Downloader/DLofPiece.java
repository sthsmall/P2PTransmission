package service.Peer.FileTransmission.Downloader;

import domain.Torrent;
import service.Peer.FileTransmission.DownloadTask.DownloadTask;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;

import java.util.ArrayList;
import java.util.HashMap;

public class DLofPiece implements Downloader{
//使用单例模式
    private static DLofPiece instance = new DLofPiece();
    public DLofPiece(){}
    public static DLofPiece getInstance(){
        return instance;
    }

    ArrayList<DownloadTask> downloadTasks = new ArrayList<>();


    @Override
    public void addTask(DownloadTask task) {
        downloadTasks.add(task);
    }

    @Override
    public void addAndStartTask(DownloadTask task) {
        downloadTasks.add(task);
        task.startDownload();
    }

    @Override
    public void removeTask(DownloadTask task) {
        downloadTasks.remove(task);
    }

    @Override
    public void startAllTasks() {
        for(DownloadTask task:downloadTasks){
            task.startDownload();
        }
    }

    @Override
    public void pauseAllTasks() {
        for (DownloadTask task:downloadTasks){
            task.pauseDownload();
        }
    }

    @Override
    public void cancelAllTasks() {
        for (DownloadTask task:downloadTasks){
            task.cancelDownload();
        }
    }
}
