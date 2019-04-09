package com.concordia.soen6441riskgame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement Tournament mode to be played on different maps, having different computer players strategies.
 * 
 * 
 * @author Abhishek, Aamrean
 * @version 1.0
 * @since 3.0
 *
 */
public class RGTournamentManager {
	
	private List<String> maps;
	private List<String> players;
	private int numberOfGames;
	private int numberOfTurns;
	private RGFile file = RGFile.getGameFile();
	private RGGame game = RGGame.getGame();
	RGPlayer playersObject=RGPlayer.getPlayers();
	ArrayList<String> results=new ArrayList<String>();

	/**
	 * This constructor is used to set the maps, players, games and turns.
	 * 
	 * 
	 * @param maps maps selected to play.
	 * @param players computer players selected to play.
	 * @param games number of games to play.
	 * @param turns maximum number turns for each game.
	 * 
	 */
	public RGTournamentManager(List<String> maps, List<String> players, int games, int turns) {
		this.maps = maps;
		this.players = players;
		this.numberOfGames = games;
		this.numberOfTurns = turns;
	}

	/**
	 * This method is used to start the tournament.
	 * 
	 */
	public void startTournament() {
		ArrayList<String> results=new ArrayList<String>();
		for (String map : maps) {
			
				for (int i = 0; i < numberOfGames; i++) {game.reloadMap();
				String mapLocation = "C://Map//" + map;
				file.openTournamentFile(mapLocation);
				try {
					StringBuilder content = file.getContent("[Territories]");
					game.createGraph(content);
					content = file.getContent("[Continents]");
					game.createContinents(content);
					playersObject.reloadPlayers();
					String contentPlayer = "";
					for (String player : players) {
						contentPlayer += player + "=" + player + ",";
					}
					String playerString = contentPlayer.substring(0, contentPlayer.length() - 1);

					game.assignCountries(playersObject.setPlayers(playerString));
					//game.setPhase("Setup");
					game.initializeGame();

					
					  while(true) { 
						  String currentPlayerName=playersObject.getPlayerTurn();
					  game.setStrategy(currentPlayerName); 
					  game.executeSetupStrategy("", currentPlayerName); 
					  if(playersObject.getSumArmiesSetup()==0) { 
						  break; 
						  } 
					  }
					  
					  results.add(this.play());
					 
					 

				

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
				
		}
				

	}
		System.out.println(results);
		
		 RGTournamentResultFrame rts=new RGTournamentResultFrame(results, maps,
		 numberOfGames); rts.setVisible(true);
}
	
	/**
	 * This method is used to run the Reinforcement, Attack and Fortification phase for each player.
	 * 
	 * @return if game turns does not exceed given number of turns then winner of the game, else draw.
	 * 
	 */
	public String play() {
	int count=0;
	  while(count<=numberOfTurns*3) {
		  
		  String currentPlayerName=playersObject.getPlayerTurn();
		  System.out.println("PlayerTurn->"+currentPlayerName);
		  game.setAllOutModeForAttackPhase(true);
		  game.setStrategy(currentPlayerName);
		  String numberOfArmiesForReinforcement=playersObject.getNumberOfArmiesForReinforcement(currentPlayerName);
			if (numberOfArmiesForReinforcement.contentEquals("0"))//first time to reinforce? 
			{
				playersObject.setNumberOfArmiesForReinforcement(currentPlayerName);
				numberOfArmiesForReinforcement=playersObject.getNumberOfArmiesForReinforcement(currentPlayerName);
			}
		  game.executeReinforcementStrategy("", currentPlayerName, numberOfArmiesForReinforcement);
		  game.executeAttackPhaseModeDecisionStrategy("", "", "", "", currentPlayerName);
		  if(game.getCurrentPlayerNumberOfCountries(currentPlayerName)==game.getTotolCountries()) {
			  return currentPlayerName;
		  }
		  game.executeFortificationPhaseStrategy("", "", 0);
		  count++;
	  }
	  return "draw";
	}
	
	int getResultsSize() {
		return results.size();
	}
}
