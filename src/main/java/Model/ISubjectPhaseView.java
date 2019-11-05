package Model;

import View.IObserverPhaseView;

public interface ISubjectPhaseView {
    public void notifyObservers();
    public void attachObserver(IObserverPhaseView observer);
    public void detachObserver(IObserverPhaseView observer);
}
