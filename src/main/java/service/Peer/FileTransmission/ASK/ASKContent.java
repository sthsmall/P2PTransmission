package service.Peer.FileTransmission.ASK;

public class ASKContent {
    int type;
    String hash;
    int pieceIndex;

    public static final  int ASK_FOR_TRACKER_PEER_INFO = 0;
    public static final  int ASK_FOR_PEER_STATUS_INFO = 1;

    public static final int ASK_FOR_PEER_FOR_PIECE = 2;
    public ASKContent(int type, String hash) {
        this.type = type;
        this.hash = hash;
    }

    public ASKContent(int type, String hash, int pieceIndex) {
        this.type = type;
        this.hash = hash;
        this.pieceIndex = pieceIndex;
    }






    public String getHash() {
        return hash;
    }
}
