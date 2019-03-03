package com.concordia.soen6441riskgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class RGGameFrame extends JFrame{
	
	private JPanel right= new JPanel(new GridLayout(1, 1));
	private JPanel left = new JPanel(new GridLayout(1, 1));
	private JFrame frame = new JFrame();
	private RGGame game;
	private RGFile file;
	private RGPlayer player;
	private RGMapGraph mapGraph;
	private String currentPlayerName;

	RGGameFrame(RGFile file, RGGame game, RGPlayer player)
	{
        this.game=game;
        this.file=file;
        this.player=player;
        mapGraph = new RGMapGraph(file, game, player);

	    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	    frame.add(left, BorderLayout.LINE_START);
        left.setPreferredSize(new Dimension(900,0));
        left.setBorder(new LineBorder(Color.white, 10));
        left.add(mapGraph);
        
        frame.add(right);
        right.setBorder(new LineBorder(Color.white, 10));
        right.setLayout(null);
		
		String phase=game.getPhase();
		setUpPhase();
        frame.setVisible(true);
	}
	
	void setUpPhase()
	{
		right.removeAll();
		right.repaint();
		right.revalidate();
		
		JLabel lblSetupPhase = new JLabel("SETUP PHASE");
		lblSetupPhase.setBounds(148, 41, 117, 33);
		right.add(lblSetupPhase);
		
		player.allocateArmies(game);
		currentPlayerName=player.getPlayerTurn();
		
		JLabel lblJulian = new JLabel("Current: "+currentPlayerName);
		lblJulian.setBounds(65, 107, 113, 23);
		right.add(lblJulian);
		
		String color=player.getPlayerColor(currentPlayerName);
		JTextArea textArea = new JTextArea();
		textArea.setBounds(188, 97, 34, 33);
		switch (color) {
		case "green":
			textArea.setBackground(Color.green);
		    break;
		case "magenta":
			textArea.setBackground(Color.magenta);
		    break;
		case "cyan":
			textArea.setBackground(Color.cyan); 
		    break;
		case "pink":
			textArea.setBackground(Color.pink);
		    break;
		case "orange":
			textArea.setBackground(Color.orange); 
		    break;
		case "blue":
			textArea.setBackground(Color.blue); 
		    break;
		default:
		    break;
		}
		right.add(textArea);
		
		JLabel lblArmiesToPlace = new JLabel("Armies to place:");
		lblArmiesToPlace.setBounds(65, 149, 117, 23);
		right.add(lblArmiesToPlace);
		
		String currentArmies=player.getCurrentArmies(currentPlayerName);
		JTextArea textArea_1 = new JTextArea(currentArmies);
		textArea_1.setBounds(188, 139, 34, 33);
		right.add(textArea_1);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(65, 191, 106, 23);
		right.add(lblCountry);
		
		ArrayList<String> currentPlayerCountries=game.getCurrentPlayerCountries(currentPlayerName);
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(65, 214, 157, 23);
		for(int k=0;k<currentPlayerCountries.size();k++)
		{
			comboBox.addItem(currentPlayerCountries.get(k));
		}
		right.add(comboBox);
		
		JButton btnPlace = new JButton("Place");
		btnPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String countrySelected=comboBox.getSelectedItem().toString();
				game.setNumberOfArmies(1, countrySelected);
				player.setNumberOfArmiesSetup(-1, currentPlayerName);
				left.validate();
				left.repaint();
				
				player.setNextTurn();	
				
				int sumArmiesSetup=player.getSumArmiesSetup();
				if(sumArmiesSetup==0)
					reinforcementPhase();
				else
				{
					currentPlayerName=player.getPlayerTurn();
					String currentArmies=player.getCurrentArmies(currentPlayerName);
					while (currentArmies.equals("0")==true)
					{
						player.setNextTurn();	
						currentPlayerName=player.getPlayerTurn();
						currentArmies=player.getCurrentArmies(currentPlayerName);
					}
				
					lblJulian.setText("Current: "+currentPlayerName);
						
					String color=player.getPlayerColor(currentPlayerName);
					switch (color) {
					case "green":
						textArea.setBackground(Color.green);
					    break;
					case "magenta":
					    textArea.setBackground(Color.magenta);
						break;
					case "cyan":
						textArea.setBackground(Color.cyan); 
						break;
					case "pink":
						textArea.setBackground(Color.pink);
						break;
					case "orange":
						textArea.setBackground(Color.orange); 
						break;
					case "blue":
						textArea.setBackground(Color.blue); 
						break;
					default:
						break;
					}
					
					textArea_1.setText(currentArmies);
						
					ArrayList<String> currentPlayerCountries=game.getCurrentPlayerCountries(currentPlayerName);
					comboBox.removeAllItems();
					for(int k=0;k<currentPlayerCountries.size();k++)
					{
						comboBox.addItem(currentPlayerCountries.get(k));
					}
				}
			}
		});
		btnPlace.setBounds(232, 214, 73, 23);
		right.add(btnPlace);
		
	}
	
	void reinforcementPhase()
	{
		right.removeAll();
		right.repaint();
		right.revalidate();
		
		
		
		JLabel lblReinforcementPhase = new JLabel("REINFORCEMENT PHASE");
		lblReinforcementPhase.setBounds(148, 41, 117, 33);
		right.add(lblReinforcementPhase);
	}
	
}