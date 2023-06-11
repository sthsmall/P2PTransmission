package service.Tracker;

import domain.Torrent;
import utils.LargeFileHashCalculator;
import utils.TrackerMG;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class TorrentServerHandler extends Thread{
    Socket accept;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    public TorrentServerHandler(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try {
            objectInputStream = new ObjectInputStream(accept.getInputStream());
            objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
            Torrent torrent = (Torrent)objectInputStream.readObject();

            File file = new File("temp");
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream2.writeObject(torrent);
            objectOutputStream2.flush();
            String hash = LargeFileHashCalculator.getHash(file);
            File fileNew = new File(hash + ".torrent");
            file.renameTo(fileNew);

            TrackerMG.getInstance().getIpToTorrent().get(accept.getInetAddress().getHostAddress()).add(hash);
            TrackerMG.getInstance().getTorrentToIp().put(hash, accept.getInetAddress().getHostAddress());
            System.out.println("接收成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
