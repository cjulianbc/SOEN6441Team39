package com.concordia.soen6441riskgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Class to manage the file where a Risk� map is stored. The map format implemented for 
 * this application is similar to the �Conquest� game (http://www.windowsgames.co.uk/conquest.html). 
 * 
 * The �Conquest� format has three tags: [Map], [Continents], and [Territories]. This application only uses 
 * the tags [Continents] and [Territories].
 *
 * 
 * @author Julian Beltran, Aamrean, Abhishek.
 * @version 1.0
 * @since   1.0
 *
 */

public class RGFile {
	
	/**
	 * Created to store the file where a Risk� map is stored.
	 */
	private File file;
	/**
	 * Created to show the window where the user can open or save a file.
	 */
	private JFileChooser fileChooser=new JFileChooser();
	
	
	/**
	 * This method is used to create a 'File' object in order to access the 
	 * content of a file (Risk� map).
	 * 
	 */
	void openFile()
	{
		if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
		{	
			file=fileChooser.getSelectedFile();
		}
	}

	/**
	 * This method is used to extract the content of a tag from a file.
	 * Possible tags are [Continents] and [Territories].
	 * 
	 * 
	 * @param label Name of the tag.
	 * @return The content of the tag.
	 * 
	 */
	StringBuilder getContent(String label) throws FileNotFoundException
	{
		StringBuilder content=new StringBuilder();
		Scanner buffer=new Scanner(file);
		String actualLine;
		String endChar;
		while(buffer.hasNext())//file is not empty
		{
			actualLine=buffer.nextLine();
			if(actualLine.equals(label))//searching tag
			{
				while(buffer.hasNext())//tag is not empty
				{
					actualLine=buffer.nextLine();
					if(!actualLine.equals(""))
					{
						endChar=actualLine.substring(0, 1);
						if(endChar.equals("["))
							break;
						else
						{
							content.append(actualLine);//copying content of each line from file to a 'StringBuilder' variable
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
	
	/**
	 * This method is used to validate the correctness of a Risk� map.
	 * 
	 * Validations: 
	 * 
	 * 1) Tag [Continents] with at least one continent.
	 * 2) Tag [Territories] with at least one country.
	 * 3) In [Continents]. Every continent must have a numeric bonus army.
	 * 4) In [Territories]. Every country must have the correct format. This is, Name, X coordinate, Y coordinate, Continent, [set of adjacent 
	 * 	  countries separated by commas].
	 * 5) In [Territories]. Two countries cannot have the same numeric X and Y coordinates.
	 * 6) The number of continents in tag [Continents] must coincide with the number of continents in tag [Territories].
	 * 7) The name of continents in tag [Continents] must coincide with the name of continents in tag [Territories].
	 * 8) Every country must have at least one adjacent country.
	 * 9) Every country must define all its adjacencies.
	 * 
	 * 
	 * @param continents Set of the continents of the map.
	 * @param countries Set of the countries of the map.
	 * @return "true" if there is no errors, "false" if there is any error.
	 * 
	 */
	boolean validateMap(String continents, String countries) {
		if(continents.equals("")) 
		{
			System.out.println("Please enter continent details");
			JOptionPane.showMessageDialog(null, "Please enter continent details", "Alert Message", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(countries.equals("")) 
		{
			System.out.println("Please enter country details");
			JOptionPane.showMessageDialog(null, "Please enter country details", "Alert Message", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String[] continentsArray=continents.split("\n");
		String[] countriesArray=countries.split("\n");
		ArrayList<String> continentsList= new ArrayList<String>();
		ArrayList<String> continentsListValidate= new ArrayList<String>();
		ArrayList<String> countriesList= new ArrayList<String>();
		HashMap<String,ArrayList<String>> adjacencyMap= new HashMap<String,ArrayList<String>>();
		boolean flag=false;
		
		try 
		{
			for(int i=0;i<continentsArray.length;i++) 
			{
				if(continentsArray[i].split("=").length!=2) 	
				{
					System.out.println("The file is incorrect. Please check '=' in the continents");
					JOptionPane.showMessageDialog(null, "The file is incorrect. Please check '=' in the continents", "Alert Message", JOptionPane.WARNING_MESSAGE);
					flag=false;
					return flag;
				}
				else 
				{
				Integer test=Integer.parseInt(continentsArray[i].split("=")[1]);
				continentsList.add(continentsArray[i].split("=")[0]);
				}
			}
		}
		catch (NumberFormatException s) 
		{
			System.out.println("Bonus army should be number");
			JOptionPane.showMessageDialog(null, "Bonus army should be number", "Alert Message", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		for (String country : countriesArray) 
		{
			if(country.equals(""))
				continue;
			ArrayList<String> adjancies=new ArrayList<String>();
			String[] countryDetails=country.split(",");
			if(countryDetails.length<5) 
			{
				System.out.println("Please enter country details in correct format");
				JOptionPane.showMessageDialog(null, "Please enter country details in correct format", "Alert Message", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			countriesList.add(countryDetails[0]);
			if(!continentsListValidate.contains(countryDetails[3]))
				continentsListValidate.add(countryDetails[3]);
			adjacencyMap.put(countryDetails[0],adjancies);
			for(int i=4;i<countryDetails.length;i++) 
			{
				adjacencyMap.get(countryDetails[0]).add(countryDetails[i]);
			}
			
		}
		for(int i=0;i<countriesArray.length-1;i++) 
		{
			String [] countriesDetails=countriesArray[i].split(",");
			try 
			{
				if(countriesDetails[i].equals(""))
					continue;
				Integer test=Integer.parseInt(countriesDetails[1]);
				Integer test2=Integer.parseInt(countriesDetails[2]);
				for(int j=i+1;j<countriesArray.length;j++) 
				{
					if(countriesDetails[j].equals(""))
						continue;
					String [] countriesDetailsValidate=countriesArray[j].split(",");
					Integer test3=Integer.parseInt(countriesDetailsValidate[1]);
					Integer test4=Integer.parseInt(countriesDetailsValidate[2]);
					if(countriesDetails[1].equals(countriesDetailsValidate[1]) && countriesDetails[2].equals(countriesDetailsValidate[2])) 
					{
						System.out.println("Two countries cannot have the same coordiantes");
						JOptionPane.showMessageDialog(null, "Two countries cannot have the same coordiantes", "Alert Message", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
			}
			catch(NumberFormatException s) 
			{
				System.out.println("Coordinates should be numbers");
				JOptionPane.showMessageDialog(null, "Coordinates should be numbers", "Alert Message", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if(continentsList.size()!=continentsListValidate.size()) 
		{
			System.out.println("The number of continents is invalid");
			JOptionPane.showMessageDialog(null, "The number of continents is invalid", "Alert Message", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		for (String continent : continentsList) 
		{
			if(!continentsListValidate.contains(continent)) 
			{
				System.out.println("The names of continents are invalid!! Please check");
				JOptionPane.showMessageDialog(null, "The names of continents are invalid!! Please check", "Alert Message", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		for (String countrykey : adjacencyMap.keySet()) 
		{
			ArrayList<String> adjacencies=adjacencyMap.get(countrykey);
			for (String country : adjacencies) {
				if(!countriesList.contains(country)) 
				{
					System.out.println("The country in the adjacency list is not created");
					JOptionPane.showMessageDialog(null, "The country in the adjacency list is not created", "Alert Message", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		for (String countrykey : adjacencyMap.keySet()) 
		{
			ArrayList<String> adjacencies=adjacencyMap.get(countrykey);
			for (String adjacentCountry : adjacencies) 
			{
				if(!adjacencyMap.get(adjacentCountry).contains(countrykey)) 
				{
					System.out.println("The Connections should be both ways for countries");
					JOptionPane.showMessageDialog(null, "The Connections should be both ways for countries", "Alert Message", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		
		flag=isConnected(adjacencyMap, countriesList);
		return flag;
	}
	
	
	/**
	 * This method is used to validate if the graph is connected. The complete
	 * Risk� map must be connected.
	 * 
	 * 
	 * @param adjacencyMap Set of the map adjacencies.
	 * @param countries Set of map countries.
	 * @return "true" if it is connected, "false" if it is not connected.
	 * 
	 */
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
			 JOptionPane.showMessageDialog(null, "Graph is connected", "Alert Message", JOptionPane.WARNING_MESSAGE);
			 return true;
		 }
		 else {
			 System.out.println("Graph is not connected");
			 JOptionPane.showMessageDialog(null, "Graph is not connected", "Alert Message", JOptionPane.WARNING_MESSAGE);
			 return false; 
		 }
			 
		
	}
	
	/**
	 * Depth First Search algorithm to check if a graph is connected. 
	 * 
	 * 
	 * @param country Name of the country.
	 * @param visited ArrayList with the countries visited by the algorithm.
	 * @param adjacencyMap ArrayList with the set of adjacencies of the country.
	 * 
	 */
	void DFS(String country,ArrayList<String> visited,HashMap<String,ArrayList<String>> adjacencyMap) {
		visited.add(country);
		for (String adjacent : adjacencyMap.get(country)) {
			if(!visited.contains(adjacent)) {
				DFS(adjacent,visited,adjacencyMap);
			}
		}
	}
	
}
