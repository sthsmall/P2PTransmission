package service.Peer.FileTransmission.Downloader;

import service.Peer.FileTransmission.DownloadTask.DownloadTask;

import java.util.ArrayList;

public class DLofPiece implements Downloader{
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
