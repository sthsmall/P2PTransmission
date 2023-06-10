package service.Peer.FileTransmission.Downloader;

import service.Peer.FileTransmission.DownloadTask.DownloadTask;

import java.util.ArrayList;

public class DLofTotalFile implements Downloader{
    ArrayList<DownloadTask> dltOfTotalFiles;
    @Override
    public void addTask(DownloadTask task) {
        dltOfTotalFiles.add(task);
    }

    @Override
    public void removeTask(DownloadTask task) {
        dltOfTotalFiles.remove(task);
    }

    @Override
    public void startAllTasks() {
        for (DownloadTask dltOfTotalFile : dltOfTotalFiles) {
            dltOfTotalFile.startDownload();
        }
    }

    @Override
    public void pauseAllTasks() {
        for (DownloadTask dltOfTotalFile : dltOfTotalFiles) {
            dltOfTotalFile.pauseDownload();
        }
    }

    @Override
    public void cancelAllTasks() {
        for (DownloadTask dltOfTotalFile : dltOfTotalFiles) {
            dltOfTotalFile.cancelDownload();
        }
    }
}
