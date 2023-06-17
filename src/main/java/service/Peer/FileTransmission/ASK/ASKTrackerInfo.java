package service.Peer.FileTransmission.ASK;

import utils.PeerMG;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


//用于告诉Tracker自己的信息
public class ASKTrackerInfo extends Thread{
    Socket socket;
    ObjectInputStream objectInputStream ;
    ObjectOutputStream objectOutputStream ;
    @Override
    public void run() {
        Content content;

        while(!Thread.interrupted()){
            try {
                socket =  new Socket(PeerMG.getInstance().getTrackerIP(), PeerMG.TrackerPort);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                content = new Content(Content.PEER_ASK_TRACKER_SELF_INFO);
                Thread.sleep(3000);
                content.setMyTorrents(PeerMG.getInstance().getTorrents());
                objectOutputStream.writeObject(content);
                Content backContent = (Content) objectInputStream.readObject();
                if(backContent.getType() == Content.OK) {
                    System.out.println("Success");
                }else {
                    System.out.println("Fail");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
