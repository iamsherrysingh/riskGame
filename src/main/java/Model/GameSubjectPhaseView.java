package Model;

import java.util.ArrayList;

import View.IObserverPhaseView;

public class GameSubjectPhaseView implements ISubjectPhaseView {
	
	State currentState;
	ArrayList<IObserverPhaseView> observerList = new ArrayList<IObserverPhaseView>();
	
	public void stateChanged(State currentState) 
    {  
		this.currentState = currentState;
        notifyObservers(); 
    }
	
	@Override
	public void notifyObservers() {		
		for(IObserverPhaseView itr:observerList) {
	//		itr.update();   commented because of error   -Sehaj
		}
	}

	@Override
	public void attachObserver(IObserverPhaseView observerObj) {
		observerList.add(observerObj);
	}

	@Override
	public void detachObserver(IObserverPhaseView observerObj) {
		observerList.remove(observerObj);
	}
}
