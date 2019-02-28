package com.concordia.soen6441project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RGGame {
	RGGraph graph=new RGGraph();
	RGGraph countryItems=new RGGraph();
	RGGraph gameItems=new RGGraph();
	
	RGGame()
	{
		gameItems.addVertex("Setup");
		gameItems.addEdge("Setup", "1");
		gameItems.addVertex("Reinforcement");
		gameItems.addEdge("Reinforcement", "0");
		gameItems.addVertex("Attack");
		gameItems.addEdge("Attack", "0");
		gameItems.addVertex("Fortification");
		gameItems.addEdge("Fortification", "0");
		
	}
	
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
	
	String getXCoord(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(0);
	}
	
	String getYCoord(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(1);
	}
	
	String getOwner(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(3);
	}
	
	String getArmies(String vertex)
	{
		ArrayList<String> edgeList = countryItems.getEdges(vertex);
		return edgeList.get(4);
	}
	
	ArrayList<String> getVertex()
	{
		ArrayList<String> vertex = countryItems.getVertex();
		return vertex;
	}
	
	ArrayList<String> getEdges(String vertex)
	{
		ArrayList<String> edges = graph.getEdges(vertex);
		return edges;
	}
	
	String getPhase()
	{
		ArrayList<String> vertex = gameItems.getVertex();
		String phase;
		String currentPhase="";
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = gameItems.getEdges(vertex.get(k));
			phase=edges.get(0);
			if(phase=="1")
			{
				currentPhase=vertex.get(k);
			}
		}
		return currentPhase;		
	}
	
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
	
	void setNumberOfArmies(int numberOfArmiesToAdd, String vertex)
	{
		ArrayList<String> edges = countryItems.getEdges(vertex);
		String finalNumberOfArmiesToAdd=String.valueOf(Integer.valueOf(edges.get(4))+numberOfArmiesToAdd);
		edges.set(4, finalNumberOfArmiesToAdd);
		countryItems.setEdge(vertex, edges);
		edges = countryItems.getEdges(vertex);
		System.out.println(edges);
	}
	

	
}
