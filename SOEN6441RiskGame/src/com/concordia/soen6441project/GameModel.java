package com.concordia.soen6441project;

import java.io.*;
import java.util.ArrayList;

public class GameModel {
	
	private boolean isLoaded;
	private BoardSetup board;
	private StringBuilder stringBuilder;
	private String line;
	private BufferedReader reader;
	//private String input;
	private ArrayList<String> countriesArray;
	private String[] testArray;
	private String[] testArray1;
	private ArrayList<String> adjacenciesArray=new ArrayList<String>();
	
	
	
public boolean initializeGame(String input) {
		
		isLoaded = false;
		/*this.playerNames = playerNames;
		this.playerTypes = playerTypes;*/
		board = new BoardSetup();
		
		// Reads countries file
			/*reader = new BufferedReader(new FileReader("countries.txt"));			
			stringBuilder = new StringBuilder();
			
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				if(line!=null)
					stringBuilder.append("\n");
			}
			input = stringBuilder.toString();
			*/
			System.out.println("Input from input file" +  ": " + input);
			// Splits the text in the file into an array
			testArray = input.split("]");
			//System.out.println(testArray[3]);
			//testArray1=testArray[1].split("[Territories]");
			testArray1=testArray[3].split("\n");
			int k=0;
			for (int i=1;i<testArray1.length;i++) {
				System.out.println(testArray1[i]);
				adjacenciesArray.add(testArray1[i]);
				//System.out.println(adjacenciesArray[k])
			}//System.out.println(adjacenciesArray);
			System.out.println("Loading board...");
			
			countriesArray=new ArrayList<String>();
			for (String string : adjacenciesArray) {
				System.out.println(string);
				String[] countries=string.split(",");
				for (String string2 : countries) {
					//System.out.println();
					System.out.println(string2);
					countriesArray.add(string2);
					//i++;
					break;
				}
				
			}
			
			
			// Reads adjacencies file
			/*reader = new BufferedReader(new FileReader("adjacentcountries.txt"));			
			stringBuilder = new StringBuilder();
			
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				if(line!=null)
					stringBuilder.append("\n");
			}
			input = stringBuilder.toString();
			*/
			//System.out.println("Input from adjacenciesFile" + ": " + input);
			
			
			
			// Creates an array of each line from the file
			//adjacenciesArray = input.split("\n");
			//adjacenciesArray = input.split("\n");
			
			/*// Reads continents file
			reader = new BufferedReader(new FileReader(continentsFile));
			stringBuilder = new StringBuilder();
			
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
			input = stringBuilder.toString();
			System.out.println("Input from " + continentsFile + ": " + input);
			
			continentsArray = input.split("\t");*/
			
			// Creates game board object
			isLoaded = board.loadBoard(countriesArray, adjacenciesArray);
		
		return isLoaded;
}
}