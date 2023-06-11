package service.Peer.FileTransmission;

import utils.PeerMG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StatusOfTotalFile {
    boolean status[];
    int len = PeerMG.FilePieceSize;
    HashMap<String,StatusOfSingleFile> hashToStatusOfSingleFile = new HashMap<>();
    HashMap<String,StatusOfSingleFile> hashToDownloadList = new HashMap<>();
    public StatusOfTotalFile(){

    }




}