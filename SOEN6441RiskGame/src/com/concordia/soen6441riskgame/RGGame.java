package com.concordia.soen6441riskgame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to control the game for human players. Only one game is
 * possible during runtime
 * 
 * 
 * @author Julian Beltran, Amit, Abhishek, Aamrean
 * @version 1.0
 * @since 1.0
 *
 */
public class RGGame extends Observable implements RGStrategy, Serializable {

	/**
	 * Created to store the Graph's data structure.
	 */
	private RGGraph graph = new RGGraph();
	
	/**
	 * Created to store the Countries' data structure.
	 */
	private RGGraph countryItems = new RGGraph();
	
	/**
	 * Created to store the Continents' data structure.
	 */
	private RGGraph continentItems = new RGGraph();

	/**
	 * Created to store the set of cards players use in Reinforcement Phase
	 */
	private RGGraph cardItems = new RGGraph();
	
	/**
	 * Created to store the current game (only one game is possible).
	 */
	private static RGGame game=new RGGame();
	
	/**
	 * Created to store the current phase that is being played.
	 */
	private String phase="";
	
	/**
	 * Created to store the status of the Attack Phase for a given player. Possible values:
	 * 1) empty: Nothing to do.
	 * 2) "end": End of the game.
	 * 3) "move": Used to show a frame to move army when a territory is captured. 
	 */
	private String attackStatus="";
	
	/**
	 * Created to know if a card was given to a player to avoid giving another card during the same turn for the same player.
	 */
	private boolean cardGiven=false;
	
	/**
	 * Created to know if "All Out mode" is set on or off
	 */
	private boolean allOutMode=false;
	
	/**
	 * Created to set and execute the strategy of every player
	 */
	private RGPlayerStrategy playerStrategy=new RGPlayerStrategy();
	
	/**
	 * Created to know if a game is loaded from a file
	 */
	private boolean savedGame=false;
	
	/**
	 * This method is used to assure only that one instance of the game (only one game) is created.
	 * 
	 * 
	 * @return Current game.
	 * 
	 */
	static RGGame getGame()
	{
		if (game==null)
			game=new RGGame();
		return game;
	}
	
	
	/**
	 * This method is used to set a saved game.
	 * 
	 * 
	 * @param game Game from file.
	 * 
	 */
	void setGame(RGGame game)
	{
		this.game=game;
	}
	
	
	/**
	 * This method is used to set the current phase of the game.
	 * 
	 * 
	 * @param phase Current phase.
	 * 
	 */
	void setPhase(String phase)
	{
		this.phase=phase;
	}
	
	/**
	 * This method returns the current phase of the game.
	 * 
	 * 
	 * @return Current phase of the game for a specific moment.
	 * 
	 */
	String getPhase()
	{
		return phase;
	}
	
	/**
	 * This method is used to create two data structures:
	 * 
	 * 1) Map Data Structure: This is a HashMap data structure that contains all the
	 * information related to the countries and their adjacencies with other
	 * countries. The key value is the name of a country (String), and the value of
	 * the key is an ArrayList (String) that contains all the adjacent countries. 
	 * 
	 * 2) Countries Data Structure: This is a HashMap data structure that contains all
	 * the information associated to the current game. The key value is the name of
	 * a country (String), and the value of the key is an ArrayList (String) that
	 * contains all the information of the county: Coordinate X in the map,
	 * coordinate Y in the map, continent, owner, and number of armies placed in a specific country.
	 * 
	 * 
	 * @param content Complete content of the tag [Territories] of the file.
	 * 
	 */
	void createGraph(StringBuilder content) {
		Scanner buffer = new Scanner(content.toString());
		String searchChar = ",";
		int index1 = 0;
		int index2;
		int i, j;
		String edge;
		String vertex = "";
		i = 0;
		while (buffer.hasNextLine()) {
			String actualLine = buffer.nextLine();
			j = 0;
			while (!actualLine.equals("")) {
				index2 = actualLine.indexOf(searchChar);
				if (index2 != -1) {
					if (j < 4) {//adding 4 first components of a country: Name of the country, X coordinate, Y coordinate, and continent
						if (j == 0) {//for the key of the data structure
							vertex = actualLine.substring(index1, index1 + index2);
							graph.addVertex(vertex);//adding the key to the Map data structure: Name of the country
							countryItems.addVertex(vertex);//adding the key to the Countries data structure: Name of the country
							actualLine = "," + actualLine;
							actualLine = actualLine.replace("," + vertex + ",", "");
						} else {//adding list of values to the data structure
							edge = actualLine.substring(index1, index1 + index2);
							countryItems.addEdge(vertex, edge);// adding: X coordinate, Y coordinate, and name of the continent to the Countries data structure
							actualLine = "," + actualLine;
							actualLine = actualLine.replace("," + edge + ",", "");
						}
						j++;
					} else {//adding the adjacent countries
						edge = actualLine.substring(index1, index1 + index2);
						graph.addEdge(vertex, edge);//adding the adjacent countries to the Map data structure
						actualLine = "," + actualLine;
						actualLine = actualLine.replace("," + edge + ",", "");
					}
				} else {
					graph.addEdge(vertex, actualLine);//adding the last adjacent country to the Map data structure
					break;
				}
			}
			if (!actualLine.equals("")) {
				i++;
				ArrayList<String> edgeList = graph.getEdges(vertex);
				System.out.print(vertex + "->");
				for (int k = 0; k < edgeList.size(); k++) {
					System.out.print(edgeList.get(k) + " -> ");
				}
				System.out.println("");

				edgeList = countryItems.getEdges(vertex);
				System.out.print(vertex + "->");
				for (int k = 0; k < edgeList.size(); k++) {
					System.out.print(edgeList.get(k) + " -> ");
				}
				System.out.println("");
			}

		}
		buffer.close();
	}

