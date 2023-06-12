package service.Tracker.Listener;

import domain.Torrent;
import service.Peer.FileTransmission.ASK.Content;
import service.Peer.Model.PeerInfo;
import utils.LargeFileHashCalculator;
import utils.TrackerMG;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Listener extends Thread{
    ServerSocket serverSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream  objectInputStream;
    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(TrackerMG.TorrentPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(!Thread.interrupted()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println(socket);
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Content content = (Content) objectInputStream.readObject();
                if(content.getType() == Content.PEER_ASK_FOR_TRACKER_PEER_INFO){
                    Content backContent = new Content(Content.PEER_BACK_FROM_TRACKER_PEER_INFO);
                    backContent.setMyPeerInfo(TrackerMG.getInstance().getTorrentToIp().get(content.getHash()));
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(backContent);
                    objectOutputStream.flush();
                } else if (content.getType() == Content.PEER_ASK_TRACK_ONLINE) {
                    TrackerMG.getInstance().getTorrentToIp().get(content.getHash()).add(new PeerInfo(socket.getInetAddress().getHostAddress(),socket.getPort()));
                    TrackerMG.getInstance().getIpToTorrent().get(socket.getInetAddress().getHostAddress()).addAll(content.getMyTorrents());
                } else if (content.getType() == Content.PEER_ASK_TRACK_FOR_TORRENT_BY_HASH) {

                } else if (content.getType() == Content.PEER_SEND_TORRENT_FILE) {

                    File file = new File("./src/TrackerTorrent/temp");
                    file.createNewFile();

                    Torrent torrent = content.getTorrent();

                    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream2.writeObject(torrent);
                    objectOutputStream2.flush();
                    objectOutputStream2.close();

                    objectOutputStream.writeObject(new Content(Content.OK));
                    String hash = LargeFileHashCalculator.getHash(file);
                    File fileNew = new File("./src/TrackerTorrent/"+hash + ".torrent");
                    file.renameTo(fileNew);


                    System.out.println("接收成功");
                }
            } catch ( IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }

    }
}
