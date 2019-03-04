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
import javax.swing.JOptionPane;
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
		
		setUpPhase();
        frame.setVisible(true);
	}
	
	void setUpPhase()
	{
		right.removeAll();
		right.validate();
		right.repaint();
		
		JLabel lblSetupPhase = new JLabel("SETUP PHASE");
		lblSetupPhase.setBounds(148, 41, 117, 33);
		right.add(lblSetupPhase);
		
		player.allocateArmies(game);
		currentPlayerName=player.getPlayerTurn();
		
		JLabel lblPlayer = new JLabel("Current: "+currentPlayerName);
		lblPlayer.setBounds(65, 107, 113, 23);
		right.add(lblPlayer);
		
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
		textArea.setEditable(false);
		
		JLabel lblArmiesToPlace = new JLabel("Armies to place:");
		lblArmiesToPlace.setBounds(65, 149, 117, 23);
		right.add(lblArmiesToPlace);
		
		String currentArmies=player.getCurrentArmies(currentPlayerName);
		JTextArea textArea_1 = new JTextArea(currentArmies);
		textArea_1.setBounds(188, 139, 34, 33);
		right.add(textArea_1);
		textArea_1.setEditable(false);
		
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
				{
					player.initializeTurn();
					reinforcementPhase();
				}
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
				
					lblPlayer.setText("Current: "+currentPlayerName);
						
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
		right.validate();
		right.repaint();

		JLabel lblSetupPhase = new JLabel("REINFORCEMENT PHASE");
		lblSetupPhase.setBounds(119, 40, 179, 33);
		right.add(lblSetupPhase);
		
		currentPlayerName=player.getPlayerTurn();
		JLabel lblPlayer = new JLabel("Current: "+currentPlayerName);
		lblPlayer.setBounds(46, 107, 113, 23);
		right.add(lblPlayer);
		
		String color=player.getPlayerColor(currentPlayerName);
		JTextArea textArea = new JTextArea();
		textArea.setBounds(169, 97, 34, 33);
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
		textArea.setEditable(false);
		
		
		JLabel lblArmiesToPlace = new JLabel("Armies to place:");
		lblArmiesToPlace.setBounds(46, 149, 117, 23);
		right.add(lblArmiesToPlace);
		
		int currentPlayerNumberOfCountries=game.getCurrentPlayerNumberOfCountries(currentPlayerName);
		currentPlayerNumberOfCountries=game.getNumberOfArmiesDueTerritories(currentPlayerNumberOfCountries);
		ArrayList<String> currentPlayerCountries=game.getCurrentPlayerCountries(currentPlayerName);
		currentPlayerNumberOfCountries=currentPlayerNumberOfCountries+(game.getNumberOfArmiesDueContinents(currentPlayerCountries));
		JTextArea textArea_1 = new JTextArea(String.valueOf(currentPlayerNumberOfCountries));
		textArea_1.setBounds(169, 141, 34, 33);
		right.add(textArea_1);
		textArea_1.setEditable(false);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(46, 191, 106, 23);
		right.add(lblCountry);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(46, 214, 157, 23);
		for(int k=0;k<currentPlayerCountries.size();k++)
		{
			comboBox.addItem(currentPlayerCountries.get(k));
		}
		right.add(comboBox);
		
		JLabel lblArmies = new JLabel("Armies:");
		lblArmies.setBounds(225, 211, 106, 23);
		right.add(lblArmies);
		
		JTextArea textArea_2 = new JTextArea("0");
		textArea_2.setBounds(283, 204, 34, 33);
		right.add(textArea_2);
		
		JButton btnPlace = new JButton("Place");
		btnPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int armiesLeft=0;
				if(Integer.valueOf(textArea_2.getText())<=Integer.valueOf(textArea_1.getText()) && Integer.valueOf(textArea_2.getText())>0) 
				{
					game.setNumberOfArmies(Integer.valueOf(textArea_2.getText()), comboBox.getSelectedItem().toString());
					armiesLeft=(Integer.valueOf(textArea_1.getText()))-(Integer.valueOf(textArea_2.getText()));
					textArea_1.setText(String.valueOf(armiesLeft));
					textArea_2.setText("0");
					left.validate();
					left.repaint();
				}
				else
				{
					armiesLeft=(Integer.valueOf(textArea_1.getText()))-(Integer.valueOf(textArea_2.getText()));
					JOptionPane.showMessageDialog(null, "Number of armies not valid", "Alert Message", JOptionPane.WARNING_MESSAGE);
				}
					
				if(armiesLeft==0)
					fortificationPhase();
			}
				
		});
		btnPlace.setBounds(46, 248, 73, 23);
		right.add(btnPlace);
	}
	
	void fortificationPhase()
	{
		right.removeAll();
		right.repaint();
		right.validate();

		JLabel lblSetupPhase = new JLabel("FORTIFICATION");
		lblSetupPhase.setBounds(119, 40, 179, 33);
		right.add(lblSetupPhase);
	}
	
}
