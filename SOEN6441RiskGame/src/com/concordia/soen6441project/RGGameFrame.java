package com.concordia.soen6441project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class RGGameFrame extends JFrame{
	
	JPanel right= new JPanel(new GridLayout(1, 1));
	JLayeredPane layeredPane = new JLayeredPane();
	JPanel setUpFrame = new JPanel();
	JPanel reinforcement = new JPanel();
	JPanel attack = new JPanel();
	JPanel fortification = new JPanel();

	RGGameFrame(RGFile file, RGGame game, RGPlayer player)
	{
        
		RGMapGraph mapGraph = new RGMapGraph(file, game, player);
		JPanel left = new JPanel(new GridLayout(1, 1));
	    JFrame frame = new JFrame();
	    
	    frame.setVisible(true);
	    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

	    frame.add(left, BorderLayout.LINE_START);
        left.setPreferredSize(new Dimension(900,0));
        left.setBorder(new LineBorder(Color.white, 10));
        left.add(mapGraph);
        
        frame.add(right);
        right.setBorder(new LineBorder(Color.white, 10));
        right.setLayout(null);
		
		right.add(layeredPane);
		layeredPane.add(setUpFrame);
		layeredPane.add(reinforcement);
		layeredPane.add(attack);
		layeredPane.add(fortification);
		
		String phase=game.getPhase();
		if(phase=="Setup")
			setUpPhase();
        frame.setVisible(true);
	}
	
	void setUpPhase()
	{
		layeredPane.removeAll();
		layeredPane.add(setUpFrame);
		layeredPane.repaint();
		layeredPane.revalidate();
		
		
		JLabel lblReinforcementPhase = new JLabel("SETUP PHASE");
		lblReinforcementPhase.setBounds(148, 41, 117, 33);
		right.add(lblReinforcementPhase);
		
		JLabel lblJulian = new JLabel("Current: Julian");
		lblJulian.setBounds(65, 107, 113, 23);
		right.add(lblJulian);
		
		JTextArea textArea = new JTextArea();
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
		comboBox.setBounds(65, 214, 157, 23);
		right.add(comboBox);
		
		JButton btnPlace = new JButton("Place");
		btnPlace.setBounds(232, 214, 73, 23);
		right.add(btnPlace);
	}
}
