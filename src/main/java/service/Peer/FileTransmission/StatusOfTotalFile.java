package service.Peer.FileTransmission;

import utils.PeerMG;

import java.util.ArrayList;

public class StatusOfTotalFile {
    boolean status[];
    int len = PeerMG.FilePieceSize;
    ArrayList<StatusOfSingleFile> statusOfSingleFiles;

    public StatusOfTotalFile() {

    }

    public StatusOfTotalFile(ArrayList<StatusOfSingleFile> statusOfSingleFiles) {
        this.statusOfSingleFiles = statusOfSingleFiles;
    }
}