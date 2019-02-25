package com.concordia.soen6441project;

import java.util.ArrayList;

public class RGPlayer extends RGFile{
	
	ArrayList<String> setOfPlayers = new ArrayList<String>();
	
	ArrayList<String> setPlayers(String players)
	{
		int index1=0;
		int index2;
		String player;
		String searchChar=",";
		
		while(!players.equals("")) 
		{
			index2=players.indexOf(searchChar);
			if (index2!=-1) 
			 { 
				player=players.substring(index1, index1+index2);
				setOfPlayers.add(player);
				players=","+players;
				players=players.replace(","+player+",", "");
			 }
			else
			{
				setOfPlayers.add(players);
				break;
			}
		}
		System.out.println(setOfPlayers);
		return setOfPlayers;
	}
}
