package com.concordia.soen6441riskgame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class to control the game for players with a random strategy. Only one game is
 * possible during runtime
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since 1.0
 *
 */
public class RGGameRandom implements RGStrategy, Serializable {
	
	/**
	 * Created to store the current game for players with a random strategy.
	 */
	private static RGGameRandom game=new RGGameRandom();
	
	/**
	 * This method is used to assure only that one instance of the game (only one game) is created.
	 * 
	 * 
	 * @return Current game.
	 * 
	 */
	static RGGameRandom getGame()
	{
		if (game==null)
			game=new RGGameRandom();
		return game;
	}
	
	/**
	 * This method is used to process automatic actions during Setup Phase of players with a random strategy:
	 * 
	 * 
	 * @param selectedCountry Selected country from list.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	public void setupPhase(String selectedCountry, String currentPlayerName)
	{
		
		RGGame game=RGGame.getGame();
		RGPlayer players=RGPlayer.getPlayers();
		selectedCountry=game.getRandomCountry(currentPlayerName);
		
		game.setNumberOfArmies(1, selectedCountry);//adding one army to the map
		players.setNumberOfArmiesSetup(-1, currentPlayerName);//subtracting one army from the players' data structure for the Setup Phase
		players.setNextTurn();//next player has to place an army	
		
		int sumArmiesSetup=players.getSumArmiesSetup();

		if(sumArmiesSetup==0)//is it the end of the Setup Phase? Go to the next Phase 
		{
			players.initializeTurn();
			game.setPhase("Reinforcement");
		}
		else
		{
			currentPlayerName=players.getPlayerTurn();//getting current turn
			String currentArmies=players.getCurrentArmies(currentPlayerName);
			while (currentArmies.equals("0")==true)//the player already placed all his/her armies; next turn must be set
			{
				players.setNextTurn();	
				currentPlayerName=players.getPlayerTurn();
				currentArmies=players.getCurrentArmies(currentPlayerName);//getting armies available to place 
			}
		}
	}

	/**
	 * This method is used to process automatic actions during Reinforcement Phase for players with a random strategy:
	 * 
	 * 
	 * @param selectedCountry Selected country from list.
	 * @param currentPlayerName Name of the player.
	 * @param armiesToPlace Number of armies to place in a given country.
	 * 
	 */
	public void reinforcementPhase(String selectedCountry, String currentPlayerName, String armiesToPlace)
	{
		
		RGGame game=RGGame.getGame();
		RGPlayer players=RGPlayer.getPlayers();
		String totalArmiesAvailable;
		List<String> selectedCards=new ArrayList<String>();
		selectedCountry=game.getRandomCountry(currentPlayerName);
		
		if(game.getPlayerCards(currentPlayerName).size()>=5){
			ArrayList<String> playerCards=((RGGame) game).getPlayerCards(currentPlayerName);
			if(Collections.frequency(playerCards, "0")>=3) {
				selectedCards.add("Infantry");
				selectedCards.add("Infantry");
				selectedCards.add("Infantry");
			}
			else if(Collections.frequency(playerCards, "1")>=3) {
				selectedCards.add("Cavalry");
				selectedCards.add("Cavalry");
				selectedCards.add("Cavalry");
			}
			else if(Collections.frequency(playerCards, "2")>=3) {
				selectedCards.add("Artillery");
				selectedCards.add("Artillery");
				selectedCards.add("Artillery");
			}
			else if(playerCards.contains("0") && playerCards.contains("1") && playerCards.contains("2")) {
				selectedCards.add("Infantry");
				selectedCards.add("Cavalry");
				selectedCards.add("Artillery");
			}
			else if(playerCards.contains("0") && playerCards.contains("1") && playerCards.contains("3")) {
				selectedCards.add("Infantry");
				selectedCards.add("Cavalry");
				selectedCards.add("Wild");
			}
			else if(playerCards.contains("0") && playerCards.contains("2") && playerCards.contains("3")) {
				selectedCards.add("Infantry");
				selectedCards.add("Artillery");
				selectedCards.add("Wild");
			}
			else if(playerCards.contains("1") && playerCards.contains("2") && playerCards.contains("3")) {
				selectedCards.add("Cavalry");
				selectedCards.add("Artillery");
				selectedCards.add("Wild");
			}
			else if(Collections.frequency(playerCards, "3")==2 && playerCards.contains("0")) {
				selectedCards.add("Infantry");
				selectedCards.add("Wild");
				selectedCards.add("Wild");
			}
			else if(Collections.frequency(playerCards, "3")==2 && playerCards.contains("1")) {
				selectedCards.add("Cavalry");
				selectedCards.add("Wild");
				selectedCards.add("Wild");
			}
			else if(Collections.frequency(playerCards, "3")==2 && playerCards.contains("2")) {
				selectedCards.add("Artillery");
				selectedCards.add("Wild");
				selectedCards.add("Wild");
			}
			game.removePlayerCards(currentPlayerName, selectedCards);
			int newArmy=players.setNumberOfArmiesForUsedCard(currentPlayerName, Integer.valueOf(armiesToPlace));
			armiesToPlace=String.valueOf(newArmy);	
		}
		
		game.setNumberOfArmies(Integer.valueOf(armiesToPlace), selectedCountry);//adding armies to the country
		players.subtractArmiesForReinforcementPhase(currentPlayerName,armiesToPlace);
		
		totalArmiesAvailable=players.getNumberOfArmiesForReinforcement(currentPlayerName);	
		if(totalArmiesAvailable.contentEquals("0"))//all armies placed? Go to the next Phase
		{
			game.setPhase("Attack");
			game.setCardGiven(false);
		}
		
		//Storing performed actions 
		StringBuilder actionPerformed=new StringBuilder();
		actionPerformed.append("*"+armiesToPlace+" army deployed in "+selectedCountry);
		actionPerformed.append("\n");
		players.setActionsPerformed(actionPerformed, currentPlayerName, "reinforcement");
	}
	
