package service.Peer.Linstener;

import service.Peer.FileTransmission.ASK.Content;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
import service.Peer.Model.PeerInfo;
import utils.PeerMG;
import utils.TrackerMG;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ListenerTCP_ASK extends Thread{
    ServerSocket serverSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(TrackerMG.TorrentPort);
            while(!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                Content content = (Content) objectInputStream.readObject();
                if(content.getType() == Content.PEER_ASK_FOR_PEER_FOR_PIECE){

                }else if(content.getType() == Content.PEER_ASK_FOR_PEER_STATUS_INFO) {
                    String hash = content.getHash();
                    Content backContent = new Content(Content.PEER_BACK_FROM_PEER_STATUS_INFO);
                    StatusOfTotalFile statusOfTotalFile = PeerMG.getInstance().getHashToStatusOfTotalFile().get(hash);
                    backContent.setPeerStatus(statusOfTotalFile);
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(backContent);
                    objectOutputStream.flush();
                } else if (content.getType() == Content.PEER_BACK_FROM_PEER_STATUS_INFO) {
                    String hash = content.getHash();
                    HashSet<PeerInfo> peerInfos = content.getMyPeerInfo();
                    HashSet<PeerInfo> temp = PeerMG.getInstance().getHashToPeerInfo().get(hash);
                    if (temp == null) {
                        PeerMG.getInstance().getHashToPeerInfo().put(hash, new HashSet<>());
                    }
                    PeerMG.getInstance().getHashToPeerInfo().get(hash).addAll(peerInfos);
                }
            }
        } catch ( IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
