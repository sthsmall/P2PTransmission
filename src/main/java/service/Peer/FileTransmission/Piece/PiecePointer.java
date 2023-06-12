package service.Peer.FileTransmission.Piece;

import utils.PeerMG;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

//块指针（用于将文件分割成块）
public class PiecePointer {
    //随机访问文件类
    RandomAccessFile randomAccessFile;
    //获取文件片段的长度
    int pieceIndex;
    int len = Piece.PieceSize;

    public PiecePointer(String hash, int pieceIndex) throws IOException {
        this.pieceIndex = pieceIndex;
        //获取文件
        File file = PeerMG.getInstance().getHashToFile().get(hash);
        randomAccessFile = new RandomAccessFile(file, "rw");
        //将文件指针指向第（start+1）个字节上
        randomAccessFile.seek(pieceIndex * Piece.PieceSize);
    }

    //写
    public void write(byte[] bytes) throws IOException {
        randomAccessFile.write(bytes, 0, len);
    }

    //读
    public void read(byte[] bytes) throws IOException {
        randomAccessFile.read(bytes, 0, len);
    }
}