	/**
	 * This method is used to process automatic actions during Attack Phase for players with an random strategy.
	 * 
	 * 
	 * 
	 * @param selectedCountryAttacker Attacking country
	 * @param selectedCountryDefender Attacked country.
	 * @param selectedDiceAttacker Number of dice to attack.
	 * @param selectedDiceDefender Number of dice to defend.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	public void attackPhaseModeDecision(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName)
	{
		RGGame game=RGGame.getGame();
		int randomNumber;

		ArrayList<String> countriesAttacker = new ArrayList<String>();
		countriesAttacker=((RGGame) game).getCountriesAttacker(currentPlayerName);
		
		if(countriesAttacker.size()==0) {
			((RGGame) game).attackPhaseNoAttackers(currentPlayerName);
		}
		else {
			randomNumber = new Random().nextInt(countriesAttacker.size());
			selectedCountryAttacker=countriesAttacker.get(randomNumber);

			ArrayList<String> countriesDefender = new ArrayList<String>();
			countriesDefender=game.getCountriesDefender(selectedCountryAttacker,currentPlayerName);
			randomNumber = new Random().nextInt(countriesDefender.size());
			selectedCountryDefender=countriesDefender.get(randomNumber);




			randomNumber = new Random().nextInt(10);
			randomNumber++;
			//while All Out Mode is on and there is enough army available to attack
			while (game.getAllOutModeForAttackPhase()==true && Integer.valueOf(game.getArmies(selectedCountryAttacker))>1 && randomNumber>0)
			{
				//setting max number of dice
				String numberOfArmies=game.getArmies(selectedCountryAttacker);
				if(Integer.valueOf(numberOfArmies)>=4)
					selectedDiceAttacker="3";
				else if(Integer.valueOf(numberOfArmies)==3)
					selectedDiceAttacker="2";
				else if(Integer.valueOf(numberOfArmies)==2)
					selectedDiceAttacker="1";
				numberOfArmies=game.getArmies(selectedCountryDefender);
				if(Integer.valueOf(numberOfArmies)>=2)
					selectedDiceDefender="2";
				else if(Integer.valueOf(numberOfArmies)==1)
					selectedDiceDefender="1";
				//let's play	
				game.attackPhase(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);
				randomNumber--;
			}
			if(Integer.valueOf(game.getArmies(selectedCountryAttacker))==1 && !game.getAttackStatus().contentEquals("move")) 
			{
				RGPlayer players=RGPlayer.getPlayers();
				StringBuilder actionPerformed=new StringBuilder();
				actionPerformed.append("Not enough army to attack");
				actionPerformed.append("\n");

				//No more army to attack. Setting all out mode off
				game.setAllOutModeForAttackPhase(false);

				//Storing performed actions 
				players.setActionsPerformed(actionPerformed, currentPlayerName, "attack");
			}
			game.setAllOutModeForAttackPhase(false);
			game.setAttackStatus("");
		}
	}

	/**
	 * This method is used to process automatic actions during Fortification Phase for players with an random strategy.
	 * 
	 * 
	 * @param countryFrom Origin country.
	 * @param countryTo Destination country.
	 * @param armiesToMove Number of armies to move.
	 * 
	 */
	public void fortificationPhase(String countryFrom, String countryTo, int armiesToMove)
	{
		RGGame game=RGGame.getGame();
		RGPlayer players=RGPlayer.getPlayers();
		String currentPlayerName = players.getPlayerTurn();
		ArrayList<String> listOfCountriesOrderedByPopulation=game.getListOfCountriesOrderedByPopulation(currentPlayerName);
		countryFrom=listOfCountriesOrderedByPopulation.get(0);
		if(Integer.valueOf(game.getArmies(countryFrom))>=2) {
			armiesToMove=Integer.valueOf(game.getArmies(countryFrom))/2;
		}
		ArrayList<String> listOfPossibleCountries = game.getCurrentPlayerAdjacentCountries(currentPlayerName, countryFrom);
		if(listOfPossibleCountries.size()!=0) {
			int randomNumber = new Random().nextInt(listOfPossibleCountries.size());
			countryTo=listOfPossibleCountries.get(randomNumber);
		}
		
		if (armiesToMove!=0 && listOfPossibleCountries.size()!=0){
			game.setNumberOfArmies(-armiesToMove, countryFrom);
			game.setNumberOfArmies(armiesToMove, countryTo);
			//Storing performed actions 
			StringBuilder actionPerformed=new StringBuilder();
			actionPerformed.append("*"+armiesToMove+" army moved from "+countryFrom+" to "+countryTo);
			actionPerformed.append("\n");
			players.setActionsPerformed(actionPerformed, currentPlayerName, "fortification");
		}
		players.setNextTurn();
		game.setPhase("Reinforcement");
	}

}
