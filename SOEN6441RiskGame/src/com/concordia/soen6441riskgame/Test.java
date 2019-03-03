package com.concordia.soen6441riskgame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Test extends JFrame {

	private JPanel right;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1059, 632);
		right = new JPanel();
		right.setForeground(Color.BLACK);
		right.setBorder(null);
		setContentPane(right);
		right.setLayout(null);
		
		JLabel lblReinforcementPhase = new JLabel("SETUP PHASE");
		lblReinforcementPhase.setBounds(148, 41, 117, 33);
		right.add(lblReinforcementPhase);
		
		JLabel lblJulian = new JLabel("Current: Julian");
		lblJulian.setBounds(65, 107, 113, 23);
		right.add(lblJulian);
		
		JTextArea textArea = new JTextArea();
		textArea.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lblJulian.setText("Current: Julian");
				//right.add(lblJulian);
			}
		});
		textArea.setBounds(188, 97, 34, 33);
		right.add(textArea);
		
		JLabel lblArmiesToPlace = new JLabel("Armies to place:");
		lblArmiesToPlace.setBounds(65, 149, 117, 23);
		right.add(lblArmiesToPlace);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(188, 139, 34, 33);
		right.add(textArea_1);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(65, 191, 106, 23);
		right.add(lblCountry);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblJulian.setText("Current: Amit");
			}
		});
		comboBox.addItem("Amit");

		comboBox.setBounds(65, 214, 157, 23);
		right.add(comboBox);
		
		JButton btnPlace = new JButton("Place");
		btnPlace.setBounds(232, 214, 73, 23);
		right.add(btnPlace);
	}

}
