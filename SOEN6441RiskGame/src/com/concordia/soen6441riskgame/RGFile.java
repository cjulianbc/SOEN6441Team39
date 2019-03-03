package com.concordia.soen6441riskgame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class RGFile {
	private File file;
	private JFileChooser fileChooser=new JFileChooser();
	
	void openFile()
	{
		if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
		{	
			file=fileChooser.getSelectedFile();
		}
	}
	
	void saveFile(String continents, String countries) throws IOException
	{
		if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
		{	
			file=fileChooser.getSelectedFile();
			//FileWriter fileWriter = new FileWriter(file);
		   // PrintWriter printWriter = new PrintWriter(fileWriter);
			BufferedWriter outFile = new BufferedWriter(new FileWriter(file));
			outFile.write("[Continents]");
			//outFile.write(continents);
			outFile.write("[Territories]\n");
			outFile.write(countries);
			outFile.close();
		}
		
	}

	StringBuilder getContent(String label) throws FileNotFoundException
	{
		StringBuilder content=new StringBuilder();
		Scanner buffer=new Scanner(file);
		String actualLine;
		String endChar;
		while(buffer.hasNext())
		{
			actualLine=buffer.nextLine();
			if(actualLine.equals(label))
			{
				while(buffer.hasNext())
				{
					actualLine=buffer.nextLine();
					if(!actualLine.equals(""))
					{
						endChar=actualLine.substring(0, 1);
						if(endChar.equals("["))
							break;
						else
						{
							content.append(actualLine);
							content.append("\n");
						}
					}
					else
					{
						content.append("\n");
					}
				}
				break;
			}
		}
		buffer.close();
		return content;
	}
	
	boolean validateMap(String continents, String countries) {
		
		String[] continentsArray=continents.split("\n");
		String[] contriesArray=countries.split("\n");
		ArrayList<String> continentsList= new ArrayList<String>();
		ArrayList<String> continentsListValidate= new ArrayList<String>();
		ArrayList<String> countriesList= new ArrayList<String>();
		HashMap<String,ArrayList<String>> AdjacencyMap= new HashMap<String,ArrayList<String>>();
		boolean flag=false;
		
		for(int i=0;i<continentsArray.length;i++) {
			if(continentsArray[i].split("=").length!=2) {
				System.out.println("The file is incorrect. Please check '=' in the continents");
				flag=false;
				return flag;
			}
			else {
				System.out.println(continentsArray[i].split("=")[0]);
				continentsList.add(continentsArray[i].split("=")[0]);
			}
		}
		
		for (String country : contriesArray) {
			if(country.equals(""))
				continue;
			ArrayList<String> adjancies=new ArrayList<String>();
			String[] countryDetails=country.split(",");
			countriesList.add(countryDetails[0]);
			System.out.println(countryDetails[0]);
			if(!continentsListValidate.contains(countryDetails[3]))
				continentsListValidate.add(countryDetails[3]);
			AdjacencyMap.put(countryDetails[0],adjancies);
			for(int i=4;i<countryDetails.length;i++) 
			{
				AdjacencyMap.get(countryDetails[0]).add(countryDetails[i]);
			}
			
		}
		
		if(continentsList.size()!=continentsListValidate.size()) {
			System.out.println("The number of continents is invalid");
			return false;
		}
		
		for (String continent : continentsList) {
			if(!continentsListValidate.contains(continent)) {
				System.out.println("The names of continents is invalid!! Please check");
				return false;
			}
		}
		
		for (String countrykey : AdjacencyMap.keySet()) {
			ArrayList<String> adjacencies=AdjacencyMap.get(countrykey);
			for (String country : adjacencies) {
				if(!countriesList.contains(country)) {
					System.out.println("The country in the adjacency list is not created");
					return false;
				}
			}
		}
		for (String countrykey : AdjacencyMap.keySet()) {
			ArrayList<String> adjacencies=AdjacencyMap.get(countrykey);
			for (String adjacentCountry : adjacencies) {
				if(!AdjacencyMap.get(adjacentCountry).contains(countrykey)) {
					System.out.println(adjacentCountry+" "+countrykey);
					System.out.println("The Connections should be both ways for countries");
					return false;
				}
			}
		}
		
		
		flag=isConnected(AdjacencyMap, countriesList);
		
		
		
		return flag;
		
	}
	
	boolean isConnected(HashMap<String,ArrayList<String>> AdjacencyMap,ArrayList<String> countries){
		ArrayList<String> visited=new ArrayList<String>();
		int flag=0;
		if(!visited.contains(countries.get(0))) {
			DFS(countries.get(0),visited,AdjacencyMap);
		}
		for (String country : countries) {
			if(visited.contains(country)) {
				flag=1;
			}
			else {
				flag=0;
				break;
			}
		}
		 if(flag==1) {
			 System.out.println("Graph is connected");
			 return true;
		 }
		 else {
			 System.out.println("Graph is not connected");
			 return false; 
		 }
			 
		
	}
	
	void DFS(String country,ArrayList<String> visited,HashMap<String,ArrayList<String>> AdjacencyMap) {
		visited.add(country);
		for (String adjacent : AdjacencyMap.get(country)) {
			if(!visited.contains(adjacent)) {
				DFS(adjacent,visited,AdjacencyMap);
			}
		}
	}
	
}
