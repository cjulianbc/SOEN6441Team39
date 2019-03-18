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
		setBounds(100, 100, 844, 538);
		right = new JPanel();
		right.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(right);
		right.setLayout(null);
		
		JLabel lblSetupPhase = new JLabel("ATTACK PHASE");
		lblSetupPhase.setBounds(134, 40, 141, 33);
		right.add(lblSetupPhase);
		
	
		
		JLabel lblPlayer = new JLabel("Attacker");
		lblPlayer.setBounds(76, 138, 113, 23);
		right.add(lblPlayer);
		
		JLabel lblCountry = new JLabel("From:");
		lblCountry.setBounds(33, 172, 106, 23);
		right.add(lblCountry);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBox.setBounds(33, 193, 127, 23);
		right.add(comboBox);
		
		JButton btnPlace = new JButton("All Out!");
		btnPlace.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlace.setBounds(46, 293, 73, 23);
		right.add(btnPlace);
		
		JLabel lblDefender = new JLabel("Defender");
		lblDefender.setBounds(233, 138, 113, 23);
		right.add(lblDefender);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(196, 172, 106, 23);
		right.add(lblTo);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(194, 193, 127, 23);
		right.add(comboBox_1);
		
		JLabel lblDice = new JLabel("Dice:");
		lblDice.setBounds(33, 227, 106, 23);
		right.add(lblDice);
		
		JLabel label = new JLabel("Dice:");
		label.setBounds(196, 227, 106, 23);
		right.add(label);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(33, 248, 127, 23);
		right.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(194, 248, 127, 23);
		right.add(comboBox_3);
		
		JButton btnOneBattle = new JButton("One Battle");
		btnOneBattle.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnOneBattle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOneBattle.setBounds(134, 293, 85, 23);
		right.add(btnOneBattle);
		
		JButton btnEnd = new JButton("End");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnd.setBounds(233, 293, 73, 23);
		right.add(btnEnd);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
		textArea.setBounds(33, 352, 290, 102);
		right.add(textArea);
		
		JLabel lblActions = new JLabel("Actions:");
		lblActions.setBounds(33, 330, 106, 23);
		right.add(lblActions);
		
		JLabel lblCurrent = new JLabel("Current:");
		lblCurrent.setBounds(87, 95, 113, 23);
		right.add(lblCurrent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 2, 2);
		right.add(scrollPane);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(205, 84, 34, 33);
		right.add(textArea_1);
	}
}
