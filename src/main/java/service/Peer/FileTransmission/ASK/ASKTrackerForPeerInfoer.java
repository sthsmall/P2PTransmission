package service.Peer.FileTransmission.ASK;

import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try (
                Socket socket = new Socket(PeerMG.getInstance().getTrackerIp(), PeerMG.TrackerPort);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                Content content = new Content(Content.PEER_ASK_FOR_TRACKER_PEER_INFO, hash);
                objectOutputStream.writeObject(content);
                objectOutputStream.flush();
                Thread.sleep(1000);
                //Content backContent = (Content) objectInputStream.readObject();







            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
