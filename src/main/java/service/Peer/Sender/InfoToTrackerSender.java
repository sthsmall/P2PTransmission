package service.Peer.Sender;

import service.Peer.FileTransmission.TorrentFileTransmitter;
import service.Peer.Model.InfoBackFromTracker;
import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
/*
应该持续发送信息给Tracker
 拉取Peer信息
 到PeerMG中更新
 */



public class InfoToTrackerSender extends Thread{

    private String ip = PeerMG.getInstance().getTrackerIP();
    private int port = PeerMG.InfoPort;
    private Socket socket;
    String hash;


    public InfoToTrackerSender(File file)  {
        hash = file.getName();


    }


    @Override
    public void run() {
        try {
            //连接服务器
            Socket socket = new Socket(ip, port);
            //发送文件
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new InfoToTrackerContent());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            InfoBackFromTracker infoBackFromTracker = (InfoBackFromTracker) objectInputStream.readObject();
            HashSet<PeerInfo> peerInfos = infoBackFromTracker.getPeerInfos();
            PeerMG.getInstance().getHashToPeerInfo().put(hash, peerInfos);
            objectInputStream.close();
            objectOutputStream.flush();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    public void Send(String str) {

    }
}
