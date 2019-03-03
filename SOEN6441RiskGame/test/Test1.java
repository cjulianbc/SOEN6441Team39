package com.concordia.soen6441riskgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test1 {
	
	String continents,countries;
	RGFile file;
	@BeforeEach public void initialize() {
		file=new RGFile();
		}
	
	@Test
	void testMapValidate() {
		continents="Asia=2"+"\n"+"North America=7";
		countries="India,143,28,Asia,Srilanka,Bangladesh,Pakistan,Nepal,Bhutan,Canada,China\n"+"Pakistan,27,34,Asia,Afghanistan,China,India\n" + 
				"Srilanka,45,66,Asia,India\n" + "Bangladesh,33,98,Asia,India,Bhutan\n" + 
						"Nepal,107,97,Asia,India,Bhutan,China\n" + 
						"Bhutan,120,45,Asia,India,Nepal,Bangladesh,China\n" + 
						"China,17,89,Asia,India,Pakistan,Nepal,Bhutan\n" + 
						"Afghanistan,29,88,Asia,Pakistan\n" + 
						"USA,9,9,North America,Canada\n" + 
						"Canada,11,11,North America,USA,India";
		assertTrue(file.validateMap(continents, countries));
	}
	
	@Test
	void testMapInvalidContinentFormat() {
		continents="Asia2"+"\n"+"North America=7";
		countries="India,143,28,Asia,Srilanka,Bangladesh,Pakistan,Nepal,Bhutan,Canada,China\n"+"Pakistan,27,34,Asia,Afghanistan,China,India\n" + 
				"Srilanka,45,66,Asia,India\n" + "Bangladesh,33,98,Asia,India,Bhutan\n" + 
						"Nepal,107,97,Asia,India,Bhutan,China\n" + 
						"Bhutan,120,45,Asia,India,Nepal,Bangladesh,China\n" + 
						"China,17,89,Asia,India,Pakistan,Nepal,Bhutan\n" + 
						"Afghanistan,29,88,Asia,Pakistan\n" + 
						"USA,9,9,North America,Canada\n" + 
						"Canada,11,11,North America,USA,India";
		assertFalse(file.validateMap(continents, countries));
	}
	
	@Test
	void testMapInvalidContinentCount() {
		continents="Asia=2"+"\n"+"North America=8";
		countries="India,143,28,Asia,Srilanka,Bangladesh,Pakistan,Nepal,Bhutan,Canada,China\n"+"Pakistan,27,34,Asia,Afghanistan,China,India\n" + 
				"Srilanka,45,66,Asia,India\n" + "Bangladesh,33,98,Asia,India,Bhutan\n" + 
						"Nepal,107,97,Asia,India,Bhutan,China\n" + 
						"Bhutan,120,45,Asia,India,Nepal,Bangladesh,China\n" + 
						"China,17,89,Asia,India,Pakistan,Nepal,Bhutan\n" + 
						"Afghanistan,29,88,Europe,Pakistan\n" + 
						"USA,9,9,North America,Canada\n" + 
						"Canada,11,11,North America,USA,India";
		assertFalse(file.validateMap(continents, countries));
	}
	
	
	 @Test void testMapInvalidContinentName() {
		continents="Asia=2"+"\n"+"North America=8"; countries=
	  "India,143,28,Asia,Srilanka,Bangladesh,Pakistan,Nepal,Bhutan,Canada,China\n"+
	  "Pakistan,27,34,Asia,Afghanistan,China,India\n" +
	  "Srilanka,45,66,Asia,India\n" + "Bangladesh,33,98,Asia,India,Bhutan\n" +
	  "Nepal,107,97,Asia,India,Bhutan,China\n" +
	  "Bhutan,120,45,Asia,India,Nepal,Bangladesh,China\n" +
	  "China,17,89,Asia,India,Pakistan,Nepal,Bhutan\n" +
	  "Afghanistan,29,88,Asia,Pakistan\n" + "USA,9,9,South America,Canada\n" +
	  "Canada,11,11,South America,USA,India";
	  assertFalse(file.validateMap(continents, countries)); }
	 
	 @Test void testMapInvalidSameCoordinates() {
			continents="Asia=2"+"\n"+"North America=8"; countries=
		  "India,143,28,Asia,Srilanka,Bangladesh,Pakistan,Nepal,Bhutan,Canada,China\n"+
		  "Pakistan,27,34,Asia,Afghanistan,China,India\n" +
		  "Srilanka,45,66,Asia,India\n" + "Bangladesh,33,98,Asia,India,Bhutan\n" +
		  "Nepal,107,97,Asia,India,Bhutan,China\n" +
		  "Bhutan,120,45,Asia,India,Nepal,Bangladesh,China\n" +
		  "China,17,89,Asia,India,Pakistan,Nepal,Bhutan\n" +
		  "Afghanistan,29,88,Asia,Pakistan\n" + 
		  "USA,9,9,North America,Canada\n" +
		  "Canada,143,28,North America,USA,India";
		  assertFalse(file.validateMap(continents, countries)); } 
	 
	 

}
