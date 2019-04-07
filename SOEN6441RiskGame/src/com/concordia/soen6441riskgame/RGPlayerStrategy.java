package com.concordia.soen6441riskgame;

import java.io.Serializable;

/**
 * Class to change a player strategy during play time. Every player must have a strategy. Strategies:
 * 
 * 1)Human: Requires user interaction to make decisions. 
 * 2)Aggressive: Computer player strategy that focuses on attack (reinforces its strongest country, then always attack with it until it cannot 
 * attack anymore, then fortifies in order to maximize aggregation of forces in one country). 
 * 3)Benevolent: Computer player strategy that focuses on protecting its weak countries (reinforces its weakest countries, never attacks, then 
 * fortifies in order to move armies to weaker countries). 
 * 4)Random: Computer player strategy that focuses on protecting its weak countries (reinforces its weakest countries, never attacks, then fortifies 
 * in order to move armies to weaker countries). 
 * 5)Cheater: Computer player strategy whose reinforce() method doubles the number of armies on all its countries, whose attack() method automatically 
 * conquers all the neighbors of all its countries, and whose fortify() method doubles the number of armies on its countries that have neighbors 
 * that belong to other players. 
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since 1.0
 *
 */
public class RGPlayerStrategy implements Serializable{
	 
	/**
	 * Created to store the strategy.
	 */
	private RGStrategy strategy;
	
	/**
	 * This method is used to set the strategy for every player.
	 * 
	 * 
	 * @param strategy Strategy.
	 * 
	 */
    public void setStrategy(RGStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
	 * This method is used to execute the Setup Phase
	 * 
	 * 
	 * @param selectedCountry Selected country.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
    public void executeSetupPhase(String selectedCountry,String currentPlayerName) {
        this.strategy.setupPhase(selectedCountry,currentPlayerName);
    }
    
    /**
	 * This method is used to execute the Reinforcement Phase
	 * 
	 * 
	 * @param selectedCountry Selected country.
	 * @param currentPlayerName Name of the player.
	 * @param armiesToPlace Number of armies to place.
	 * 
	 */
    public void executeReinforcementPhase(String selectedCountry, String currentPlayerName, String armiesToPlace) {
        this.strategy.reinforcementPhase(selectedCountry, currentPlayerName, armiesToPlace);
    }
    
    /**
	 * This method is used to execute the Attack Phase
	 * 
	 * 
	 * @param selectedCountryAttacker Country where attacker wants to attack from.
	 * @param selectedCountryDefender Country that is going to be defended.
	 * @param selectedDiceAttacker Number of dice for attacker to toss.
	 * @param selectedDiceDefender Number of dice for defender to toss.
	 * @param currentPlayerName Name of the player.
	 * 
	 */
    public void executeAttackPhaseModeDecision(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName) {
        this.strategy.attackPhaseModeDecision(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);
    }
    
    /**
	 * This method is used to execute the Fortification Phase
	 * 
	 * 
	 * @param countryFrom  is the Country name from where the armies to move.
	 * @param countryTo    is the Country name To where the armies to move.
	 * @param aemiesToMove is the number of armies going to move.
	 * 
	 */
       public void executeFortificationPhase(String countryFrom, String countryTo, int armiesToMove) {
           this.strategy.fortificationPhase(countryFrom, countryTo, armiesToMove);
       }
}
