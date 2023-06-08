package service.Peer.FileTransmission;

import service.Peer.Model.PeerInfo;
import service.Peer.Sender.InfoToTrackerSender;
import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

//一个Torrent文件包含文件的传输类
public class TorrentFileTransmitter extends Thread{
    File file;
    String hash;
    TotalFileStatus totalFileStatus;
    public TorrentFileTransmitter(File file) {
        this.file = file;
        hash = file.getName();
        totalFileStatus = new TotalFileStatus();
    }

    @Override
    public void run() {
        //创建心跳线程
        InfoToTrackerSender infoToTrackerSender = new InfoToTrackerSender(file);
        infoToTrackerSender.run();
        HashSet<PeerInfo> peerInfos = PeerMG.getInstance().getHashToPeerInfo().get(hash);
        try {
            ArrayList<TotalFileStatus> totalFileStatuses=pieceInfoAnalyser(file, peerInfos);
            ArrayList<SingleFileStatus> singleFileStatuses = MakeDownloadList(totalFileStatuses);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //向Peer发送文件状况请求
    private ArrayList<TotalFileStatus> pieceInfoAnalyser(File file, HashSet<PeerInfo> peerInfos) throws IOException, ClassNotFoundException {
        ArrayList<TotalFileStatus> totalFileStatuses = new ArrayList<>();
        for(PeerInfo peerInfo : peerInfos) {
            Socket socket = new Socket(peerInfo.getIp(), peerInfo.getPort());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new Content());
            objectOutputStream.flush();
            TotalFileStatus totalFileStatus = (TotalFileStatus) objectInputStream.readObject();
            totalFileStatuses.add(totalFileStatus);
        }
        return totalFileStatuses;
    }

    private ArrayList<SingleFileStatus> MakeDownloadList(ArrayList<TotalFileStatus> totalFileStatuses){
        return null;
    }

    private void StartDownload(ArrayList<SingleFileStatus> singleFileStatuses){
        for(SingleFileStatus singleFileStatus : singleFileStatuses){
            new SingleFileTransmitter(singleFileStatus).start();

        }
    }
}


