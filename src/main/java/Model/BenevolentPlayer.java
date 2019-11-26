package Model;

import java.util.ArrayList;

public class BenevolentPlayer implements IPlayer {

    private String name;
    private Integer number, numberOfArmies, numberOfFreeArmies;
    private ArrayList<Integer> myCountries = new ArrayList<Integer>();
    private Integer exchangeCardsTimes;
    public ArrayList<Card> playerCards;
    public boolean countryConquered;
    public boolean defenderRemoved;
    static Integer lastDiceSelected = null;

    public BenevolentPlayer(Integer number, String name, Integer numberOfArmies) {
        this.number = number;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
        playerCards = new ArrayList<Card>();
        exchangeCardsTimes = 0;
        countryConquered = false;
        defenderRemoved = false;
    }
    @Override
    public PlayerStrategy getPlayerStrategy() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setNumber(Integer number) {

    }

    @Override
    public Integer getNumber() {
        return null;
    }

    @Override
    public void setNumberOfArmies(Integer numberOfArmies) {

    }

    @Override
    public Integer getNumberOfArmies() {
        return null;
    }

    @Override
    public void setNumberOfFreeArmies(Integer numberOfFreeArmies) {

    }

    @Override
    public Integer getNumberOfFreeArmies() {
        return null;
    }

    @Override
    public void setMyCountries(Integer number) {

    }

    @Override
    public ArrayList<Integer> getMyCountries() {
        return null;
    }

    @Override
    public Integer getExchangeCardsTimes() {
        return null;
    }

    @Override
    public void setExchangeCardsTimes(Integer exchangeCardsTimes) {

    }

    @Override
    public ArrayList<Card> getPlayerCards() {
        return null;
    }

    @Override
    public void setPlayerCards(Card card) {

    }

    @Override
    public boolean getCountryConquered() {
        return false;
    }

    @Override
    public void setCountryConquered(boolean countryConquered) {

    }

    @Override
    public boolean getDefenderRemoved() {
        return false;
    }

    @Override
    public void setDefenderRemoved(boolean countryConquered) {

    }

    @Override
    public boolean reinforcement(String countryName, Integer numberOfArmies, Graph graphObj, CurrentPlayer currentPlayerObj) {
        return false;
    }

    public boolean reinforcement( Graph graphObj, CurrentPlayer currentPlayerObj) {
        // TODO Auto-generated method stub

        Integer numberOfArmies=currentPlayerObj.getNumReinforceArmies();
        Country weakestCountry = null;

        for (Country country : graphObj.getAdjList()) {

            if(country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
                if(weakestCountry==null) {
                    weakestCountry=country;
                    //countryName=country.getName();
                }else {
                    if(country.getNumberOfArmies() < weakestCountry.getNumberOfArmies()) {
                        weakestCountry=country;
                    }
                }
            }
        }

        // check: if target country is not existent, return false
        Country targetCountry = weakestCountry;

        if (targetCountry == null) {
            System.out.println("This country does not exist.");
            return false;
        }

        // check: if country does not belong to the currentPlayer, return false
        if (targetCountry.getOwner() != null) {
            if (targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false) {
                System.out.println("The country does not belong to the current player");
                return false;
            }
        }

        // Reinforce armies in the target country
        targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + numberOfArmies);

        // increase the number of armies belong to the player
        currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
        currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);

        return true;
    }



    @Override
    public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {
        return false;
    }

    @Override
    public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {
        return false;
    }

    @Override
    public Integer getNumberOfCountriesOwned(String playerName, Graph gameGraph) {
        return null;
    }

    @Override
    public Integer getTotalArmiesOwnedByPlayer(Graph gameGraph) {
        return null;
    }

    @Override
    public boolean attackCountry(String fromCountry, String toCountry, Integer numDice, Graph graphObj, CurrentPlayer currentPlayerObj) {
        return false;
    }
}
