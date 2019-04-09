package com.concordia.soen6441riskgame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Class to control the information of the players. Only one player data structure is possible for a game.
 * 
 * 
 * @author Julian Beltran, Abhishek, Aamrean
 * @version 1.0
 * @since 1.0
 *
 */
public class RGPlayer extends Observable implements Serializable {
	
	/**
	 * Created to store the names of the players.
	 */
	private ArrayList<String> setOfPlayers = new ArrayList<String>();
	
	/**
	 * Created to store the Players' data structure.
	 */
	private RGGraph playerItems=new RGGraph();
	
	/**
	 * Created to store the six colors: Green, magenta, cyan, pink, orange, and blue.
	 */
	private ArrayList<String> colors = new ArrayList<String>();
	
	/**
	 * Created to store the set of players of the current game
	 */
	private static RGPlayer players=new RGPlayer();
	
	/**
	 * Created to store the behaviors of the players
	 */
	private RGGraph playerBehaviors=new RGGraph();
	
	/**
	 * This constructor creates a list of six colors to assign them later to every player where it is required.
     *   
     *   
	 */
	RGPlayer()
	{
		colors.add("green");
		colors.add("magenta");
		colors.add("cyan");
		colors.add("pink");
		colors.add("orange");
		colors.add("blue");	
	}
	
	/**
	 * This method is used to assure only one instance (only one set of players) is created.
	 * 
	 * 
	 * @return Set of players.
	 * 
	 */
	static RGPlayer getPlayers()
	{
		if (players==null)
			players=new RGPlayer();
		return players;
	}
	
	/**
	 * This method is used to set the players from a saved game.
	 * 
	 * 
	 * @param players Set of saved players..
	 * 
	 */
	void setPlayer(RGPlayer players)
	{
		this.players=players;
	}
	
	/**
	 * This method is used to validate the number of players registered by the user. Must be between 2 and 6.
	 * 
	 * 
	 * @param players String with the names of players separated by commas.
	 * @return 'true' if number of players between 2 and 6; otherwise 'false'.
	 * 
	 */
	boolean validateNumberOfPLayers(String players)
	{
		String[] playersList = players.split(",");
		if (playersList.length>=2 && playersList.length<=6)
			return true;
		return false;
	}
	
	/**
	 * This method is used to create the player's data structure: 
	 * 
	 * 1) Players' Data Structure: It is a HashMap data structure that contains all the information associated to each player. 
	 * The key value is the name of the player (String), and the value of the key is an ArrayList (String) that contains all the information of 
	 * the player: Turn, color, and number of armies for Setup Phase.
     * 
     *    
     * @param players String with the names of players separated by commas.
     * @return List with the name of the players created.
     *   
	 */		
	ArrayList<String> setPlayers(String players)
	{
		int index1=0;
		int index2;
		String player;
		String behavior;
		String searchChar=",";
		int i=0;
		
		while(!players.equals("")) 
		{
			index2=players.indexOf(searchChar);
			if (index2!=-1) 
			 { 
				player=players.substring(index1, index1+index2);//getting the name of a player
				String[] playerAndBehavior=player.split("=");
				player=playerAndBehavior[0];
				behavior=playerAndBehavior[1];
				playerBehaviors.addVertex(player);
				playerBehaviors.addEdge(player,behavior);
				playerItems.addVertex(player);//creating a key (name of the player) of the players' data structure
				if(i==0)
					playerItems.addEdge(player, "1");//creating 'Turn' value. First player begins the game
				else
					playerItems.addEdge(player, "0");//creating 'Turn' value for the rest of the players
				playerItems.addEdge(player, colors.get(i));//creating 'Color' value 
				setOfPlayers.add(player);
				players=","+players;
				players=players.replace(","+player+"="+behavior+",", "");//a player is already created; the player has to be deleted from String that contains the names of players separated by commas
				
				//printing a player
				ArrayList<String> edgeList = playerItems.getEdges(player);
				System.out.print(player+ "->");
				for(int k=0;k<edgeList.size();k++)
				{
					System.out.print(edgeList.get(k)+" -> ");
				}
				System.out.println("");
				i++;
				
			 }
			else//last player of the String that contains the names of players separated by commas
			{
				String[] playerAndBehavior=players.split("=");
				players=playerAndBehavior[0];
				behavior=playerAndBehavior[1];
				playerBehaviors.addVertex(players);
				playerBehaviors.addEdge(players,behavior);
				playerItems.addVertex(players);//creating a key (name of the player) of the players' data structure
				if(i==0)
					playerItems.addEdge(players, "1");
				else
					playerItems.addEdge(players, "0");//creating 'Turn' value for the last players
				playerItems.addEdge(players, colors.get(i));//creating 'Color' value for the last player
				setOfPlayers.add(players);
				
				//printing a player
				ArrayList<String> edgeList = playerItems.getEdges(players);
				System.out.print(players+ "->");
				for(int k=0;k<edgeList.size();k++)
				{
					System.out.print(edgeList.get(k)+" -> ");
				}
				System.out.println("");
				i++;
				
				break;
				
			}
			
		}
		System.out.println(setOfPlayers);
		return setOfPlayers;
	}
	
