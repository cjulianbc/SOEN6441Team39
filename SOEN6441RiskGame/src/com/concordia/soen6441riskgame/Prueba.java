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
import java.awt.Component;
import javax.swing.border.LineBorder;

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
		setBounds(100, 100, 367, 217);
		right = new JPanel();
		right.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setContentPane(right);
		right.setLayout(null);
		
		JLabel lblSetupPhase = new JLabel("WORLD DOMINATION VIEW");
		lblSetupPhase.setBounds(111, 11, 205, 33);
		right.add(lblSetupPhase);
		
		JLabel lblMapCntrol = new JLabel("Map Control");
		lblMapCntrol.setBounds(44, 55, 103, 33);
		right.add(lblMapCntrol);
		
		JLabel label = new JLabel("0");
		label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label.setBounds(202, 55, 50, 33);
		right.add(label);
		
		JLabel lblContinentsControlled = new JLabel("Continents Controlled");
		lblContinentsControlled.setBounds(44, 76, 152, 33);
		right.add(lblContinentsControlled);
		
		JLabel lblArmyOwned = new JLabel("Army Owned");
		lblArmyOwned.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblArmyOwned.setBounds(44, 136, 115, 33);
		right.add(lblArmyOwned);
		
		JLabel label_4 = new JLabel("0");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_4.setBounds(202, 133, 50, 33);
		right.add(label_4);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(202, 87, 125, 46);
		right.add(textArea);
	}
}
