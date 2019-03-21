package com.concordia.soen6441riskgame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * Class that creates a panel to show the "Players World Domination View" with the following information:
 * 
 * 1) The percentage of the map controlled by every player.
 * 2) The continents controlled by every player.
 * 3) The total number of armies owned by every player.
 * 
 * This is the right south panel of the main frame. 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGDominationView extends JPanel implements Observer{
	
	/**
	 * This method is called when something has changed in the game (class RGGame). This method creates a frame with the following information:  
     *   
     * 1) The percentage of the map controlled by every player.
     * 2) The continents controlled by every player.
     * 3) The total number of armies owned by every player.
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
		setLayout(null);
		setPreferredSize(new Dimension(375,195));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		RGPlayer players=RGPlayer.getPlayers();
		String currentPlayerName=players.getPlayerTurn();//getting current turn
		
		JLabel lblSetupPhase = new JLabel("WORLD DOMINATION VIEW");
		lblSetupPhase.setBounds(111, 11, 205, 33);
		add(lblSetupPhase);
		
		JLabel lblMapCntrol = new JLabel("Map Control");
		lblMapCntrol.setBounds(44, 55, 103, 33);
		add(lblMapCntrol);
		
		int percentageControlled=((RGGame) game).percentageMapControlledByPlayer(currentPlayerName);
		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label.setBounds(202, 55, 50, 33);
		label.setText(String.valueOf(percentageControlled)+"%");
		add(label);
		
		JLabel lblContinentsControlled = new JLabel("Continents Controlled");
		lblContinentsControlled.setBounds(44, 76, 152, 33);
		add(lblContinentsControlled);
		
		JLabel lblArmyOwned = new JLabel("Army Owned");
		lblArmyOwned.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblArmyOwned.setBounds(44, 136, 115, 33);
		add(lblArmyOwned);
		
		JLabel label_4 = new JLabel();
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_4.setBounds(202, 133, 50, 33);
		label_4.setText(((RGGame) game).getTotalNumberOfArmies(currentPlayerName));
		add(label_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(202, 87, 125, 46);
		add(scrollPane);
		
		StringBuilder continentsOwned;
		continentsOwned=((RGGame) game).getContinentsOwnedByPlayer(currentPlayerName);
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		if(continentsOwned.length()==0)
			textArea.setText(String.valueOf("None"));
		else
			textArea.setText(String.valueOf(continentsOwned));
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
	}

}
