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
            System.out.println("连接成功");
            Content content = new Content(Content.PEER_SEND_TORRENT_FILE);
            content.setTorrent(torrent);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);
            objectOutputStream.flush();
            System.out.println("发送成功");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Content Backcontent = (Content)objectInputStream.readObject();
            if(Backcontent.getType() == Content.OK){
                System.out.println("接收成功");
            }else {
                System.out.println("接收失败");
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