	/**
	 * This method returns the color of a player. 
	 * 
     *    
     * @param vertex Name of the player. 
     * @return Color of a player.
     *   
	 */		
	String getPlayerColor(String vertex)
	{
		ArrayList<String> edgeList = playerItems.getEdges(vertex);
		return edgeList.get(1);//color is placed in second position of the list of values of a player
	}
	
	/**
	 * This method is used to set the number of armies for a player in the Setup Phase.  
	 * 
     *    
     * @param game object with the information of the current game.
     *   
	 */	
	void allocateArmies(RGGame game)
	{
		int numberOfArmiesSetup, numberOfArmiesAlreadyPlaced;
		ArrayList<String> vertex = playerItems.getVertex();
		numberOfArmiesSetup=vertex.size();
		numberOfArmiesSetup=50-(numberOfArmiesSetup*5);//Risk® game rule: 2 players=40 armies, 3 players=35 armies, etc...
		for(int k=0;k<vertex.size();k++)//for each player
		{
			numberOfArmiesAlreadyPlaced=game.getCurrentPlayerNumberOfCountries(vertex.get(k));//one army is already placed in every country
			playerItems.addEdge(vertex.get(k), String.valueOf(numberOfArmiesSetup-numberOfArmiesAlreadyPlaced));//armies already placed must be subtracted
		}
	}
	
