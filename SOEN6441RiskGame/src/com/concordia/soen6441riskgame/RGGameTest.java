package com.concordia.soen6441riskgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		game.createGraph(content);
		RGGraph countryItems = game.getCountryItems();
		countryItems.addEdge("India", "A");
		countryItems.addEdge("India", "1");

		countryItems.addEdge("Nepal", "B");
		countryItems.addEdge("Nepal", "1");

		countryItems.addEdge("Bhuntan", "C");
		countryItems.addEdge("Bhuntan", "1");

		countryItems.addEdge("Bangladesh", "A");
		countryItems.addEdge("Bangladesh", "1");

		countryItems.addEdge("Sri Lanka", "B");
		countryItems.addEdge("Sri Lanka", "1");

		countryItems.addEdge("Maldives", "C");
		countryItems.addEdge("Maldives", "1");
        

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
		assertEquals(game.getOwner("Bangladesh"), "A");
	}

	@Test
	void testGetArmies() {
		assertEquals(game.getArmies("Bangladesh"), "1");
		// fail("Not yet implemented");
	}

	@Test
	void testGetVertex() {
		// fail("Not yet implemented");
		ArrayList<String> vert = game.getVertex();
		// System.out.print("---------------------------- " + vert.get(0));
		assertEquals(vert.get(0), "Bangladesh");
	}

	@Test
	void testGetEdges() {
		// fail("Not yet implemented");
		ArrayList<String> edg = game.getEdges("India");
		// System.out.print("---------------------------- " + edg.get(0));
		assertEquals(edg.get(0), "Pakistan");
	}

	@Test
	void testGetCurrentPlayerCountries() {
		ArrayList<String> playercountries = game.getCurrentPlayerCountries("A");

		assertEquals(playercountries.get(0), "Bangladesh");
		// fail("Not yet implemented");
	}

	@Test
	void testSetNumberOfArmies() {
		game.setNumberOfArmies(2, "India");
		assertEquals(game.getArmies("India"), "3");
		// fail("Not yet implemented");
	}

	@Test
	void testGetCurrentPlayerNumberOfCountries() {
		// fail("Not yet implemented");
		assertEquals(game.getCurrentPlayerNumberOfCountries("B"), 2);
	}

	@Test
	void testGetNumberOfArmiesDueTerritories() {
		assertEquals(game.getNumberOfArmiesDueTerritories(10), 3);
	}

	@Test
	void testGetNumberOfArmiesDueContinents() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentPlayerAdjacentCountries() {
		// fail("Not yet implemented");
		ArrayList<String> adjPath = game.getCurrentPlayerAdjacentCountries("A", "India");
		assertEquals(adjPath.get(0), "Bangladesh");
	}

	@Test
	void testGetPlayersName() {
		fail("Not yet implemented");
	}

	// @Test
	// void testFortificationPhase() {
	// fail("Not yet implemented");
	// }

	@Test
	void testGetCountriesAttacker() {
		fail("Not yet implemented");
	}

	@Test
	void testSetAttackStatus() {
		fail("Not yet implemented");
	}

	@Test
	void testSetAllOutModeForAttackPhase() {
		fail("Not yet implemented");
	}

}
