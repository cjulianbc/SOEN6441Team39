package com.concordia.soen6441riskgame;

public interface RGStrategy {
	
	void setupPhase(String selectedCountry, String currentPlayerName);
	void reinforcementPhase(String selectedCountry, String currentPlayerName, String armiesToPlace);
	void attackPhaseModeDecision(String selectedCountryAttacker,String selectedCountryDefender,String selectedDiceAttacker,String selectedDiceDefender,String currentPlayerName);
	void fortificationPhase(String countryFrom, String countryTo, int armiesToMove);
}
