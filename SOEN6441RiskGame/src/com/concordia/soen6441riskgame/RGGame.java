package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to store and control the information of a game. Only one game is possible 
 * in runtime
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGGame {
	private RGGraph graph=new RGGraph();
	private RGGraph countryItems=new RGGraph();
	private RGGraph continentItems=new RGGraph();
	
	/**
	 * This method is used to create two data structures: 
	 * 
	 * 1) Map Data Structure: This is a HashMap data structure that contains all the information related to the countries and their 
     *    adjacencies with other countries. The key value is the name of a country (String), and the value of the key is an ArrayList (String) that 
     *    contains all the adjacent countries.
     * 2) Countries Data Structure: This is a HashMap data structure that contains all the information associated to the current game. 
     *    The key value is the name of a country (String), and the value of the key is an ArrayList (String) that contains all the information 
     *    of the county: Coordinate X in the map, coordinate y in the map, continent, owner, and number of armies available.
     *    
     *    
     * @param content Complete content of the tag [Territories] 
     *   
	 */
	void createGraph(StringBuilder content)
	{
		Scanner buffer = new Scanner(content.toString());
		String searchChar=",";
		int index1=0;
		int index2;
		int i,j;
		String edge;
		String vertex="";
		i=0;
		while (buffer.hasNextLine())
		{
			String actualLine = buffer.nextLine();
			j=0;
			while(!actualLine.equals("")) 
			{
				 index2=actualLine.indexOf(searchChar);
				 if (index2!=-1) 
				 { 
					if (j<4)
					{
						if(j==0)
						{
							vertex=actualLine.substring(index1, index1+index2);
							graph.addVertex(vertex);
							countryItems.addVertex(vertex);
							actualLine=","+actualLine;
							actualLine=actualLine.replace(","+vertex+",", "");
						}
						else
						{
							edge=actualLine.substring(index1, index1+index2);
							countryItems.addEdge(vertex, edge);
							actualLine=","+actualLine;
							actualLine=actualLine.replace(","+edge+",", "");
						}
						j++;
					}
					else
					{
						edge=actualLine.substring(index1, index1+index2);
						graph.addEdge(vertex, edge);
						actualLine=","+actualLine;
						actualLine=actualLine.replace(","+edge+",", "");
					}
				 }
				 else
				 {
					 graph.addEdge(vertex, actualLine);
					 break;
				 }
			}
			if(!actualLine.equals(""))
			{
				i++;
				ArrayList<String> edgeList = graph.getEdges(vertex);
				System.out.print(vertex+ "->");
				for(int k=0;k<edgeList.size();k++)
				{
					System.out.print(edgeList.get(k)+" -> ");
				}
				System.out.println("");
				
				edgeList = countryItems.getEdges(vertex);
				System.out.print(vertex+ "->");
				for(int k=0;k<edgeList.size();k++)
				{
					System.out.print(edgeList.get(k)+" -> ");
				}
				System.out.println("");
			}
			
		}
		buffer.close();
	}
	
	/**
	 * This method is used to create one data structures: 
	 * 
	 * 1) Continents Data Structure: This is a HashMap data structure that contains all the information associated to the continents of the map. 
	 *    The key value is the name of a continent (String), and the value of the key is an ArrayList (String) that contains all the information of 
	 *    the continent: continent bonus army, and all the countries that belong to that continent.
     *    
     *    
     * @param content Complete content of the tag [Continents] 
     *   
	 */
	void createContinents(StringBuilder content)
	{
		Scanner buffer = new Scanner(content.toString());
		String searchChar="=";
		int index2;
		String edge;
		String vertex="";
		String bonusArmies="";
		//Creating HashMap: Key=name of the continent, Value= ArrayList with just one value: The number of bonus armies.
		while (buffer.hasNextLine())
		{
			String actualLine = buffer.nextLine();
			if(!actualLine.equals(""))
			{
				if(!actualLine.substring(0,1).equals("["))
				{
					index2=actualLine.indexOf(searchChar);
					vertex=actualLine.substring(0,index2);
					continentItems.addVertex(vertex);
					bonusArmies=actualLine.substring(index2+1,actualLine.length());
					continentItems.addEdge(vertex, bonusArmies);
				}
				else 
					break;
			}
			else
				break;
		}
		
		//Adding countries to each continent.
		ArrayList<String> verticesCountry = countryItems.getVertex();
		for(int k=0;k<verticesCountry.size();k++)
		{
			ArrayList<String> edgeList = countryItems.getEdges(verticesCountry.get(k));
			continentItems.addEdge(edgeList.get(2), verticesCountry.get(k));
		}
		ArrayList<String> verti = continentItems.getVertex();
		for(int j=0;j<verti.size();j++)
		{
			System.out.print(verti.get(j)+" -> ");
			ArrayList<String> edgi = continentItems.getEdges(verti.get(j));
			for(int k=0;k<edgi.size();k++)
			{
				System.out.print(edgi.get(k)+" -> ");
			}
			System.out.println("");
		}
		
	}
	
	
	/**
	 * This method is used to assign each country a random player 
	 * 
     *    
     * @param players List of Players (names) 
     *   
	 */
	void assignCountries(ArrayList<String> players)
	{
		ArrayList<String> vertex = graph.getVertex();
		ArrayList<Integer> numbers = new ArrayList<Integer>(); 
		int randomPlayer,i=0;
		for(int k=0;k<vertex.size();k++)
		{
			  
			while (numbers.size() < players.size()) 
			{
				randomPlayer=new Random().nextInt(players.size());
			    if (!numbers.contains(randomPlayer)) 
			    {
			        numbers.add(randomPlayer);
			    }
			}
			
			if(i<players.size())
			{
				String player=players.get(numbers.get(i));
				countryItems.addEdge(vertex.get(k), player);
				countryItems.addEdge(vertex.get(k), "1");
				i++;
				if(i==players.size())
				{
					i=0;
					numbers.clear();
				}
			}
		
			
			ArrayList<String> edgeList = countryItems.getEdges(vertex.get(k));
			System.out.print(vertex.get(k)+ "->");
			for(int j=0;j<edgeList.size();j++)
			{
				System.out.print(edgeList.get(j)+" -> ");
			}
			System.out.println("");
		}	
	}
	
	/**
	 * This method is used to obtain the X Coordinate of a country 
	 * 
     *    
     * @param vertex Name of the country 
     * @return X coordinate of a given country
     *   
	 */
	String getXCoord(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(0);
	}
	
	/**
	 * This method is used to obtain the Y Coordinate of a country 
	 * 
     *    
     * @param vertex Name of the country 
     * @return Y coordinate of a given country 
     *   
	 */
	String getYCoord(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(1);
	}
	
	/**
	 * This method is used to obtain the owner of a country 
	 * 
     *    
     * @param vertex Name of the country 
     * @return Owner of a country 
     *   
	 */
	String getOwner(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(3);
	}
	
	/**
	 * This method is used to obtain the current number of armies placed in a country  
	 * 
     *    
     * @param vertex Name of the country 
     * @return Number of armies placed in a country 
     *   
	 */
	String getArmies(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(4);
	}
	
	/**
	 * This method is used to obtain a set of countries of the complete Risk® map  
	 * 
	 * @return Set of countries of the complete Risk® map
     *   
	 */
	ArrayList<String> getVertex()
	{
		ArrayList<String> vertex = countryItems.getVertex();
		return vertex;
	}
	
	/**
	 * This method is used to obtain a set of adjacent countries of a specific country
	 * 
     *    
     * @param vertex Name of the country 
     * @return Set of adjacent countries of a specific country
     *   
	 */
	ArrayList<String> getEdges(String vertex)
	{
		ArrayList<String> edges = graph.getEdges(vertex);
		return edges;
	}
	
	/**
	 * This method is used to obtain the list of countries a player owns
	 * 
     *    
     * @param player Name of the player 
     * @return List of countries a player owns
     *   
	 */
	ArrayList<String> getCurrentPlayerCountries(String player)
	{
		ArrayList<String> currentPlayerCountries=new ArrayList<String>();
		ArrayList<String> vertex = countryItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if(edges.get(3)==player)
			{
				currentPlayerCountries.add(vertex.get(k));
			}
		}
		return currentPlayerCountries;
	}
	
	/**
	 * This method is used to add armies to a specific country
	 * 
     *    
     * @param numberOfArmiesToAdd Number of armies to add
     * @param vertex Name of the country 
     *   
	 */
	void setNumberOfArmies(int numberOfArmiesToAdd, String vertex)
	{
		ArrayList<String> edges = countryItems.getEdges(vertex);
		String finalNumberOfArmiesToAdd=String.valueOf(Integer.valueOf(edges.get(4))+numberOfArmiesToAdd);
		edges.set(4, finalNumberOfArmiesToAdd);
		countryItems.setEdge(vertex, edges);
		edges = countryItems.getEdges(vertex);
		System.out.println(edges);
	}
	
	/**
	 * This method is used to obtain the number of countries a player owns
	 * 
     *    
     * @param player Name of the player 
     * @return Number of countries a player owns
     *   
	 */
	int getCurrentPlayerNumberOfCountries(String player)
	{
		int currentPlayerNumberOfCountries=0;
		ArrayList<String> vertex = countryItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = countryItems.getEdges(vertex.get(k));
			if(edges.get(3)==player)
			{
				currentPlayerNumberOfCountries++;
			}
		}
		return currentPlayerNumberOfCountries;
	}
	
	/**
	 * For Reinforcement Phase: This method is used to obtain the number of armies a player has the right to place on his/her countries.
	 * This number is calculated according to the number of countries a player owns.
	 * 
     *    
     * @param currentPlayerNumberOfCountries Number of countries a player owns 
     * @return Number of armies a player has the right to place on his/her countries
     *   
	 */
	int getNumberOfArmiesDueTerritories(int currentPlayerNumberOfCountries)
	{
		if(currentPlayerNumberOfCountries>=9)
			currentPlayerNumberOfCountries=currentPlayerNumberOfCountries/3;
		else
			currentPlayerNumberOfCountries=3;
		return currentPlayerNumberOfCountries;
	}
	
	/**
	 * For Reinforcement Phase: This method is used to obtain the number of armies a player has the right to place on his/her countries.
	 * This number is calculated according to the number of continents a player owns.
	 * 
     *    
     * @param currentPlayerCountries List of countries a player owns 
     * @return Number of armies a player has the right to place on his/her countries
     *   
	 */
	int getNumberOfArmiesDueContinents(ArrayList<String> currentPlayerCountries)
	{
		int numberOfArmiesDueContinents=0;
		int numberOfArmiesDueContinentsAux;
		ArrayList<String> vertex = continentItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = continentItems.getEdges(vertex.get(k));
			numberOfArmiesDueContinentsAux=(Integer.valueOf(edges.get(0)));
			edges.remove(0);
			if(currentPlayerCountries.containsAll(edges))
			{
				System.out.println(vertex.get(k));
				System.out.println(numberOfArmiesDueContinentsAux);
				numberOfArmiesDueContinents=numberOfArmiesDueContinents+numberOfArmiesDueContinentsAux;
			}
		}
		
		return numberOfArmiesDueContinents;
	}

	/**
	 * This method is used to obtain all the adjacent countries of a given country for a given player
	 * 
	 * 
	 * @param   player Name of the player 
	 * @param   country Name of the country
	 * @return All the adjacent countries of a given country for a given player
	 */
	ArrayList<String> getCurrentPlayerAdjacentCountries(String player, String country)
	{
		ArrayList<String> currentPlayerAdjacentCountries = new ArrayList<String>();
		ArrayList<String> currentPlayerCountries=getCurrentPlayerCountries(player);
		ArrayList<String> adjacentcountries=getEdges(country);
		
		for (String adcountry : adjacentcountries) {
			
			for (String pcountry : currentPlayerCountries) {
				if(adcountry.equals(pcountry)) {
					System.out.println(adcountry);
					currentPlayerAdjacentCountries.add(adcountry);}
				
			}
			
		}
		return currentPlayerAdjacentCountries;
	}
	
}
