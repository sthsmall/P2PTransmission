package service.Peer.FileTransmission.Downloader;

import service.Peer.FileTransmission.DownloadTask.DownloadTask;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;

import java.util.ArrayList;

public class DLofTorrentFile implements Downloader{
    //使用单例模式
    private static DLofTorrentFile instance = new DLofTorrentFile();
    private DLofTorrentFile(){}
    public static DLofTorrentFile getInstance(){
        return instance;
    }
    ArrayList<DownloadTask> downloadTasks = new ArrayList<>();
    //StatusOfSingleFile statusOfSingleFile = new StatusOfSingleFile();
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
        for(DownloadTask downloadTask : downloadTasks){
            downloadTask.startDownload();
        }
    }

    @Override
    public void pauseAllTasks() {
        for(DownloadTask downloadTask : downloadTasks){
            downloadTask.pauseDownload();
        }
    }

    @Override
    public void cancelAllTasks() {
        for(DownloadTask downloadTask : downloadTasks){
            downloadTask.cancelDownload();
        }
    }
}
