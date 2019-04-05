package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class to control the game for players with a cheater strategy. Only one game is
 * possible during runtime
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since 1.0
 *
 */
public class RGGameCheater implements RGStrategy{
	
	/**
	 * Created to store the current game for players with a cheater strategy.
	 */
	private static RGGameCheater game=new RGGameCheater();
	
	/**
	 * This method is used to assure only that one instance of the game (only one game) is created.
	 * 
	 * 
	 * @return Current game.
	 * 
	 */
	static RGGameCheater getGame()
	{
		if (game==null)
			game=new RGGameCheater();
		return game;
	}
	/**
	 * This method is used to process automatic actions during Setup Phase of players with a cheater strategy:
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
	 * This method is used to process automatic actions during Reinforcement Phase for players with a cheater strategy:
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
		
		game.doubleNumberOfArmies(currentPlayerName);
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
	 * This method is used to process automatic actions during Attack Phase for players with an cheater strategy.
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
		RGPlayer players=RGPlayer.getPlayers();
		StringBuilder actionPerformed=new StringBuilder();
		ArrayList<String> countriesAttacker = new ArrayList<String>();
		countriesAttacker=((RGGame) game).getCountriesAttacker(currentPlayerName);
		
		for(int i=0;i<countriesAttacker.size();i++) {
			
			selectedCountryAttacker=countriesAttacker.get(i);
			ArrayList<String> countriesDefender = new ArrayList<String>();
			countriesDefender=game.getCountriesDefender(selectedCountryAttacker,currentPlayerName);
			
			for(int j=0;i<countriesDefender.size();j++) {
				selectedCountryDefender=countriesDefender.get(j);
				String loserPlayerName=game.getOwner(selectedCountryDefender);

				//setting owner and moving armies (Risk rule: "Move at least as many armies as the number of dice winner rolled in the last battle")
				ArrayList<String> edges = game.getCountryItemsEdges(selectedCountryDefender);
				edges.set(3, currentPlayerName);
				game.setCountryItemsEdges(selectedCountryDefender, edges);
				//game.setNumberOfArmies((Integer.valueOf(selectedDiceAttacker))*(-1), selectedCountryAttacker);
				game.setNumberOfArmies(1, selectedCountryDefender);
				actionPerformed.append("1 army already moved to your new captured territory");
				actionPerformed.append("\n");

				//informing who won and who lost
				actionPerformed.append(selectedCountryDefender+" was captured by "+currentPlayerName+" from "+selectedCountryAttacker);
				actionPerformed.append("\n");

				//attacker won a card
				if(game.getCardGiven()==false)
				{
					ArrayList<String> vertex = game.getCardItemsVertex();
					int randomCard = new Random().nextInt(vertex.size());
					edges = game.getCardItemsEdges(vertex.get(randomCard));

					while(!edges.get(1).contentEquals(""))
					{
						randomCard = new Random().nextInt(vertex.size());
						edges = game.getCardItemsEdges(vertex.get(randomCard));
					}
					edges.set(1, currentPlayerName);
					game.setCardItemsEdge(vertex.get(randomCard), edges);
					if(edges.get(0).contentEquals("0")) 
					{
						actionPerformed.append(currentPlayerName+" won an Infantry ("+vertex.get(randomCard)+") card");
						actionPerformed.append("\n");
					}
					if(edges.get(0).contentEquals("1")) 
					{
						actionPerformed.append(currentPlayerName+" won a Cavalry ("+vertex.get(randomCard)+") card");
						actionPerformed.append("\n");
					}
					if(edges.get(0).contentEquals("2")) 
					{
						actionPerformed.append(currentPlayerName+" won an Artillery ("+vertex.get(randomCard)+") card");
						actionPerformed.append("\n");
					}
					if(edges.get(0).contentEquals("3")) 
					{
						actionPerformed.append(currentPlayerName+" won a Wild card :)");
						actionPerformed.append("\n");
					}
					game.setCardGiven(true);
				}

				//does loser have more territories?
				ArrayList<String> vertex = game.getCountryItemsVertex();
				int totalCountriesOwned=0;
				for (int k = 0; k < vertex.size(); k++) 
				{
					edges = game.getCountryItemsEdges(vertex.get(k));
					if(edges.get(3).contentEquals(loserPlayerName))
						totalCountriesOwned++;//counting countries owned by loser
				}
				if(totalCountriesOwned==0)
				{
					players.deletePlayer(loserPlayerName);
					actionPerformed.append("Bye bye "+loserPlayerName);
					actionPerformed.append("\n");
				}

				//current player controls all territories?
				vertex = game.getCountryItemsVertex();
				totalCountriesOwned=0;
				for (int k = 0; k < vertex.size(); k++) 
				{
					edges = game.getCountryItemsEdges(vertex.get(k));
					if(edges.get(3).contentEquals(currentPlayerName))
						totalCountriesOwned++;//counting countries owned by current player
				}
				if(totalCountriesOwned==Integer.valueOf(vertex.size()))
				{
					actionPerformed.append("***This is the end. You won!***");
					actionPerformed.append("\n");
					game.setAttackStatus("end");
				}
				else
					game.setAttackStatus("move");
			}
			
		}
		game.setAllOutModeForAttackPhase(false);
		if(game.getAttackStatus().contentEquals("move"))
			game.setAttackStatus("");
	}

	/**
	 * This method is used to process automatic actions during Fortification Phase for players with an cheater strategy.
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
		
		ArrayList<String> countriesAttacker = new ArrayList<String>();
		countriesAttacker=((RGGame) game).getCountriesAttacker(currentPlayerName);
		
		for(int i=0;i<countriesAttacker.size();i++) {
			
			countryFrom=countriesAttacker.get(i);
			ArrayList<String> countriesDefender = new ArrayList<String>();
			countriesDefender=game.getCountriesDefender(countryFrom,currentPlayerName);
			
			if(countriesDefender.size()!=0) {
				game.setNumberOfArmies(Integer.valueOf(game.getArmies(countryFrom))*2, countryFrom);
				//Storing performed actions 
				StringBuilder actionPerformed=new StringBuilder();
				actionPerformed.append("*"+armiesToMove+" army moved from "+countryFrom+" to "+countryTo);
				actionPerformed.append("\n");
				players.setActionsPerformed(actionPerformed, currentPlayerName, "fortification");
			}
		}
		players.setNextTurn();
		game.setPhase("Reinforcement");
	}

}
