package Tracker.Model;

import java.util.HashMap;

//种子文件的哈希值与种子文件的对应关系
public class TorrentsMap {

    private HashMap<String, Torrent> torrentsMap = new HashMap<String, Torrent>();

    public void addTorrent(Torrent torrent) {
        torrentsMap.put(torrent.getHash(), torrent);
    }

}
