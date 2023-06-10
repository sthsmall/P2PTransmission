package service.Peer.FileTransmission;

import domain.Torrent;
import utils.PeerMG;

import java.util.ArrayList;

public class TotalFileStatus {
    boolean status[];
    int len = PeerMG.FilePieceSize;
    ArrayList<SingleFileStatus> singleFileStatuses;

    public TotalFileStatus() {

    }

    public TotalFileStatus(ArrayList<SingleFileStatus> singleFileStatuses) {
        this.singleFileStatuses = singleFileStatuses;
    }
}