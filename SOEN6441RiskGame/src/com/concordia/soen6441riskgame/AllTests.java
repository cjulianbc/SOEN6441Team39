package com.concordia.soen6441riskgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This is for running all the test files at ones.
 */
@RunWith(Suite.class)
@SuiteClasses({ RGFileTest.class, RGGameTest.class, RGPlayerTest.class, RGGraphTest.class, RGTournamentTest.class})
public class AllTests {

}
