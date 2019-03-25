package com.concordia.soen6441riskgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RGGameTest {
	// RGGraph graph = new RGGraph();
	RGGame game = RGGame.getGame();// new RGGame();
	// RGGraph countryItems = new RGGraph();
	// RGGraph playerItems= new RGGraph();
	    
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
		
		
		// game.setNumberOfArmies(10, "India");
		// game.setNumberOfArmies(10, "Nepal");
		// game.setNumberOfArmies(10, "Bhuntan");
		// game.setNumberOfArmies(10, "Bangladesh");
		// game.setNumberOfArmies(10, "Maldives");

		// game.addEdge("India", "A");
		// countryItems.addEdge("Nepal", "B");
		// countryItems.addEdge("Bhuntan", "C");
		// countryItems.addEdge("Bangladesh", "Q");

		ArrayList<String> players = new ArrayList<String>();
		players.add("A");
		players.add("B");
		players.add("C");

		game.assignCountries(players);



		// RGPlayer player=RGPlayer.getPlayers();
		// player.setPlayers("a,b,c");
        
        

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
		fail("Not yet implemented");
		// assertEquals(game.getOwner("Bangladesh"), "Q");
	}

	@Test
	void testGetArmies() {
		assertEquals(game.getArmies("Bangladesh"), "1");
		// fail("Not yet implemented");
	}

	@Test
	void testGetVertex() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEdges() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentPlayerCountries() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNumberOfArmies() {
		game.setNumberOfArmies(2, "India");
		assertEquals(game.getArmies("India"), "3");
		// fail("Not yet implemented");
	}

	@Test
	void testGetCurrentPlayerNumberOfCountries() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNumberOfArmiesDueTerritories() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNumberOfArmiesDueContinents() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentPlayerAdjacentCountries() {
		fail("Not yet implemented");
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
