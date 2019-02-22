package com.concordia.soen6441project;

import java.util.ArrayList;

public class Continents {

    private String name;
    //private int bonusArmies;
    private ArrayList<Countries> mcountries;

    public Continents(String name, int bonusArmies, ArrayList<Countries> memberCountries) {
		this.name = name;
		//this.bonusArmies = bonusArmies;
		mcountries = memberCountries;
		
		System.out.println("Created continent: " + name + "\nBonus armies: " + bonusArmies);
    }

    public String getName() {
		return name;
    }

    /**
     *  Returns the number of bonus armies a player gets per round when the player controls this
     * continent
     **/
   /* public int getBonusArmies() {
		return bonusArmies;
    }*/

    /**
     * Retuens a list of the country objects that are on this continent
     **/
    public ArrayList<Countries> getMemberCountries() {
		return mcountries;
    }
}
