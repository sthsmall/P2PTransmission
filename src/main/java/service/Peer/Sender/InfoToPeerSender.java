package service.Peer.Sender;

import service.Peer.FileTransmission.SingleFileStatus;

public class InfoToPeerSender extends Thread{
    SingleFileStatus singleFileStatus;

    public InfoToPeerSender(SingleFileStatus singleFileStatus) {
        this.singleFileStatus = singleFileStatus;
    }

    @Override
    public void run() {
        super.run();
    }
}
