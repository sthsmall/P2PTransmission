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
            Socket socket = new Socket(PeerMG.getInstance().getTrackerIp(), PeerMG.getInstance().getTrackerInfoPort());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            while(!Thread.currentThread().isInterrupted()){
                Thread.sleep(1000);
                ASKContent content = new ASKContent(ASKContent.ASK_FOR_TRACKER_PEER_INFO, hash);
                objectOutputStream.writeObject(content);
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                PeerMG.getInstance().getHashToPeerInfo().put(hash,(HashSet<PeerInfo>) objectInputStream.readObject());
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
