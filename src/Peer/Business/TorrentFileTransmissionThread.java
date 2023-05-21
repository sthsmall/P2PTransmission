package Peer.Business;

import Peer.PeerMG;
import Tracker.Model.Torrent;

import java.io.*;
import java.net.Socket;

public class TorrentFileTransmissionThread extends Thread{
    Torrent torrent;
    public TorrentFileTransmissionThread(Torrent torrent) {
        this.torrent = torrent;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(torrent.getDownloadUrl(), PeerMG.FilePort);
            System.out.println("连接成功");

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(torrent);
            objectOutputStream.flush();
            objectOutputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("发送成功");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
