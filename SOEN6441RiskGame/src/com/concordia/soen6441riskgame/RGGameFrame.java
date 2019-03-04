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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * Class that creates a frame with two panels. On the left panel, players can
 * see the map, and its current status. On the right panel, players can interact
 * with the application; in other words, where players can play Risk�. Players
 * can play all the phases on this frame.
 * 
 * 
 * @author Julian Beltran, Nesar, Amit
 * @version 1.0
 * @since 1.0
 *
 */
public class RGGameFrame extends JFrame {

	private JPanel right = new JPanel(new GridLayout(1, 1));
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

	/**
	 * This constructor set the frame and the panels. On the left panel the
	 * constructor add the drawing of the map that is created in the class
	 * RGMapGraph.
	 * 
	 * 
	 * @param File    File where the Risk� map was extracted from
	 * @param Game    Current game
	 * @param Current set of players
	 * 
	 */
	RGGameFrame(RGFile file, RGGame game, RGPlayer player) {
		this.game = game;
		this.file = file;
		this.player = player;
		mapGraph = new RGMapGraph(file, game, player);

		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		frame.add(left, BorderLayout.LINE_START);
		left.setPreferredSize(new Dimension(900, 0));
		left.setBorder(new LineBorder(Color.white, 10));
		left.add(mapGraph);

		frame.add(right);
		right.setBorder(new LineBorder(Color.white, 10));
		right.setLayout(null);

		setUpPhase();
		frame.setVisible(true);
	}

