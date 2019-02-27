package com.concordia.soen6441project;

import java.util.ArrayList;

public class RGPlayer{
	
	ArrayList<String> setOfPlayers = new ArrayList<String>();
	RGGraph playerItems=new RGGraph();
	ArrayList<String> colors = new ArrayList<String>();
	
	RGPlayer()
	{
		colors.add("green");
		colors.add("magenta");
		colors.add("cyan");
		colors.add("pink");
		colors.add("orange");
		colors.add("blue");	
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
}
