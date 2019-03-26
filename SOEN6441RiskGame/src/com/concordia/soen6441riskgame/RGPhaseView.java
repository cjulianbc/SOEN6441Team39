package com.concordia.soen6441riskgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * Class that creates a panel to show the "Phase View" with the following information:
 * 
 * 1) Name of the game phase currently being played. 
 * 2) Current name of the player.
 * 3) Information about actions that are taking place during current phase.
 * 
 * This is the right north panel of the main frame. 
 * 
 * 
 * @author Julian Beltran, Abhishek, Aamrean
 * @version 1.0
 * @since   1.0
 *
 */
public class RGPhaseView extends JPanel implements Observer{
	
	/**
	 * This method is called when something has changed in the game (class RGGame). This method creates a frame with the following information:  
     *   
     * 1) Name of the game phase currently being played. 
     * 2) Current name of the player.
     * 3) Information about actions that are taking place during current phase.
     *   
     * @param game Current game (the observable object).
     * @param arg A different object passed as argument. It does no have any use in this application. 
     *   
	 */
	@Override
	public void update(Observable game, Object arg)
	{
		removeAll();
		validate();
		repaint();
		
		String phase=((RGGame)game).getPhase();
		RGPlayer players=RGPlayer.getPlayers();
		setLayout(null);
		setPreferredSize(new Dimension(375,445));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		if(phase.contentEquals("Setup"))
		{
			JLabel lblSetupPhase = new JLabel("SETUP PHASE");
			lblSetupPhase.setBounds(148, 11, 117, 33);
			add(lblSetupPhase);
			
			String currentPlayerName=players.getPlayerTurn();//getting current turn
			JLabel lblPlayer=new JLabel();
			lblPlayer.setText("Current: "+currentPlayerName);
			lblPlayer.setBounds(65, 57, 113, 23);
			add(lblPlayer);
			
			String color=players.getPlayerColor(currentPlayerName);//getting the color of the player
			JTextArea textArea = new JTextArea();
			textArea.setBounds(188, 47, 34, 33);
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
			add(textArea);
			textArea.setEditable(false);
			
			JLabel lblArmiesToPlace = new JLabel("Armies to place:");
			lblArmiesToPlace.setBounds(65, 99, 117, 23);
			add(lblArmiesToPlace);
			
			String currentArmies=players.getCurrentArmies(currentPlayerName);//getting armies available to place 
			JTextArea textArea_1 = new JTextArea(currentArmies);
			textArea_1.setBounds(188, 89, 34, 33);
			add(textArea_1);
			textArea_1.setEditable(false);
			
			JLabel lblCountry = new JLabel("Country:");
			lblCountry.setBounds(65, 141, 106, 23);
			add(lblCountry);
			
			ArrayList<String> currentPlayerCountries=((RGGame) game).getCurrentPlayerCountries(currentPlayerName);
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(65, 164, 157, 23);
			for(int k=0;k<currentPlayerCountries.size();k++)//adding all the countries owned by the player to the combo
			{
				comboBox.addItem(currentPlayerCountries.get(k));
			}
			add(comboBox);
			
			JButton btnPlace = new JButton("Place");
			btnPlace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selectedCountry=comboBox.getSelectedItem().toString();
					((RGGame) game).setupPhase(selectedCountry, currentPlayerName);
				}
			});
			btnPlace.setBounds(232, 164, 73, 23);
			add(btnPlace);
		}
		
		if(phase.contentEquals("Reinforcement"))
		{
			JLabel lblSetupPhase = new JLabel("REINFORCEMENT PHASE");
			lblSetupPhase.setBounds(119, 10, 179, 33);
			add(lblSetupPhase);
			
			String currentPlayerName=players.getPlayerTurn();//getting current turn
			JLabel lblPlayer = new JLabel("Current: "+currentPlayerName);
			lblPlayer.setBounds(46, 77, 113, 23);
			add(lblPlayer);
			
			String color=players.getPlayerColor(currentPlayerName);//getting the color of the player
			JTextArea textArea = new JTextArea();
			textArea.setBounds(169, 67, 34, 33);
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
			add(textArea);
			textArea.setEditable(false);
			
			
			JLabel lblArmiesToPlace = new JLabel("Armies to place:");
			lblArmiesToPlace.setBounds(46, 119, 117, 23);
			add(lblArmiesToPlace);
			
			
			//getting number of armies to reinforce
			String numberOfArmiesForReinforcement=players.getNumberOfArmiesForReinforcement(currentPlayerName);
			if (numberOfArmiesForReinforcement.contentEquals("0"))//first time to reinforce? 
			{
				players.setNumberOfArmiesForReinforcement(currentPlayerName);
				numberOfArmiesForReinforcement=players.getNumberOfArmiesForReinforcement(currentPlayerName);
			}
				
			JTextArea totalArmiesAvailable = new JTextArea(numberOfArmiesForReinforcement);
			totalArmiesAvailable.setBounds(169, 111, 34, 33);
			add(totalArmiesAvailable);
			totalArmiesAvailable.setEditable(false);
			
			JLabel lblCountry = new JLabel("Country:");
			lblCountry.setBounds(46, 161, 106, 23);
			add(lblCountry);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(46, 184, 157, 23);
			ArrayList<String> currentPlayerCountries=((RGGame) game).getCurrentPlayerCountries(currentPlayerName);
			for(int k=0;k<currentPlayerCountries.size();k++)//adding all the countries owned by the player to the combo
			{
				comboBox.addItem(currentPlayerCountries.get(k));
			}
			add(comboBox);
			
			JLabel lblArmies = new JLabel("Armies:");
			lblArmies.setBounds(225, 181, 106, 23);
			add(lblArmies);
			
			JTextArea armiesToPlace = new JTextArea("0");
			armiesToPlace.setBounds(283, 174, 34, 33);
			add(armiesToPlace);
			
			JButton btnPlace = new JButton("Place");
			btnPlace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((RGGame) game).getPlayerCards(currentPlayerName).size()<5) {
						
						if(Integer.valueOf(armiesToPlace.getText())<=Integer.valueOf(totalArmiesAvailable.getText()) && Integer.valueOf(armiesToPlace.getText())>0)//player can place between one and the max number of armies available for Reinforcement Phase 
						{
							String selectedCountry=comboBox.getSelectedItem().toString();
							((RGGame) game).reinforcementPhase(selectedCountry, currentPlayerName, armiesToPlace.getText());
						}
						else
							JOptionPane.showMessageDialog(null, "Invalid number of armies", "Alert Message", JOptionPane.WARNING_MESSAGE);
						}
						
						else 
							JOptionPane.showMessageDialog(null, "Use available cards first", "Alert Message", JOptionPane.WARNING_MESSAGE);
						
						}
					
			});
			btnPlace.setBounds(46, 218, 73, 23);
			add(btnPlace);
			
			JButton btnUseCards = new JButton("Use Cards");
			btnUseCards.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(((RGGame) game).getPlayerCards(currentPlayerName).size()!=0){
						RGCardsFrame frame = new RGCardsFrame(totalArmiesAvailable,currentPlayerName);
						frame.setVisible(true);
			}
					else {
						JOptionPane.showMessageDialog(null, "No Cards available", "Alert Message", JOptionPane.WARNING_MESSAGE);
					}
					
				}
					
			});
			btnUseCards.setBounds(130, 218, 100, 23);
			add(btnUseCards);
			
			JLabel lblActions = new JLabel("Actions:");
			lblActions.setBounds(33, 300, 106, 23);
			add(lblActions);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(33, 322, 290, 95);
			add(scrollPane);
			
			String actionsReinforcementPhase=players.getActionsPerformed(currentPlayerName, "reinforcement");
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 10));
			textArea_1.setText("");
			textArea_1.setText(actionsReinforcementPhase);
			scrollPane.setViewportView(textArea_1);
		}
		
		if(phase.contentEquals("Attack"))
		{
			JLabel lblSetupPhase = new JLabel("ATTACK PHASE");
			lblSetupPhase.setBounds(134, 10, 141, 33);
			add(lblSetupPhase);
			
			String currentPlayerName=players.getPlayerTurn();//getting current turn
			JLabel lblCurrent = new JLabel("Current: "+currentPlayerName);
			lblCurrent.setBounds(87, 65, 113, 23);
			add(lblCurrent);
			
			String color=players.getPlayerColor(currentPlayerName);//getting the color of the player
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setBounds(205, 54, 34, 33);
			switch (color) {
			case "green":
				textArea_1.setBackground(Color.green);
			    break;
			case "magenta":
				textArea_1.setBackground(Color.magenta);
			    break;
			case "cyan":
				textArea_1.setBackground(Color.cyan); 
			    break;
			case "pink":
				textArea_1.setBackground(Color.pink);
			    break;
			case "orange":
				textArea_1.setBackground(Color.orange); 
			    break;
			case "blue":
				textArea_1.setBackground(Color.blue); 
			    break;
			default:
			    break;
			}
			add(textArea_1);
			
			JLabel lblPlayer = new JLabel("Attacker");
			lblPlayer.setBounds(76, 108, 113, 23);
			add(lblPlayer);
			
			JLabel lblCountry = new JLabel("From:");
			lblCountry.setBounds(33, 142, 106, 23);
			
			JLabel lblDefender = new JLabel("Defender");
			lblDefender.setBounds(233, 108, 113, 23);
			add(lblDefender);
			
			JLabel lblTo = new JLabel("To:");
			lblTo.setBounds(196, 142, 106, 23);
			add(lblTo);
			add(lblCountry);
			
			ArrayList<String> countriesAttacker = new ArrayList<String>();
			countriesAttacker=((RGGame) game).getCountriesAttacker(currentPlayerName);
			JComboBox comboBox = new JComboBox();
			JComboBox comboBox_1 = new JComboBox();
			JComboBox comboBox_2 = new JComboBox();
			JComboBox comboBox_3 = new JComboBox();
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ArrayList<String> countriesDefender = new ArrayList<String>();
					String selectedCountry=(String) comboBox.getSelectedItem();
					countriesDefender=((RGGame) game).getCountriesDefender(selectedCountry,currentPlayerName);
					
					comboBox_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(comboBox_1.getItemCount()!=0)
							{
								ArrayList<String> diceDefender = new ArrayList<String>();
								String selectedCountryDefender=(String) comboBox_1.getSelectedItem();
								diceDefender=((RGGame) game).getDiceDefender(selectedCountryDefender);
								
								JLabel label = new JLabel("Dice:");
								label.setBounds(196, 197, 106, 23);
								add(label);
								
								comboBox_3.removeAllItems(); 
								for(int k=0;k<diceDefender.size();k++)//adding the number of dice for the defender
								{
									comboBox_3.addItem(diceDefender.get(k));
								}
							}
						}
					});
					
					comboBox_1.removeAllItems(); 
					for(int k=0;k<countriesDefender.size();k++)//adding all the countries available to attack
					{
						comboBox_1.addItem(countriesDefender.get(k));
					}
					
					//Setting dice to attack
					ArrayList<String> diceAttacker = new ArrayList<String>();
					diceAttacker=((RGGame) game).getDiceAttacker(selectedCountry);
					
					JLabel lblDice = new JLabel("Dice:");
					lblDice.setBounds(33, 197, 106, 23);
					add(lblDice);
					
					comboBox_2.removeAllItems(); 
					for(int k=0;k<diceAttacker.size();k++)//adding the number of dice for the attacker
					{
						comboBox_2.addItem(diceAttacker.get(k));
					}
				}
			});
			comboBox.setBounds(33, 163, 127, 23);
			comboBox_1.setBounds(194, 163, 127, 23);
			comboBox_2.setBounds(33, 218, 127, 23);
			comboBox_3.setBounds(194, 218, 127, 23);
			for(int k=0;k<countriesAttacker.size();k++)//adding all the countries from where to attack (minimum two army in each country)
			{
				comboBox.addItem(countriesAttacker.get(k));
			}
			add(comboBox);
			add(comboBox_1);
			add(comboBox_2);
			add(comboBox_3);
			
			JButton btnAllOut = new JButton("All Out!");
			JButton btnEnd = new JButton("End");
			JButton btnOneBattle = new JButton("One Battle");
			btnAllOut.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnAllOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//All Out mode
					((RGGame) game).setAllOutModeForAttackPhase(true);
					String selectedCountryAttacker=comboBox.getSelectedItem().toString();
					String selectedCountryDefender=comboBox_1.getSelectedItem().toString();
					String selectedDiceAttacker=comboBox_2.getSelectedItem().toString();
					String selectedDiceDefender=comboBox_3.getSelectedItem().toString();	
					((RGGame) game).attackPhaseModeDecision(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);
				
					//moving army to the captured country?
					if (((RGGame) game).getAttackStatus().contentEquals("move")) 
					{
						if(!((RGGame) game).getArmies(selectedCountryAttacker).contentEquals("1")) {
							RGCapturedTerritoryFrame frame = new RGCapturedTerritoryFrame(selectedCountryAttacker,selectedCountryDefender,currentPlayerName);
							frame.setVisible(true);
						}
						else if(((RGGame) game).getArmies(selectedCountryAttacker).contentEquals("1"))
							((RGGame) game).capturedTerritoriesNoMovements();
					}
				}
			});
			btnAllOut.setBounds(46, 263, 73, 23);
			add(btnAllOut);
			
			btnOneBattle.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnOneBattle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//One battle mode
					String selectedCountryAttacker=comboBox.getSelectedItem().toString();
					String selectedCountryDefender=comboBox_1.getSelectedItem().toString();
					String selectedDiceAttacker=comboBox_2.getSelectedItem().toString();
					String selectedDiceDefender=comboBox_3.getSelectedItem().toString();
					((RGGame) game).attackPhaseModeDecision(selectedCountryAttacker,selectedCountryDefender,selectedDiceAttacker,selectedDiceDefender,currentPlayerName);

					//moving army to the captured country?
					if (((RGGame) game).getAttackStatus().contentEquals("move")) 
					{
						if(!((RGGame) game).getArmies(selectedCountryAttacker).contentEquals("1")) {
							RGCapturedTerritoryFrame frame = new RGCapturedTerritoryFrame(selectedCountryAttacker,selectedCountryDefender,currentPlayerName);
							frame.setVisible(true);
						}
						else if(((RGGame) game).getArmies(selectedCountryAttacker).contentEquals("1"))
							((RGGame) game).capturedTerritoriesNoMovements();
					}
				}
			});
			btnOneBattle.setBounds(134, 263, 85, 23);
			add(btnOneBattle);
			
			btnEnd.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnEnd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((RGGame) game).attackPhaseNoMovements();
				}
			});
			btnEnd.setBounds(233, 263, 73, 23);
			add(btnEnd);
			
			JLabel lblActions = new JLabel("Actions:");
			lblActions.setBounds(33, 300, 106, 23);
			add(lblActions);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(33, 322, 290, 95);
			add(scrollPane);
			
			String actionsAttackPhase=players.getActionsPerformed(currentPlayerName, "attack");
			JTextArea textArea = new JTextArea();
			textArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
			textArea.setText("");
			textArea.setText(actionsAttackPhase);
			scrollPane.setViewportView(textArea);
			
			//no more territories to attack? moving to next phase automatically.
			if(countriesAttacker.size()==0 && !((RGGame) game).getAttackStatus().contentEquals("move") && !((RGGame) game).getAttackStatus().contentEquals("end"))
			{
				((RGGame) game).attackPhaseNoAttackers(currentPlayerName);
			}	
			
			//end of the game? 
			else if (((RGGame) game).getAttackStatus().contentEquals("end")) 
			{
				btnAllOut.setEnabled(false);
				btnOneBattle.setEnabled(false);
				btnEnd.setEnabled(false);
			}
			
		}
		
		if(phase.contentEquals("Fortification"))
		{
			JLabel lblSetupPhase = new JLabel("FORTIFICATION PHASE");
			lblSetupPhase.setBounds(119, 10, 179, 33);
			add(lblSetupPhase);
			
			String currentPlayerName = players.getPlayerTurn();
			JLabel lblPlayer = new JLabel("Current: " + currentPlayerName);
			lblPlayer.setBounds(46, 77, 113, 23);
			add(lblPlayer);

			String color = players.getPlayerColor(currentPlayerName);
			JTextArea textArea = new JTextArea();
			textArea.setBounds(169, 67, 34, 33);
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
			add(textArea);
			textArea.setEditable(false);
			
			JLabel lblOwnedCountries = new JLabel("From:");
			lblOwnedCountries.setBounds(46, 119, 100, 14);
			add(lblOwnedCountries);

			ArrayList<String> currentPlayerCountries = ((RGGame) game).getCurrentPlayerCountries(currentPlayerName);
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(169, 117, 166, 20);
			for (int k = 0; k < currentPlayerCountries.size(); k++) {
				comboBox.addItem(currentPlayerCountries.get(k));
			}
			add(comboBox);
			
			JLabel lblAvaiableArmy = new JLabel("Avaiable army:");
			lblAvaiableArmy.setBounds(46, 152, 86, 14);
			add(lblAvaiableArmy);
			
			String country = comboBox.getSelectedItem().toString(); 
			JLabel lblNewLabel_1 = new JLabel();
			lblNewLabel_1.setText(((RGGame) game).getArmies(country)); 
			lblNewLabel_1.setEnabled(false); 
			lblNewLabel_1.setBounds(169, 152, 117, 23); 
			add(lblNewLabel_1);
			
			JLabel lblConnectedCountriesOf = new JLabel("To:");
			lblConnectedCountriesOf.setBounds(46, 190, 115, 14);
			add(lblConnectedCountriesOf);
			
			JComboBox comboBox_1 = new JComboBox();
			comboBox_1.setBounds(169, 190, 163, 20);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String country = comboBox.getSelectedItem().toString();// "country" is the current selected Item, it will be used to populate the no.of army available
					lblNewLabel_1.setText(((RGGame) game).getArmies(country)); // setting the army in the field in real time passing country as its parameter
					ArrayList<String> listcountrytwo = ((RGGame) game).getCurrentPlayerAdjacentCountries(currentPlayerName, country);
					comboBox_1.removeAllItems(); // Clearing all the items from the current comboBox_1 for the new items.
					for (int k = 0; k < listcountrytwo.size(); k++) {
						comboBox_1.addItem(listcountrytwo.get(k));
					}
				}

			});

			ArrayList<String> listcountry = new ArrayList<String>();
			listcountry = ((RGGame) game).getCurrentPlayerAdjacentCountries(currentPlayerName, country);
			for (int k = 0; k < listcountry.size(); k++) {
				comboBox_1.addItem(listcountry.get(k));
			}
			add(comboBox_1);

			JLabel lblNoOfArmy = new JLabel("Army to move:");
			lblNoOfArmy.setBounds(46, 233, 115, 14);
			add(lblNoOfArmy);

			// number of army the player is going to move. Must be ("No. of Army < available army")
			JTextField textField = new JTextField();
			textField.setBounds(169, 233, 166, 20);
			add(textField);
			textField.setColumns(10);
			textField.setText("0");

			JButton btnFinish = new JButton("Go!");
			btnFinish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int armiesToMove = Integer.parseInt(textField.getText());
					int armiesAvailable = Integer.parseInt(lblNewLabel_1.getText());
					if (armiesToMove >= armiesAvailable || armiesToMove < 0) 
					{
						JOptionPane.showMessageDialog(null, "No. of army can not be greater or equal than avaiable army, or less or equal than 0", "Alert Message", JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						if(comboBox_1.getItemCount()!=0)
						{
							String countryTo=comboBox.getSelectedItem().toString();
							String countryFrom=comboBox_1.getSelectedItem().toString();
							if (armiesToMove!=0)
								((RGGame) game).fortificationPhase(countryFrom, countryTo, armiesToMove);
							else
								((RGGame) game).fortificationPhaseNoMovements();
						}
						else
							((RGGame) game).fortificationPhaseNoMovements();
					}
						
				}
			});
			btnFinish.setBounds(144, 270, 89, 23);
			add(btnFinish);
			
			JLabel lblActions = new JLabel("Actions:");
			lblActions.setBounds(46, 300, 106, 23);
			add(lblActions);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(46, 322, 290, 95);
			add(scrollPane);
			
			String actionsFortificationPhase=players.getActionsPerformed(currentPlayerName, "fortification");
			JTextArea textArea_1 = new JTextArea();
			textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 10));
			textArea_1.setText("");
			textArea_1.setText(actionsFortificationPhase);
			scrollPane.setViewportView(textArea_1);
		}
	}

}
