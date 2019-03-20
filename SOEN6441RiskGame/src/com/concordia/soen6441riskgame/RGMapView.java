package com.concordia.soen6441riskgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage; //This class will read the incoming BMP image
import javax.imageio.ImageIO; //This class will convert BMP to PNG
import java.io.File; //to read input and write output images
import java.io.FileNotFoundException;
import java.io.IOException;

public class RGMapView extends JPanel implements Observer{
	
	
	@Override
	public void update(Observable game, Object arg)
	{
		String xCoord;
		String yCoord;
		String armies;
		String owner;
		String color;
		
		removeAll();
		setLayout(null);
		setPreferredSize(new Dimension(900,720));
		
		//getting name of the file where image is stored
		RGFile file=RGFile.getGameFile();
		StringBuilder content=new StringBuilder();
		try {
			content=file.getMapImageFileName("[Map]");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//getting
		
		//converting BMP image to PNG image
		String mapImagePath=file.getImageFilePath();
		System.out.println(mapImagePath);
		System.out.println(content);
		BufferedImage input_image = null; 
		try {
			input_image = ImageIO.read(new File(mapImagePath+"\\"+content));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		File outputfile = new File(mapImagePath+"\\MapTeam39.png");
		try {
			ImageIO.write(input_image, "PNG", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//Creating map image background		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setVerticalTextPosition(SwingConstants.TOP);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
	    lblNewLabel.setIcon(new ImageIcon(mapImagePath+"\\MapTeam39.png"));
	    lblNewLabel.setBounds(0, 0, 900, 640);
	    add(lblNewLabel);
	    lblNewLabel.setLayout(null);
		
	    ArrayList<String> vertex = ((RGGame)game).getVertex(); //getting the set of countries of the Risk® map
	    for(int k=0;k<vertex.size();k++) //for each country
		{
			xCoord=((RGGame)game).getXCoord(vertex.get(k));
			yCoord=((RGGame)game).getYCoord(vertex.get(k));
			armies=((RGGame)game).getArmies(vertex.get(k));
			owner=((RGGame)game).getOwner(vertex.get(k));
			RGPlayer players=RGPlayer.getPlayers();
			color=players.getPlayerColor(owner);			
			
			JTextArea txtArea = new JTextArea();
			JTextArea txtArea_1 = new JTextArea();
			txtArea.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseExited(MouseEvent e) {
					txtArea_1.setVisible(false);
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					txtArea_1.setVisible(true);
				}
			});
			txtArea.setFont(new Font("Arial", 0, 13));
			txtArea.setText(armies);
			txtArea.setBounds(Integer.valueOf(xCoord)-5, Integer.valueOf(yCoord)-7, 15, 15);	
			
			
			txtArea_1.setFont(new Font("Arial", 0, 10));
			txtArea_1.setText(vertex.get(k));
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
			lblNewLabel.add(txtArea);
			txtArea.setEditable(false);
			lblNewLabel.add(txtArea_1);
			txtArea_1.setEditable(false);
			txtArea_1.setVisible(false);
		}
	}

}
