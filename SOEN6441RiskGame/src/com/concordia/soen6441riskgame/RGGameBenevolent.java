package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to control the game for players with a benevolent strategy. Only one game is
 * possible during runtime
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since 1.0
 *
 */
public class RGGameBenevolent implements RGStrategy{
	
	/**
	 * Created to store the current game for players with a benevolent strategy.
	 */
	private static RGGameBenevolent game=new RGGameBenevolent();
	
	/**
	 * This method is used to assure only that one instance of the game (only one game) is created.
	 * 
	 * 
	 * @return Current game.
	 * 
	 */
	static RGGameBenevolent getGame()
	{
		if (game==null)
			game=new RGGameBenevolent();
		return game;
	}
	
	/**
	 * This method is used to process automatic actions during Setup Phase of players with a benevolent strategy:
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
	 * This method is used to process automatic actions during Reinforcement Phase for players with a benevolent strategy:
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
		selectedCountry=game.getLeastPopulatedCountry(currentPlayerName);
		
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
	 * This method is used to process automatic actions during Attack Phase for players with an benevolent strategy.
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
		game.setAllOutModeForAttackPhase(false);
	}

	/**
	 * This method is used to process automatic actions during Fortification Phase for players with an benevolent strategy.
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
		countryTo=game.getLeastPopulatedCountry(currentPlayerName);
		
		ArrayList<String> listOfPossibleCountries = game.getCurrentPlayerAdjacentCountries(currentPlayerName, countryTo);
		int maxNumberOfArmy=0;
		for(int i=0;i<listOfPossibleCountries.size();i++) {
			if(Integer.valueOf(game.getArmies(listOfPossibleCountries.get(i)))>maxNumberOfArmy) {
				maxNumberOfArmy=Integer.valueOf(game.getArmies(listOfPossibleCountries.get(i)));
				countryFrom=listOfPossibleCountries.get(i);
				if (maxNumberOfArmy>2) {
					armiesToMove=Integer.valueOf(game.getArmies(listOfPossibleCountries.get(i)))/2;
				}
			}
					
		}
		
		if (armiesToMove!=0){
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
