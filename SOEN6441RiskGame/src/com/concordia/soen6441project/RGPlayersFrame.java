package com.concordia.soen6441project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
	RGPlayer players=new RGPlayer();
	RGGame game;
	RGFile file;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGPlayersFrame frame = new RGPlayersFrame(this.file, this.game);
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
		this.file=file;
		this.game=game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(90, 89, 265, 54);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Write the names of the players separated by commas:");
		lblNewLabel.setBounds(90, 69, 297, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblExampleAmitNesar = new JLabel("Example: Amit, Nesar, Aamrean, Abhishek, Julian...");
		lblExampleAmitNesar.setBounds(90, 147, 297, 14);
		contentPane.add(lblExampleAmitNesar);
		
		JLabel lblPlayers = new JLabel("PLAYERS");
		lblPlayers.setBounds(10, 11, 86, 29);
		contentPane.add(lblPlayers);
		
		JButton btnGo = new JButton("Play");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.assignCountries(players.setPlayers(textArea.getText()));
			}
		});
		btnGo.setBounds(166, 188, 89, 23);
		contentPane.add(btnGo);
	}
}
