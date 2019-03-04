package com.concordia.soen6441riskgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Prueba extends JFrame {

	private JPanel right;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba frame = new Prueba();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Prueba() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 538);
		right = new JPanel();
		right.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(right);
		right.setLayout(null);
		
		JLabel lblSetupPhase = new JLabel("REINFORCEMENT PHASE");
		lblSetupPhase.setBounds(119, 40, 179, 33);
		right.add(lblSetupPhase);
		
	
		
		JLabel lblPlayer = new JLabel("Current: ");
		lblPlayer.setBounds(46, 107, 113, 23);
		right.add(lblPlayer);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(169, 97, 34, 33);
		right.add(textArea);
		
		JLabel lblArmiesToPlace = new JLabel("Armies to place:");
		lblArmiesToPlace.setBounds(46, 149, 117, 23);
		right.add(lblArmiesToPlace);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(283, 204, 34, 33);
		right.add(textArea_1);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(46, 191, 106, 23);
		right.add(lblCountry);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(46, 214, 157, 23);
		right.add(comboBox);
		
		JButton btnPlace = new JButton("Place");
		btnPlace.setBounds(46, 248, 73, 23);
		right.add(btnPlace);
		
		JLabel lblArmies = new JLabel("Armies:");
		lblArmies.setBounds(225, 211, 106, 23);
		right.add(lblArmies);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(169, 141, 34, 33);
		right.add(textArea_2);
	}

}
