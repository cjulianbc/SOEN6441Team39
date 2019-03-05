package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Class that implements a collection of lists not only to represent a graph but also to handle the information of continents, the game, and the players. 
 * From this perspective, same methods can be used to manipulate the information of any data structure.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
	public class RGGraph {
	
	private Map<String,ArrayList<String>> adjVertices=new HashMap<String,ArrayList<String>>();
	
	/**
	 * This method is used to create the key of the HashMap. 
	 * 
	 * Data Structures:
	 * 1) Map Data Structure: Key=Name of the country
	 * 2) Countries Data Structure: Key=Name of the country
	 * 3) Continents Data Structure: Key=Name of the continent
	 * 4) Players Data Structure: Key=Name of the player
	 * 
     *    
     * @param vertex Name of the key
     *   
	 */
	void addVertex(String vertex) 
	{
		ArrayList<String> edges = new ArrayList<String>();
		adjVertices.put(vertex, edges);
	}
	
	/**
	 * This method is used to create one value for a given key. The data structure can have more than one value. For that reason, the value of the key is an
	 * ArrayList 
	 * 
	 * Data Structures:
	 * 1) Map Data Structure: Values=[Set of adjacent countries]
	 * 2) Countries Data Structure: Values=[X Coordinate, Y Coordinate, Continent, Owner, Armies]
	 * 3) Continents Data Structure: Values=[bonus army number, Continent's counties]
	 * 4) Players Data Structure: Values=[Turn, Color, Armies to place in setup phase]
	 * 
     *    
     * @param vertex1 Name of the key
     * @param vertex2 New value to add
     *   
	 */
	void addEdge(String vertex1, String vertex2){
		(adjVertices.get(vertex1)).add(vertex2);
	}
	
	/**
	 * This method is used to replace a specific value of a given key
	 * 
     *    
     * @param vertex Name of the key
     * @param edges Complete set of values to replace
     *   
	 */
	void setEdge(String vertex, ArrayList<String> edges){
		adjVertices.replace(vertex,edges);
	}
	
	/**
	 * This method returns the set of values of a given key
	 * 
     *    
     * @param vertex Name of the key
     * @return Set of values of a given key  
     *   
	 */
	ArrayList<String> getEdges(String vertex){
		return new ArrayList<String>(adjVertices.get(vertex));
	}
	
	/**
	 * This method returns the set of keys of a given data structure
	 * 
     *    
     * @return Set of keys of a given data structure
     *   
	 */
	ArrayList<String> getVertex(){
		return new ArrayList<String>(adjVertices.keySet());
	}

}
