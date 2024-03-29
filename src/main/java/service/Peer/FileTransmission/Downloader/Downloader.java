package service.Peer.FileTransmission.Downloader;

import service.Peer.FileTransmission.DownloadTask.DownloadTask;

import java.util.List;

/*
文件URL：表示要下载的文件的网络地址。
保存路径：指定下载文件保存的本地路径。
下载状态：表示下载任务的当前状态，例如等待中、下载中、已暂停、已完成等。
已下载字节数：记录已成功下载的字节数，用于跟踪下载进度和断点续传。
开始下载、暂停下载、取消下载：方法用于控制下载任务的开始、暂停和取消。
处理下载事件：方法用于处理下载过程中的事件，例如成功下载一个块、下载错误等。
 */
public interface Downloader {
    void addTask(DownloadTask task);
    void addAndStartTask(DownloadTask task);

    void removeTask(DownloadTask task);

    void startAllTasks();

    void pauseAllTasks();

    void cancelAllTasks();
}
