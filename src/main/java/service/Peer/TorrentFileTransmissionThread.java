package service.Peer;

import utils.PeerMG;
import domain.Torrent;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TorrentFileTransmissionThread extends Thread{
    File file;
    public TorrentFileTransmissionThread(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            Torrent torrent = Torrent.createTorrentFromFile(file);
            Socket socket = new Socket(torrent.getDownloadUrl(), PeerMG.FilePort);
            System.out.println("连接成功");
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(torrent);
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
