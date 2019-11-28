package GameLogic;
import java.util.*;

public class AggressivePlayer extends Player implements IPlayer {
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

    @Override
    /**
     * This method attacks until no attack is possible using maximum number of dice to attack or defend
     * @param fromCountry It is a String and denotes the name of the country from where the armies are to be sent
     * @param toCountry It is a String and denotes the name of the country that is to be attacked
     * @param graphObj It is an object of the class Graph
     * @param currentPlayerObj It is an object of the class CurrentPlayer
     * @return true(if the method executes successfully) or false(if the country entered is invalid or some other validation fails)
     */
    public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {

        Country strongestCountry=null;
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
        Country attackerCountry = strongestCountry;
        System.out.println(GamePlay.getInstance().getCurrentPlayerName()+" attacking from "+strongestCountry.getName());
        Country defenderCountry = null;
        Integer weakestCountryNumber = null;

        if (attackerCountry.neighbours.size() != 0) {

            for (int i = 0; i < attackerCountry.neighbours.size(); i++) {

                Integer countryNumber = attackerCountry.neighbours.get(i);

                if (weakestCountryNumber == null) {
                    weakestCountryNumber = countryNumber;
                } else if (Country.getCountryByNumber(weakestCountryNumber, graphObj).getNumberOfArmies() > Country.getCountryByNumber(countryNumber, graphObj).getNumberOfArmies() &&
                        Country.getCountryByNumber(weakestCountryNumber, graphObj).getOwner()!=Country.getCountryByNumber(countryNumber, graphObj).getOwner() ){
                    weakestCountryNumber = countryNumber;
                }

            }

            defenderCountry = Country.getCountryByNumber(weakestCountryNumber, graphObj);
            System.out.println("Attacking "+defenderCountry.getName());
            String attackerName = attackerCountry.getOwner();
            String defenderName = defenderCountry.getOwner();
            IPlayer attacker = Database.getPlayerByName(attackerName);
            IPlayer defender = Database.getPlayerByName(defenderName);

            if (attackerCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

                if (defenderCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

                    System.out.println("You can only attack the countries that are owned by some other player");
                    return false;
                } else {

                    if (attackerCountry.neighbours.contains(defenderCountry.getNumber())) {

                        Integer AttackerArmiesSelected = null;
                        Integer DefenderArmiesSelected = null;

                        if (attackerCountry.getNumberOfArmies() > 3) {
                            // armies selected 3
                            AttackerArmiesSelected = 3;
                        } else if (attackerCountry.getNumberOfArmies() == 3) {
                            // armies selected 2
                            AttackerArmiesSelected = 2;
                        } else if (attackerCountry.getNumberOfArmies() == 2) {
                            // armies selected 1
                            AttackerArmiesSelected = 1;
                        } else {
                            System.out.println("Cannot attack anymore " + fromCountry + ", has only 1 army left!");
                        }

                        if (defenderCountry.getNumberOfArmies() >= 2) {
                            // armies selected 2
                            DefenderArmiesSelected = 2;
                        } else if (defenderCountry.getNumberOfArmies() == 1) {
                            // armies selected 1
                            DefenderArmiesSelected = 1;
                        } else {
                            System.out.println("No more armies to defend the country");
                        }

                        System.out.println("Before Atack");
                        System.out.println("Attacker Armies : " + Country
                                .getCountryByName(attackerCountry.name, Graph.getInstance()).getNumberOfArmies());
                        System.out.println("Defender Armies : " + Country
                                .getCountryByName(defenderCountry.name, Graph.getInstance()).getNumberOfArmies());
                        battle(attackerCountry, defenderCountry, AttackerArmiesSelected, DefenderArmiesSelected,
                                attacker, defender);
                        AttackerArmiesSelected = null;
                        DefenderArmiesSelected = null;
                        System.out.println("After Atack");
                        System.out.println("Attacker Armies : " + Country
                                .getCountryByName(attackerCountry.name, Graph.getInstance()).getNumberOfArmies());
                        System.out.println("Defender Armies : " + Country
                                .getCountryByName(defenderCountry.name, Graph.getInstance()).getNumberOfArmies());
                        System.out.println("--------------------------------");

                        if (defenderCountry.getNumberOfArmies() == 0) {
                            System.out.println("Attacker won the country " + defenderCountry.name);
                            attacker.setMyCountries(defenderCountry.getNumber());
                            defenderCountry.setOwner(attackerCountry.getOwner());
                            defender.getMyCountries().remove(defenderCountry.getNumber());
                            setCountryConquered(true);
                            if (defender.getMyCountries().size() == 0) {
                                defenderRemoved = true;
                            }
                            // System.out.println("Please enter a command to move armies to " +
                            // defenderCountry.name);
                            // System.out.println("Please select a number greater than or equal to " +
                            // lastDiceSelected
                            // + " and less than " + attackerCountry.getNumberOfArmies());

                            Integer attackMove = null;

                            attackMove = lastDiceSelected;

                            if (attackMove != null) {
                                attackMove(attackerCountry, defenderCountry, attackMove);
                            } else {
                                System.out.println("something went wrong!!");
                            }
                            System.out.println("AttackAllOut is finished here.");

                        } else if (attackerCountry.getNumberOfArmies() == 1) {
                            System.out.println("no attack anymore possible!!!");
                        } else {
                            attackAllout(fromCountry, toCountry, graphObj, currentPlayerObj);
                        }

                    } else {
                        System.out.println("Attacker country and the defender country should be adjacent");
                        return false;
                    }
                }
            } else {
                System.out.println("Please select the country owned by you("
                        + currentPlayerObj.getCurrentPlayer().getName() + ") as attackerCountry");
                return false;
            }

            GamePlay.getInstance().setCurrentOperation(
                    "Performing all-out attack from " + attackerCountry.name + " to " + defenderCountry.name);

            // if defender lost all of his country, attacker will owned all of his cards.
            if ( getDefenderRemoved() == true) {

                for (Card itr : defender.getPlayerCards()) {
                    Card tempcard = itr;
                    tempcard.setOwner(currentPlayerObj.getCurrentPlayer().getNumber());
                    currentPlayerObj.getCurrentPlayer().setPlayerCards(tempcard);
                }
                Database.removePlayer(defenderName);
                setDefenderRemoved(false);
            }
            return true;
        } else {
            System.out.println("All the neighbouring countries are owned by the current player");
            return false;
        }

    }


