package Tracker.Model;

import Tracker.TrackerMG;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

//实际文件管理器
public class TrueFileMG {
    private int filePieceSize = TrackerMG.FilePieceSize;
    File file;
    public TrueFileMG(File file){
        this.file = file;
    }
    //获取文件的某一片段的指针
    public PiecePointer getPiecePointer(long num) throws IOException {
        long start = filePieceSize * num;
        long end = num * filePieceSize + 1;
        return new PiecePointer(file,start,end);
    }

}
