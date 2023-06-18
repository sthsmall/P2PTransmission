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
import java.util.HashSet;

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
                System.out.println(content);
                if(content.getType() == Content.PEER_ASK_FOR_TRACKER_PEER_INFO){
                    Content backContent = new Content(Content.PEER_BACK_FROM_TRACKER_PEER_INFO);
                    backContent.setMyPeerInfo(TrackerMG.getInstance().getTorrentToIp().get(content.getHash()));
                    backContent.setHash(content.getHash());
                    if(backContent.getMyPeerInfo() == null){
                        backContent.setMyPeerInfo(new HashSet<>());
                    }

                    objectOutputStream.writeObject(backContent);
                    objectOutputStream.flush();

                } else if (content.getType() == Content.PEER_ASK_TRACK_ONLINE) {
                    TrackerMG.getInstance().getTorrentToIp().get(content.getHash()).add(new PeerInfo(socket.getInetAddress().getHostAddress(),socket.getPort()));
                    TrackerMG.getInstance().getIpToTorrent().get(socket.getInetAddress().getHostAddress()).addAll(content.getMyTorrents());
                } else if (content.getType() == Content.PEER_ASK_TRACK_FOR_TORRENT_BY_HASH) {
                    Torrent torrent = (Torrent) new ObjectInputStream(new FileInputStream(new File("./src/TrackerTorrent/"+content.getHash()+".torrent"))).readObject();
                    Content backContent = new Content(Content.OK);
                    backContent.setTorrent(torrent);
                    objectOutputStream.writeObject(backContent);
                    objectOutputStream.flush();
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
                    System.out.println(file.renameTo(fileNew));



                    System.out.println("接收成功");
                } else if (content.getType() == Content.PEER_ASK_TRACKER_SELF_INFO) {
                    HashSet<String> hashSet = content.getMyTorrents();
                    for (String hash : hashSet) {
                        HashSet<PeerInfo> peerInfos = TrackerMG.getInstance().getTorrentToIp().get(hash);
                        if (peerInfos == null) {
                            peerInfos = new HashSet<>();
                            TrackerMG.getInstance().getTorrentToIp().put(hash, peerInfos);
                        }
                        peerInfos = TrackerMG.getInstance().getTorrentToIp().get(hash);
                        peerInfos.add(new PeerInfo(socket.getInetAddress().getHostAddress()));
                    }

                    HashSet<String> ipSet = TrackerMG.getInstance().getIpToTorrent().get(socket.getInetAddress().getHostAddress());
                    if (ipSet == null) {
                        ipSet = new HashSet<>();
                        TrackerMG.getInstance().getIpToTorrent().put(new PeerInfo(socket.getInetAddress().getHostAddress()), ipSet);
                    }
                    ipSet = TrackerMG.getInstance().getIpToTorrent().get(new PeerInfo(socket.getInetAddress().getHostAddress()));
                    ipSet.addAll(hashSet);

                    objectOutputStream.writeObject(new Content(Content.OK));
                    objectOutputStream.flush();


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
