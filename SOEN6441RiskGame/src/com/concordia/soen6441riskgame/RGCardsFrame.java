package com.concordia.soen6441riskgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

/**
 * Class that creates a frame that shows up on the screen when a player clicks on use Cards in Reinforcement Phase. In this frame, players 
 * are able to exchange cards for armies.
 * 
 * 
 * @author Aamrean,Abhishek
 * @version 1.0
 * @since   1.0
 *
 */
public class RGCardsFrame extends JFrame {

	private JPanel selectCardsPanel;
	RGPlayer players=RGPlayer.getPlayers();
	RGGame game=RGGame.getGame();

	/**
	 * This constructor sets the frame and shows all the available cards for the player .
     *   
     *   
	 */
	public RGCardsFrame(JTextArea totalArmiesAvailable, String currentPlayerName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 405, 309);
		selectCardsPanel = new JPanel();
		selectCardsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(selectCardsPanel);
		selectCardsPanel.setLayout(null);
		
		DefaultListModel model = new DefaultListModel();
		JList list = new JList(model);
		
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(81, 100, 197, 118);
		selectCardsPanel.add(scrollPane);
		
		
		ArrayList<String> playerCards=((RGGame) game).getPlayerCards(currentPlayerName);
			for(int i=0;i<playerCards.size();i++) {
				if(playerCards.get(i).equals("0"))
					model.add(i, "Infantry");
				else if(playerCards.get(i).equals("1"))
					model.add(i, "Cavalry");
				else if(playerCards.get(i).equals("2"))
					model.add(i, "Artillery");
				else
					model.add(i, "Wild");
				
			}
		
		
		JLabel lblSetupPhase = new JLabel("Reinforcement Phase");
		lblSetupPhase.setBounds(91, 13, 150, 33);
		lblSetupPhase.setFont(new Font("Tahoma", Font.BOLD, 13));
		selectCardsPanel.add(lblSetupPhase);
		
		JLabel lblCurrent = new JLabel("Current: "+currentPlayerName);
		lblCurrent.setBounds(42, 46, 113, 23);
		selectCardsPanel.add(lblCurrent);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(117, 46, 34, 33);
		selectCardsPanel.add(textArea_1);
		
		String color=players.getPlayerColor(currentPlayerName);
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
		
		JLabel lblDoYouWnat = new JLabel("Please select 3 same or 3 different cards from the below list");
		lblDoYouWnat.setBounds(42, 76, 333, 23);
		lblDoYouWnat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		selectCardsPanel.add(lblDoYouWnat);
		
		JButton btnMove = new JButton("Use Cards");
		btnMove.setBounds(91, 226, 109, 23);
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> selectedCards=list.getSelectedValuesList();
				if(selectedCards.size()!=3) {
					JOptionPane.showMessageDialog(null, "Please select 3 cards only", "Alert Message", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(((RGGame) game).validateCards(selectedCards)) {
						int oldArmy=Integer.parseInt(totalArmiesAvailable.getText());
						
						int newArmy=players.setNumberOfArmiesForUsedCard(currentPlayerName, oldArmy);
						totalArmiesAvailable.setText(String.valueOf(newArmy));
						
						((RGGame) game).removePlayerCards(currentPlayerName, selectedCards);
						
						ArrayList<String> playerCards=((RGGame) game).getPlayerCards(currentPlayerName);
						System.out.println(playerCards.size());
						model.clear();
							for(int i=0;i<playerCards.size();i++) {
								if(playerCards.get(i).equals("0"))
									model.add(i, "Infantry");
								else if(playerCards.get(i).equals("1"))
									model.add(i, "Cavalry");
								else if(playerCards.get(i).equals("2"))
									model.add(i, "Artillery");
								else
									model.add(i, "Wild");
								
							}
						
						
					}
				}
			}
		});
		selectCardsPanel.add(btnMove);
		
		JButton btnNo = new JButton("Skip");
		btnNo.setBounds(210, 226, 59, 23);
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		selectCardsPanel.add(btnNo);
		
		
		
		
		
	}
}
