package service.Tracker.Listener;

import service.Tracker.TorrentServerHandler;
import utils.TrackerMG;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerOfTorrent extends Thread{
    ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(TrackerMG.TorrentPort);
            while(!Thread.interrupted()) {
                new TorrentServerHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
