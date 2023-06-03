/*
@Subject:
@Author:叶兆威
@Time:2023/6/3
*/

import Peer.PeerMG;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class Test1 {
    @Test
    public void test1(){
        File file = new File("F:\\2125060016.txt");
        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(file);
        PeerMG.getInstance().MakeTorrentFromFile(arrayList);

    }

}
