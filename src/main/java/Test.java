import utils.PeerMG;
import utils.TrackerMG;

/*
启动服务器
*/
public class Test {
    public static void main(String[] args) {
        TrackerMG.getInstance().startInfoServerListener();
    }
}
