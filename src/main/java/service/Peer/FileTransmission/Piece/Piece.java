package service.Peer.FileTransmission.Piece;

import lombok.AllArgsConstructor;
import lombok.Data;

//分块
@Data
public class Piece {
    public static final int PieceSize = 1024*1024;
    String path;
    //数据大小
    byte [] bytes;
    //块的偏移量（第几块）
    int num;
    //获取数据大小
    public int getSize(){
        return bytes.length;
    }

    public Piece(String path, byte[] bytes, int num) {
        this.path = path;
        this.bytes = bytes;
        this.num = num;
    }
}
