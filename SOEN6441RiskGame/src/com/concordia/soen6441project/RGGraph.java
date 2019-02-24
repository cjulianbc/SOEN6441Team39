package com.concordia.soen6441project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RGGraph {
	
	private Map<String,ArrayList<String>> adjVertices;
	
	void addVertex(String vertex) 
	{
		adjVertices=new HashMap<String,ArrayList<String>>();
		ArrayList<String> edges = new ArrayList<String>();
		adjVertices.put(vertex, edges);
	}
	
	public void addEdge(String vertex1, String vertex2){
		(adjVertices.get(vertex1)).add(vertex2);
		//(adjVertices.get(vertex2)).add(vertex1);
	}

	
	void removeVertex(String vertex) 
	{
	    adjVertices.values()
	      .stream()
	      .map(e -> e.remove(vertex))
	      .collect(Collectors.toList());
	    adjVertices.remove(vertex);
	}
	
	void removeEdge(String vertex1, String vertex2) 
	{
	    ArrayList<String> eV1 = adjVertices.get(vertex1);
	    ArrayList<String> eV2 = adjVertices.get(vertex2);
	    if (eV1 != null)
	        eV1.remove(vertex2);
	    if (eV2 != null)
	        eV2.remove(vertex1);
	}
	
	public ArrayList<String> getEdges(String vertex){
		return new ArrayList<String>(adjVertices.get(vertex));
	}

}
