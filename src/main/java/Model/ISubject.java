package Model;

import View.IObserver;

/**
 * It is a subject interface for the observer class
 *
 */
public interface ISubject {
    public void notifyObservers();
    public void attachObserver(IObserver observer);
    public void detachObserver(IObserver observer);
}
