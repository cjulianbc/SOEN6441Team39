package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Class that implements a collection of lists not only to represent a graph but also to handle the information of continents, cards, 
 * counties, the game, and the players. From this perspective, same methods can be used to manage the information of any data structure.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
	public class RGGraph {
		
	/**
	 * Created to store any HashMap data structure. 
	 */
	private Map<String,ArrayList<String>> adjVertices=new HashMap<String,ArrayList<String>>();
	
	/**
	 * This method is used to create the key of the HashMap. 
	 * 
	 * Data Structures:
	 * 
	 * 1) Map Data Structure: Key=Name of the country.
	 * 2) Countries Data Structure: Key=Name of the country.
	 * 3) Continents Data Structure: Key=Name of the continent.
	 * 4) Players Data Structure: Key=Name of the player.
	 * 5) Cards Data Structure: Key=Name of the country.
	 * 
     *    
     * @param vertex Name of the key.
     *   
	 */
	void addVertex(String vertex) 
	{
		ArrayList<String> edges = new ArrayList<String>();
		adjVertices.put(vertex, edges);
	}
	
	/**
	 * This method is used to delete a key of the HashMap.
	 * 
	 *  * Data Structures:
	 * 
	 * 1) Map Data Structure: Key=Name of the country.
	 * 2) Countries Data Structure: Key=Name of the country.
	 * 3) Continents Data Structure: Key=Name of the continent.
	 * 4) Players Data Structure: Key=Name of the player.
	 * 5) Cards Data Structure: Key=Name of the country.
	 * 
	 * 
     * @param vertex Name of the key.
     *   
	 */
	void deleteVertex(String vertex) 
	{
		adjVertices.remove(vertex);
	}
	
	/**
	 * This method is used to create one value for a given key. The data structure can have more than one value. For that reason, the value of the key is an
	 * ArrayList.
	 * 
	 * Data Structures:
	 * 1) Map Data Structure: Values=[set of adjacent countries].
	 * 2) Countries Data Structure: Values=[X coordinate, Y coordinate, continent, owner, number of armies placed in the country].
	 * 3) Continents Data Structure: Values=[bonus army number, country 1, country 2, ... , country n].
	 * 4) Players Data Structure: Values=[turn, color, armies to place in setup phase, number of cards type 1, number of cards type 2, number of cards type 3,
	 * 									  number of armies left for reinforcement phase, list of actions performed for attack phase, list of actions performed 
	 * 									  for setup phase, list of actions performed for reinforcement phase, list of actions performed for fortification phase]
     * 5) Cards Data Structure: Values=[type of card, owner of the card].   
     *    
     * @param vertex1 Name of the key.
     * @param vertex2 New value to add.
     *   
	 */
	void addEdge(String vertex1, String vertex2){
		(adjVertices.get(vertex1)).add(vertex2);
	}
	
	/**
	 * This method is used to replace a specific value of a given key.
	 * 
     *    
     * @param vertex Name of the key.
     * @param edges Complete set of values to replace.
     *   
	 */
	void setEdge(String vertex, ArrayList<String> edges){
		adjVertices.replace(vertex,edges);
	}
	
	/**
	 * This method returns the set of values of a given key.
	 * 
     *    
     * @param vertex Name of the key.
     * @return Set of values of a given key.
     *   
	 */
	ArrayList<String> getEdges(String vertex){
		return new ArrayList<String>(adjVertices.get(vertex));
	}
	
	/**
	 * This method returns the set of keys of a given data structure.
	 * 
     *    
     * @return Set of keys of a given data structure.
     *   
	 */
	ArrayList<String> getVertex(){
		return new ArrayList<String>(adjVertices.keySet());
	}

}
