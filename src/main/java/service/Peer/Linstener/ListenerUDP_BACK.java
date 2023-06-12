package service.Peer.Linstener;


import service.Peer.FileTransmission.ASK.Content;
import service.Peer.FileTransmission.Piece.PiecePointer;
import utils.TrackerMG;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenerUDP_BACK extends Thread{

    DatagramSocket datagramSocket ;
    DatagramPacket datagramPacket;

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                datagramSocket = new DatagramSocket(TrackerMG.TorrentPort);
                datagramSocket.receive(datagramPacket);
                byte[] receivedData = datagramPacket.getData();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivedData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                Content content = (Content)objectInputStream.readObject(); // 反序列化对象

                if(content.getType() == Content.PEER_BACK_FROM_PEER_FOR_PIECE){
                    PiecePointer piecePointer = new PiecePointer(content.getHash(),content.getPieceIndex());
                    piecePointer.write(content.getPiece().getBytes());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
