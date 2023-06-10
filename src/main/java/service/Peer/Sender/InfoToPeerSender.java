package service.Peer.Sender;

import service.Peer.FileTransmission.StatusOfSingleFile;

public class InfoToPeerSender extends Thread{
    StatusOfSingleFile statusOfSingleFile;

    public InfoToPeerSender(StatusOfSingleFile statusOfSingleFile) {
        this.statusOfSingleFile = statusOfSingleFile;
    }

    @Override
    public void run() {
        super.run();
    }
}
