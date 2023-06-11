package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.Downloader.DLofPiece;
import service.Peer.FileTransmission.StatusOfSingleFile;

import java.util.ArrayList;

public class DLTaskOfSingleFile extends  Thread implements DownloadTask{

    String hash;
    StatusOfSingleFile statusOfSingleFile;
    public DLTaskOfSingleFile(String hash, StatusOfSingleFile statusOfSingleFile){
        this.hash = hash;
        this.statusOfSingleFile = statusOfSingleFile;
    }



    @Override
    public void run() {
        ArrayList<DLofPiece> dlOfPieces = new ArrayList<>();
        Boolean status[] = statusOfSingleFile.getPieceStatus();
        for(int i = 0; i < status.length; i++){
            if(status[i] == true){
                DLofPiece dlOfPiece = new DLofPiece();
                dlOfPieces.add(dlOfPiece);
                dlOfPiece.addAndStartTask(new DLTaskOfPiece(hash, i));
            }
        }
    }

    @Override
    public void startDownload() {

    }

    @Override
    public void pauseDownload() {

    }

    @Override
    public void cancelDownload() {

    }
}
