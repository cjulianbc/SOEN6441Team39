package com.concordia.soen6441riskgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RGPlayersFrame extends JFrame {

	private JPanel contentPane;
	private RGGame game;
	private RGFile file;
	private RGPlayer players=new RGPlayer();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGPlayersFrame frame = new RGPlayersFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
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
		
		JLabel lblNewLabel = new JLabel("Write the names of the players:");
		lblNewLabel.setBounds(71, 69, 297, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblExampleAmitNesar = new JLabel("Example: Nesar,Julian,Amit ");
		lblExampleAmitNesar.setBounds(71, 117, 297, 14);
		contentPane.add(lblExampleAmitNesar);
		
		JLabel lblPlayers = new JLabel("PLAYERS");
		lblPlayers.setBounds(10, 11, 86, 29);
		contentPane.add(lblPlayers);
		
		JButton btnGo = new JButton("Play");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.assignCountries(players.setPlayers(textArea.getText()));				
				RGGameFrame gameFrame=new RGGameFrame(file, game, players);
			}
		});
		btnGo.setBounds(148, 142, 89, 23);
		contentPane.add(btnGo);
	}
}
