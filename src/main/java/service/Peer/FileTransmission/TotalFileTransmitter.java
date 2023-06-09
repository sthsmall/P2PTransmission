package service.Peer.FileTransmission;

import java.io.File;
import java.util.ArrayList;

//所有文件的传输类
public class TotalFileTransmitter extends Thread{
    @Override
    public void run() {
        //获取Torrent文件列表
        ArrayList<File> files = new ArrayList<>();
        File source = new File("./Torrent");
        String lists[] = source.list();
        for (String list : lists) {
            new TorrentFileTransmitter(new File("./Torrent/" + list)).start();
        }
    }
}
