package com.concordia.soen6441project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public class RGMapGraph extends JComponent {
	RGFile file;
	RGGame game;
	RGPlayer player;
	
	RGMapGraph(RGFile file, RGGame game, RGPlayer player)
	{
		this.file=file;
		this.game=game;
		this.player=player;
	}
	
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
		ArrayList<String> vertex = game.getVertex();
		removeAll();
		for(int k=0;k<vertex.size();k++)
		{
			xCoord=this.game.getXCoord(vertex.get(k));
			yCoord=this.game.getYCoord(vertex.get(k));
			armies=this.game.getArmies(vertex.get(k));
			System.out.println(armies);
			owner=this.game.getOwner(vertex.get(k));
			color=player.getPlayerColor(owner);			
			
			JTextArea txtArea = new JTextArea();
			txtArea.setText(armies);
			txtArea.setBounds(Integer.valueOf(xCoord), Integer.valueOf(yCoord), 18, 18);	
			
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
			
			ArrayList<String> edges = game.getEdges(vertex.get(k));
			for(int j=0;j<edges.size();j++)
			{
				xCoordEnd=this.game.getXCoord(edges.get(j));
				yCoordEnd=this.game.getYCoord(edges.get(j));
				g.drawLine(Integer.valueOf(xCoord), Integer.valueOf(yCoord),Integer.valueOf(xCoordEnd), Integer.valueOf(yCoordEnd));
			}
		}
	}
	
}
