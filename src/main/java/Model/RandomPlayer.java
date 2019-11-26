package Model;

import java.util.ArrayList;

public class RandomPlayer implements IPlayer {
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
