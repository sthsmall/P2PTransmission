package service.Peer.FileTransmission.DownloadTask;

import service.Peer.FileTransmission.ASK.Content;
import service.Peer.FileTransmission.Status.StatusOfSingleFile;
import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DLTaskOfPiece extends Thread implements DownloadTask{
    String hash;
    String path;
    int pieceIndex;
    StatusOfSingleFile statusOfSingleFile;
    public DLTaskOfPiece(String hash, String path){
        this.hash = hash;
        this.path = path.split(":")[0];
        this.pieceIndex = Integer.parseInt(path.split(":")[1]);
        this.statusOfSingleFile = PeerMG.getInstance().getHashToStatusOfSingleFile().get(hash);
    }



    @Override
    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            Content content = new Content(Content.PEER_ASK_FOR_PEER_FOR_PIECE,hash,pieceIndex);
            ObjectOutput objectOutput = new ObjectOutputStream(new ByteArrayOutputStream());
            objectOutput.writeObject(content);
            byte[] bytes = ((ByteArrayOutputStream) objectOutput).toByteArray();

            for(PeerInfo peerInfo: PeerMG.getInstance().getHashToPeerInfo().get(hash)){
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(peerInfo.getIp()), peerInfo.getPort());
                datagramSocket.send(datagramPacket);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startDownload() {
        new Thread(this).start();
    }

    @Override
    public void pauseDownload() {
        new Thread(this).interrupt();
    }

    @Override
    public void cancelDownload() {
        new Thread(this).interrupt();
    }
}
