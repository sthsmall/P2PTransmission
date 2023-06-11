package service.Tracker.Listener;

import service.Peer.FileTransmission.ASK.Content;
import utils.TrackerMG;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread{
    ServerSocket serverSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream  objectInputStream;
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(TrackerMG.TorrentPort);
            while(!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                Content content = (Content) objectInputStream.readObject();
                if(content.getType() == Content.PEER_ASK_FOR_TRACKER_PEER_INFO){

                }

            }
        } catch ( IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
