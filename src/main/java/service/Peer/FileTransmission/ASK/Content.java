package service.Peer.FileTransmission.ASK;

import domain.Torrent;
import lombok.Data;
import service.Peer.FileTransmission.Piece.Piece;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;
import service.Peer.Model.PeerInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

@Data
public class Content implements Serializable {
    public static final int PEER_BACK_FROM_PEER_STATUS_INFO = 5;
    public static final int PEER_BACK_FROM_TRACKER_PEER_INFO = 8;
    public static final int PEER_ASK_TRACK_FOR_TORRENT_BY_HASH = 9;
    public static final int OK = 10;
    public static final int PEER_ASK_TRACKER_SELF_INFO = 11;
    int type;
    Torrent torrent;
    String hash;
    String path;
    int pieceIndex;
    Piece piece;
    HashSet<String> myTorrents;
    StatusOfTotalFile peerStatus;
    HashSet<PeerInfo> myPeerInfo;

    public static final  int PEER_ASK_FOR_TRACKER_PEER_INFO = 0;
    public static final  int PEER_ASK_FOR_PEER_STATUS_INFO = 1;
    //UDP
    public static final int PEER_ASK_FOR_PEER_FOR_PIECE = 2;

    //public static final int TRACKER_ASK_FOR_PEER_INFO = 3;
    public static final int PEER_BACK_FROM_PEER_FOR_PIECE = 4;

    public static final int PEER_ASK_TRACK_ONLINE = 6;

    public static final int PEER_SEND_TORRENT_FILE = 7;
    public Content(int type, String hash) {
        this.type = type;
        this.hash = hash;
    }

    public Content(int type, String hash, int pieceIndex) {
        this.type = type;
        this.hash = hash;
        this.pieceIndex = pieceIndex;
    }

    public Content(int type) {
        this.type = type;
    }






    public String getHash() {
        return hash;
    }

    public HashSet<String> getMyTorrents() {
        return myTorrents;
    }

    public void setMyPeerInfo(HashSet<PeerInfo> myPeerInfo) {
         this.myPeerInfo = myPeerInfo;
    }


    public Torrent getTorrent() {

        return torrent;
    }
}
