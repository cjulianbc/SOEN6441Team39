package com.concordia.soen6441project;

import java.util.ArrayList;

public class Countries {

    /*private int armies;
	private boolean hasPlayer;*/
	
    private String name;
    //private Player occupant;
    private ArrayList<Countries> adjacencies;

    public Countries(String name) {
	
		this.name = name;
	/*	hasPlayer = false;
		armies = 0;*/
		
		System.out.println("Created country: " + name);
	}

    /**
     * Used only when contstructing the country object, it should not be called after
     * the board is initialized
     **/
    public void addAdjacencies(ArrayList<Countries> adjacencies) {
	
		this.adjacencies = adjacencies;
    }

    public String getName() {
		return name;
    }
    
    public ArrayList<Countries> getAdjacencies() {
		return adjacencies;
    }
}

    /**
     * When a player conquers a country the player object is set as the occupant of the 
	 * country
     **/
   /* public void setOccupant(Player occupant) {
		hasPlayer = true;
		this.occupant = occupant;
    }

    *//**
     * Returns the player object who currently occupies the country
     **//*
    public Player getOccupant() {
		return occupant;
    }
	
    *//**
     * Used to set the number of armies currently stationed in this country
     **//*
    public void setNumArmies(int numArmies) {
		armies = numArmies;
    }
	
	public void incrementArmies(int numArmies) {
		armies = armies + numArmies;
		System.out.println(occupant.getName() + " added " + numArmies + " armies to " + name + ".");
	}
	
	public void decrementArmies(int numArmies) {
		armies = armies - numArmies;
		System.out.println(occupant.getName() + " lost " + numArmies + " armies in " + name + ".");
	}

    *//**
     * Returns the number of armies currently stationed in this country
     **//*
    public int getArmies() {
		return armies;
    }

    *//**
     *  Returns a list of the country objects that are adjacent to this country on the baord
     **//*
    
	
	public boolean hasPlayer() {
		return hasPlayer;
	}
}
*/