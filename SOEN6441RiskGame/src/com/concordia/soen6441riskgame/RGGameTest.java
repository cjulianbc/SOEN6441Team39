package com.concordia.soen6441riskgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * This is a test class containing cases to validate the game validation of a
 * correct startup phase; calculation of number of reinforcement armies; various
 * test for the attack phase, and end of game; validation of a correct
 * fortification phase.
 * 
 *
 * 
 * @author Amit, Nesar
 * @version 1.1
 * @since 1.1
 *
 */
class RGGameTest {
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
		countryItems.addEdge("India", "7");

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
		
		RGPlayer player=RGPlayer.getPlayers();
		 player.setPlayers("a,b,c");
		 game.initializeGame();

	}

	@Test
	void testGetXCoord() {
		assertEquals(game.getXCoord("India"), "314");
	}

	@Test
	void testGetYCoord() {
		assertEquals(game.getXCoord("Bangladesh"), "394");
	}

	@Test
	void testGetOwner() {
		// fail("Not yet implemented");
		assertEquals(game.getOwner("Bangladesh"), "B");
	}

	@Test
	void testGetArmies() {
		assertEquals(game.getArmies("Bangladesh"), "1");
		// fail("Not yet implemented");
	}

	@Test
	void testGetVertex() {
		ArrayList<String> vert = game.getVertex();
		assertEquals(vert.get(1), "Bangladesh");
	}

	@Test
	void testGetEdges() {
		ArrayList<String> edg = game.getEdges("India");
		assertEquals(edg.get(0), "Pakistan");
	}

	@Test
	void testGetCurrentPlayerCountries() {
		ArrayList<String> playercountries = game.getCurrentPlayerCountries("A");
		// System.out.print("---------------------------- " + playercountries.get(0));
		assertEquals(playercountries.get(0), "Bhuntan");
	}

	@Test
	void testSetNumberOfArmies() {
		game.setNumberOfArmies(2, "India");
		
		assertEquals(game.getArmies("India"), "9");
	}

	@Test
	void testGetCurrentPlayerNumberOfCountries() {
		assertEquals(game.getCurrentPlayerNumberOfCountries("B"), 3);
	}

	@Test
	void testGetNumberOfArmiesDueTerritories() {
		assertEquals(game.getNumberOfArmiesDueTerritories(10), 3);
	}

	@Test
	void testGetNumberOfArmiesDueContinents() {
		// fail("Not yet implemented");
		int currentPlayerNumberOfCountries = game.getCurrentPlayerNumberOfCountries("C");
		assertEquals(currentPlayerNumberOfCountries, 4);
	}

	@Test
	void testGetCurrentPlayerAdjacentCountries() {
		ArrayList<String> adjPath = game.getCurrentPlayerAdjacentCountries("A", "India");
		assertEquals(adjPath.get(0), "Bhuntan");
	}

	// @Test
	// void testGetPlayersName() {
	// fail("Not yet implemented");
	// }

	// @Test
	// void testFortificationPhase() {
	// fail("Not yet implemented");
	// }


	@Test
	void testGetAttackStatus() {
		// fail("Not yet implemented");
		// System.out.print("---------------------------- " + game.getAttackStatus());
		assertEquals(game.getAttackStatus(), "");
	}

	@Test
	void testSetAllOutModeForAttackPhase() {
		game.setAllOutModeForAttackPhase(true);
		assertTrue(game.getAllOutModeForAttackPhase());
	}
	
	@Test
	void testGetPlayersName() {
		
		assertEquals(game.getPlayersName("India"),"A");
		
		
	}

	
	@Test
	void testvalidateCards() {
		//List selectedCards=new List();
		List<String> selectedCards=Arrays.asList("Artilery","Infantry","Wild");
		assertTrue(game.validateCards(selectedCards));
	}
	
	@Test
	void testFortificationPhase() {
		
		game.fortificationPhase("Bangladesh", "India", 2);
		 
		assertEquals(game.getCountryItems().getEdges("Bangladesh").get(4),"3");
	}

}
