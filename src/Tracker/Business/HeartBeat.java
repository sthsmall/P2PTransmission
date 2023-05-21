package Tracker.Business;

public class HeartBeat  extends Thread{
    //心跳线程
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000*60*5);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //发送心跳包
            //更新在线用户列表

        }
    }
}
