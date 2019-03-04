package com.concordia.soen6441riskgame;

import java.util.ArrayList;

public class RGPlayer{
	
	private ArrayList<String> setOfPlayers = new ArrayList<String>();
	private RGGraph playerItems=new RGGraph();
	private ArrayList<String> colors = new ArrayList<String>();
	
	RGPlayer()
	{
		colors.add("green");
		colors.add("magenta");
		colors.add("cyan");
		colors.add("pink");
		colors.add("orange");
		colors.add("blue");	
	}
	
	boolean validateNumberOfPLayers(String players)
	{
		String[] playersList = players.split(",");
		if (playersList.length>=2 && playersList.length<=6)
			return true;
		return false;
	}
	
			
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
				player=players.substring(index1, index1+index2);
				playerItems.addVertex(player);
				if(i==0)
					playerItems.addEdge(player, "1");
				else
					playerItems.addEdge(player, "0");
				playerItems.addEdge(player, colors.get(i));
				setOfPlayers.add(player);
				players=","+players;
				players=players.replace(","+player+",", "");
				
				ArrayList<String> edgeList = playerItems.getEdges(player);
				System.out.print(player+ "->");
				for(int k=0;k<edgeList.size();k++)
				{
					System.out.print(edgeList.get(k)+" -> ");
				}
				System.out.println("");
				i++;
				
			 }
			else
			{
				playerItems.addVertex(players);
				if(i==0)
					playerItems.addEdge(players, "1");
				else
					playerItems.addEdge(players, "0");
				playerItems.addEdge(players, colors.get(i));
				setOfPlayers.add(players);
				
				
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
	
	String getPlayerColor(String vertex)
	{
		ArrayList<String> edgeList = playerItems.getEdges(vertex);
		return edgeList.get(1);
	}
	
	void allocateArmies(RGGame game)
	{
		int numberOfArmiesSetup, numberOfArmiesAlreadyPlaced;
		ArrayList<String> vertex = playerItems.getVertex();
		numberOfArmiesSetup=vertex.size();
		numberOfArmiesSetup=50-(numberOfArmiesSetup*5);
		for(int k=0;k<vertex.size();k++)
		{
			numberOfArmiesAlreadyPlaced=game.getCurrentPlayerNumberOfCountries(vertex.get(k));
			playerItems.addEdge(vertex.get(k), String.valueOf(numberOfArmiesSetup-numberOfArmiesAlreadyPlaced));
		}
	}
	
	String getPlayerTurn()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		ArrayList<String> edgeList;
		String currentPlayerTurn="";
		for(int k=0;k<vertex.size();k++)
		{
			edgeList = playerItems.getEdges(vertex.get(k));
			if(edgeList.get(0)=="1")
			{
				currentPlayerTurn=vertex.get(k);
			}
		}
		return currentPlayerTurn;
	}
	
	String getCurrentArmies(String vertex)
	{
		ArrayList<String> edgeList = playerItems.getEdges(vertex);
		return edgeList.get(2);
	}
	
	void setNumberOfArmiesSetup(int numberOfArmiesToAdd, String vertex)
	{
		ArrayList<String> edges = playerItems.getEdges(vertex);
		String finalNumberOfArmiesToAdd=String.valueOf(Integer.valueOf(edges.get(2))+numberOfArmiesToAdd);
		edges.set(2, finalNumberOfArmiesToAdd);
		playerItems.setEdge(vertex, edges);
		edges = playerItems.getEdges(vertex);
		System.out.println(edges);
	}
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
				if(k==vertex.size())
				{
					k=0;
				}
				edges = playerItems.getEdges(vertex.get(k));
				edges.set(0, "1");
				playerItems.setEdge(vertex.get(k), edges);
				k=vertex.size();
			}
		}
	}
	
	int getSumArmiesSetup() 
	{
		int sumArmiesSetup=0;
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = playerItems.getEdges(vertex.get(k));
			sumArmiesSetup=sumArmiesSetup+Integer.valueOf(edges.get(2));
		}
		return sumArmiesSetup;
	}
	
	void initializeTurn()
	{
		ArrayList<String> vertex = playerItems.getVertex();
		for(int k=0;k<vertex.size();k++)
		{
			ArrayList<String> edges = playerItems.getEdges(vertex.get(k));
			if (k==0)
				edges.set(0, "1");
			else
				edges.set(0, "0");
		}
	}
		
}
