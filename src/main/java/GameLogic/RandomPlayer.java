package GameLogic;
import java.util.*;

public class RandomPlayer extends Player implements IPlayer {
    Integer randomNumberGenerated;
    Country attackerCountry;
    
    /**
     * This is a constructor of the class RandomPlayer.
     * It implements the game using the RandomPlayer stratedy.
     * @param number Integer number denoting the number given to the player
     * @param name String name of the player
     * @param numberOfArmies The total number of armies in integer form
     */
    public RandomPlayer(Integer number, String name, Integer numberOfArmies) {
        this.number = number;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
        playerCards = new ArrayList<Card>();
        exchangeCardsTimes = 0;
        countryConquered = false;
        defenderRemoved = false;
    }

	public RandomPlayer() {
		playerCards = new ArrayList<Card>();
	}
	
    public PlayerStrategy getPlayerStrategy() {
        return PlayerStrategy.random;
    }
    @Override
    /**
     * This method reinforces any random country.
     * @param countryName This is n string which specified by user for reinforcement
     * @param numberOfArmies This is an integer parameter which specify the number of armies for reinforcement
     * @param graphObj This is an object which pass the game graph.
     * @param currentPlayerObj This is an object which is current player of the game.
     * @return true(if runs successfully and the country is reinforced) or false (in case it fails any validation)
     */
    public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
                                 CurrentPlayer currentPlayerObj) {

        Integer randomNumberOfArmies = getRandomNumber(currentPlayerObj.getNumReinforceArmies());

        numberOfArmies = randomNumberOfArmies;
        Country randomCountry = null;

        ArrayList<Country> countriesOwned = new ArrayList<Country>();

        for (Country country : graphObj.getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {

                countriesOwned.add(country);

            }
        }

        randomCountry = countriesOwned.get(getRandomNumber(countriesOwned.size()) - 1);
        attackerCountry = randomCountry;

        // check: if target country is not exist, return false
        Country targetCountry = randomCountry;

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
        targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + numberOfArmies);

        // increase the number of armies belong to the player
        currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
        currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);

        GamePlay.getInstance().setCurrentOperation(
                "Country " + targetCountry.name + " reinforced with " + numberOfArmies + " armies.");
        return true;

    }

    @Override
    /**
     * This methodcovers the functionality of the game in which any random country attacks any other random country
     * random number of times.
     * @param fromCountry      AttackerCountry
     * @param toCountry        DefenderCountry
     * @param graphObj         its graphs instance
     * @param currentPlayerObj its current player's instance
     * @return true(if runs successfully) or false(in case it fails any validation)
     */
    public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {

        Country defenderCountry = null;

        if (attackerCountry.neighbours.size() != 0) {

//			for (int i = 0; i < attackerCountry.neighbours.size(); i++) {
//
//				Integer countryNumber = attackerCountry.neighbours.get(i);
//
//				if (weakestCountryNumber == null) {
//					weakestCountryNumber = countryNumber;
//				} else if (Country.getCountryByNumber(weakestCountryNumber, graphObj).getNumberOfArmies() > Country
//						.getCountryByNumber(countryNumber, graphObj).getNumberOfArmies()) {
//					weakestCountryNumber = countryNumber;
//				}
//
//			}
//
            ArrayList<Integer> tempNeighbourList = new ArrayList<Integer>();
            for (int i = 0; i < attackerCountry.neighbours.size(); i++) {

                if (!((Country.getCountryByNumber(attackerCountry.neighbours.get(i), graphObj).getOwner())
                        .equalsIgnoreCase(attackerCountry.getOwner()))) {

                    tempNeighbourList.add(attackerCountry.neighbours.get(i));
                    System.out.println("size" + tempNeighbourList.size());

                }

            }

            Integer no;
            if (tempNeighbourList.isEmpty()) {
                no = 0;
            } else {
                no = getRandomNumber(tempNeighbourList.size());
            }
            System.out.println(no);
            if(no>0) {
                defenderCountry = Country.getCountryByNumber(tempNeighbourList.get(no - 1), graphObj);
//					attackerCountry.neighbours.get(getRandomNumber(attackerCountry.neighbours.size())), graphObj);
            }else {
                System.out.println("All the neighbouring countries are owned by the same player");
                return false;
            }
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
                            AttackerArmiesSelected = getRandomNumber(3);
                        } else if (attackerCountry.getNumberOfArmies() == 3) {
                            // armies selected 2
                            AttackerArmiesSelected = getRandomNumber(2);
                        } else if (attackerCountry.getNumberOfArmies() == 2) {
                            // armies selected 1
                            AttackerArmiesSelected = 1;
                        } else {
                            System.out.println("Cannot attack anymore " + fromCountry + ", has only 1 army left!");
                        }

                        if (defenderCountry.getNumberOfArmies() >= 2) {
                            // armies selected 2
                            DefenderArmiesSelected = getRandomNumber(2);
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
            return true;
        } else {
            System.out.println("All the neighbouring countries are owned by the current player");
            return false;
        }

    }

    /**
     * This handles the attackMove command to move the right number of armies from
     * attacker country to defender country when attacker wins the defender country
     * @param attackerCountry The country attacking
     * @param defenderCountry The country that is being attacked
     * @param numberOfArmiesToMove The total number of armies that are moved in integer form
     * @return true(If the attack took place) or false(If the attack was unsuccessful or the number of armies is incorrect)
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
     * This method manages the battle between attackerCountry and defenderCountry and declares whoever wins the battle.
     *
     * @param attackerCountry object of the country that attacks the other country.
     * @param defenderCountry object of the country that is attacked.
     * @param attackerArmies Integer value, number of armies selected by attacker to attack.
     * @param defenderArmies Integer value, number of armies selected by defender to defend.
     * @param attacker  Object of IPlayer
     * @param defender Object of  IPlayer
     * @return true(if a battle is played between the attacker and defender)
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
     *This method fortifies the countries by placing 2 times the army to the country that have another player's country as their neighbour.
     * @param fromCname The name of the country from where the armies are to be moved
     * @param toCountryName The name of the country to which the armies are to be moved
     * @param numberOfArmies The total number of armies to be moved
     * @param gameGraph This is an object of the class Graph
     * @return true(If all the conditions are satisfied and the countries are fortified) or false(If the countries specified are absent or it does not fulfill the requirements)
     */
    public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {

        Country randomCountry = null;

        ArrayList<Country> countriesOwned = new ArrayList<Country>();

        for (Country country : gameGraph.getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(GamePlay.getInstance().getCurrentPlayerName())) {

                countriesOwned.add(country);

            }
        }
        Country fromCountry = countriesOwned.get(getRandomNumber(countriesOwned.size())-1);
        Country toCountry = null;

        toCountry = countriesOwned.get(getRandomNumber(countriesOwned.size())-1);
        if (fromCountry.equals(toCountry)) {

            while (fromCountry.equals(toCountry)) {
                toCountry = countriesOwned.get(getRandomNumber(countriesOwned.size()));
            }

        }

//		for (Country country : gameGraph.getAdjList()) {
//
//			if(country.getOwner().equalsIgnoreCase(fromCountry.getOwner())) {
//
//				if(country.equals(fromCountry)) {
//
//				}else {
//
//				}
//
//
//
////				if((country != fromCountry)&& (toCountry==null)) {
////					toCountry = country;
////				}else if((country != fromCountry)&& (toCountry.getNumberOfArmies()<country.getNumberOfArmies())) {
////					toCountry=country;
////				}
//			}
//		}

        if ((fromCountry.numberOfArmies - 1) > 0) {
            numberOfArmies = getRandomNumber((fromCountry.numberOfArmies - 1));
        } else {
        }

        if (fromCountry == null || toCountry == null) {
            System.out.println("One or both countries do not exist");
            return false;
        } else if (!(toCountry.getOwner().equalsIgnoreCase(fromCountry.getOwner()))) {
            System.out.println("A player has to own both the countries");
            return false;
        } else if (!(Mapx.checkPath(toCountry.name, fromCountry.name, gameGraph))) {
            System.out.println("weakestNeighbourEnemyd be the two countries.\n Current Player should own the path.");
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
