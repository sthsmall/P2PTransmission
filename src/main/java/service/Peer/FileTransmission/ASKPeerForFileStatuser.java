package service.Peer.FileTransmission;

import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ASKPeerForFileStatuser extends Thread{
    File file;
    HashSet<PeerInfo> peerInfos;
    ASKPeerForFileStatuser(File file, HashSet<PeerInfo> peerInfos){
        this.file = file;
        this.peerInfos = peerInfos;
    }
    @Override
    public void run() {
        try {
            ArrayList<StatusOfTotalFile> statusOfTotalFiles = new ArrayList<>();
            ObjectOutputStream objectOutputStream;
            ObjectInputStream objectInputStream;
            for (PeerInfo peerInfo : peerInfos) {
                Socket socket = new Socket(peerInfo.getIp(), peerInfo.getPort());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(new Content(Content.ASK_FOR_PEER_STATUS_INFO, file.getName()));
                objectOutputStream.flush();
                StatusOfTotalFile statusOfTotalFile = (StatusOfTotalFile) objectInputStream.readObject();
                statusOfTotalFiles.add(statusOfTotalFile);
            }
            PeerMG.getInstance().getHashALLToTotalFileStatus().put(file.getName(), statusOfTotalFiles);
            PeerMG.getInstance().getHashToTotalFileStatus().put(file.getName(),PieceInfoAnalyser(statusOfTotalFiles));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //计算所有文件的总状态
    public static StatusOfTotalFile PieceInfoAnalyser(ArrayList<StatusOfTotalFile> statusOfTotalFiles){
        StatusOfTotalFile statusOfTotalFile = new StatusOfTotalFile();
        for(StatusOfTotalFile t1 : statusOfTotalFiles){

        }
        return statusOfTotalFile;
    }


}
