package com.concordia.soen6441riskgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
public class RGPlayerTest {
    RGPlayer player = RGPlayer.getPlayers();
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
		player.setPlayers("A=human,B=aggressive,C=random");
        game.initializeGame();
    }

	/**
	 * testValidateNumberOfPLayers() of RGPlayerTest validate the number of players
	 * registered by the user. Must be between 2 and 6.
	 */
    @Test
    public void testValidateNumberOfPLayers() {
        assertTrue(player.validateNumberOfPLayers("A,B,C"));
    }

	/**
	 * testGetPlayerColor() of RGPlayerTest validate if a player has the expected
	 * color as it suppose to be.
	 */
    @Test
    public void testGetPlayerColor() {
        assertEquals(player.getPlayerColor("B"), "magenta");
    }

	/**
	 * testGetPlayerTurn() of RGPlayerTest validate the turn a player get.
	 */
    @Test
    public void testGetPlayerTurn() {
        assertEquals(player.getPlayerTurn(), "A");
    }





	/**
	 * testGetNumberOfArmiesForReinforcement() of RGPlayerTest validate if a player
	 * gets the right amount of army for Reinforcement as it suppose to have by the
	 * provided context.
	 */
    @Test
    public void testGetNumberOfArmiesForReinforcement() {
        assertEquals(player.getNumberOfArmiesForReinforcement("B"), "0");
    }
    
	/**
	 * testSetNumberOfArmiesForUsedCard() of RGPlayerTest validate if a player gets
	 * the right amount of army received after Reinforcement phase when exchanging
	 * the cards by the provided context.
	 */
	@Test
	public void testSetNumberOfArmiesForUsedCard() {
		player.initializeCardUsedCount();
		assertEquals(player.setNumberOfArmiesForUsedCard("B", 12), 16);
	}

	/**
	 * testGetPlayerStrategy() of RGPlayerTest validate a player's strategy
	 */
	@Test
	public void testGetPlayerStrategy() {
		assertEquals(player.getPlayerStrategy("A"), "human");
	}

}




