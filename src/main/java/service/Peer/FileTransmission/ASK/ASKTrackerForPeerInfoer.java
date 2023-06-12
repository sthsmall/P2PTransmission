package service.Peer.FileTransmission.ASK;

import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;

public class ASKTrackerForPeerInfoer extends Thread{
    String hash;
    public ASKTrackerForPeerInfoer(File file) {
        this.hash = file.getName();
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream;
            ObjectInputStream objectInputStream;
            Socket socket = new Socket(PeerMG.getInstance().getTrackerIp(), PeerMG.TrackerPort);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            while(!Thread.currentThread().isInterrupted()){
                Thread.sleep(1000);
                Content content = new Content(Content.PEER_ASK_FOR_TRACKER_PEER_INFO, hash);
                objectOutputStream.writeObject(content);
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                Content backContent = (Content) objectInputStream.readObject();
                HashSet<PeerInfo> peerInfos = backContent.myPeerInfo;
                HashSet<PeerInfo> temp = PeerMG.getInstance().getHashToPeerInfo().get(hash);
                if(temp == null){
                    PeerMG.getInstance().getHashToPeerInfo().put(hash,new HashSet<>());
                }
                PeerMG.getInstance().getHashToPeerInfo().get(hash).addAll(peerInfos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
