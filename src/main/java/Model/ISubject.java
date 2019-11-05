package Model;

import View.IObserver;

public interface ISubject {
    public void notifyObservers();
    public void attachObserver(IObserver observer);
    public void detachObserver(IObserver observer);
}
