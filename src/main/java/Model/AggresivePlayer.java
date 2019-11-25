package Model;

import java.util.ArrayList;

public abstract class AggresivePlayer implements IPlayer {

	@Override
	public PlayerStrategy getPlayerStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumber(Integer number) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumberOfArmies(Integer numberOfArmies) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getNumberOfArmies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNumberOfFreeArmies(Integer numberOfFreeArmies) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getNumberOfFreeArmies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMyCountries(Integer number) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Integer> getMyCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getExchangeCardsTimes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExchangeCardsTimes(Integer exchangeCardsTimes) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Card> getPlayerCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayerCards(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getCountryConquered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCountryConquered(boolean countryConquered) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getDefenderRemoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDefenderRemoved(boolean countryConquered) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean reinforcement( Graph graphObj,
			CurrentPlayer currentPlayerObj) {
		// TODO Auto-generated method stub
		
		Integer numberOfArmies=currentPlayerObj.getNumReinforceArmies();
		Country strongestCountry = null;
		
		for (Country country : graphObj.getAdjList()) {
			
			if(country.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName())) {
				if(strongestCountry==null) {
					strongestCountry=country;
					//countryName=country.getName();
				}else {
					if(country.getNumberOfArmies()>strongestCountry.getNumberOfArmies()) {
						strongestCountry=country;
					}
				}
			}
		}
		
		
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
		targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + numberOfArmies);

		// increase the number of armies belong to the player
		currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
		currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);

		return true;
	}

	@Override
	public boolean attackAllout(String fromCountry, String toCountry, Graph graphObj, CurrentPlayer currentPlayerObj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fortify(String fromCname, String toCountryName, Integer numberOfArmies, Graph gameGraph) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getTotalArmiesOwnedByPlayer(Graph gameGraph) {
		// TODO Auto-generated method stub
		return null;
	}

}
