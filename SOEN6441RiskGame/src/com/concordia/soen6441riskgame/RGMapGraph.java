package com.concordia.soen6441riskgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JTextArea;

/**
 * This class draws a complete map. Each country is represented by textArea, and the lines represent the adjacencies among countries. 
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGMapGraph extends JComponent {
	private RGFile file;
	private RGGame game;
	private RGPlayer player;
	
	/**
	 * This constructor set the objects of the current, game, graph and set of players.
	 * 
     *    
     * @param file File where the Risk® map is stored
     * @param game Current game
     * @param player Current set of players
     *   
	 */
	RGMapGraph(RGFile file, RGGame game, RGPlayer player)
	{
		this.file=file;
		this.game=game;
		this.player=player;
	}
	
	
	
	/**
	 * This method overrides the paintComponent method that is used to paint in a canvas.  
	 * 
	 * Components of the canvas:
	 *
	 * 1) Each country is represented by a textArea, and every textArea has a color.
	 * 2) Each color represents a player.
	 * 3) Countries are connected by lines. Lines represent countries' adjacencies.
	 * 4) Inside each country (textArea) there is a number that represents the number of armies placed in that country.
	 * 5) Below every textArea that represents a country there is another textArea with the name of the country.
     *    
     *    
     * @param g object of a Graphic class 
     * @see Graphics
     *   
	 */
	@Override
    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		String xCoord;
		String yCoord;
		String xCoordEnd;
		String yCoordEnd;
		String armies;
		String owner;
		String color;
		ArrayList<String> vertex = game.getVertex(); //getting the set of countries of the Risk® map
		removeAll();
		for(int k=0;k<vertex.size();k++) //for each country
		{
			xCoord=this.game.getXCoord(vertex.get(k));
			yCoord=this.game.getYCoord(vertex.get(k));
			armies=this.game.getArmies(vertex.get(k));
			owner=this.game.getOwner(vertex.get(k));
			color=player.getPlayerColor(owner);			
			
			JTextArea txtArea = new JTextArea();
			txtArea.setFont(new Font("Arial", 0, 13));
			txtArea.setText(armies);
			txtArea.setBounds(Integer.valueOf(xCoord), Integer.valueOf(yCoord), 15, 15);	
			
			JTextArea txtArea_1 = new JTextArea();
			txtArea_1.setFont(new Font("Arial", 0, 10));
			txtArea_1.setText(vertex.get(k));
			txtArea_1.setOpaque(false);
			txtArea_1.setBounds(Integer.valueOf(xCoord), Integer.valueOf(yCoord)+15, 90, 13);
			
			switch (color) {
			case "green":
				txtArea.setBackground(Color.green);
			    break;
			case "magenta":
				txtArea.setBackground(Color.magenta);
			    break;
			case "cyan":
				txtArea.setBackground(Color.cyan); 
			    break;
			case "pink":
				txtArea.setBackground(Color.pink);
			    break;
			case "orange":
				txtArea.setBackground(Color.orange); 
			    break;
			case "blue":
				txtArea.setBackground(Color.blue); 
			    break;
			default:
			    break;
			}
			add(txtArea);
			txtArea.setEditable(false);
			add(txtArea_1);
			txtArea_1.setEditable(false);
			
			ArrayList<String> edges = game.getEdges(vertex.get(k));
			for(int j=0;j<edges.size();j++)//drawing all the lines (adjacencies) for each country
			{
				xCoordEnd=this.game.getXCoord(edges.get(j));
				yCoordEnd=this.game.getYCoord(edges.get(j));
				g.drawLine(Integer.valueOf(xCoord), Integer.valueOf(yCoord),Integer.valueOf(xCoordEnd), Integer.valueOf(yCoordEnd));
			}
		}
	}
	
}
