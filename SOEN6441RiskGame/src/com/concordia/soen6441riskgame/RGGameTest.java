package com.concordia.soen6441riskgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RGGameTest {

	RGGame game = RGGame.getGame();

	/**
	 * SetUp() of RGPlayerTest has build a context for running the test for the
	 * method from the RGPlayer class. Here a few country, its coordinate, continent
	 * adjacent country, owner and solder number has setup for the test purpose.
	 */
	@Before
	public void setUp() throws Exception {
		
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
		
		RGPlayer player = RGPlayer.getPlayers();
		player.setPlayers("A=human,B=aggressive,C=random");
		 game.initializeGame();

	}

	/**
	 * testGetXCoord() of RGGameTest validate a country's X coordinate.
	 */
	@Test
	public void testGetXCoord() {
		assertEquals(game.getXCoord("India"), "314");
	}

	/**
	 * testGetYCoord() of RGGameTest validate a country's Y coordinate.
	 */
	@Test
	public void testGetYCoord() {
		assertEquals(game.getXCoord("Bangladesh"), "394");
	}

	/**
	 * testGetOwner() of RGGameTest validate a country's current owner.
	 */
	@Test
	public void testGetOwner() {
		// fail("Not yet implemented");
		assertEquals(game.getOwner("Bangladesh"), "B");
	}

	/**
	 * testGetArmies() of RGGameTest validate a country's current armies.
	 */
	@Test
	public void testGetArmies() {
		assertEquals(game.getArmies("Bangladesh"), "1");
		// fail("Not yet implemented");
	}

	/**
	 * testGetVertex() of RGGameTest validate the country list.
	 */
	@Test
	public void testGetVertex() {
		ArrayList<String> vert = game.getVertex();
		assertEquals(vert.get(1), "Bangladesh");
	}

	/**
	 * testGetEdges() of RGGameTest validate the adjacent countries of given
	 * country.
	 */
	@Test
	public void testGetEdges() {
		ArrayList<String> edg = game.getEdges("India");
		assertEquals(edg.get(0), "Pakistan");
	}

	/**
	 * testGetCurrentPlayerCountries() of RGGameTest validate the countries of given
	 * owner.
	 */
	@Test
	public void testGetCurrentPlayerCountries() {
		ArrayList<String> playercountries = game.getCurrentPlayerCountries("A");
		// System.out.print("---------------------------- " + playercountries.get(0));
		assertEquals(playercountries.get(0), "Bhuntan");
	}

	/**
	 * testSetNumberOfArmies() of RGGameTest validate if the armies are set
	 * properly.
	 */
	@Test
	public void testSetNumberOfArmies() {
		game.setNumberOfArmies(2, "India");
		
		assertEquals(game.getArmies("India"), "9");
	}

	/**
	 * testGetCurrentPlayerNumberOfCountries() of RGGameTest validate if the armies
	 * of a given player is accurate for the context.
	 */
	@Test
	public void testGetCurrentPlayerNumberOfCountries() {
		assertEquals(game.getCurrentPlayerNumberOfCountries("B"), 3);
	}

	/**
	 * testGetNumberOfArmiesDueTerritories() of RGGameTest validate if the number of
	 * armies due to a territories is accurate for the context.
	 */
	@Test
	public void testGetNumberOfArmiesDueTerritories() {
		assertEquals(game.getNumberOfArmiesDueTerritories(10), 3);
	}

	/**
	 * testGetNumberOfArmiesDueContinents() of RGGameTest validate if the number of
	 * armies due to a continents is accurate for the context.
	 */
	@Test
	public void testGetNumberOfArmiesDueContinents() {
		int currentPlayerNumberOfCountries = game.getCurrentPlayerNumberOfCountries("C");
		assertEquals(currentPlayerNumberOfCountries, 4);
	}

	/**
	 * testGetCurrentPlayerAdjacentCountries() of RGGameTest validate if the
	 * adjacent country of a given player is accurate for the context.
	 */
	@Test
	public void testGetCurrentPlayerAdjacentCountries() {
		// fail("Not yet implemented");
		ArrayList<String> adjPath = game.getCurrentPlayerAdjacentCountries("A", "India");
		assertEquals(adjPath.get(0), "Bhuntan");
	}

	/**
	 * testGetAttackStatus() of RGGameTest validate the attack status, here we are
	 * checking only the 'end' status.
	 */
	@Test
	public void testGetAttackStatus() {
		game.setAttackStatus("end");
		assertEquals(game.getAttackStatus(), "end");
	}

	/**
	 * testSetAllOutModeForAttackPhase() of RGGameTest check the mode of the attack
	 * phase.
	 */
	@Test
	public void testSetAllOutModeForAttackPhase() {
		game.setAllOutModeForAttackPhase(true);
		assertTrue(game.getAllOutModeForAttackPhase());
	}
	
	/**
	 * testGetPlayersName() of RGGameTest validate a player name/owner given by a
	 * country name.
	 */
	@Test
	public void testGetPlayersName() {
		assertEquals(game.getPlayersName("India"),"A");
		
		
	}

	/**
	 * testvalidateCards() of RGGameTest validate the cards.
	 */
	@Test
	public void testvalidateCards() {
		List<String> selectedCards=Arrays.asList("Artilery","Infantry","Wild");
		assertTrue(game.validateCards(selectedCards));
	}
	
	/**
	 * testFortificationPhase() of RGGameTest validate the fortification phase for a
	 * given context.
	 */
	@Test
	public void testFortificationPhase() {
		game.fortificationPhase("Bangladesh", "India", 1);
		// System.out.print("------------------------i am
		// here---------------------------- "+
		// game.getCountryItems().getEdges("Bangladesh").get(4) + " ---------");
		assertEquals(game.getCountryItems().getEdges("Bangladesh").get(4), "0");
	}
	
	/**
	 * testFortificationPhase() of RGGameTest validate the fortification phase for a
	 * given context.
	 */
	@Test
    public void testPercentageMapControlledByPlayer() {
		assertEquals(game.percentageMapControlledByPlayer("C"), 40);
    }	
	/**
	 * method to test the save and load game
	 */
	@Test
	public void saveMap() {
		game.saveGame();
		game.setSavedGame(true);
		assertTrue(game.loadGame());
		
	}

	@Test
	public void testAttackPhase() {
		game.setAllOutModeForAttackPhase(true);
		game.attackPhaseModeDecision("India", "Nepal", "", "", "A");
		assertEquals("A", game.getOwner("Nepal"));

	}

	@Test
	public void testValidMoveAfterConquering() {
		game.setAllOutModeForAttackPhase(true);
		game.attackPhaseModeDecision("India", "Nepal", "", "", "A");
		assertEquals("3", game.getArmies("Nepal"));

	}

	@Test
	public void testEndOFGame() {
		game.setAttackStatus("end");
		assertEquals(game.getAttackStatus(), "end");

	}
}

