package service.Tracker;

import domain.Torrent;

import java.util.ArrayList;


public class OneFileDownLoader extends Thread{
    ArrayList<Torrent> torrents;
    public OneFileDownLoader(ArrayList<Torrent> torrents){
        this.torrents = torrents;
    }

    @Override
    public void run() {

    }
}
