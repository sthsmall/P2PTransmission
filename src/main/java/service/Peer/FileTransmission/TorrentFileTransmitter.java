package service.Peer.FileTransmission;

import domain.Torrent;
import domain.TorrentFile;
import service.Peer.Model.PeerInfo;
import service.Peer.Sender.InfoToTrackerSender;
import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
            MakeDownloadList(file);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //向Peer发送文件状况请求

    private TotalFileStatus MakeDownloadList(File file) throws Exception {
        //向Tracker请求Peer信息并存储到PeerMG中
        PeerMG.getInstance().getHashToPeerInfo().put(hash, ASKTrackerForPeerInfo(file));
        //获取Peer信息
        HashSet<PeerInfo> peerInfos = PeerMG.getInstance().getHashToPeerInfo().get(hash);
        //向Peer请求文件状况
        ArrayList<TotalFileStatus> status = ASKpeerForFileStatus(file, peerInfos);
        //分析文件状况
        return PieceInfoAnalyser(status);
    }

    private TotalFileStatus PieceInfoAnalyser(ArrayList<TotalFileStatus> totalFileStatuses){
        //暂定

    }

    private HashSet<PeerInfo> ASKTrackerForPeerInfo(File file) throws Exception {
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        String hash = file.getName();

        Socket socket = new Socket(PeerMG.getInstance().getTrackerIp(), PeerMG.getInstance().getTrackerInfoPort());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        Content content = new Content(Content.ASK_FOR_TRACKER_PEER_INFO,file.getName());
        objectOutputStream.writeObject(content);
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        return (HashSet<PeerInfo>) objectInputStream.readObject();
    }

    private ArrayList<TotalFileStatus> ASKpeerForFileStatus(File file, HashSet<PeerInfo> peerInfos) throws IOException, ClassNotFoundException {
        ArrayList<TotalFileStatus> totalFileStatuses = new ArrayList<>();
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        for(PeerInfo peerInfo : peerInfos) {
            Socket socket = new Socket(peerInfo.getIp(), peerInfo.getPort());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new Content(Content.ASK_FOR_PEER_STATUS_INFO, file.getName()));
            objectOutputStream.flush();
            TotalFileStatus totalFileStatus = (TotalFileStatus) objectInputStream.readObject();
            totalFileStatuses.add(totalFileStatus);
        }
        return totalFileStatuses;
    }


    private void StartDownload(ArrayList<SingleFileStatus> singleFileStatuses){
        for(SingleFileStatus singleFileStatus : singleFileStatuses){
            new SingleFileTransmitter(singleFileStatus).start();
        }
    }
}


