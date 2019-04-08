/**
 * 
 */
package com.concordia.soen6441riskgame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author amit
 *
 */
public class RGGraphTest {

	RGGame game = RGGame.getGame();
	RGGraph graph = new RGGraph();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Test method for {@link com.concordia.soen6441riskgame.RGGraph#addVertex(java.lang.String)}.
	 */
	@Test
	public void testAddVertex() {
		graph.addVertex("CANADA");
		ArrayList<String> vert = graph.getVertex();
		assertEquals(vert.get(0), "CANADA");
	}

	/**
	 * Test method for {@link com.concordia.soen6441riskgame.RGGraph#deleteVertex(java.lang.String)}.
	 */
	@Test
	public void testDeleteVertex() {
		graph.deleteVertex("CANADA");
		ArrayList<String> vert = graph.getVertex();
		assertTrue(vert.isEmpty());
	}

	/**
	 * Test method for {@link com.concordia.soen6441riskgame.RGGraph#addEdge(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddEdge() {
		graph.addVertex("CANADA");
		graph.addEdge("CANADA", "USA");
		ArrayList<String> vert = graph.getEdges("CANADA");
		assertEquals(vert.get(0), "USA");
	}

	/**
	 * Test method for
	 * {@link com.concordia.soen6441riskgame.RGGraph#getEdges(java.lang.String)}.
	 */
	@Test
	public void testGetEdges() {
		graph.addVertex("CANADA");
		graph.addEdge("CANADA", "USA");
		ArrayList<String> vert = graph.getEdges("CANADA");
		assertEquals(vert.get(0), "USA");
	}

	/**
	 * Test method for {@link com.concordia.soen6441riskgame.RGGraph#setEdge(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testSetEdge() {
		graph.addVertex("CANADA");
		graph.addEdge("CANADA", "USA");
		ArrayList<String> vert = new ArrayList<String>();
		vert.add("USA");
		vert.add("Maxico");
		vert.add("Colombia");
		graph.setEdge("CANADA", vert);
		ArrayList<String> edg = graph.getEdges("CANADA");
		assertEquals(edg.get(1), "Maxico");
	}

	/**
	 * Test method for {@link com.concordia.soen6441riskgame.RGGraph#getVertex()}.
	 */
	@Test
	public void testGetVertex() {
		graph.addVertex("CANADA");
		graph.addVertex("Maxico");
		graph.addVertex("USA");
		ArrayList<String> vert = graph.getVertex();
		assertEquals(vert.get(0), "USA");
	}

}
