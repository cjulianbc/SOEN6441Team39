package com.concordia.soen6441riskgame;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * This is a test class containing test cases to validate the player data
 * structure i.e: Validate Number Of PLayers, current number of armies, player
 * turn, calculation of number of reinforcement armies etc
 * 
 * 
 *
 * 
 * @author Amit, Nesar, Aamreen
 * @version 1.1
 * @since 1.1
 *
 */
class RGPlayerTest {

	RGPlayer player = RGPlayer.getPlayers();
	RGGame game = RGGame.getGame();
	@BeforeEach
	void setUp() throws Exception {

		StringBuilder content = new StringBuilder();
		content.append(
				"India,314,339,Indian Sub-Continent,Pakistan,China,Nepal,Bhuntan,Myanmar,Bangladesh,Maldives,Sri Lanka");
		content.append("\n");
		content.append("Nepal,347,293,Indian Sub-Continent,India");
		content.append("\n");
		content.append("Bhuntan,396,296,Indian Sub-Continent,India");
		content.append("\n");
		content.append("Bangladesh,394,332,Indian Sub-Continent,India,Myanmar");
		content.append("\n");
		content.append("Sri Lanka,330,457,Indian Sub-Continent,India");
		content.append("\n");
		content.append("Maldives,273,493,Indian Sub-Continent,India");

		content.append("\n");
		content.append("Indonesia,620,461,Oceania,Singapore,East Malaysia,Philippines");
		content.append("\n");
		content.append("East Malaysia,572,452,Oceania,Brunei,Vietnam,West Malaysia,Indonesia,Philippines");
		content.append("\n");
		content.append("Brunei,589,437,Oceania,Vietnam,East Malaysia");
		content.append("\n");
		content.append("Philippines,627,360,Oceania,China,East Malaysia,Indonesia");

		game.createGraph(content);
		RGGraph countryItems = game.getCountryItems();

		StringBuilder continent = new StringBuilder();
		continent.append("Indian Sub-Continent=2");
		continent.append("\n");
		continent.append("Oceania=4");

		countryItems.addEdge("Indonesia", "C");
		countryItems.addEdge("Indonesia", "1");

		countryItems.addEdge("India", "A");
		countryItems.addEdge("India", "1");

		countryItems.addEdge("Nepal", "B");
		countryItems.addEdge("Nepal", "1");

		countryItems.addEdge("East Malaysia", "C");
		countryItems.addEdge("East Malaysia", "1");

		countryItems.addEdge("Bhuntan", "A");
		countryItems.addEdge("Bhuntan", "1");

		countryItems.addEdge("Bangladesh", "B");
		countryItems.addEdge("Bangladesh", "1");

		countryItems.addEdge("Brunei", "C");
		countryItems.addEdge("Brunei", "1");

		countryItems.addEdge("Sri Lanka", "A");
		countryItems.addEdge("Sri Lanka", "1");

		countryItems.addEdge("Maldives", "B");
		countryItems.addEdge("Maldives", "1");

		countryItems.addEdge("Philippines", "C");
		countryItems.addEdge("Philippines", "1");
		player.setPlayers("A,B,C");
		game.initializeGame();
	}

	@Test
	void testValidateNumberOfPLayers() {
		assertTrue(player.validateNumberOfPLayers("A,B,C"));
	}

	@Test
	void testGetPlayerColor() {
		assertEquals(player.getPlayerColor("B"), "magenta");
	}

	@Test
	void testGetPlayerTurn() {
		assertEquals(player.getPlayerTurn(), "A");
	}

	@Test
	void testGetCurrentArmies() {
		assertEquals(player.getCurrentArmies("B"), "35");
	}

	@Test
	void testSetNumberOfArmiesSetup() {
		player.setNumberOfArmiesSetup(3, "C");
		assertEquals(player.getCurrentArmies("C"), "38");
	}

	@Test
	void testGetNumberOfArmiesForReinforcement() {
		assertEquals(player.getNumberOfArmiesForReinforcement("B"), "0");
	}

}
