package com.concordia.soen6441riskgame;

import java.util.ArrayList;


/**
 * Class to store and control the information of a set of players --between 2 and 6 players per game.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGPlayer{
	
	private ArrayList<String> setOfPlayers = new ArrayList<String>();
	private RGGraph playerItems=new RGGraph();
	private ArrayList<String> colors = new ArrayList<String>();
	
	
	/**
	 * This constructor creates a list of six colors to assign them later to every player where it is required
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
	 * This method is used to validate the number of players registered by the user. Must be between 2 and 6.
	 * 
	 * 
	 * @param players String with the names of players separated by commas
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
     * @param players String with the names of players separated by commas 
     * @return List with the name of the players created
     *   
	 */		
	ArrayList<String> setPlayers(String players)
	{
		int index1=0;
		int index2;
		String player;
		String searchChar=",";
		int i=0;
		
		while(!players.equals("")) 
		{
			index2=players.indexOf(searchChar);
			if (index2!=-1) 
			 { 
				player=players.substring(index1, index1+index2);//getting the name of a player
				playerItems.addVertex(player);//creating a key (name of the player) of the players' data structure
				if(i==0)
					playerItems.addEdge(player, "1");//creating 'Turn' value. First player begins the game
				else
					playerItems.addEdge(player, "0");//creating 'Turn' value for the rest of the players
				playerItems.addEdge(player, colors.get(i));//creating 'Color' value 
				setOfPlayers.add(player);
				players=","+players;
				players=players.replace(","+player+",", "");//a player is already created; the player has to be deleted from String with the names of players separated by commas
				
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
			else//last player of the String with the names of players separated by commas
			{
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
	 * This method returns the color of a player 
	 * 
     *    
     * @param vertex Name of the player 
     * @return Color of a player
     *   
	 */		
	String getPlayerColor(String vertex)
	{
		ArrayList<String> edgeList = playerItems.getEdges(vertex);
		return edgeList.get(1);//color is placed in second position of the list of values of a player
	}
	
	/**
	 * This method is used to set the number of armies given to a player in the Setup Phase  
	 * 
     *    
     * @param game object with the information of the current game
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
	 * This method is used to get the current turn in the game  
	 * 
     *    
     * @return Name of the player with the current turn
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
			if(edgeList.get(0)=="1")//is the player with the current turn?
			{
				currentPlayerTurn=vertex.get(k);
			}
		}
		return currentPlayerTurn;
	}
	
	/**
	 * This method is used to get the current armies a player can place on his/her countries  
	 * 
	 * 
     * @param vertex Name of the player   
     * @return Number of armies available for a given player
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
     * @param numberOfArmiesToAdd Number of armies to subtract in this case  
     * @param vertex Name of the player
     *   
	 */	
	void setNumberOfArmiesSetup(int numberOfArmiesToAdd, String vertex)
	{
		ArrayList<String> edges = playerItems.getEdges(vertex);
		String finalNumberOfArmiesToAdd=String.valueOf(Integer.valueOf(edges.get(2))+numberOfArmiesToAdd);
		edges.set(2, finalNumberOfArmiesToAdd);//the value 'number of armies for Setup Phase' is in the second position of the players' data structure
		playerItems.setEdge(vertex, edges);
		edges = playerItems.getEdges(vertex);
		System.out.println(edges);
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
			if(edges.get(0)=="1")
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
	 * This method is used to get the total of armies to place in the Setup Phase for all players. This is used to know when the Setup Phase is finished. 
	 * 
	 * @return Total of armies available to place of all players
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
	 * This method is used to set the first turn once the Setup Phase is finished
	 *
	 * 
	 */	
	void initializeTurn()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		System.out.println(vertex);
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
		
}
