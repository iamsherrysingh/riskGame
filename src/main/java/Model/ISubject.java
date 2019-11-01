package Model;

public interface ISubject {
    public void notifyObservers();
    public void attachObserver(Object observer);
    public void detachObserver(Object observer);
}
