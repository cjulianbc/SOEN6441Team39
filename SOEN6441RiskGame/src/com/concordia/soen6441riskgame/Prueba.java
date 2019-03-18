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
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
		setBounds(100, 100, 211, 338);
		right = new JPanel();
		right.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(right);
		right.setLayout(null);
		
		JLabel lblSetupPhase = new JLabel("ATTACK PHASE");
		lblSetupPhase.setBounds(65, 35, 141, 33);
		right.add(lblSetupPhase);
		
		JLabel lblCurrent = new JLabel("Current:");
		lblCurrent.setBounds(35, 91, 113, 23);
		right.add(lblCurrent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 2, 2);
		right.add(scrollPane);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(140, 81, 34, 33);
		right.add(textArea_1);
		
		JLabel lblDoYouWnat = new JLabel("Do you wnat to move more army to");
		lblDoYouWnat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDoYouWnat.setBounds(21, 125, 196, 23);
		right.add(lblDoYouWnat);
		
		JLabel lblYourNewConquered = new JLabel("your new conquered territory?");
		lblYourNewConquered.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblYourNewConquered.setBounds(21, 139, 196, 23);
		right.add(lblYourNewConquered);
		
		JLabel lblAvailable = new JLabel("Available:");
		lblAvailable.setBounds(35, 180, 113, 23);
		right.add(lblAvailable);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(140, 170, 34, 33);
		right.add(textArea);
		
		JLabel lblArmyToMove = new JLabel("Army to move:");
		lblArmyToMove.setBounds(35, 219, 113, 23);
		right.add(lblArmyToMove);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(140, 209, 34, 33);
		right.add(textArea_2);
		
		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMove.setBounds(21, 267, 59, 23);
		right.add(btnMove);
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNo.setBounds(128, 267, 59, 23);
		right.add(btnNo);
	}
}
