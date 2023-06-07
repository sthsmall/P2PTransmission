package service.Peer.Model;

import java.util.ArrayList;
import java.util.HashSet;

//从Tracker返回的信息类
public class InfoBackFromTracker {
    private HashSet<PeerInfo> peerInfos;

    public HashSet<PeerInfo> getPeerInfos() {
        return peerInfos;
    }

    public void setPeerInfos(HashSet<PeerInfo> peerInfos) {
        this.peerInfos = peerInfos;
    }
}
