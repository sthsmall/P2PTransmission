package service.Peer.FileTransmission.Piece;

import lombok.AllArgsConstructor;
import lombok.Data;

//分块
@Data
@AllArgsConstructor
public class Piece {
    public static final int PieceSize = 1024*1024;
    //数据大小
    byte [] bytes;
    //块的偏移量（第几块）
    int num;
    //获取数据大小
    public int getSize(){
        return bytes.length;
    }


}
