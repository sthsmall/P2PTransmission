package service.Peer.FileTransmission.ASK;

import service.Peer.FileTransmission.Status.StatusOfTotalFile;
import service.Peer.Model.PeerInfo;
import utils.PeerMG;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class ASKPeerForFileStatuser extends Thread{
    File file;
    HashSet<PeerInfo> peerInfos;
    public ASKPeerForFileStatuser(File file){
        this.file = file;
        this.peerInfos = peerInfos;
    }
    @Override
    public void run() {
        try {
            ArrayList<StatusOfTotalFile> statusOfTotalFiles = new ArrayList<>();
            ObjectOutputStream objectOutputStream;
            ObjectInputStream objectInputStream;

            //隔一段时间向Peer询问文件状态
            while(!Thread.currentThread().isInterrupted()){
                Thread.sleep(1000);
                peerInfos = PeerMG.getInstance().getHashToPeerInfo().get(file.getName());
                if (peerInfos == null) continue;
                for (PeerInfo peerInfo : peerInfos) {
                    Socket socket;
                    try {
                        socket = new Socket(peerInfo.getIp(), peerInfo.getPort());
                    }catch (Exception e){
                        System.out.println("Peer "+peerInfo.getIp()+" "+peerInfo.getPort()+" is not online");
                        continue;
                    }
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(new Content(Content.PEER_ASK_FOR_PEER_STATUS_INFO, file.getName()));
                    objectOutputStream.flush();
                    StatusOfTotalFile statusOfTotalFile = (StatusOfTotalFile) objectInputStream.readObject();
                    statusOfTotalFiles.add(statusOfTotalFile);
                }

                HashMap<String,Integer> status = PieceInfoAnalyser(statusOfTotalFiles);
                PeerMG.getInstance().getHashALLToTotalFileStatus().put(file.getName(), status);

                PeerMG.getInstance().getHashToTotalFileStatus().put(file.getName(),false);

                Queue<String> downloadList = PeerMG.getInstance().strategyOfDownload(status);
                PeerMG.getInstance().getHashToDownloadList().put(file.getName(),downloadList);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //计算所有文件的总状态
    public static HashMap<String,Integer> PieceInfoAnalyser(ArrayList<StatusOfTotalFile> statusOfTotalFiles){
        StatusOfTotalFile statusOfTotalFile = new StatusOfTotalFile();
        HashMap<String,Integer> howPieceHave = new HashMap<>();
        for(StatusOfTotalFile t1 : statusOfTotalFiles){
            HashSet<String> hashToAllPiece = t1.getHashToAllPiece();
            for(String hash : hashToAllPiece){
                if(howPieceHave.containsKey(hash)){
                    howPieceHave.put(hash,howPieceHave.get(hash)+1);
                }else{
                    howPieceHave.put(hash,1);
                }
            }
        }
        return howPieceHave;
    }


}
