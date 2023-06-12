package service.Peer.FileTransmission.DownloadTask;

import domain.Torrent;
import domain.TorrentFile;
import service.Peer.FileTransmission.Downloader.DLofTorrentFile;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
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
                    PeerMG.getInstance().getHashToFile().put(file.getName(),file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    Torrent torrent = (Torrent)objectInputStream.readObject();
                    PeerMG.getInstance().getHashToTorrent().put(file.getName(),torrent);


                    //创建文件状态
                    StatusOfSingleFile fileStruct = torrent.getFileStruct();

                    //遍历文件状态将每个文件状态加入到总文件状态中
                    for(StatusOfSingleFile s : fileStruct.getChildren()){
                        for(StatusOfSingleFile ss : s.getChildren()){
                            if(ss.isDirectory()){
                                cire(ss);
                            }else {
                                PeerMG.getInstance().getHashToStatusOfSingleFile().put(ss.getPath(),ss);
                            }
                        }
                    }

                    StatusOfTotalFile statusOfTotalFile = new StatusOfTotalFile();
                    statusOfTotalFile.setFileStruct(fileStruct);
                    PeerMG.getInstance().getHashToStatusOfTotalFile().put(file.getName(),statusOfTotalFile);

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
                String[] lists = source.list();
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


    private void cire(StatusOfSingleFile statusOfSingleFile){
          for (StatusOfSingleFile s : statusOfSingleFile.getChildren()){
              if(s.isDirectory()){
                  cire(s);
              }else {
                  PeerMG.getInstance().getHashToStatusOfSingleFile().put(s.getPath(),s);
              }
          }
    }

}
