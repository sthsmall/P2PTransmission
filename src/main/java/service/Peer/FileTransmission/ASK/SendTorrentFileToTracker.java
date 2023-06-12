package service.Peer.FileTransmission.ASK;

import utils.PeerMG;
import domain.Torrent;

import java.io.*;
import java.net.Socket;

public class SendTorrentFileToTracker extends Thread{
    File file;
    public SendTorrentFileToTracker(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            Torrent torrent = Torrent.createTorrentFromFile(file);
            Socket socket = new Socket(PeerMG.getInstance().getTrackerIP(), PeerMG.TrackerPort);
            System.out.println("Connect to Tracker");
            Content content = new Content(Content.PEER_SEND_TORRENT_FILE);
            content.setTorrent(torrent);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Content Backcontent = (Content)objectInputStream.readObject();
            if(Backcontent.getType() == Content.OK){
                System.out.println("Success");
            }else {
                System.out.println("Fail");
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
