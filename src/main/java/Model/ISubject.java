package Model;

public interface ISubject {
    public void notifyObservers();
    public void addObserver(Object observer);
    public void detachObserver(Object observer);
}
