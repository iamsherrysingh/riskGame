package View;

import GameLogic.*;

/**
 * This implements Observer Pattern and runs all the update methods
 */
public class PhaseView implements IObserver{
    String header=          "================================================"  + "\n" +
                            "===================PHASE VIEW===================";
    String footer=          "________________________________________________";


    public  String data;
    @Override
    public void update(GamePlay gamePlay, IPlayer player) {
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

