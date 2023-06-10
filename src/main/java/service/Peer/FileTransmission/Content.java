package service.Peer.FileTransmission;

public class Content {
    int type;
    String hash;

    public static final  int ASK_FOR_TRACKER_PEER_INFO = 0;
    public static final  int ASK_FOR_PEER_STATUS_INFO = 1;

    public Content(int type, String hash) {
        this.type = type;
        this.hash = hash;
    }






    public String getHash() {
        return hash;
    }
}
