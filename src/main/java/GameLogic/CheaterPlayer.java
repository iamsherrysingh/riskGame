package GameLogic;
import java.util.*;

public class CheaterPlayer  extends Player implements IPlayer{
    /**
     * This is a constructor of the class CheaterPlayer.
     * It implements the CheaterPlayer strategy when given command.
     * @param number It is the number of the player as an integer
     * @param name It is the name of the player as a String
     * @param numberOfArmies It is the integer number denoting the total number of armies of the player
     */
    public CheaterPlayer(Integer number, String name, Integer numberOfArmies) {
        this.number = number;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
        playerCards = new ArrayList<Card>();
        exchangeCardsTimes = 0;
        countryConquered = false;
        defenderRemoved = false;
    }

    public PlayerStrategy getPlayerStrategy() {
        return PlayerStrategy.cheater;
    }

    @Override
    /**
     * This method implements using the CheaterPlayer strategy.
     * While reinforcing, this method doubles the number of armies on all its countries.
     * @param countryName The name of the country to be reinforced
     * @param numberOfArmies The total number of armies in integer form
     * @param graphObj Object of the class Graph
     * @param currentPlayerObj Object of the class CurrentPlayer and fetches the state of the current player
     * @return true(If the method executes and reinforcement is doubled)
     */
    public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj,
                                 CurrentPlayer currentPlayerObj) {

        for (Country country : graphObj.getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(currentPlayerObj.currentPlayer.getName())) {
                country.setNumberOfArmies(country.getNumberOfArmies() * 2);
            }

        }

        GamePlay.getInstance().setCurrentOperation("Reinforcement done by cheater player");
        return true;
    }

    @Override
    /**
     * This method attacks in a form that it conquers all the neighbours of all the countries in a single move
     * @param fromCountry It is a String and denotes the name of the country from where the armies are to be sent
     * @param toCountry It is a String and denotes the name of the country that is to be attacked
     * @param graphObj It is an object of the class Graph
     * @param currentPlayerObj It is an object of the class CurrentPlayer
     * @return true(if the method executes successfully) or false(if the country entered is invalid or some other validation fails)
     */
    public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {

        ArrayList<Country> countriesOwnedbyPlayer = new ArrayList<Country>();
        for (Country country : graphObj.getAdjList()) {
            if (country.getOwner().equalsIgnoreCase(currentPlayerObj.currentPlayer.getName())) {
                countriesOwnedbyPlayer.add(country);
            }
        }

        for (int i = 0; i < countriesOwnedbyPlayer.size(); i++) {

            ArrayList<Integer> neighboursOfCountry = countriesOwnedbyPlayer.get(i).neighbours;

            for (int j = 0; j < neighboursOfCountry.size(); j++) {
                Country.getCountryByNumber(neighboursOfCountry.get(j), graphObj.getInstance())
                        .setOwner(currentPlayerObj.currentPlayer.getName());
            }
        }

        GamePlay.getInstance().setCurrentOperation("Performing all-out attack by cheater player");
        return true;
    }

    @Override
    /**
     *This method doubles the number of armies of the countries whose neighbours are owned by other players.
     * 	@param fromCname The name of the country from where the armies are to be moved
     * 	@param toCountryName The name of the country to which the armies are to be moved
     *  @param numberOfArmies The total number of armies to be moved
     *  @param gameGraph This is an object of the class Graph
     *  @return true(If all the conditions are satisfied and the desired country is fortified) or false(If the countries specified are absent or it does not fulfill the requirements
     */
    public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {

        boolean neighbourWithDifferentOwner = false;

        for (Country country : gameGraph.getAdjList()) {

            if (country.getOwner().equalsIgnoreCase(CurrentPlayer.getCurrentPlayerObj().getCurrentPlayer().getName())) {

                for (int i = 0; i < country.neighbours.size(); i++) {

                    if (!Country.getCountryByNumber(country.neighbours.get(i), gameGraph).getOwner()
                            .equalsIgnoreCase(CurrentPlayer.getCurrentPlayerObj().getCurrentPlayer().getName())) {

                        neighbourWithDifferentOwner = true;
                        break;

                    }

                }

                if (neighbourWithDifferentOwner = true) {
                    country.setNumberOfArmies(country.getNumberOfArmies() * 2);
                    neighbourWithDifferentOwner = false;
                }

            }

        }

        System.out.println("Fortification done in cheater player");
        return true;
    }
}
