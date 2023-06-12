package service.Peer.FileTransmission.ASK;

import lombok.Data;
import service.Peer.FileTransmission.Piece.Piece;
import service.Peer.FileTransmission.Status.StatusOfTotalFile;

@Data
public class Content {
    public static final int PEER_BACK_FROM_PEER_STATUS_INFO = 5;
    int type;
    String hash;
    String path;
    int pieceIndex;
    Piece piece;
    StatusOfTotalFile peerStatus;

    public static final  int PEER_ASK_FOR_TRACKER_PEER_INFO = 0;
    public static final  int PEER_ASK_FOR_PEER_STATUS_INFO = 1;

    public static final int PEER_ASK_FOR_PEER_FOR_PIECE = 2;

    public static final int TRACKER_ASK_FOR_PEER_INFO = 3;
    public static final int PEER_BACK_FROM_PEER_FOR_PIECE = 4;
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
}
