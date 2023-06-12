package service.Peer.FileTransmission.ASK;

import utils.PeerMG;
import domain.Torrent;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
            System.out.println("连接成功");
            Content content = new Content(Content.PEER_SEND_TORRENT_FILE);
            content.setTorrent(torrent);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);
            objectOutputStream.flush();
            socket.close();
            System.out.println("发送成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
