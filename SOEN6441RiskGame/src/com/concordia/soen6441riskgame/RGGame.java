package com.concordia.soen6441riskgame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Class to store and control the information of a game. Only one game is
 * possible during runtime
 * 
 * 
 * @author Julian Beltran, Amit
 * @version 1.0
 * @since 1.0
 *
 */
public class RGGame extends Observable{

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
	 * Created to store the current game (only one game is possible).
	 */
	private String phase="";
	
	/**
	 * Created to store the status of the Attack Phase for a given player.
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
	 * This method is used to assure only one instance (only one game) is created
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
	 * This method is used to set the current phase of the game.
	 * 
	 * 
	 */
	void setPhase(String phase)
	{
		this.phase=phase;
	}
	
	/**
	 * This method returns the current phase of the game for a specific moment.
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

			//printing Countries data structure
			ArrayList<String> edgeList = countryItems.getEdges(vertex.get(k));
			System.out.print(vertex.get(k) + "->");
			for (int j = 0; j < edgeList.size(); j++) {
				System.out.print(edgeList.get(j) + " -> ");
			}
			System.out.println("");
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
		System.out.println(edges);
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
				System.out.println(vertex.get(k));
				System.out.println(numberOfArmiesDueContinentsAux);
				numberOfArmiesDueContinents = numberOfArmiesDueContinents + numberOfArmiesDueContinentsAux;
			}
		}

		return numberOfArmiesDueContinents;
	}

	/**
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
				ArrayList<String> edges = countryItems.getEdges(vertex.get(k));// continentItems
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
		initializeSetOfCards();
		players.initializeArmiesForReinforcementPhase();//allocating zero armies for every player
		players.initializePerformedActionsForEachPhase();//creating positions in players' data structure to store actions performed by players during play time
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to create the set of cards in the cardItems' data structure. A random type is assigned for every country in position 0. 
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
	void setupPhase(String selectedCountry, String currentPlayerName)
	{
		
		RGPlayer players=RGPlayer.getPlayers();
		((RGGame) game).setNumberOfArmies(1, selectedCountry);//adding one army to the map
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
		
		setChanged();
		notifyObservers(this);
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
	void reinforcementPhase(String selectedCountry, String currentPlayerName, String armiesToPlace)
	{
		
		RGPlayer players=RGPlayer.getPlayers();
		String totalArmiesAvailable=players.getNumberOfArmiesForReinforcement(currentPlayerName);
		game.setNumberOfArmies(Integer.valueOf(armiesToPlace), selectedCountry);//adding armies to the country
		players.subtractArmiesForReinforcementPhase(currentPlayerName,armiesToPlace);
		totalArmiesAvailable=players.getNumberOfArmiesForReinforcement(currentPlayerName);
			
		if(totalArmiesAvailable.contentEquals("0"))//all armies placed? Go to the next Phase
		{
			game.setPhase("Attack");
			cardGiven=false;
		}

		setChanged();
		notifyObservers(this);
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
	void fortificationPhase(String countryFrom, String countryTo, int armiesToMove)
	{
		RGPlayer players=RGPlayer.getPlayers();
		game.setNumberOfArmies(armiesToMove, countryFrom);
		game.setNumberOfArmies(-armiesToMove, countryTo);
		players.setNextTurn();
		game.setPhase("Reinforcement");
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to skip the Fortification Phase due to the player does not want to move any army.
	 * 
	 * 
	 */
	void fortificationPhaseNoMovements()
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
	 * This method is used to obtain the list of countries a given player can attack.
	 * 
	 * 
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
			System.out.println("currentino: ");
			System.out.println(currentCountry);
			//printing the player's turns
			ArrayList<String> verti = graph.getVertex();
			for(int j=0;j<verti.size();j++)
			{
				System.out.print(verti.get(j)+" -> ");
				ArrayList<String> edgi = graph.getEdges(verti.get(j));
				for(int l=0;l<edgi.size();l++)
				{
					System.out.print(edgi.get(l)+" -> ");
				}
				System.out.println("");
			}
			edgesCountryItems = countryItems.getEdges(currentCountry);
			
			if (!edgesCountryItems.get(3).contentEquals(currentPlayerName)) 
			{
				countriesDefender.add(currentCountry);
			}
		}
		return countriesDefender;
	}
	
	/**
	 * This method is used to obtain the list with the number of dice a player can use to attack.
	 * 
	 * 
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
	 * This method is used to obtain the list with the number of dice a player can use to attack.
	 * 
	 * 
	 * @return List with the number of dice a player can use to attack.
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
	 * 
	 */
	void attackPhaseModeDecision(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName)
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
				game.attackPhase(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);
			}
			if(Integer.valueOf(game.getArmies(selectedCountryAttacker))==1) 
			{
				RGPlayer players=RGPlayer.getPlayers();
				StringBuilder actionPerformed=new StringBuilder();
				actionPerformed.append("Not enough army to attack");
				actionPerformed.append("\n");
				
				//No more army to attack. Setting all out mode off
				game.allOutMode=false;
				
				//Storing performed actions 
				players.setActionsPerformed(actionPerformed, currentPlayerName, "attack");
			}
		}
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to process the actions taken by a player during the Attack Phase:
	 * 
	 * 
	 * @param selectedCountryAttacker Attacking country
	 * @param selectedCountryDefender Attacked country.
	 * @param selectedDiceAttacker Number of dice to attack.
	 * @param selectedDiceDefender Number of dice to defend.
	 * 
	 */
	void attackPhase(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName)
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
			//territory captured. Setting all out mode off
			game.allOutMode=false;

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

			//printing cards data structure
			ArrayList<String> verti = cardItems.getVertex();
			for (int j = 0; j < verti.size(); j++) {
				System.out.print(verti.get(j) + " -> ");
				ArrayList<String> edgi = cardItems.getEdges(verti.get(j));
				for (int k = 0; k < edgi.size(); k++) {
					System.out.print(edgi.get(k) + " -> ");
				}
				System.out.println("");
			}

			//current player controls all territories?
			ArrayList<String> vertex = countryItems.getVertex();
			int totalCountriesOwned=0;
			for (int k = 0; k < vertex.size(); k++) 
			{
				edges = countryItems.getEdges(vertex.get(k));// continentItems
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
	void attackPhaseNoMovements()
	{
		game.setPhase("Fortification");
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * This method is used to know if a player captured a territory and then show up a frame to let players move army to the new captured
	 * territory.
	 * 
	 * 
	 * @return Status.
	 * 
	 */
	String getAttackStatus()
	{
		return game.attackStatus;
	}
	
	/**
	 * This method is used to set the status "move" to know if a player has captured a territory. The word "move" is used to show up a frame to let 
	 * players move army to the new captured territory.
	 * 
	 * 
	 * @param Status.
	 * 
	 */
	void setAttackStatus(String status)
	{
		game.attackStatus=status;
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
	 * This method is used to move to the next phase due to there are no more available territories to attack.
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
		game.allOutMode=mode;
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
		return game.allOutMode;
	}
}
