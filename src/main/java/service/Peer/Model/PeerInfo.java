package service.Peer.Model;

import java.util.Objects;

//Peer的信息类
public class PeerInfo {
    String ip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeerInfo peerInfo = (PeerInfo) o;
        return Objects.equals(ip, peerInfo.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    int port;

    public PeerInfo(String ip) {
        this.ip = ip;
    }
    public PeerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
