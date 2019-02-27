package com.concordia.soen6441project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
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
        left.setPreferredSize(new Dimension(1000,0));
        left.setBorder(new LineBorder(Color.white, 10));
        left.add(mapGraph);
        
        frame.add(right, BorderLayout.CENTER);
        right.setBorder(new LineBorder(Color.white, 10));
		
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
		
		
		JLabel lblTitle = new JLabel("REINFORCEMENT PHASE");
		lblTitle.setLocation(1050, 50);
		right.add(lblTitle);
		
		JLabel lblJulian = new JLabel("Julian");
		lblTitle.setLocation(1050, 50);
		right.add(lblJulian);
	}
}
