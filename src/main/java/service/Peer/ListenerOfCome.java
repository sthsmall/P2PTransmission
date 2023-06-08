package service.Peer;

import service.Peer.FileTransmission.Content;
import service.Peer.FileTransmission.SingleFileStatus;
import service.Peer.FileTransmission.TotalFileStatus;
import utils.PeerMG;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerOfCome extends Thread{
    int Port = PeerMG.InfoPort;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Port);
            while (true){
                Socket socket = serverSocket.accept();
                new OwnershipOfFile(socket).start();


            }







        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class OwnershipOfFile extends Thread{
        Socket socket;
        ObjectOutputStream objectOutputStream;
        ObjectInputStream  objectInputStream;
        public OwnershipOfFile(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Content content=(Content)objectInputStream.readObject();
                String hash = content.getHash();
                if(PeerMG.getInstance().getHashToPeerInfo().containsKey(hash)) {
                    //如果拥有该文件
                    //向请求者发送文件状况
                    objectOutputStream.writeObject(AnalyseContent(hash));
                    objectOutputStream.flush();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        //对文件的分片拥有状况进行分析
        private TotalFileStatus AnalyseContent(String hash) {
            return null;
        }
    }
}
