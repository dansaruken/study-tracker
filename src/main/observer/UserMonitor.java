package observer;

import java.util.Observable;
import java.util.Observer;

public class UserMonitor implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("Course added: " + arg);
    }
}
