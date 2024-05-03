package view;

import common.Observable;
import common.Observer;

public abstract class View extends Observable implements Observer {
    public abstract void run();
}
