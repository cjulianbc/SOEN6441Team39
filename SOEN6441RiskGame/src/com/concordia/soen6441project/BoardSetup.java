package com.concordia.soen6441project;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BoardSetup {
	
	private int i;
	private int j;
	
	private boolean isLoaded;
	private boolean isAdjacent;
	
	private String name;
	
	private String[] continentsArray;
	private String[] adjacenciesArray;
	
	private ArrayList<Countries> countriesList;
	private ArrayList<Countries> adjacenciesList;
	private ArrayList<Countries> memberCountries;
	//private ArrayList<Countries> unoccupied;
	private ArrayList<Continents> continentsList;

    private HashMap<String, Countries> countriesMap;
    private HashMap<String, Continents> continentsMap;
	
	 public boolean loadBoard(ArrayList<String> countriesArray, ArrayList<String> adjacenciesFileArray) {
			
			isLoaded = false;
		
			countriesMap = new HashMap<String, Countries>();
			continentsMap = new HashMap<String, Continents>();
			
			// Populates countriesMap
			for (i = 0; i < countriesArray.size(); i++) {
			
				countriesMap.put(countriesArray.get(i), new Countries(countriesArray.get(i)));
				//System.out.println("new Country created");
			}
			
			countriesList = new ArrayList<Countries>(countriesMap.values());
			//System.out.println(countriesList);
			
			// Populates country adjacencies
			for (i = 0; i < adjacenciesFileArray.size(); i++) {
			
				System.out.println("Building new adjacenciesArray with " + adjacenciesFileArray.get(i) + "...");	
				
				// Splits each line into the individual country names
				adjacenciesArray = adjacenciesFileArray.get(i).split(",");
				
				System.out.println("Building new adjacenciesList...");
				
				adjacenciesList = new ArrayList<Countries>();
				
				// Creates a list of countries adjacent to the country in index 0
				for (j = 1; j < adjacenciesArray.length; j++) {
				
					System.out.println("Adding to adjacenciesList: " + adjacenciesArray[j]);
					
					adjacenciesList.add(countriesMap.get(adjacenciesArray[j]));
				
				}
				
				// Adds the adjacencies to the country
				countriesMap.get(adjacenciesArray[0]).addAdjacencies(adjacenciesList);
			}
			
			
			
			/*// Populates continents
			for (i = 0; i < continentsFileArray.length; i++) {
			// Splits the data of each line
				continentsArray = continentsFileArray[i].split(",");
				
				memberCountries = new ArrayList<Countries>();
					
				// Populates memberCountries, starting at index 2
				for (j = 2; j < continentsArray.length; j++) {
				
					System.out.println("Adding memberCountry to " + continentsArray[0] + ": " + continentsArray[j]);
					
					memberCountries.add(countriesMap.get(continentsArray[j]));
				}
				
				// Populates continents hash map
				continentsMap.put(continentsArray[0], new Continents(continentsArray[0], Integer.parseInt(continentsArray[1]), memberCountries));
			}
			*/
			// Sets isLoaded to true if successful
			if(isConnected(countriesMap,countriesList)) {
				System.out.println("Connected Graph");
				isLoaded = true;
			}
			else
			{
				System.out.println("This is not a connected graph");
			}
			return isLoaded;
		}
	 
	 public boolean isConnected(HashMap<String,Countries> countryMap, ArrayList<Countries> countryList) {
		 int flag=0;
		 ArrayList<Countries> visited=new ArrayList<Countries>();
		 
			 if(!visited.contains(countryList.get(0))) {
				 DFS(countryList.get(0),visited);
			 }
			
		
		 for (Countries countries : countryList) {
			if(visited.contains(countries)) {
				flag=1;
			}
			else {
				flag=0;
				break;
			}
		}
		 if(flag==1)
			 return true;
		 else
			 return false;
		 
	 }
	 public void DFS(Countries country,ArrayList<Countries> visited) {
		 visited.add(country);
		 for (Countries countries : visited) {
			System.out.println(countries.getName());
		}
		 for (Countries countries : country.getAdjacencies()) {
			 if(!visited.contains(countries))
			 DFS(countries,visited); 
		}
	 }

}
