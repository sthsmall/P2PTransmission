package service.Peer.Linstener;


import service.Peer.FileTransmission.ASK.Content;
import service.Peer.FileTransmission.Piece.Piece;
import service.Peer.FileTransmission.Piece.PiecePointer;
import utils.PeerMG;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenerUDP_ASK extends Thread{
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                datagramSocket.receive(datagramPacket);
                byte[] receivedData = datagramPacket.getData();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivedData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                Content content = (Content)objectInputStream.readObject(); // 反序列化对象

                if(content.getType() == Content.PEER_ASK_FOR_PEER_FOR_PIECE){
                    PiecePointer piecePointer = new PiecePointer(content.getHash(),content.getPieceIndex());
                    byte[] bytes = new byte[Piece.PieceSize];
                    piecePointer.read(bytes);
                    Content backContent = new Content(Content.PEER_BACK_FROM_PEER_FOR_PIECE);
                    backContent.setPiece(new Piece(content.getPath(),bytes, content.getPieceIndex()));
                    send(backContent);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void send(Content backContent) throws IOException {
        DatagramSocket datagramSocket1 = new DatagramSocket();
        ObjectOutput objectOutput = new ObjectOutputStream(new ByteArrayOutputStream());
        objectOutput.writeObject(backContent);
        byte[] bytes = ((ByteArrayOutputStream) objectOutput).toByteArray();
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length,datagramSocket.getInetAddress(), PeerMG.PieceReceivePort);
        datagramSocket.send(datagramPacket);

    }
}
