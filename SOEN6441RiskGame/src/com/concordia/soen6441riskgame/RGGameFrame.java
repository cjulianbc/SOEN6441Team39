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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	String country;
	String movetocountry;
	ArrayList<String> adjacentcountries;
	ArrayList<String> listcountry;

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
				{
					reinforcementPhase();
					fortificationPhase();
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
	
	void fortificationPhase()
	{
		// Clear and ready the right view for the fortificationPhase
		right.removeAll();
		right.repaint();
		right.revalidate();
		
		//-------------------------------------
		//Gets the player name below
		currentPlayerName=player.getPlayerTurn();
		
		//Player Name and State Name
		JLabel NameNState = new JLabel("Fortification Phase of "+currentPlayerName);
		NameNState.setBounds(10, 11, 198, 14);
		right.add(NameNState);
		
		//print the number of armies available in a country 
		JLabel lblNewLabel_1 = new JLabel(); // @ print No.of armies
		lblNewLabel_1.setEnabled(false); //makes editable or not
		lblNewLabel_1.setBounds(132, 96, 115, 14);		
		right.add(lblNewLabel_1);
		
		
		//Drop down box (Array list) of the countries held by the player
		ArrayList<String> currentPlayerCountries=game.getCurrentPlayerCountries(currentPlayerName);
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(132, 51, 166, 20);
		for(int k=0;k<currentPlayerCountries.size();k++)
		{
			comboBox.addItem(currentPlayerCountries.get(k));
		}
		right.add(comboBox);
		country = comboBox.getSelectedItem().toString(); // "country" is the current selected Item, it will be used to populate the no.of army available
		lblNewLabel_1.setText(game.getArmies(country)); // setting the army in the field in realtime passing country as its parameter
		
		
		
		listcountry= new ArrayList<String>();
		
		
		// Adjacent countries of the player X. changes according to first dropdown box.
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(135, 132, 163, 20);
		right.add(comboBox_1);
		//--------------------------------------------
		
		//listcountry is an ArrayList that has all the adjacent player's countries of that player
		listcountry = game.getCurrentPlayerAdjacentCountries(currentPlayerName, country);
		//Adding each of the arraylist item in the combobox
		for(int k=0;k<listcountry.size();k++)
		{
			comboBox_1.addItem(listcountry.get(k));
		}
		
		//---------------------------------------------
		//Item listener for the drop down box changes of country (ComboBox)
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						country = comboBox.getSelectedItem().toString();// "country" is the current selected Item, it will be used to populate the no.of army available
						lblNewLabel_1.setText(game.getArmies(country)); //setting the army in the field in realtime passing country as its parameter
						ArrayList<String> listcountry = game.getCurrentPlayerAdjacentCountries(currentPlayerName, country);
						comboBox_1.removeAllItems(); // Clearing all the items from the current comboBox_1 for the new items. 
						for(int k=0;k<listcountry.size();k++)
						{
							comboBox_1.addItem(listcountry.get(k));
						}
					}
					
				});
				
				//number of army the player going to move, must be ("No. of Army < avaiable army")
				JTextField textField = new JTextField();
				textField.setBounds(132, 175, 166, 20);
				right.add(textField);
				textField.setColumns(10);
				textField.setText("0");
				
				
				
				//Button
				JButton btnFinish = new JButton("Finish");
				btnFinish.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int result = Integer.parseInt(textField.getText());
						int asrt = Integer.parseInt(lblNewLabel_1.getText());
						if (result>=asrt||result<0) {
							textField.setText(null);
						JOptionPane.showMessageDialog(null, "No. of Army can not be greater or equl than avaiable army or less than 0");
						}else {
							movetocountry = comboBox_1.getSelectedItem().toString();
							game.setNumberOfArmies(result,movetocountry);
							game.setNumberOfArmies(-result,country);
							left.validate();
							left.repaint();
							JOptionPane.showMessageDialog(null, "Fortification Successful for the player: " + currentPlayerName);
							player.setNextTurn();
							reinforcementPhase();
						}
					}
				});
				btnFinish.setBounds(209, 227, 89, 23);
				right.add(btnFinish);
		
		// ------------------------JLabel-------------------------------------------------
		
		//owned country Title
		JLabel lblOwnedCountries = new JLabel("Owned Countries");
		lblOwnedCountries.setBounds(10, 54, 100, 14);
		right.add(lblOwnedCountries);

		
		JLabel lblAvaiableArmy = new JLabel("Avaiable Army");
		lblAvaiableArmy.setBounds(10, 96, 86, 14);
		right.add(lblAvaiableArmy);
		
		JLabel lblConnectedCountriesOf = new JLabel("Connected Countries of the selected player");
		lblConnectedCountriesOf.setBounds(10, 135, 115, 14);
		right.add(lblConnectedCountriesOf);
		
		JLabel lblNoOfArmy = new JLabel("No. of Army Moving");
		lblNoOfArmy.setBounds(10, 178, 115, 14);
		right.add(lblNoOfArmy);
		//---------------------------------------
	}
	
}
