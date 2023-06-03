package Tracker.Model;

//分块
public class Piece {
    //数据大小
    byte [] bytes;
    //块的偏移量（第几块）
    int num;

    public Piece(byte[] bytes, int num) {
        this.bytes = bytes;
        this.num = num;
    }
    //获取数据大小
    public int getSize(){
        return bytes.length;
    }
}
