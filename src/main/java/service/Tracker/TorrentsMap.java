package service.Tracker;

import domain.Torrent;

import java.util.HashMap;

//种子文件的哈希值与种子文件的对应关系
public class TorrentsMap {

    private final HashMap<String, Torrent> torrentsMap = new HashMap<String, Torrent>();

    public void addTorrent(Torrent torrent) {
        torrentsMap.put(torrent.getHash(), torrent);
    }

}