    /**
     * This method defines how the attack takes place.
     * It covers all the steps of the aggressive attack mode.
     * @param attackerCountry It is an object of the class Country and gives the attacking country
     * @param defenderCountry It is an object of the class Country and gives the defending country
     * @param numberOfArmiesToMove It is an integer value of the total number of armies that attack the defending country
     * @return true(If the method executes and the the attack move executes successfully) or false(If the number of armies entered is invalid or any other validation fails)
     */
    public static boolean attackMove(Country attackerCountry, Country defenderCountry, Integer numberOfArmiesToMove) {

        if (defenderCountry.getNumberOfArmies() == 0) {

            if (attackerCountry.getNumberOfArmies() > numberOfArmiesToMove) {

                defenderCountry.setNumberOfArmies(numberOfArmiesToMove);
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
     * This method takes care of the battle between the attackerCountry and the defenderCountry and is responsible for declaring whoever wins the battle
     * @param attackerCountry object of the country that attacks another country
     * @param defenderCountry object of the country that defends itself in the turn
     * @param attackerArmies Integer number of armies that are to be used for attack
     * @param defenderArmies Integer number of armies that are to be used for defend
     * @param attacker Object of the class IPlayer
     * @param defender Object of the class IPlayer
     * @return true(If the method executes and battle is executed successfully)
     */
    public static boolean battle(Country attackerCountry, Country defenderCountry, Integer attackerArmies,
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
        defenderCountry.setNumberOfArmies(defenderCountry.getNumberOfArmies() - defenderArmiesKilled);

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
                if((country != fromCountry)&& (toCountry==null)) {
                    toCountry = country;
                }else if((country != fromCountry)&& (toCountry.getNumberOfArmies()<country.getNumberOfArmies())) {
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
            System.out.println("There should be the two countries.\n Current Player should own the path.");
            return false;
        } else if (!(fromCountry.getNumberOfArmies() - numberOfArmies > 0)) {
            System.out.println("You must leave at least 1 army unit behind");
            return false;
        }

        ArrayList<Integer> toCountryNeighbours = toCountry.getNeighbours();

        fromCountry.setNumberOfArmies(fromCountry.getNumberOfArmies() - numberOfArmies);
        toCountry.setNumberOfArmies(toCountry.getNumberOfArmies() + numberOfArmies);

        Country.updatePlayerListAndDeclareWinner(gameGraph);

        return true;

    }

}