	/**
	 * This method is used to create the Setup Phase. This method draws all the
	 * components of this phase on the right panel of the frame.
	 * 
	 * 
	 */
	void setUpPhase() {
		right.removeAll();
		right.validate();
		right.repaint();

		JLabel lblSetupPhase = new JLabel("SETUP PHASE");
		lblSetupPhase.setBounds(148, 41, 117, 33);
		right.add(lblSetupPhase);

		player.allocateArmies(game);
		currentPlayerName = player.getPlayerTurn();

		JLabel lblPlayer = new JLabel("Current: " + currentPlayerName);
		lblPlayer.setBounds(65, 107, 113, 23);
		right.add(lblPlayer);

		String color = player.getPlayerColor(currentPlayerName);
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

		String currentArmies = player.getCurrentArmies(currentPlayerName);
		JTextArea textArea_1 = new JTextArea(currentArmies);
		textArea_1.setBounds(188, 139, 34, 33);
		right.add(textArea_1);
		textArea_1.setEditable(false);

		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(65, 191, 106, 23);
		right.add(lblCountry);

		ArrayList<String> currentPlayerCountries = game.getCurrentPlayerCountries(currentPlayerName);
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(65, 214, 157, 23);
		for (int k = 0; k < currentPlayerCountries.size(); k++) {
			comboBox.addItem(currentPlayerCountries.get(k));
		}
		right.add(comboBox);

		JButton btnPlace = new JButton("Place");
		btnPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String countrySelected = comboBox.getSelectedItem().toString();
				game.setNumberOfArmies(1, countrySelected);
				player.setNumberOfArmiesSetup(-1, currentPlayerName);
				left.validate();
				left.repaint();

				player.setNextTurn();

				int sumArmiesSetup = player.getSumArmiesSetup();

				if (sumArmiesSetup == 0) {
					player.initializeTurn();
					reinforcementPhase();
				} else {
					currentPlayerName = player.getPlayerTurn();
					String currentArmies = player.getCurrentArmies(currentPlayerName);
					while (currentArmies.equals("0") == true) {
						player.setNextTurn();
						currentPlayerName = player.getPlayerTurn();
						currentArmies = player.getCurrentArmies(currentPlayerName);
					}

					lblPlayer.setText("Current: " + currentPlayerName);

					String color = player.getPlayerColor(currentPlayerName);
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

					ArrayList<String> currentPlayerCountries = game.getCurrentPlayerCountries(currentPlayerName);
					comboBox.removeAllItems();
					for (int k = 0; k < currentPlayerCountries.size(); k++) {
						comboBox.addItem(currentPlayerCountries.get(k));
					}
				}
			}
		});
		btnPlace.setBounds(232, 214, 73, 23);
		right.add(btnPlace);

	}

	/**
	 * This method is used to create the Reinforcement Phase. This method draws all
	 * the components of this phase on the right panel of the frame.
	 * 
	 * 
	 */
	void reinforcementPhase() {
		right.removeAll();
		right.validate();
		right.repaint();

		JLabel lblSetupPhase = new JLabel("REINFORCEMENT PHASE");
		lblSetupPhase.setBounds(119, 40, 179, 33);
		right.add(lblSetupPhase);

		currentPlayerName = player.getPlayerTurn();
		JLabel lblPlayer = new JLabel("Current: " + currentPlayerName);
		lblPlayer.setBounds(46, 107, 113, 23);
		right.add(lblPlayer);

		String color = player.getPlayerColor(currentPlayerName);
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

		int currentPlayerNumberOfCountries = game.getCurrentPlayerNumberOfCountries(currentPlayerName);
		currentPlayerNumberOfCountries = game.getNumberOfArmiesDueTerritories(currentPlayerNumberOfCountries);
		ArrayList<String> currentPlayerCountries = game.getCurrentPlayerCountries(currentPlayerName);
		currentPlayerNumberOfCountries = currentPlayerNumberOfCountries
				+ (game.getNumberOfArmiesDueContinents(currentPlayerCountries));
		JTextArea textArea_1 = new JTextArea(String.valueOf(currentPlayerNumberOfCountries));
		textArea_1.setBounds(169, 141, 34, 33);
		right.add(textArea_1);
		textArea_1.setEditable(false);

		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(46, 191, 106, 23);
		right.add(lblCountry);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(46, 214, 157, 23);
		for (int k = 0; k < currentPlayerCountries.size(); k++) {
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
				int armiesLeft = 0;
				if (Integer.valueOf(textArea_2.getText()) <= Integer.valueOf(textArea_1.getText())
						&& Integer.valueOf(textArea_2.getText()) > 0) {
					game.setNumberOfArmies(Integer.valueOf(textArea_2.getText()),
							comboBox.getSelectedItem().toString());
					armiesLeft = (Integer.valueOf(textArea_1.getText())) - (Integer.valueOf(textArea_2.getText()));
					textArea_1.setText(String.valueOf(armiesLeft));
					textArea_2.setText("0");
					left.validate();
					left.repaint();
				} else {
					armiesLeft = (Integer.valueOf(textArea_1.getText())) - (Integer.valueOf(textArea_2.getText()));
					JOptionPane.showMessageDialog(null, "Number of armies not valid", "Alert Message",
							JOptionPane.WARNING_MESSAGE);
				}

				if (armiesLeft == 0)
					fortificationPhase();
			}

		});
		btnPlace.setBounds(46, 248, 73, 23);
		right.add(btnPlace);
	}

	/**
	 * This method is used to create the Fortification Phase. This method draws all
	 * the components of this phase on the right panel of the frame.
	 * 
	 * 
	 */
	void fortificationPhase() {
		// Clear and ready the right view for the fortificationPhase
		right.removeAll();
		right.repaint();
		right.revalidate();

		// Gets the player name below
		currentPlayerName = player.getPlayerTurn();

		// ----------------st here---------------------

		// print the number of armies available in a country
		JLabel lblNewLabel_1 = new JLabel(); // @ print No.of armies
		lblNewLabel_1.setEnabled(false); // makes editable or not
		lblNewLabel_1.setBounds(169, 197, 117, 23); // 46, 191,
		right.add(lblNewLabel_1);

		JLabel lblAvaiableArmy = new JLabel("Avaiable Army");
		lblAvaiableArmy.setBounds(46, 197, 86, 14);
		right.add(lblAvaiableArmy);

		JLabel lblConnectedCountriesOf = new JLabel("Connected Countries of the selected player");
		lblConnectedCountriesOf.setBounds(46, 245, 115, 14);
		right.add(lblConnectedCountriesOf);

		JLabel lblNoOfArmy = new JLabel("No. of Army Moving");
		lblNoOfArmy.setBounds(46, 293, 115, 14);
		right.add(lblNoOfArmy);

		JLabel lblSetupPhase = new JLabel("FORTIFICATION PHASE");
		lblSetupPhase.setBounds(119, 40, 179, 33);
		right.add(lblSetupPhase);

		currentPlayerName = player.getPlayerTurn();
		JLabel lblPlayer = new JLabel("Current: " + currentPlayerName);
		lblPlayer.setBounds(46, 107, 113, 23);
		right.add(lblPlayer);

		String color = player.getPlayerColor(currentPlayerName);
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

		// owned country Title
		JLabel lblOwnedCountries = new JLabel("Owned Countries");
		lblOwnedCountries.setBounds(46, 149, 100, 14);
		right.add(lblOwnedCountries);

		// Drop down box (Array list) of the countries held by the player
		ArrayList<String> currentPlayerCountries = game.getCurrentPlayerCountries(currentPlayerName);
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(169, 147, 166, 20);
		for (int k = 0; k < currentPlayerCountries.size(); k++) {
			comboBox.addItem(currentPlayerCountries.get(k));
		}
		right.add(comboBox);
		country = comboBox.getSelectedItem().toString(); // "country" is the current selected Item, it will be used to
															// populate the no.of army available
		lblNewLabel_1.setText(game.getArmies(country)); // setting the army in the field in real time passing country as
														// its parameter

		listcountry = new ArrayList<String>();

		// Adjacent countries of the player X. changes according to first drop down box.

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(169, 247, 163, 20);
		right.add(comboBox_1);
		// --------------------------------------------

		// listcountry is an ArrayList that has all the adjacent player's countries of
		// that player
		listcountry = game.getCurrentPlayerAdjacentCountries(currentPlayerName, country);
		// Adding each of the arraylist item in the combobox
		for (int k = 0; k < listcountry.size(); k++) {
			comboBox_1.addItem(listcountry.get(k));
		}

		// ---------------------------------------------
		// Item listener for the drop down box changes of country (ComboBox)
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				country = comboBox.getSelectedItem().toString();// "country" is the current selected Item, it will be
																// used to populate the no.of army available
				lblNewLabel_1.setText(game.getArmies(country)); // setting the army in the field in realtime passing
																// country as its parameter
				ArrayList<String> listcountry = game.getCurrentPlayerAdjacentCountries(currentPlayerName, country);
				comboBox_1.removeAllItems(); // Clearing all the items from the current comboBox_1 for the new items.
				for (int k = 0; k < listcountry.size(); k++) {
					comboBox_1.addItem(listcountry.get(k));
				}
			}

		});

		// number of army the player going to move, must be ("No. of Army < avaiable
		// army")
		JTextField textField = new JTextField();
		textField.setBounds(169, 297, 166, 20);
		right.add(textField);
		textField.setColumns(10);
		textField.setText("0");

		// Button
		JButton btnFinish = new JButton("Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = Integer.parseInt(textField.getText());
				int asrt = Integer.parseInt(lblNewLabel_1.getText());
				if (result >= asrt || result < 0) {
					textField.setText(null);
					JOptionPane.showMessageDialog(null,
							"No. of Army can not be greater or equl than avaiable army or less than 0");
				} else {
					movetocountry = comboBox_1.getSelectedItem().toString();
					game.setNumberOfArmies(result, movetocountry);
					game.setNumberOfArmies(-result, country);
					left.validate();
					left.repaint();
					JOptionPane.showMessageDialog(null,
							"Fortification Successful for the player: " + currentPlayerName);
					player.setNextTurn();
					reinforcementPhase();
				}
			}
		});
		btnFinish.setBounds(169, 341, 89, 23);
		right.add(btnFinish);

	}

}
