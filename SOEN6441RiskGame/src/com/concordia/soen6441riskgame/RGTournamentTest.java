package com.concordia.soen6441riskgame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RGTournamentTest {
	RGTournamentManager tournament;
	
	@Before
	public void setUp() throws Exception {
		List<String> players=new ArrayList<>();
		players.add("aggressive");
		players.add("benevolent");
		players.add("random");
		ArrayList<String> maps=new ArrayList<String>();
		maps.add("Africa.map");
		maps.add("Aden.map");
		tournament=new RGTournamentManager(maps, players, 3, 11);
	}
	
	@Test
	public void testTournament() {
		tournament.startTournament();
		assertEquals(6,tournament.getResultsSize());
	}
	@Test
	public void testValidateNumberOfMaps() {
		
		assertTrue(tournament.validateNumberOfMaps());
	}
	
	@Test
	public void testValidateNumberOfPlayers() {
		assertTrue(tournament.validateNumberOfPlayers());
			
	}
	
	@Test
	public void testValidateNumberOfTurns() {
		assertTrue(tournament.validateTurns());
			
	}
}
