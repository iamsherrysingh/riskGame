package View;

import Model.Database;
import Model.GamePlay;
import Model.Player;

/**
 * This is a concrete observer that implements phase view
 */
public class PhaseView implements IObserver{
    String header=          "================================================"  + "\n" +
                            "===================PHASE VIEW===================";
    String footer=          "________________________________________________";


    String data;

    /**
     * This method prints the phase view on console
     * @param gamePlay is an object of Model.GamePlay
     * @param player is an object of Model.Player
     */
    @Override
    public void update(GamePlay gamePlay, Player player) {
        data= "Current State is :  "+gamePlay.getCurrentState().toString();
        try {
            data += "\nCurrent Player is: " + gamePlay.getCurrentPlayerName();
        }catch (Exception e){
            //System.out.println("Skip above statement as player isn't initilized yet");
        }
        try {
            if(gamePlay.getCurrentOperation() != null)
                data += "\nOperation: " + gamePlay.getCurrentOperation();
        }catch (Exception e){
            //System.out.println("Skip above statement as player isn't initilized yet");
        }

        System.out.println(header + "\n" + data+"\n"+footer);

    }

    public void printPhaseView(){
        System.out.println(header + "\n" + data+"\n"+footer);

    }
}

