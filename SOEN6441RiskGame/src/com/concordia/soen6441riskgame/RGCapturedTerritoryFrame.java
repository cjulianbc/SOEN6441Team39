package com.concordia.soen6441riskgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * Class that creates a frame that is shown when a player captures a territory in Attack Phase. In this frame, players 
 * are able to move army to the new captured territory.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGCapturedTerritoryFrame extends JFrame {

	private JPanel moveArmyAttackPhase;
	RGPlayer players=RGPlayer.getPlayers();
	RGGame game=RGGame.getGame();

	/**
	 * This constructor builds a frame where the player can move army from the attacking territory to the captured territory.
     *   
     *   
     * @param selectedCountryAttacker Attacking territory.
     * @param selectedCountryDefender Captured territory.
     * @param currentPlayerName Name of the player
     *   
	 */
	public RGCapturedTerritoryFrame(String selectedCountryAttacker, String selectedCountryDefender, String currentPlayerName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 405, 309);
		moveArmyAttackPhase = new JPanel();
		moveArmyAttackPhase.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(moveArmyAttackPhase);
		moveArmyAttackPhase.setLayout(null);
		
		JLabel lblSetupPhase = new JLabel("ATTACK PHASE");
		lblSetupPhase.setBounds(159, 37, 141, 33);
		moveArmyAttackPhase.add(lblSetupPhase);
		
		JLabel lblCurrent = new JLabel("Current: "+currentPlayerName);
		lblCurrent.setBounds(35, 91, 113, 23);
		moveArmyAttackPhase.add(lblCurrent);
		
		String color=players.getPlayerColor(currentPlayerName);//getting the color of the player
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(140, 81, 34, 33);
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
		textArea_1.setEditable(false);
		moveArmyAttackPhase.add(textArea_1);
		
		JLabel lblDoYouWnat = new JLabel("Do you want to move more army to your new captured territory?");
		lblDoYouWnat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDoYouWnat.setBounds(35, 140, 333, 23);
		moveArmyAttackPhase.add(lblDoYouWnat);
		
		JLabel lblAvailable = new JLabel("Available:");
		lblAvailable.setBounds(35, 180, 113, 23);
		moveArmyAttackPhase.add(lblAvailable);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(140, 170, 34, 33);
		textArea.setText(game.getArmies(selectedCountryAttacker));
		textArea.setEditable(false);
		moveArmyAttackPhase.add(textArea);
		
		JLabel lblArmyToMove = new JLabel("Army to move:");
		lblArmyToMove.setBounds(35, 219, 113, 23);
		moveArmyAttackPhase.add(lblArmyToMove);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(140, 209, 34, 33);
		textArea_2.setText("1");
		moveArmyAttackPhase.add(textArea_2);
		
		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((Integer.valueOf(textArea.getText())-Integer.valueOf(textArea_2.getText()))>=1 &&(Integer.valueOf(textArea_2.getText())>=1))
				{
					game.capturedTerritoriesArmyDeployment(selectedCountryAttacker, selectedCountryDefender, textArea_2.getText(), currentPlayerName);
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Invalid number of armies to deploy", "Alert Message", JOptionPane.WARNING_MESSAGE);
				
			}
		});
		btnMove.setBounds(196, 219, 82, 23);
		moveArmyAttackPhase.add(btnMove);
		
		JButton btnNo = new JButton("Skip");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.capturedTerritoriesNoMovements();
				dispose();
			}
		});
		btnNo.setBounds(288, 219, 59, 23);
		moveArmyAttackPhase.add(btnNo);
	}

}