	/**
	 * This method is used to create one data structure:
	 * 
	 * 1) Continents Data Structure: This is a HashMap data structure that contains
	 * all the information associated to the continents of the map. The key value is
	 * the name of a continent (String), and the value of the key is an ArrayList
	 * (String) that contains all the information of the continent: continent bonus
	 * army, and all the countries that belong to that continent.
	 * 
	 * 
	 * @param content Complete content of the tag [Continents].
	 * 
	 */
	void createContinents(StringBuilder content) {
		Scanner buffer = new Scanner(content.toString());
		String searchChar = "=";
		int index2;
		String edge;
		String vertex = "";
		String bonusArmies = "";
		// Creating HashMap: Key=name of the continent, Value= ArrayList with just one
		// value: The number of bonus armies.
		while (buffer.hasNextLine()) {
			String actualLine = buffer.nextLine();
			if (!actualLine.equals("")) {//last line?
				if (!actualLine.substring(0, 1).equals("[")) {//last line?
					index2 = actualLine.indexOf(searchChar);
					vertex = actualLine.substring(0, index2);
					continentItems.addVertex(vertex);//adding key: Name of the continent
					bonusArmies = actualLine.substring(index2 + 1, actualLine.length());
					continentItems.addEdge(vertex, bonusArmies);//adding key values: Bonus army
				} else
					break;
			} else
				break;
		}

		// Adding countries to each continent.
		ArrayList<String> verticesCountry = countryItems.getVertex();
		for (int k = 0; k < verticesCountry.size(); k++) {
			ArrayList<String> edgeList = countryItems.getEdges(verticesCountry.get(k));
			continentItems.addEdge(edgeList.get(2), verticesCountry.get(k));//adding key values: name of the countries that belong to each continent
		}
		
		//printing continents data structure
		ArrayList<String> verti = continentItems.getVertex();
		for (int j = 0; j < verti.size(); j++) {
			System.out.print(verti.get(j) + " -> ");
			ArrayList<String> edgi = continentItems.getEdges(verti.get(j));
			for (int k = 0; k < edgi.size(); k++) {
				System.out.print(edgi.get(k) + " -> ");
			}
			System.out.println("");
		}

	}

	/**
	 * This method is used to assign each country a random player, and one army by default to each country.
	 * 
	 * 
	 * @param players List of Players (names).
	 * 
	 */
	void assignCountries(ArrayList<String> players) {
		ArrayList<String> vertex = graph.getVertex();
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int randomPlayer, i = 0;
		for (int k = 0; k < vertex.size(); k++) {//for each country

			//This while assures to assign a random player to each country.
			//Each player take a turn to be assigned
			while (numbers.size() < players.size()) {
				randomPlayer = new Random().nextInt(players.size());
				if (!numbers.contains(randomPlayer)) {
					numbers.add(randomPlayer);
				}
			}

			if (i < players.size()) {
				String player = players.get(numbers.get(i));
				countryItems.addEdge(vertex.get(k), player);//adding: owner of the country to the Countries data structure
				countryItems.addEdge(vertex.get(k), "1");//adding one army by default to each country
				i++;
				if (i == players.size()) {
					i = 0;
					numbers.clear();
				}
			}
		}
	}

	/**
	 * This method is used to obtain the X Coordinate of a country.
	 * 
	 * 
	 * @param vertex Name of the country.
	 * @return X coordinate of a given country.
	 * 
	 */
	String getXCoord(String vertex) {
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(0);
	}

	/**
	 * This method is used to obtain the Y Coordinate of a country.
	 * 
	 * 
	 * @param vertex Name of the country.
	 * @return Y coordinate of a given country.
	 * 
	 */
	String getYCoord(String vertex) {
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(1);
	}

	/**
	 * This method is used to obtain the owner of a country.
	 * 
	 * 
	 * @param vertex Name of the country.
	 * @return Owner of a country.
	 * 
	 */
	String getOwner(String vertex) {
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(3);
	}

	/**
	 * This method is used to obtain the current number of armies placed in a
	 * country.
	 * 
	 * 
	 * @param vertex Name of the country.
	 * @return Number of armies placed in a country.
	 * 
	 */
	String getArmies(String vertex) {
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(4);
	}

	/**
	 * This method is used to obtain a set of countries of the complete Risk� map.
	 * 
	 * @return Set of countries of the complete Risk� map.
	 * 
	 */
	ArrayList<String> getVertex() {
		ArrayList<String> vertex = countryItems.getVertex();
		return vertex;
	}

	/**
	 * This method is used to obtain a set of adjacent countries of a specific
	 * country.
	 * 
	 * 
	 * @param vertex Name of the country.
	 * @return Set of adjacent countries of a specific country.
	 * 
	 */
	ArrayList<String> getEdges(String vertex) {
		ArrayList<String> edges = graph.getEdges(vertex);
		return edges;
	}

