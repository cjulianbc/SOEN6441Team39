package com.concordia.soen6441riskgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class creates a frame where the user is asked to enter the names of the players of the Risk® game. Names must be separated by commas.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGPlayersFrame extends JFrame {

	/**
	 * Panel where the players can be created.
	 */
	private JPanel contentPane;
	
	/**
	 * Created to store the information of the current game.
	 */
	private RGGame game=RGGame.getGame();
	
	/**
	 * Created to store the file where a Risk® map is stored.
	 */
	private RGFile file=RGFile.getGameFile();
	
	/**
	 * Created to store the set of players of the current game.
	 */
	private RGPlayer players=RGPlayer.getPlayers();

	/**
	 * This constructor set the frame and add all the objects the user needs to create a set of players.
	 * 
     *   
	 */
	public RGPlayersFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 527, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(71, 100, 368, 29);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Write the names of the players and their behaviors.");
		lblNewLabel.setBounds(71, 67, 297, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblExampleAmitNesar = new JLabel("Example: Joe=aggressive,Anna=random, ... ");
		lblExampleAmitNesar.setBounds(71, 128, 297, 14);
		contentPane.add(lblExampleAmitNesar);
		
		JLabel lblPlayers = new JLabel("PLAYERS");
		lblPlayers.setBounds(10, 11, 86, 29);
		contentPane.add(lblPlayers);
		
		JButton btnGo = new JButton("Play");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(players.validateNumberOfPLayers(textArea.getText()))
				{
					game.assignCountries(players.setPlayers(textArea.getText()));//giving each country an random owner(player)
					RGGameFrame gameFrame=new RGGameFrame();//creating the game frame where players can play Risk® game
					gameFrame.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Invalid number of players. Must be between 2 and 6", "Alert Message", JOptionPane.WARNING_MESSAGE);
				
			}
		});
		btnGo.setBounds(213, 208, 89, 23);
		contentPane.add(btnGo);
		
		JLabel lblbetweenAnd = new JLabel("(between 2 and 6 players):");
		lblbetweenAnd.setBounds(71, 81, 297, 22);
		contentPane.add(lblbetweenAnd);
		
		JLabel lblBehaviorsHumanAggresive = new JLabel("Behaviors: human, aggressive, benevolent,");
		lblBehaviorsHumanAggresive.setBounds(71, 142, 297, 22);
		contentPane.add(lblBehaviorsHumanAggresive);
		
		JLabel lblRandomCheater = new JLabel("random, cheater.");
		lblRandomCheater.setBounds(71, 160, 297, 22);
		contentPane.add(lblRandomCheater);
	}
}
