package service.Peer.FileTransmission.ASK;

import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;

public class ASKTrackerForPeerInfoer extends Thread {
    String hash;

    public ASKTrackerForPeerInfoer(File file) {
        this.hash = file.getName();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = new Socket(PeerMG.getInstance().getTrackerIP(), PeerMG.TrackerPort);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Thread.sleep(1000);
                Content content = new Content(Content.PEER_ASK_FOR_TRACKER_PEER_INFO, hash);
                objectOutputStream.writeObject(content);
                objectOutputStream.flush();

                Content backContent = (Content) objectInputStream.readObject();

                //去除自己
                backContent.getMyPeerInfo().remove(new PeerInfo(InetAddress.getLocalHost().getHostAddress()));
                System.out.println("backContent = " + backContent);
                HashSet<PeerInfo> peerInfos = backContent.myPeerInfo;
                HashSet<PeerInfo> temp = PeerMG.getInstance().getHashToPeerInfo().get(hash);


                if (temp == null) {
                    PeerMG.getInstance().getHashToPeerInfo().put(hash, new HashSet<>());
                }
                PeerMG.getInstance().getHashToPeerInfo().get(hash).addAll(peerInfos);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
