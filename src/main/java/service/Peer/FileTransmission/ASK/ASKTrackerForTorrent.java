package service.Peer.FileTransmission.ASK;

import domain.Torrent;
import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ASKTrackerForTorrent {
    String hash;
    public ASKTrackerForTorrent(String hash){
        this.hash = hash;
    }


    public void run() {
        try {
            Socket socket = new Socket(PeerMG.getInstance().getTrackerIP(), PeerMG.TrackerTorrentPort);
            Content content = new Content(Content.PEER_ASK_TRACK_FOR_TORRENT_BY_HASH);
            content.setHash(hash);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(content);
            objectOutputStream.flush();
            Content content1 = (Content) objectInputStream.readObject();
            File file = PeerMG.getInstance().StorageTorrent(content1.getTorrent());
            PeerMG.getInstance().getNowDownloadingTorrents().add(file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
