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
	private RGGame game;
	/**
	 * Created to store the file where a Risk® map is stored.
	 */
	private RGFile file;
	/**
	 * Created to store the set of players of the current game.
	 */
	private RGPlayer players=new RGPlayer();

	/**
	 * This constructor set the frame and add all the objects the user needs to create a set of players.
	 * 
	 * 
	 * @param file File where the Risk® map was extracted from.
     * @param game Current game.
     *   
	 */
	public RGPlayersFrame(RGFile file, RGGame game) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 404, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(71, 90, 263, 29);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Write the names of the players (between 2 and 6):");
		lblNewLabel.setBounds(71, 69, 297, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblExampleAmitNesar = new JLabel("Example: Joe,Anna,Bruce ");
		lblExampleAmitNesar.setBounds(71, 117, 297, 14);
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
					RGGameFrame gameFrame=new RGGameFrame(file, game, players);//creating the game frame where players can play Risk® game
				}
				else
					JOptionPane.showMessageDialog(null, "Invalid number of players. Must be between 2 and 6", "Alert Message", JOptionPane.WARNING_MESSAGE);
				
			}
		});
		btnGo.setBounds(148, 142, 89, 23);
		contentPane.add(btnGo);
	}
}
