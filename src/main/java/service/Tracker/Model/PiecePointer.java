package service.Tracker.Model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

//块指针（用于将文件分割成块）
public class PiecePointer {
    //随机访问文件类
    RandomAccessFile randomAccessFile;
    //获取文件片段的长度
    long len;

    public PiecePointer(File file, long start, long end) throws IOException {
        this.len = end - start + 1;
        randomAccessFile = new RandomAccessFile(file, "rw");
        //将文件指针指向第（start+1）个字节上
        randomAccessFile.seek(start);
    }

    //写
    public void write(byte[] bytes) throws IOException {
        randomAccessFile.write(bytes, 0, (int) len);
    }

    //读
    public void read(byte[] bytes) throws IOException {
        randomAccessFile.read(bytes, 0, (int) len);
    }
}
