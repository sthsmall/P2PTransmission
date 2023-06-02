package Tracker.Model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
//用于输出文件的某一片段
public class PiecePointer {
    RandomAccessFile randomAccessFile;
    long len;
    public PiecePointer(File file, long start,long end) throws IOException {
        this.len = end - start + 1;
        randomAccessFile = new RandomAccessFile(file,"rw");
        randomAccessFile.seek(start);
    }


    public void write(byte[] bytes) throws IOException {
        randomAccessFile.write(bytes,0,(int)len);
    }

    public void read(byte[] bytes) throws IOException {
         randomAccessFile.read(bytes,0,(int)len);
    }
}
