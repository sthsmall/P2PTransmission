import service.Tracker.Listener.Listener;
import utils.TrackerMG;

public class TrackerStarter {
    public static void main(String[] args) {
        new Listener().start();
        TrackerMG.getInstance().startInfoServerListener();
    }
}
