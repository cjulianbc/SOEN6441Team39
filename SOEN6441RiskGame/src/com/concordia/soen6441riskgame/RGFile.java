package com.concordia.soen6441riskgame;

import java.io.File;
import java.io.FileNotFoundException;
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
		if(continents.equals("")) {
			System.out.println("Please enter continent details");
			return false;
		}
		if(countries.equals("")) {
			System.out.println("Please enter country details");
			return false;
		}
		String[] continentsArray=continents.split("\n");
		String[] countriesArray=countries.split("\n");
		ArrayList<String> continentsList= new ArrayList<String>();
		ArrayList<String> continentsListValidate= new ArrayList<String>();
		ArrayList<String> countriesList= new ArrayList<String>();
		HashMap<String,ArrayList<String>> adjacencyMap= new HashMap<String,ArrayList<String>>();
		boolean flag=false;
		
		try {
		for(int i=0;i<continentsArray.length;i++) {
			if(continentsArray[i].split("=").length!=2) {
				System.out.println("The file is incorrect. Please check '=' in the continents");
				flag=false;
				return flag;
			}
			else {
				Integer test=Integer.parseInt(continentsArray[i].split("=")[1]);
				continentsList.add(continentsArray[i].split("=")[0]);
			}
		}
		}
		catch (NumberFormatException s) {
			System.out.println("Bonus Army should be number");
			return false;
		}
		for (String country : countriesArray) {
			if(country.equals(""))
				continue;
			ArrayList<String> adjancies=new ArrayList<String>();
			String[] countryDetails=country.split(",");
			if(countryDetails.length<5) {
				System.out.println("Please enter country details in correct format");
				return false;
			}
			countriesList.add(countryDetails[0]);
			if(!continentsListValidate.contains(countryDetails[3]))
				continentsListValidate.add(countryDetails[3]);
			adjacencyMap.put(countryDetails[0],adjancies);
			for(int i=4;i<countryDetails.length;i++) {
				adjacencyMap.get(countryDetails[0]).add(countryDetails[i]);
			}
			
		}
		for(int i=0;i<countriesArray.length-1;i++) {
			String [] countriesDetails=countriesArray[i].split(",");
			try {
				Integer test=Integer.parseInt(countriesDetails[1]);
				Integer test2=Integer.parseInt(countriesDetails[2]);
						for(int j=i+1;j<countriesArray.length;j++) {
							String [] countriesDetailsValidate=countriesArray[j].split(",");
							Integer test3=Integer.parseInt(countriesDetailsValidate[1]);
							Integer test4=Integer.parseInt(countriesDetailsValidate[2]);
							if(countriesDetails[1].equals(countriesDetailsValidate[1]) && countriesDetails[2].equals(countriesDetailsValidate[2])) {
								System.out.println("Two countries cannot have the same co-ordiantes");
								return false;
							}
						}
			}
			catch(NumberFormatException s) {
				System.out.println("Coordinates should be numbers");
				return false;
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
		
		for (String countrykey : adjacencyMap.keySet()) {
			ArrayList<String> adjacencies=adjacencyMap.get(countrykey);
			for (String country : adjacencies) {
				if(!countriesList.contains(country)) {
					System.out.println("The country in the adjacency list is not created");
					return false;
				}
			}
		}
		for (String countrykey : adjacencyMap.keySet()) {
			ArrayList<String> adjacencies=adjacencyMap.get(countrykey);
			for (String adjacentCountry : adjacencies) {
				if(!adjacencyMap.get(adjacentCountry).contains(countrykey)) {
					System.out.println("The Connections should be both ways for countries");
					return false;
				}
			}
		}
		
		
		flag=isConnected(adjacencyMap, countriesList);
		
		
		
		return flag;
		
	}
	
	boolean isConnected(HashMap<String,ArrayList<String>> adjacencyMap,ArrayList<String> countries){
		ArrayList<String> visited=new ArrayList<String>();
		int flag=0;
		if(!visited.contains(countries.get(0))) {
			DFS(countries.get(0),visited,adjacencyMap);
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
	
	void DFS(String country,ArrayList<String> visited,HashMap<String,ArrayList<String>> adjacencyMap) {
		visited.add(country);
		for (String adjacent : adjacencyMap.get(country)) {
			if(!visited.contains(adjacent)) {
				DFS(adjacent,visited,adjacencyMap);
			}
		}
	}
	
}
