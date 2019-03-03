package com.concordia.soen6441riskgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RGGraph {
	
	private Map<String,ArrayList<String>> adjVertices=new HashMap<String,ArrayList<String>>();
	
	void addVertex(String vertex) 
	{
		ArrayList<String> edges = new ArrayList<String>();
		adjVertices.put(vertex, edges);
	}
	
	void addEdge(String vertex1, String vertex2){
		(adjVertices.get(vertex1)).add(vertex2);
	}
	
	void setEdge(String vertex, ArrayList<String> edges){
		adjVertices.replace(vertex,edges);
	}
	
	ArrayList<String> getEdges(String vertex){
		return new ArrayList<String>(adjVertices.get(vertex));
	}
	
	ArrayList<String> getVertex(){
		return new ArrayList<String>(adjVertices.keySet());
	}

}
