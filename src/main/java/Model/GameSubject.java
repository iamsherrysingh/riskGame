package Model;

import java.util.ArrayList;

import View.IObserver;

public class GameSubject implements ISubject {
	
	State currentState;
	ArrayList<IObserver> observerList = new ArrayList<IObserver>(); 
	
	public void stateChanged(State currentState) 
    {  
		this.currentState = currentState;
        notifyObservers(); 
    } 
	
	@Override
	public void notifyObservers() {		
		for(IObserver itr:observerList) {
			itr.update();
		}
	}

	@Override
	public void attachObserver(IObserver observerObj) {
		observerList.add(observerObj);
	}

	@Override
	public void detachObserver(IObserver observerObj) {
		observerList.remove(observerObj);
	}
}
