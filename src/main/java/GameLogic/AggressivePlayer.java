package GameLogic;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This is a type of behavior of player used with Strategy Pattern
 */
public class AggressivePlayer extends Player implements IPlayer {
	public boolean fortifiedAlready=false;
    //Country strongestCountryFound;
    /**
     * This is a constructor of the class AggressivePlayer
     * This method playing strategy focuses on attack mode of the game
     * @param number This a variable of the type int and denotes the player's number
     * @param name It is a String type variable and denotes the name of the player
     * @param numberOfArmies It is an integer number that denotes the number of armies a player has
     */
    public AggressivePlayer(Integer number, String name, Integer numberOfArmies) {
        this.number = number;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
        playerCards = new ArrayList<Card>();
        exchangeCardsTimes = 0;
        countryConquered = false;
        defenderRemoved = false;
    }

	public AggressivePlayer() {
		playerCards = new ArrayList<Card>();
	}
	
    public PlayerStrategy getPlayerStrategy() {
        return PlayerStrategy.aggressive;
    }
    @Override
    /**
     * This method check validation of reinforcements and do reinforce.
     * @param countryName This is n string which specified by user for reinforcement
     * @param numberOfArmies This is an integer parameter which specify the number of armies for reinforcement
     * @param graphObj This is an object which pass the game graph.
     * @param currentPlayerObj This is an object which is current player of the game.
     * @return true(if runs successfully and the country is reinforced) or false (in case it fails any validation)
     */
    public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
                                 CurrentPlayer currentPlayerObj) {

        numberOfArmies = currentPlayerObj.getNumReinforceArmies();
        Country strongestCountry = null;

        for (Country country : graphObj.getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
                if (strongestCountry == null) {
                    strongestCountry = country;
                    // countryName=country.getName();
                } else {
                    if (country.getNumberOfArmies() > strongestCountry.getNumberOfArmies()) {
                        strongestCountry = country;
                    }
                }
            }
        }

        Country strongestCountryFound = strongestCountry; // used in other methods also
        // check: if target country is not exist, return false
        Country targetCountry = strongestCountry;

        if (targetCountry == null) {
            System.out.println("This country does not exist.");
            return false;
        }

        // check: if country does not belong to the currentPlayer, return false
        if (targetCountry.getOwner() != null) {
            if (targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false) {
                System.out.println("The country is not belong to the current player");
                return false;
            }
        }

        // check: if numberOfArmy is more than allocated army, return false
        if (numberOfArmies > currentPlayerObj.getNumReinforceArmies()) {
            System.out.println(
                    "The current player can reinforce just " + currentPlayerObj.getNumReinforceArmies() + "armies");
            return false;
        }

        // Reinforce armies in the target country
        targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + currentPlayerObj.getNumReinforceArmies());

        // increase the number of armies belong to the player
        currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
        currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);

        GamePlay.getInstance().setCurrentOperation(
                "Country " + targetCountry.name + " reinforced with " + numberOfArmies + " armies.");
        return true;

    }

    public Country getStrongestCountry(CurrentPlayer currentPlayerObj){
        Country strongestCountry=null;
        for (Country country : GamePlay.getInstance().getGraphObj().getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
                if (strongestCountry == null) {
                    strongestCountry = country;
                    // countryName=country.getName();
                } else {
                    if (country.getNumberOfArmies() > strongestCountry.getNumberOfArmies()) {
                        strongestCountry = country;
                    }
                }
            }
        }

        return strongestCountry;
    }

    public Country getWeakestNeighbourEnemy(Country attackerCountry){
        ArrayList<Integer> nlist= attackerCountry.neighbours;
        if(nlist.size()==0)
            return null;
        Country weakestNeighbourEnemy=null;
        for(Integer nnumbr:nlist) {
            Country neighbour= Country.getCountryByNumber(nnumbr, GamePlay.getInstance().getGraphObj());
            if(!(neighbour.getOwner().equalsIgnoreCase(attackerCountry.getOwner())))
                continue;
            if (weakestNeighbourEnemy == null) {
                weakestNeighbourEnemy = neighbour;
                // countryName=country.getName();
            } else if(neighbour.getNumberOfArmies() < weakestNeighbourEnemy.getNumberOfArmies()) {
                weakestNeighbourEnemy=neighbour;
            }
        }
        return weakestNeighbourEnemy;
    }
    public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {
        Country strongestCountry=getStrongestCountry(currentPlayerObj);
        Country weakestNeighbourEnemy= getWeakestNeighbourEnemy(strongestCountry);
        if(weakestNeighbourEnemy==null){//All neighbours conquered
                                        //fortify armies to strongest neighbour with 1 or more enemy
            fortifyStrongestNeighbour(strongestCountry);
            fortifiedAlready=true;
            return true;
        }
        System.out.println(">>>>"+strongestCountry.name+" attacking "+weakestNeighbourEnemy.name);

        while (getWeakestNeighbourEnemy(strongestCountry) != null) {
            Integer AttackerArmiesSelected = null;
            Integer DefenderArmiesSelected = null;
            if (strongestCountry.getNumberOfArmies() > 3) {
                // armies selected
                AttackerArmiesSelected = 3;
            } else if (strongestCountry.getNumberOfArmies() == 3) {
                // armies selected 2
                AttackerArmiesSelected = 2;
            } else if (strongestCountry.getNumberOfArmies() == 2) {
                // armies selected 1
                AttackerArmiesSelected = 1;
            } else{
                //Fortify Strongest country with max armies possible
                return true;
            }

            if (weakestNeighbourEnemy.getNumberOfArmies() >= 2) {
                // armies selected 2
                DefenderArmiesSelected = 2;
            } else if (weakestNeighbourEnemy.getNumberOfArmies() < 2) {
                // armies selected 1
                DefenderArmiesSelected = 1;
            }

            battle(strongestCountry, weakestNeighbourEnemy, AttackerArmiesSelected, DefenderArmiesSelected,
                    Database.getPlayerByName(strongestCountry.owner), Database.getPlayerByName(weakestNeighbourEnemy.owner));
            System.out.println("battle Executed");
            //try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){}
            if (weakestNeighbourEnemy.getNumberOfArmies() == 0) {
                System.out.println("Attacker won the country " + weakestNeighbourEnemy.name);
                Database.getPlayerByName(strongestCountry.owner).setMyCountries(weakestNeighbourEnemy.getNumber());
                weakestNeighbourEnemy.setOwner(strongestCountry.getOwner());
                Database.getPlayerByName(weakestNeighbourEnemy.owner).getMyCountries().remove(weakestNeighbourEnemy.getNumber());
                setCountryConquered(true);
                if (Database.getPlayerByName(weakestNeighbourEnemy.owner).getMyCountries().size() == 0) {
                    for (Card itr : Database.getPlayerByName(weakestNeighbourEnemy.owner).getPlayerCards()) {
                        Card tempcard = itr;
                        tempcard.setOwner(currentPlayerObj.getCurrentPlayer().getNumber());
                        currentPlayerObj.getCurrentPlayer().setPlayerCards(tempcard);
                    }
                    Database.getInstance().getPlayerList().remove(Database.getPlayerByName(weakestNeighbourEnemy.owner).getNumber() - 1);
                    defenderRemoved = false;
                }
            }
                //attack move
                weakestNeighbourEnemy.setNumberOfArmies(weakestNeighbourEnemy.getNumberOfArmies()+AttackerArmiesSelected);
                strongestCountry.setNumberOfArmies(strongestCountry.getNumberOfArmies()-AttackerArmiesSelected);


        }
        return true;
    }

    private void fortifyStrongestNeighbour(Country strongestCountry) {
        ArrayList<Country> neighbourList= Graph.getInstance().getNeighbourListAsCountries(strongestCountry);
        System.out.println("in fortifyStrongestNeighbour");
//        try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){}
        Country strongestNeighbour=null;
        for(Country country:neighbourList){
            if(country.owner.equalsIgnoreCase(strongestCountry.owner)){
                if(strongestNeighbour==null)
                    strongestNeighbour=country;
                else if(country.getNumberOfArmies()>strongestNeighbour.getNumberOfArmies()) {
                    strongestNeighbour = country;
                    if(getWeakestNeighbourEnemy(strongestNeighbour)==null){
                        fortifyStrongestNeighbour(strongestNeighbour);
                    }
                    else{
                        return;
                    }
                }
            }
        }
    }


    /**
     * This method defines how the attack takes place.
     * It covers all the steps of the aggressive attack mode.
     * @param attackerCountry It is an object of the class Country and gives the attacking country
     * @param weakestNeighbourEnemy It is an object of the class Country and gives the defending country
     * @param numberOfArmiesToMove It is an integer value of the total number of armies that attack the defending country
     * @return true(If the method executes and the the attack move executes successfully) or false(If the number of armies entered is invalid or any other validation fails)
     */
    public static boolean attackMove(Country attackerCountry, Country weakestNeighbourEnemy, Integer numberOfArmiesToMove) {

        if (weakestNeighbourEnemy.getNumberOfArmies() == 0) {

            if (attackerCountry.getNumberOfArmies() > numberOfArmiesToMove) {

                weakestNeighbourEnemy.setNumberOfArmies(numberOfArmiesToMove);
                attackerCountry.setNumberOfArmies(attackerCountry.getNumberOfArmies() - numberOfArmiesToMove);

            } else {

                System.out.println("you selected a greater number than you are allowed to move from attacker country");
                return false;
            }

        } else {
            System.out.println("something went wrong!!");
            return false;
        }
        return true;
    }

    /**
     * This method performs a dice roll and generates a random number ranging till maxDice.
     * @param maxDice its an integer value defining the upper value of the number to be generated
     * @return a random number
     */
    public static int getRandomNumber(Integer maxDice) {
        Random randomGenerator;
        randomGenerator = new Random();
        return randomGenerator.nextInt(maxDice) + 1;
    }


    /**
     * This method takes care of the battle between the attackerCountry and the weakestNeighbourEnemy and is responsible for declaring whoever wins the battle
     * @param attackerCountry object of the country that attacks another country
     * @param weakestNeighbourEnemy object of the country that defends itself in the turn
     * @param attackerArmies Integer number of armies that are to be used for attack
     * @param defenderArmies Integer number of armies that are to be used for defend
     * @param attacker Object of the class IPlayer
     * @param defender Object of the class IPlayer
     * @return true(If the method executes and battle is executed successfully)
     */
    public static boolean battle(Country attackerCountry, Country weakestNeighbourEnemy, Integer attackerArmies,
                                 Integer defenderArmies, IPlayer attacker, IPlayer defender) {

        lastDiceSelected = attackerArmies;
        Integer index = 0;
        Integer defenderArmiesKilled = 0;
        Integer attackerArmiesKilled = 0;
        ArrayList<Integer> defenderDices = new ArrayList<Integer>();
        ArrayList<Integer> attackerDices = new ArrayList<Integer>();

        for (int i = 0; i < attackerArmies; i++) {
            index = getRandomNumber(6);
            attackerDices.add(index);
        }
        for (int i = 0; i < defenderArmies; i++) {
            index = getRandomNumber(6);
            defenderDices.add(index);
        }
        Collections.sort(attackerDices);
        Collections.sort(defenderDices);
        Collections.reverse(attackerDices);
        Collections.reverse(defenderDices);

        if (defenderArmies > attackerArmies) {
            for (int i = 0; i < attackerArmies; i++) {
                if (attackerDices.get(i) > defenderDices.get(i)) {
                    defenderArmiesKilled++;
                    System.out.println("--- Attacker wins the battle ---");
                    defender.setNumberOfArmies(defender.getNumberOfArmies() - 1);
                } else {
                    attackerArmiesKilled++;
                    System.out.println("--- Defender wins the battle ---");
                    attacker.setNumberOfArmies(defender.getNumberOfArmies() - 1);
                }
            }
        } else {

            for (int i = 0; i < defenderArmies; i++) {

                if (attackerDices.get(i) > defenderDices.get(i)) {
                    defenderArmiesKilled++;
                    System.out.println("--- Attacker wins the battle ---");
                    defender.setNumberOfArmies(defender.getNumberOfArmies() - 1);
                } else {
                    attackerArmiesKilled++;
                    System.out.println("--- Defender wins the battle ---");
                    attacker.setNumberOfArmies(defender.getNumberOfArmies() - 1);
                }
            }
        }

        attackerCountry.setNumberOfArmies(attackerCountry.getNumberOfArmies() - attackerArmiesKilled);
        weakestNeighbourEnemy.setNumberOfArmies(weakestNeighbourEnemy.getNumberOfArmies() - defenderArmiesKilled);

        return true;
    }

    @Override
    /**
     *This method adds the functionality for the player to send their armies from a neighbouring country
     * to the weaker country
     *  @param fromCname The name of the country from where the armies are to be moved
     * 	@param toCountryName The name of the country to which the armies are to be moved
     *  @param numberOfArmies The total number of armies to be moved
     *  @param gameGraph This is an object of the class Graph
     *  @return true(If all the conditions are satisfied and the desired country is fortified) or false(If the countries specified are absent or it does not fulfill the requirements)
     *
     */
    public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {

        Country strongestCountry=null;
        for (Country country : gameGraph.getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerObj().getCurrentPlayer().getName())) {
                if (strongestCountry == null) {
                    strongestCountry = country;
                    // countryName=country.getName();
                } else {
                    if (country.getNumberOfArmies() > strongestCountry.getNumberOfArmies()) {
                        strongestCountry = country;
                    }
                }
            }
        }
        Country fromCountry = strongestCountry;
        Country toCountry = null;

        for (Country country : gameGraph.getAdjList()) {

            if(country.getOwner().equalsIgnoreCase(fromCountry.getOwner())) {
                if((country.getNumber()!= fromCountry.getNumber())&& (toCountry==null)) {
                    toCountry = country;
                }else if((country.getNumber()!= fromCountry.getNumber())&& (toCountry.getNumberOfArmies()<country.getNumberOfArmies())) {
                    toCountry=country;
                }
            }
        }

        numberOfArmies = (fromCountry.numberOfArmies-1);



        if (fromCountry == null || toCountry == null) {
            System.out.println("One or both countries do not exist");
            return false;
        } else if (!(toCountry.getOwner().equalsIgnoreCase(fromCountry.getOwner()))) {
            System.out.println("A player has to own both the countries");
            return false;
        } else if (!(Mapx.checkPath(toCountry.name,fromCountry.name, gameGraph))) {
            System.out.println("weakestNeighbourEnemyd be path between two countries to fortify");
            return false;
        } else if ((fromCountry.getNumberOfArmies() - numberOfArmies < 1)) {
            System.out.println("You must leave at least 1 army unit behind");
            return false;
        }
        System.out.println(">>>>Aggressive fortiying "+fromCountry.name+fromCountry.getNumberOfArmies()+">>"+toCountry.name+toCountry.getNumberOfArmies());
        ArrayList<Integer> toCountryNeighbours = toCountry.getNeighbours();

        fromCountry.setNumberOfArmies(fromCountry.getNumberOfArmies() - numberOfArmies);
        toCountry.setNumberOfArmies(toCountry.getNumberOfArmies() + numberOfArmies);

        Country.updatePlayerListAndDeclareWinner(gameGraph);

        return true;

    }

}
