import service.Tracker.Listener.Listener;
import service.Tracker.Listener.ListenerOfTorrent;

public class Test1 {
    public static void main(String[] args) {
        new Listener().start();
        new ListenerOfTorrent().start();
    }
}