	/**
	 * This method is used to obtain the list of countries a player owns.
	 * 
	 * 
	 * @param player Name of the player.
	 * @return List of countries a player owns.
	 * 
	 */
	ArrayList<String> getCurrentPlayerCountries(String player) 
	{
		ArrayList<String> currentPlayerCountries = new ArrayList<String>();
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) 
		{
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if (edges.get(3) == player) 
			{
				currentPlayerCountries.add(vertex.get(k));
			}
		}
		return currentPlayerCountries;
	}

	/**
	 * This method is used to add armies to a specific country.
	 * 
	 * 
	 * @param numberOfArmiesToAdd Number of armies to add.
	 * @param vertex Name of the country.
	 * 
	 */
	void setNumberOfArmies(int numberOfArmiesToAdd, String vertex) 
	{
		ArrayList<String> edges = countryItems.getEdges(vertex);
		String finalNumberOfArmiesToAdd = String.valueOf(Integer.valueOf(edges.get(4)) + numberOfArmiesToAdd);
		edges.set(4, finalNumberOfArmiesToAdd);
		countryItems.setEdge(vertex, edges);
		edges = countryItems.getEdges(vertex);
	}

	/**
	 * This method is used to obtain the number of countries a player owns.
	 * 
	 * 
	 * @param player Name of the player.
	 * @return Number of countries a player owns.
	 * 
	 */
	int getCurrentPlayerNumberOfCountries(String player) 
	{
		int currentPlayerNumberOfCountries = 0;
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) 
		{
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if (edges.get(3) == player) 
			{
				currentPlayerNumberOfCountries++;
			}
		}
		return currentPlayerNumberOfCountries;
	}

	/**
	 * For Reinforcement Phase: This method is used to obtain the number of armies a
	 * player has the right to place on his/her countries. This number is calculated
	 * according to the number of countries a player owns.
	 * 
	 * 
	 * @param currentPlayerNumberOfCountries Current number of countries a player owns.
	 * @return Number of armies a player has the right to place on his/her countries.
	 * 
	 */
	int getNumberOfArmiesDueTerritories(int currentPlayerNumberOfCountries) 
	{
		if (currentPlayerNumberOfCountries >= 9)
			currentPlayerNumberOfCountries = currentPlayerNumberOfCountries / 3;
		else
			currentPlayerNumberOfCountries = 3;
		return currentPlayerNumberOfCountries;
	}

	/**
	 * For Reinforcement Phase: This method is used to obtain the number of armies a
	 * player has the right to place on his/her countries. This number is calculated
	 * according to the number of continents a player owns.
	 * 
	 * 
	 * @param currentPlayerCountries List of countries a player owns.
	 * @return Number of armies a player has the right to place on his/her countries.
	 * 
	 */
	int getNumberOfArmiesDueContinents(ArrayList<String> currentPlayerCountries) 
	{
		int numberOfArmiesDueContinents = 0;
		int numberOfArmiesDueContinentsAux;
		ArrayList<String> vertex = continentItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) 
		{
			ArrayList<String> edges = continentItems.getEdges(vertex.get(k));
			numberOfArmiesDueContinentsAux = (Integer.valueOf(edges.get(0)));
			edges.remove(0);
			if (currentPlayerCountries.containsAll(edges)) 
			{
				numberOfArmiesDueContinents = numberOfArmiesDueContinents + numberOfArmiesDueContinentsAux;
			}
		}

		return numberOfArmiesDueContinents;
	}

	/**
	 * For fortification phase: This method is used to obtain the territories to where a player can move army.
	 * 
	 * 
	 * @param player Name of the player.
	 * @param country Name of the country.
	 * @return A list of all the countries that have a path in between.
	 *         
	 */
	ArrayList<String> getCurrentPlayerAdjacentCountries(String player, String country) {
		// currentPlayersLinkedCountries store all the counties and return at the end
		ArrayList<String> currentPlayersLinkedCountries = new ArrayList<String>();

		// queue used to mark if visited or not
		LinkedList<String> queue = new LinkedList<String>();

		String originalCountry=country;
		queue.add(country);

		while (!queue.isEmpty()) {
			country = queue.poll();
			ArrayList<String> adjacentcountries = getEdges(country);

			for (int k = 0; k < adjacentcountries.size(); k++) {
				String adcountry = adjacentcountries.get(k);

				if (getPlayersName(adcountry).equals(player) && !currentPlayersLinkedCountries.contains(adcountry) && !adcountry.contentEquals(originalCountry)) {

					currentPlayersLinkedCountries.add(adcountry);

					queue.add(adcountry);

				}

			}
		}
		return currentPlayersLinkedCountries;

	}

	/**
	 * This method is used to obtain the owner of a country.
	 * 
	 * 
	 * @param country Name of the country.
	 * @return The owner of the country.
	 * 
	 */
	String getPlayersName(String country) {
		String playerName = "";
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			if (vertex.get(k).equals(country)) {
				ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
				playerName = edges.get(3);
				break;

			}
		}
		return playerName;
	}
	
	/**
	 * This method is used to set initial values for the current game:
	 * 
	 * 
	 *  1) Set the number of armies for a player in the Setup Phase
	 *  2) Allocate zero cards for every player.
	 *  3) Allocate zero armies for every player for Reinforcement Phase.
	 * 
	 */
	void initializeGame()
	{
		RGPlayer players=RGPlayer.getPlayers();
		players.allocateArmies(game);//allocating available armies for every player to place in Setup Phase
		players.initializeCards();//allocating zero cards for each player
		initializeSetOfCards();//creating the set of cards in the cardItems' data structure
		players.initializeArmiesForReinforcementPhase();//allocating zero armies for every player
		players.initializePerformedActionsForEachPhase();//creating positions in players' data structure to store actions performed by players during play time
		players.initializeCardUsedCount();//setting count for card used as 0
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to create the set of cards in the cardItems' data structure. A random card type is assigned for every country in position 0. 
	 * 
	 * Types: 
	 * 1) Infantry represented by number (0).
	 * 2) Cavalry: Represented by number (1).
	 * 3) Artillery: Represented by number (2).
	 * 4) Wild: Represented by number (3).
	 * 
	 * In position 1, it will be stored the owner of the card.
	 * 
	 */
	void initializeSetOfCards()
	{
		ArrayList<String> vertex = countryItems.getVertex();
		ArrayList<Integer> cardType = new ArrayList<Integer>();
		int randomCardType, i=0;
		
		//adding cards, and card's type for every country
		for (int k = 0; k < vertex.size(); k++) 
		{
			cardItems.addVertex(vertex.get(k));
			if (i <= 2) 
			{
				cardItems.addEdge(vertex.get(k), String.valueOf(i));
				cardItems.addEdge(vertex.get(k), "");
				if (i == 2)
					i = 0;
				else
					i++;
			}
			
		}
		
		//adding Wild cards
		cardItems.addVertex("Wild 1");
		cardItems.addEdge("Wild 1", "3");
		cardItems.addEdge("Wild 1", "");
		cardItems.addVertex("Wild 2");
		cardItems.addEdge("Wild 2", "3");
		cardItems.addEdge("Wild 2", "");
	}
	
	
	/**
	 * This method is used to process the actions taken by a player during the Setup Phase:
	 * 
	 * 
	 * @param selectedCountry Selected country from list.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	public void setupPhase(String selectedCountry, String currentPlayerName)
	{
		
		RGPlayer players=RGPlayer.getPlayers();
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
	 * This method is used to process the actions taken by a player during the Reinforcement Phase:
	 * 
	 * 
	 * @param selectedCountry Selected country from list.
	 * @param currentPlayerName Name of the player.
	 * @param armiesToPlace Number of armies to place in a given country.
	 * 
	 */
	public void reinforcementPhase(String selectedCountry, String currentPlayerName, String armiesToPlace)
	{
		
		RGPlayer players=RGPlayer.getPlayers();
		String totalArmiesAvailable;
		game.setNumberOfArmies(Integer.valueOf(armiesToPlace), selectedCountry);//adding armies to the country
		players.subtractArmiesForReinforcementPhase(currentPlayerName,armiesToPlace);
		
		totalArmiesAvailable=players.getNumberOfArmiesForReinforcement(currentPlayerName);	
		if(totalArmiesAvailable.contentEquals("0"))//all armies placed? Go to the next Phase
		{
			game.setPhase("Attack");
			cardGiven=false;
		}
		
		//Storing performed actions 
		StringBuilder actionPerformed=new StringBuilder();
		actionPerformed.append("*"+armiesToPlace+" army deployed in "+selectedCountry);
		actionPerformed.append("\n");
		players.setActionsPerformed(actionPerformed, currentPlayerName, "reinforcement");
	}
	
	/**
	 * This method is used to process the actions taken by a player during the Fortification Phase:
	 * 
	 * 
	 * @param countryFrom Origin country.
	 * @param countryTo Destination country.
	 * @param armiesToMove Number of armies to move.
	 * 
	 */
	public void fortificationPhase(String countryFrom, String countryTo, int armiesToMove)
	{
		RGPlayer players=RGPlayer.getPlayers();
		game.setNumberOfArmies(-armiesToMove, countryFrom);
		game.setNumberOfArmies(armiesToMove, countryTo);
		
		//Storing performed actions 
		StringBuilder actionPerformed=new StringBuilder();
		actionPerformed.append("*"+armiesToMove+" army moved from "+countryFrom+" to "+countryTo);
		actionPerformed.append("\n");
		String currentPlayerName = players.getPlayerTurn();
		players.setActionsPerformed(actionPerformed, currentPlayerName, "fortification");
		
		players.setNextTurn();
		game.setPhase("Reinforcement");
	}
	
	/**
	 * This method is used to skip the Fortification Phase due to the player does not want to move any army.
	 * 
	 * 
	 */
	public void fortificationPhaseNoMovements()
	{
		RGPlayer players=RGPlayer.getPlayers();
		players.setNextTurn();
		game.setPhase("Reinforcement");
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to obtain the list of countries a given player can use to attack.
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return List of countries a given player can use to attack.
	 * 
	 */
	ArrayList<String> getCountriesAttacker(String currentPlayerName)
	{
		ArrayList<String> countriesAttacker = new ArrayList<String>();
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if ((edges.get(3)).contentEquals(currentPlayerName) && (Integer.valueOf(edges.get(4)))>=2) 
			{
				ArrayList<String> countriesDefender = new ArrayList<String>();
				countriesDefender=getCountriesDefender(vertex.get(k),currentPlayerName);
				if (!countriesDefender.isEmpty())
					countriesAttacker.add(vertex.get(k));
			}
		}
		return countriesAttacker;
	}
	
	/**
	 * This method is used to obtain the list of countries a given player can use to attack (countries with only one army are included).
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return List of countries a given player can use to attack.
	 * 
	 */
	ArrayList<String> getCountriesAttackerOneArmy(String currentPlayerName)
	{
		ArrayList<String> countriesAttacker = new ArrayList<String>();
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if ((edges.get(3)).contentEquals(currentPlayerName) && (Integer.valueOf(edges.get(4)))>=1) 
			{
				ArrayList<String> countriesDefender = new ArrayList<String>();
				countriesDefender=getCountriesDefender(vertex.get(k),currentPlayerName);
				if (!countriesDefender.isEmpty())
					countriesAttacker.add(vertex.get(k));
			}
		}
		return countriesAttacker;
	}
	
	/**
	 * This method is used to obtain the list of countries to where a given player can attack.
	 * 
	 * 
	 * @param selectedCountry Country from where a player wants to attack.
	 * @param currentPlayerName Name of the player.
	 * @return List of countries a given player can attack.
	 * 
	 */
	ArrayList<String> getCountriesDefender(String selectedCountry, String currentPlayerName)
	{
		ArrayList<String> countriesDefender = new ArrayList<String>();
		ArrayList<String> edgesGraph = graph.getEdges(selectedCountry);
		ArrayList<String> edgesCountryItems= new ArrayList<String>();
		String currentCountry;
		for (int k = 0; k < edgesGraph.size(); k++) 
		{
			currentCountry=edgesGraph.get(k);
			edgesCountryItems = countryItems.getEdges(currentCountry);
			
			if (!edgesCountryItems.get(3).contentEquals(currentPlayerName)) 
			{
				countriesDefender.add(currentCountry);
			}
		}
		return countriesDefender;
	}
	
	/**
	 * This method is used to obtain a list with the number of dice a player can use to attack.
	 * 
	 * 
	 * @param selectedCountry Country from where a player wants to attack.
	 * @return List with the number of dice a player can use to attack.
	 * 
	 */
	ArrayList<String> getDiceAttacker(String selectedCountry)
	{
		ArrayList<String> diceAttacker = new ArrayList<String>();
		ArrayList<String> edges = countryItems.getEdges(selectedCountry);
		int numberOfArmy=Integer.valueOf(edges.get(4));
		if (numberOfArmy==2)
		{
			diceAttacker.add("1");
		}
		if (numberOfArmy==3)
		{
			diceAttacker.add("1");
			diceAttacker.add("2");
		}
		if (numberOfArmy>=4)
		{
			diceAttacker.add("1");
			diceAttacker.add("2");
			diceAttacker.add("3");
		}
		return diceAttacker;
	}
	
	/**
	 * This method is used to obtain a list with the number of dice a player can use to defend.
	 * 
	 * 
	 * @param selectedCountry Defending country.
	 * @return List with the number of dice a player can use to defend.
	 * 
	 */
	ArrayList<String> getDiceDefender(String selectedCountry)
	{
		ArrayList<String> diceDefender = new ArrayList<String>();
		ArrayList<String> edges = countryItems.getEdges(selectedCountry);
		int numberOfArmy=Integer.valueOf(edges.get(4));
		if (numberOfArmy==1)
		{
			diceDefender.add("1");
		}
		if (numberOfArmy>=2)
		{
			diceDefender.add("1");
			diceDefender.add("2");
		}
		return diceDefender;
	}
	
	/**
	 * This method is used to play a battle according to the mode chosen by the player:
	 * 
	 * 1) One battle mode.
	 * 2) All out mode.
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
		if(game.allOutMode==false)
		{
			game.attackPhase(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);
		}
		else if(game.allOutMode==true)
		{
			//while All Out Mode is on and there is enough army available to attack
			while (game.allOutMode==true && Integer.valueOf(game.getArmies(selectedCountryAttacker))>1)
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
			}
			if(Integer.valueOf(game.getArmies(selectedCountryAttacker))==1 && !attackStatus.contentEquals("move")) 
			{
				RGPlayer players=RGPlayer.getPlayers();
				StringBuilder actionPerformed=new StringBuilder();
				actionPerformed.append("Not enough army to attack");
				actionPerformed.append("\n");
				
				//No more army to attack. Setting all out mode off
				allOutMode=false;
				
				//Storing performed actions 
				players.setActionsPerformed(actionPerformed, currentPlayerName, "attack");
			}
		}
	}
	
	/**
	 * This method is used to process the actions taken by a player during the Attack Phase:
	 * 
	 * 
	 * @param selectedCountryAttacker Attacking country
	 * @param selectedCountryDefender Attacked country.
	 * @param selectedDiceAttacker Number of dice to attack.
	 * @param selectedDiceDefender Number of dice to defend.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	public void attackPhase(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName)
	{
		RGPlayer players=RGPlayer.getPlayers();
		StringBuilder actionPerformed=new StringBuilder();
		int randomNumberOneToSix;
		ArrayList<Integer> setAttackerDiceNumbers = new ArrayList<Integer>();
		ArrayList<Integer> setDefenderDiceNumbers = new ArrayList<Integer>();
			 
		//Tossing dice for attacker
		actionPerformed.append("*Dice results for attacker (");
		for (int k = 0; k < Integer.valueOf(selectedDiceAttacker); k++)
		{
			randomNumberOneToSix = new Random().nextInt(6);
			randomNumberOneToSix++;
			setAttackerDiceNumbers.add(randomNumberOneToSix);
			actionPerformed.append("["+randomNumberOneToSix+"]");
		}
		actionPerformed.append(")");
		actionPerformed.append("\n");

		//Tossing dice for defender
		actionPerformed.append("Dice results for defender (");
		for (int k = 0; k < Integer.valueOf(selectedDiceDefender); k++)
		{
			randomNumberOneToSix = new Random().nextInt(6);
			randomNumberOneToSix++;
			setDefenderDiceNumbers.add(randomNumberOneToSix);
			actionPerformed.append("["+randomNumberOneToSix+"]");
		}
		actionPerformed.append(")");
		actionPerformed.append("\n");

		//Battle begins
		int highestDiceAttacker;
		int highestDiceDefender;
		while(!setAttackerDiceNumbers.isEmpty() && !setDefenderDiceNumbers.isEmpty())
		{
			highestDiceAttacker=Collections.max(setAttackerDiceNumbers);
			highestDiceDefender=Collections.max(setDefenderDiceNumbers);
			if(highestDiceAttacker>highestDiceDefender)
			{
				actionPerformed.append("Attacker wins (Attacker:"+highestDiceAttacker+" vs Defender:"+highestDiceDefender+")");
				actionPerformed.append("\n");
				game.setNumberOfArmies(-1, selectedCountryDefender);
			}
			else if(highestDiceAttacker<=highestDiceDefender)
			{
				actionPerformed.append("Defender wins (Attacker:"+highestDiceAttacker+" vs Defender:"+highestDiceDefender+")");
				actionPerformed.append("\n");
				game.setNumberOfArmies(-1, selectedCountryAttacker);
			}
			int highestDiceAttackerIndex = setAttackerDiceNumbers.indexOf(highestDiceAttacker);
			setAttackerDiceNumbers.remove(highestDiceAttackerIndex);
			int highestDiceDefenderIndex = setDefenderDiceNumbers.indexOf(highestDiceDefender);
			setDefenderDiceNumbers.remove(highestDiceDefenderIndex);
		}

		//Country captured?
		int numberOfCountries=Integer.valueOf(game.getArmies(selectedCountryDefender));
		if(numberOfCountries==0)//country was captured
		{
			String loserPlayerName=game.getOwner(selectedCountryDefender);
			
			//territory captured. Setting all out mode off
			allOutMode=false;

			//setting owner and moving armies (Risk rule: "Move at least as many armies as the number of dice winner rolled in the last battle")
			ArrayList<String> edges = countryItems.getEdges(selectedCountryDefender);
			edges.set(3, currentPlayerName);
			countryItems.setEdge(selectedCountryDefender, edges);
			game.setNumberOfArmies((Integer.valueOf(selectedDiceAttacker))*(-1), selectedCountryAttacker);
			game.setNumberOfArmies(Integer.valueOf(selectedDiceAttacker), selectedCountryDefender);
			actionPerformed.append(selectedDiceAttacker+" army already moved to your new captured territory");
			actionPerformed.append("\n");

			//informing who won and who lost
			actionPerformed.append(selectedCountryDefender+" was captured by "+currentPlayerName+" from "+selectedCountryAttacker);
			actionPerformed.append("\n");

			//attacker won a card
			if(cardGiven==false)
			{
				ArrayList<String> vertex = cardItems.getVertex();
				int randomCard = new Random().nextInt(vertex.size());
				edges = cardItems.getEdges(vertex.get(randomCard));

				while(!edges.get(1).contentEquals(""))
				{
					randomCard = new Random().nextInt(vertex.size());
					edges = cardItems.getEdges(vertex.get(randomCard));
				}
				edges.set(1, currentPlayerName);
				cardItems.setEdge(vertex.get(randomCard), edges);
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
				cardGiven=true;
			}

			/*printing cards data structure
			ArrayList<String> verti = cardItems.getVertex();
			for (int j = 0; j < verti.size(); j++) {
				System.out.print(verti.get(j) + " -> ");
				ArrayList<String> edgi = cardItems.getEdges(verti.get(j));
				for (int k = 0; k < edgi.size(); k++) {
					System.out.print(edgi.get(k) + " -> ");
				}
				System.out.println("");
			}*/
			
			//does loser have more territories?
			ArrayList<String> vertex = countryItems.getVertex();
			int totalCountriesOwned=0;
			for (int k = 0; k < vertex.size(); k++) 
			{
				edges = countryItems.getEdges(vertex.get(k));
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
			vertex = countryItems.getVertex();
			totalCountriesOwned=0;
			for (int k = 0; k < vertex.size(); k++) 
			{
				edges = countryItems.getEdges(vertex.get(k));
				if(edges.get(3).contentEquals(currentPlayerName))
					totalCountriesOwned++;//counting countries owned by current player
			}
			if(totalCountriesOwned==Integer.valueOf(vertex.size()))
			{
				actionPerformed.append("***This is the end. You won!***");
				actionPerformed.append("\n");
				game.attackStatus="end";
			}
			else
				game.attackStatus="move";
		}
		
		//Storing performed actions 
		players.setActionsPerformed(actionPerformed, currentPlayerName, "attack");  
	}
	
	/**
	 * This method is used to skip the Attack Phase due to the player does not want to attack.
	 * 
	 * 
	 */
	public void attackPhaseNoMovements()
	{
		game.setPhase("Fortification");
		setChanged();
		notifyObservers(this);
	}
	
	
	/**
	 * This method is used to get the attack status. Possible values:
	 * 
	 * 1) empty: Nothing to do.
	 * 2) "end": End of the game.
	 * 3) "move": Used to show a frame to move army when a territory is captured. 
	 * 
	 * 
	 * @return Status.
	 * 
	 */
	String getAttackStatus()
	{
		return attackStatus;
	}
	
	/**
	 * This method is used to set the attack status. Possible values:
	 * 
	 * 1) empty: Nothing to do.
	 * 2) "end": End of the game.
	 * 3) "move": Used to show a frame to move army when a territory is captured. 
	 * 
	 * @param status Status.
	 * 
	 */
	void setAttackStatus(String status)
	{
		attackStatus=status;
	}
	
	/**
	 * This method is used to skip deployment of army to a new captured territory in Attack Phase.
	 * 
	 * 
	 */
	void capturedTerritoriesNoMovements()
	{
		game.setAttackStatus("");
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to deploy army to a new captured territory in Attack Phase.
	 * 
	 * 
	 * @param selectedCountryAttacker Attacking country.
	 * @param selectedCountryDefender Attacked country.
	 * @param armyToDeploy Number of army to deploy.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	void capturedTerritoriesArmyDeployment(String selectedCountryAttacker, String selectedCountryDefender, String armyToDeploy, String currentPlayerName)
	{	
		StringBuilder actionPerformed=new StringBuilder();
		RGPlayer players=RGPlayer.getPlayers();
		game.setNumberOfArmies(-Integer.valueOf(armyToDeploy), selectedCountryAttacker);
		game.setNumberOfArmies(Integer.valueOf(armyToDeploy), selectedCountryDefender);
		game.setAttackStatus("");

		//Storing performed actions 
		actionPerformed.append("*"+armyToDeploy+" army deployed from "+selectedCountryAttacker+" to "+selectedCountryDefender);
		actionPerformed.append("\n");
		players.setActionsPerformed(actionPerformed, currentPlayerName, "attack");
		
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to move to the next phase automatically because there are no more available territories to attack.
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	void attackPhaseNoAttackers(String currentPlayerName)
	{
		//Storing performed actions 
		StringBuilder actionPerformed=new StringBuilder();
		RGPlayer players=RGPlayer.getPlayers();
		actionPerformed.append("*No more available territories to attack");
		actionPerformed.append("\n");
		players.setActionsPerformed(actionPerformed, currentPlayerName, "attack");
		
		game.setPhase("Fortification");
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to set the All Out mode. On=true, Off=false
	 *
	 *
	 *@param mode All Out mode on=true or off=false
	 * 
	 */
	void setAllOutModeForAttackPhase(boolean mode)
	{
		allOutMode=mode;
	}
	
	/**
	 * This method is used to know if All Out mode is set "on" or "off"
	 *
	 * 
	 * @return All Out mode on=true or off=false
	 * 
	 */
	boolean getAllOutModeForAttackPhase()
	{
		return allOutMode;
	}
	
	/**
	 * This method is used to obtain the percentage of the map controlled by every player
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return Percentage controlled.
	 * 
	 */
	int percentageMapControlledByPlayer(String currentPlayerName)
	{
		int percentageControlled=0;
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) 
		{
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if(edges.get(3).contentEquals(currentPlayerName))
			{
				percentageControlled++;
			}
		}
		percentageControlled=(percentageControlled*100)/vertex.size();
		return percentageControlled;
	}
	
	/**
	 * This method is used to obtain the continents controlled by a given player.
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return List of continents owned by a given player.
	 * 
	 */
	StringBuilder getContinentsOwnedByPlayer(String currentPlayerName)
	{
		ArrayList<String> currentPlayerCountries;
		StringBuilder continentsOwned=new StringBuilder();
		currentPlayerCountries=game.getCurrentPlayerCountries(currentPlayerName);
		ArrayList<String> vertex = continentItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) 
		{
			ArrayList<String> edges = continentItems.getEdges(vertex.get(k));
			edges.remove(0);
			if (currentPlayerCountries.containsAll(edges)) 
			{
				continentsOwned.append(vertex.get(k));
				continentsOwned.append("\n");
			}
		}
		return continentsOwned;
	}
	
	/**
	 * This method is used to obtain the total number of army a player owns.
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return Total number of army given a player.
	 * 
	 */
	String getTotalNumberOfArmies(String currentPlayerName)
	{
		int totalNumberOfArmies=0;
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) 
		{
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if (edges.get(3).contentEquals(currentPlayerName)) 
			{
				totalNumberOfArmies=totalNumberOfArmies+Integer.valueOf(edges.get(4));
			}
		}
		return String.valueOf(totalNumberOfArmies);
	}
	
	/**
	 * This method is used to obtain the total number of cards for a given player.
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * @return ArrayList of cards given a player.
	 * 
	 */
	ArrayList<String> getPlayerCards(String currentPlayerName){
		ArrayList<String> playerCards=new ArrayList<String>();
		ArrayList<String> vertex=cardItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			ArrayList<String> edges = cardItems.getEdges(vertex.get(k));
			if(edges.get(1).contentEquals(currentPlayerName)) {
				playerCards.add(edges.get(0));
			}
		}
		return playerCards;
	}
	
	/**
	 * This method is used to validated the selected cards.
	 * 
	 * 
	 * @param selectedCards List of selected cards.
	 * @return true or false based on criteria.
	 * 
	 */
	boolean validateCards (List<String> selectedCards) {
		boolean flag=false;
		
		int count=Collections.frequency(selectedCards, "Wild");
		if(count==1) {
			flag= true;
		}
		if((selectedCards.get(0)!=selectedCards.get(1) && selectedCards.get(1)!=selectedCards.get(2) && selectedCards.get(0)!=selectedCards.get(2)) 
				|| (selectedCards.get(0)==selectedCards.get(1) && selectedCards.get(1)==selectedCards.get(2))) {
			flag=true;
		}
		
		
		return flag;
	}
	
	/**
	 * This method is used to remove the cards that have been used.
	 * 
	 * 
	 * @param selectedCards List of selected cards.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
	void removePlayerCards(String currentPlayerName, List<String> selectedCards) {
		//ArrayList<String> playerCards=new ArrayList<String>();
		ArrayList<String> vertex=cardItems.getVertex();
		for (String card : selectedCards) {
			for (int k = 0; k < vertex.size(); k++) {
				ArrayList<String> edges = cardItems.getEdges(vertex.get(k));
				if(edges.get(1).contentEquals(currentPlayerName)) {
					{

						if(card.equals("Infantry") && edges.get(0).equals("0")) {
							//System.out.println(edges.get(0)+" "+edges.get(1));
							edges.set(1, "");
							cardItems.setEdge(vertex.get(k), edges);
							break;
						}
						else if(card.equals("Cavalry") && edges.get(0).equals("1")) {
							//System.out.println(edges.get(0)+" "+edges.get(1));
							edges.set(1, "");
							cardItems.setEdge(vertex.get(k), edges);
							break;
						}
						else if(card.equals("Artillery") && edges.get(0).equals("2")) {
							//System.out.println(edges.get(0)+" "+edges.get(1));
							edges.set(1, "");
							cardItems.setEdge(vertex.get(k), edges);
							break;
						}
						else if(card.equals("Wild") && edges.get(0).equals("3")) {
							//System.out.println(edges.get(0)+" "+edges.get(1));
							edges.set(1, "");
							cardItems.setEdge(vertex.get(k), edges);
							break;
						}

					}
				}
			}
		}
		
	}
	
	/**
	 * This method is used to get the list of cards. 
	 * 
	 *
	 * @return List of cards.
	 * 
	 */
	ArrayList<String> getCardItemsVertex() {
		return cardItems.getVertex();
    }
	
	/**
	 * This method is used to get the values of a given card in cardItems' data structure 
	 * 
	 *
	 * @return List of cards.
	 * 
	 */
	ArrayList<String> getCardItemsEdges(String nameOfCard) {
		return cardItems.getEdges(nameOfCard);
    }
	
	/**
	 * This method is used to set the values of a given card in cardItems' data structure 
	 * 
	 *
	 * @param nameOfCard Name of the card (country).
	 * @param edges Values for the key (given card).
	 * 
	 */
	void setCardItemsEdge(String nameOfCard, ArrayList<String> edges) {
		cardItems.setEdge(nameOfCard, edges);
    }
	
	/**
	 * This method is used to obtain countryItems' data structure where the current game is stored. 
	 * 
	 *
	 * @return countryItems' data structure.
	 * 
	 */
	RGGraph getCountryItems() {
        return countryItems;
    }
	
	/**
	 * This method is used to set countryItems' from a saved game. 
	 * 
	 *
	 * @param countryItems countryItems' data structure.
	 * 
	 */
	void setCountryItems(RGGraph countryItems) {
        this.countryItems=countryItems;
    }
	
	/**
	 * This method is used to get the list of countries of the map. 
	 * 
	 *
	 * @return List of countries of the map.
	 * 
	 */
	ArrayList<String> getCountryItemsVertex() {
		return countryItems.getVertex();
    }
	
	/**
	 * This method is used to get the values of a given key in countryItems' data structure. 
	 * 
	 *
	 * @return Values of the given key (country).
	 * 
	 */
	ArrayList<String> getCountryItemsEdges(String vertex) {
		return countryItems.getEdges(vertex);
    }
	
	/**
	 * This method is used to set a value in countryItems' data structure. 
	 * 
	 *
	 * @param vertex Name of a country.
	 * @param edges Values associated to a country.
	 * 
	 */
	void setCountryItemsEdges(String vertex, ArrayList<String> edges) {
		countryItems.setEdge(vertex, edges);
    }
	
	/**
	 * This method is used to obtain a random country.
	 * 
	 *
	 * @return A random country.
	 * 
	 */
	String getRandomCountry(String currentPlayerName) {
		ArrayList<String> listOfCountries=getCurrentPlayerCountries(currentPlayerName);
        int randomCountryNumber = new Random().nextInt(listOfCountries.size());
        return listOfCountries.get(randomCountryNumber);
    }
	
	/**
	 * This method is used to set the player behavior strategy.  
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void setStrategy(String currentPlayerName) {
		RGPlayer players=RGPlayer.getPlayers();
		String strategy=players.getPlayerStrategy(currentPlayerName);
		if(strategy.contentEquals("human")) {
			RGGame game=RGGame.getGame();
			this.playerStrategy.setStrategy(game);
		}
		else if(strategy.contentEquals("aggressive")) {
			RGGameAggressive game=RGGameAggressive.getGame();
			this.playerStrategy.setStrategy(game);
		}
		else if(strategy.contentEquals("benevolent")) {
			RGGameBenevolent game=RGGameBenevolent.getGame();
			this.playerStrategy.setStrategy(game);
		}
		else if(strategy.contentEquals("random")) {
			RGGameRandom game=RGGameRandom.getGame();
			this.playerStrategy.setStrategy(game);
		}
		else if(strategy.contentEquals("cheater")) {
			RGGameCheater game=RGGameCheater.getGame();
			this.playerStrategy.setStrategy(game);
		}
		
	}
	
	/**
	 * This method is used to execute the player behavior strategy for Setup Phase.  
	 * 
	 * 
	 * @param selectedCountry Selected country
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void executeSetupStrategy(String selectedCountry, String currentPlayerName) {
		this.playerStrategy.executeSetupPhase(selectedCountry, currentPlayerName);
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to execute the player behavior strategy for Reinforcement Phase.  
	 * 
	 * 
	 * @param selectedCountry Selected country
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void executeReinforcementStrategy(String selectedCountry, String currentPlayerName, String armiesToPlace) {
		this.playerStrategy.executeReinforcementPhase(selectedCountry, currentPlayerName, armiesToPlace);
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to execute the player behavior strategy for Attack Phase.  
	 * 
	 * 
	 * @param selectedCountry Selected country
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void executeAttackPhaseModeDecisionStrategy(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName) {
		this.playerStrategy.executeAttackPhaseModeDecision(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);
		RGGame game=RGGame.getGame();
		RGPlayer players=RGPlayer.getPlayers();
		String strategy=players.getPlayerStrategy(currentPlayerName);
		if(!game.getAttackStatus().contentEquals("end") && !strategy.contentEquals("human"))
			game.setPhase("Fortification");
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to execute the player behavior strategy for Fortification Phase.  
	 * 
	 * 
	 * @param selectedCountry Selected country
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void executeFortificationPhaseStrategy(String countryFrom, String countryTo, int armiesToMove) {
		this.playerStrategy.executeFortificationPhase(countryFrom, countryTo, armiesToMove);
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to inform if a card was given or not to a player during Attack Phase.    
	 * 
	 * 
	 * @param status True if a card was already given to a player in Attack Phase. 
	 * 
	 */	
	void setCardGiven(boolean status) {
		cardGiven=status;
	}
	
	/**
	 * This method is used to know if a card was given or not to a player during Attack Phase.    
	 * 
	 * 
	 * @return True if a card was already given to a player in Attack Phase. 
	 * 
	 */	
	boolean getCardGiven() {
		return cardGiven;
	}
	
	/**
	 * This method is used to know what is the country with the largest number of armies.    
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	String getLeastPopulatedCountry(String currentPlayerName) {
		ArrayList<String> listOfCountries = countryItems.getVertex();
		int minNumberOfArmy=1000;
		String selectedCountry="";
		for (int k = 0; k < listOfCountries.size(); k++) 
		{
			ArrayList<String> edges = countryItems.getEdges(listOfCountries.get(k));
			if(edges.get(3).contentEquals(currentPlayerName))
			{
				if(Integer.valueOf(edges.get(4))<minNumberOfArmy) {
					minNumberOfArmy=Integer.valueOf(edges.get(4));
					selectedCountry=listOfCountries.get(k);
				}
			
			}
		}
		return selectedCountry;
	}
	
	/**
	 * This method is used to double the number of armies of a given country.   
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	void doubleNumberOfArmies(String currentPlayerName) {
		ArrayList<String> listOfCountries = countryItems.getVertex();
		for (int k = 0; k < listOfCountries.size(); k++) 
		{
			ArrayList<String> edges = countryItems.getEdges(listOfCountries.get(k));
			if(edges.get(3).contentEquals(currentPlayerName))
			{
				edges.set(4, String.valueOf((Integer.valueOf(edges.get(4)))*2));
				countryItems.setEdge(listOfCountries.get(k), edges);
			}
		}
	}
	
	/**
	 * This method is used to obtain a list of countries ordered by the number of army placed in each country.    
	 * 
	 * 
	 * @param currentPlayerName Name of the player.
	 * 
	 */	
	ArrayList<String> getListOfCountriesOrderedByPopulation(String currentPlayerName) {
		ArrayList<String> listOfCountries = game.getCountriesAttackerOneArmy(currentPlayerName);
		ArrayList<String> listOfSortedCountries= new ArrayList<String>();
		listOfSortedCountries.add(listOfCountries.get(0));
		boolean flagAdded=false;
		for (int i=1; i<listOfCountries.size();i++) {
			String numberOfArmyFrom=game.getArmies(listOfCountries.get(i));
			for (int j=0; j<listOfSortedCountries.size();j++) {
				String numberOfArmyTo=game.getArmies(listOfSortedCountries.get(j));
				if(Integer.valueOf(numberOfArmyFrom)>Integer.valueOf(numberOfArmyTo)){
					listOfSortedCountries.add(j, listOfCountries.get(i));
					flagAdded=true;
					break;
				}
			}
			if(!flagAdded)
				listOfSortedCountries.add(listOfCountries.get(i));
			else
				flagAdded=false;
		}
		return listOfSortedCountries;
	}
	
	/**
	 * This method is used to know if a game is being loaded from a file.
	 * 
	 * 
	 * @param status True=A game is being loaded from a file.
	 * 
	 */
	void setSavedGame (boolean status) {
		savedGame=status;
	}
	
	/**
	 * This method is used to know if a game is being loaded from a file.
	 * 
	 * 
	 * @return True=A game is being loaded from a file.
	 * 
	 */
	boolean getSavedGame () {
		return savedGame;
	}
	
	/**
	 * This method is used to notify the observers when a game is being loaded.
	 * 
	 * 
	 */
	void savedGameNotifyObservers () {
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to obtain graph's data structure where the map with its adjacencies is stored. 
	 * 
	 *
	 * @return graph's data structure.
	 * 
	 */
	RGGraph getGraph() {
        return graph;
    }
	
	/**
	 * This method is used to set graph's data structure from a saved game. 
	 * 
	 *
	 * @param graph graph's data structure.
	 * 
	 */
	void setGraph(RGGraph graph) {
        this.graph=graph;
    }
	
	/**
	 * This method is used to obtain continentItems' data structure where the information of the continents is stored. 
	 * 
	 *
	 * @return continentItems data structure.
	 * 
	 */
	RGGraph getContinentItems() {
        return continentItems;
    }
	
	/**
	 * This method is used to set continentItems' from a saved game. 
	 * 
	 *
	 * @param continentItems continentItems' data structure.
	 * 
	 */
	void setContinentItems(RGGraph continentItems) {
        this.continentItems=continentItems;
    }
	
	/**
	 * This method is used to obtain cardItems' data structure. 
	 * 
	 *
	 * @return cardItems' data structure.
	 * 
	 */
	RGGraph getCardItems() {
        return cardItems;
    }
	
	/**
	 * This method is used to set cardItems' from a saved game. 
	 * 
	 *
	 * @param cardItems cardItems' data structure.
	 * 
	 */
	void setCardItems(RGGraph cardItems) {
        this.cardItems=cardItems;
    }
	
	/**
	 * This method is used to obtain the strategy of a player. 
	 * 
	 *
	 * @return Strategy of a player.
	 * 
	 */
	RGPlayerStrategy getPlayerStrategy() {
        return playerStrategy;
    }
	
	/**
	 * This method is used to set the strategy of a player. 
	 * 
	 *
	 * @param playerStrategy Strategy of a player.
	 * 
	 */
	void setPlayerStrategy(RGPlayerStrategy playerStrategy) {
        this.playerStrategy=playerStrategy;
    }

}
