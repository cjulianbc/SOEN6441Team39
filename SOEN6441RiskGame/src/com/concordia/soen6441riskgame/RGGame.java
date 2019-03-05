package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

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
public class RGGame {

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
	 * This method is used to obtain a set of countries of the complete Risk® map.
	 * 
	 * @return Set of countries of the complete Risk® map.
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
	ArrayList<String> getCurrentPlayerCountries(String player) {
		ArrayList<String> currentPlayerCountries = new ArrayList<String>();
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if (edges.get(3) == player) {
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
	void setNumberOfArmies(int numberOfArmiesToAdd, String vertex) {
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
	int getCurrentPlayerNumberOfCountries(String player) {
		int currentPlayerNumberOfCountries = 0;
		ArrayList<String> vertex = countryItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if (edges.get(3) == player) {
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
	int getNumberOfArmiesDueTerritories(int currentPlayerNumberOfCountries) {
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
	int getNumberOfArmiesDueContinents(ArrayList<String> currentPlayerCountries) {
		int numberOfArmiesDueContinents = 0;
		int numberOfArmiesDueContinentsAux;
		ArrayList<String> vertex = continentItems.getVertex();
		for (int k = 0; k < vertex.size(); k++) {
			ArrayList<String> edges = continentItems.getEdges(vertex.get(k));
			numberOfArmiesDueContinentsAux = (Integer.valueOf(edges.get(0)));
			edges.remove(0);
			if (currentPlayerCountries.containsAll(edges)) {
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
		// currentPlayersLinkedCountries store all the counties and return at th end
		ArrayList<String> currentPlayersLinkedCountries = new ArrayList<String>();

		// queue used to mark if visited or not
		LinkedList<String> queue = new LinkedList<String>();

		queue.add(country);

		while (!queue.isEmpty()) {
			country = queue.poll();
			ArrayList<String> adjacentcountries = getEdges(country);

			for (int k = 0; k < adjacentcountries.size(); k++) {
				String adcountry = adjacentcountries.get(k);

				if (getPlayersName(adcountry).equals(player) && !currentPlayersLinkedCountries.contains(adcountry)) {

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
}
