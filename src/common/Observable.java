package common;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {
    private final Set<Observer> observers;

    public Observable() {
        this.observers = new HashSet<>();
    }

    public synchronized void removeObserver(Observer observer) {
        if (observer == null) throw new NullPointerException();
        observers.remove(observer);
    }

    public synchronized void addObserver(Observer observer) {
        if (observer == null) throw new NullPointerException();
        observers.add(observer);
    }

    public void notifyObservers(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
