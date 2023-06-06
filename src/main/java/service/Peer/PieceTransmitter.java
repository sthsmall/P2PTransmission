package service.Peer;

import domain.Piece;
import service.Tracker.InfoServerHandler;

//用于传输分块的线程
public class PieceTransmitter extends Thread{
    Piece piece;
    InfoServerHandler infoServerHandler;
    public PieceTransmitter(Piece piece, InfoServerHandler infoServerHandler) {
        this.piece = piece;
        this.infoServerHandler = infoServerHandler;
    }

    @Override
    public void run() {

    }

}
