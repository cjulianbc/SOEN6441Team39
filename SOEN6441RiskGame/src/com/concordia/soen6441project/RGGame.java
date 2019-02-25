package com.concordia.soen6441project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RGGame {
	RGGraph graph=new RGGraph();
	RGGraph countryItems=new RGGraph();
	
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

}