	/**
	 * This method is used to allocate zero cards for every player for the Attack Phase 
	 * 
     *   
	 */	
	void initializeCards()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)//for each player
		{
			playerItems.addEdge(vertex.get(k), "0");//initializing in zero number of cards type A
			playerItems.addEdge(vertex.get(k), "0");//initializing in zero number of cards type B
			playerItems.addEdge(vertex.get(k), "0");//initializing in zero number of cards type C
		}
	}
	
	/**
	 * This method is used to allocate zero armies for every player for the Reinforcement Phase 
	 * 
     *   
	 */	
	void initializeArmiesForReinforcementPhase()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)//for each player
		{
			playerItems.addEdge(vertex.get(k), "0");//initializing in zero number of armies for Reinforcement Phase
		}
	}
	
	/**
	 * This method is used to create positions 7, 8, 9, 10 in players' data structure to store the actions performed by players during play time 
	 * 
     *   
	 */	
	void initializePerformedActionsForEachPhase()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)//for each player
		{
			playerItems.addEdge(vertex.get(k), "Attack Phase:  \n");//Actions performed in Attack Phase
			playerItems.addEdge(vertex.get(k), "Setup Phase: \n");//Actions performed in Setup Phase
			playerItems.addEdge(vertex.get(k), "Reinforcement Phase: \n");//Actions performed in Reinforcement Phase
			playerItems.addEdge(vertex.get(k), "Fortification Phase: \n");//Actions performed in Fortification Phase
		}
	}
	
	/**
	 * This method is used to get the current turn in the game.  
	 * 
     *    
     * @return Name of the player with the current turn.
     *   
	 */	
	String getPlayerTurn()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		ArrayList<String> edgeList;
		String currentPlayerTurn="";
		for(int k=0;k<vertex.size();k++)//for every player
		{
			edgeList = playerItems.getEdges(vertex.get(k));
			if(edgeList.get(0).contentEquals("1"))//is the player with the current turn?
			{
				currentPlayerTurn=vertex.get(k);
			}
		}
		return currentPlayerTurn;
	}
	
	/**
	 * This method is used to get the current armies a player can place on his/her countries.  
	 * 
	 * 
     * @param vertex Name of the player.   
     * @return Number of armies available for a given player.
     *   
	 */	
	String getCurrentArmies(String vertex)
	{
		ArrayList<String> edgeList = playerItems.getEdges(vertex);
		return edgeList.get(2);
	}
	
	/**
	 * This method is used to subtract one army of the value 'number of armies for Setup Phase' of the players' data structure for a given player.
	 * 
	 * 
     * @param numberOfArmiesToAdd Number of armies to subtract in this case.  
     * @param vertex Name of the player.
     *   
	 */	
	void setNumberOfArmiesSetup(int numberOfArmiesToAdd, String vertex)
	{
		ArrayList<String> edges = playerItems.getEdges(vertex);
		String finalNumberOfArmiesToAdd=String.valueOf(Integer.valueOf(edges.get(2))+numberOfArmiesToAdd);
		edges.set(2, finalNumberOfArmiesToAdd);//the value 'number of armies for Setup Phase' is in the second position of the players' data structure
		playerItems.setEdge(vertex, edges);
		edges = playerItems.getEdges(vertex);
	}
	
	/**
	 * This method is used to set the next player's turn.
	 * 
	 * 
	 */	
	void setNextTurn()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = playerItems.getEdges(vertex.get(k));
			if(edges.get(0).contentEquals("1"))
			{
				edges.set(0, "0");
				playerItems.setEdge(vertex.get(k), edges);
				k++;
				if(k==vertex.size())//end of the list of players? 
				{
					k=0;//start over. First player begins
				}
				edges = playerItems.getEdges(vertex.get(k));
				edges.set(0, "1");//set the value in position 0
				playerItems.setEdge(vertex.get(k), edges);
				k=vertex.size();
			}
		}
	}
	
	/**
	 * This method is used to get the total of armies (of all players) to place in the Setup Phase. This is used to know when the Setup Phase is finished. 
	 * 
	 * 
	 * @return Total of armies (of all players) available to place.
	 * 
	 */	
	int getSumArmiesSetup() 
	{
		int sumArmiesSetup=0;
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)//for each player
		{
			ArrayList<String> edges = playerItems.getEdges(vertex.get(k));
			sumArmiesSetup=sumArmiesSetup+Integer.valueOf(edges.get(2));
		}
		return sumArmiesSetup;
	}
	
	/**
	 * This method is used to set the first turn once the Setup Phase is finished.
	 *
	 * 
	 */	
	void initializeTurn()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = playerItems.getEdges(vertex.get(k));
			if (k==0)//first player on the list, first turn
			{
				edges.set(0, "1");
				playerItems.setEdge(vertex.get(k), edges);
			}
			else//the rest of the players
			{
				edges.set(0, "0");
				playerItems.setEdge(vertex.get(k), edges);
			}
		}
		
		//printing the player's turns
		ArrayList<String> verti = playerItems.getVertex();
		for(int j=0;j<verti.size();j++)
		{
			System.out.print(verti.get(j)+" -> ");
			ArrayList<String> edgi = playerItems.getEdges(verti.get(j));
			for(int k=0;k<edgi.size();k++)
			{
				System.out.print(edgi.get(k)+" -> ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * This method is used to get the total of armies for Reinforcement Phase stored in position 6 of Players' data structure. 
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return Total of armies for Reinforcement Phase
	 * 
	 */	
	String getNumberOfArmiesForReinforcement(String currentPlayerName)
	{
		ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
		String numberOfArmiesForReinforcement=edges.get(6);
		return numberOfArmiesForReinforcement;
	}
	
	/**
	 * This method is used to set the total of armies for Reinforcement Phase given a player. 
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void setNumberOfArmiesForReinforcement(String currentPlayerName)
	{
		RGGame game=RGGame.getGame();
		
		int currentPlayerNumberOfCountries=game.getCurrentPlayerNumberOfCountries(currentPlayerName);
		currentPlayerNumberOfCountries=game.getNumberOfArmiesDueTerritories(currentPlayerNumberOfCountries);
		ArrayList<String> currentPlayerCountries=game.getCurrentPlayerCountries(currentPlayerName);
		currentPlayerNumberOfCountries=currentPlayerNumberOfCountries+game.getNumberOfArmiesDueContinents(currentPlayerCountries);
		ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
		edges.set(6, String.valueOf(currentPlayerNumberOfCountries));
		playerItems.setEdge(currentPlayerName, edges);
	}
	
	/**
	 * This method is used to subtract the number of armies just placed in a country from the total number of armies available to place. 
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @param armiesToPlace Number of armies to place.
	 * 
	 */	
	void subtractArmiesForReinforcementPhase(String currentPlayerName, String armiesToPlace)
	{
		ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
		String currentValueOfArmies=edges.get(6);
		int newValueOfArmies=(Integer.valueOf(currentValueOfArmies))-(Integer.valueOf(armiesToPlace));
		edges.set(6, String.valueOf(newValueOfArmies));
		playerItems.setEdge(currentPlayerName, edges);
	}
	
	/**
	 * This method is used to store the actions performed during play time in the players' data structure for Attack Phase given a player name.
	 * 
	 * 
	 * @param actionPerformed List of action performed in Attack Phase.
	 * @param currentPlayerName Name of the player.
	 * @param phase Current phase.
	 * 
	 */	
	void setActionsPerformed(StringBuilder actionPerformed, String currentPlayerName, String phase)
	{
		if (phase.contentEquals("attack"))
		{
			ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
			String currentActions=edges.get(7);
			currentActions=currentActions+actionPerformed;
			edges.set(7, currentActions);
			playerItems.setEdge(currentPlayerName, edges);
		}
		if (phase.contentEquals("reinforcement"))
		{
			ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
			String currentActions=edges.get(9);
			currentActions=currentActions+actionPerformed;
			edges.set(9, currentActions);
			playerItems.setEdge(currentPlayerName, edges);
		}
		if (phase.contentEquals("fortification"))
		{
			ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
			String currentActions=edges.get(10);
			currentActions=currentActions+actionPerformed;
			edges.set(10, currentActions);
			playerItems.setEdge(currentPlayerName, edges);
		}
	}
	
	/**
	 * This method is used to obtain the list of actions performed by any user in AttackPhase.
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @param phase Current phase.
	 * @return List of actions.
	 * 
	 */	
	String getActionsPerformed(String currentPlayerName, String phase)
	{
		String actionsPerformed="";
		if (phase.contentEquals("attack"))
		{
			ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
			actionsPerformed=edges.get(7);
		}
		else if (phase.contentEquals("reinforcement"))
		{
			ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
			actionsPerformed=edges.get(9);
		}
		else if (phase.contentEquals("fortification"))
		{
			ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
			actionsPerformed=edges.get(10);
		}
		return actionsPerformed;
	}
	
	/**
	 * This method is used to create position 11 in players' data structure to store the number of times the cards are used by the player 
	 * 
     *   
	 */	
	void initializeCardUsedCount() {
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)//for each player
		{
		playerItems.addEdge(vertex.get(k), "0");
		}
	}
	
	/**
	 * This method is used to set the total of armies after use of cards for a given player. 
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @param army number of army received from reinforcement phase.
	 * @return New number of armies after exchanging cards.
	 * 
	 */	
	int setNumberOfArmiesForUsedCard(String currentPlayerName,int army)
	{
		RGGame game=RGGame.getGame();
		int newArmy=0;
		System.out.println(army);
		ArrayList<String> edges = playerItems.getEdges(currentPlayerName);
		if(Integer.parseInt(edges.get(11))<=4) {
		newArmy=Integer.parseInt(edges.get(11))*2+4+army;
		}
		else
			newArmy=(Integer.parseInt(edges.get(11))-3)*5+army;
		edges.set(6, String.valueOf(newArmy));
		int count=Integer.parseInt(edges.get(11))+1;
		edges.set(11, String.valueOf(count));
		playerItems.setEdge(currentPlayerName, edges);
		
		return newArmy;
	}
	
	/**
	 * This method is used to delete a player when he/she does not own more territories. 
	 * 
	 * 
	 * @param loserPlayerName Name of the loser.
	 * 
	 */	
	void deletePlayer(String loserPlayerName)
	{
		playerItems.deleteVertex(loserPlayerName);
	}
	
	/**
	 * This method is used to obtain the player strategy.  
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	String getPlayerStrategy(String currentPlayerName) {
		ArrayList <String> strategy=playerBehaviors.getEdges(currentPlayerName);
		return strategy.get(0);
	}
	
	/**
	 * This method is used to obtain the palette of colors for players.  
	 * 
	 * 
	 * @return Palette of colors for players.
	 * 
	 */	
	ArrayList<String> getColors(){
		return this.colors;
	}
	
	/**
	 * This method is used to set the palette of colors for players from a saved game.
	 * 
	 * 
	 * @param colors Palette of colors for players.
	 * 
	 */	
	void setColors(ArrayList<String> colors){
		this.colors=colors;
	}
	
	/**
	 * This method is used to obtain playerItems' data structure. 
	 * 
	 *
	 * @return playerItems' data structure.
	 * 
	 */
	RGGraph getPlayerItems() {
        return playerItems;
    }
	
	/**
	 * This method is used to set playerItems' data structure from a saved game. 
	 * 
	 *
	 * @param playerItems playerItems' data structure.
	 * 
	 */
	void setPlayerItems(RGGraph playerItems) {
        this.playerItems=playerItems;
    }
	
	/**
	 * This method is used to obtain playerBehaviors' data structure. 
	 * 
	 *
	 * @return playerBehaviors' data structure.
	 * 
	 */
	RGGraph getPlayerBehaviors() {
        return playerBehaviors;
    }
	
	/**
	 * This method is used to set playerBehaviors' data structure from a saved game. 
	 * 
	 *
	 * @param playerBehaviors playerBehaviors' data structure.
	 * 
	 */
	void setPlayerBehaviors(RGGraph playerBehaviors) {
        this.playerBehaviors=playerBehaviors;
    }
	
	/**
	 * This method is used to obtain the list with the names of the players. 
	 * 
	 *
	 * @return List with the names of the players.
	 * 
	 */
	ArrayList<String> getSetOfPlayers() {
        return setOfPlayers;
    }
	
	/**
	 * This method is used to set the list with the names of the players. 
	 * 
	 *
	 * @param setOfPlayers List with the names of the players.
	 * 
	 */
	void setSetOfPlayers(ArrayList<String> setOfPlayers) {
        this.setOfPlayers=setOfPlayers;
    }
	
	/**
	 * This method is used to refresh the playerBehaviours,playerItems,setOfPlayers for each game.  
	 * 
	 * 
	 * 
	 */	
	void reloadPlayers() {
		this.playerBehaviors=new RGGraph();
		this.playerItems=new RGGraph();
		this.setOfPlayers=new ArrayList<String>();
	}
	
}
