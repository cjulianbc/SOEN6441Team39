package com.concordia.soen6441riskgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.MouseAdapter;

/**
 * Class that creates a frame with three panels: 
 * 
 * 1) On the left panel, players can see the Risk map.
 * 2) On the right-north panel, players can see the Phase View where can interact with the application.
 * 3) On the right-south panel, players can see the World DOmination View.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGGameFrame extends JFrame {

	private JPanel contentPane;
	private RGMapView mapView=new RGMapView();
	private RGPhaseView phaseView=new RGPhaseView();
	private RGDominationView dominationView=new RGDominationView();

	/**
	 * This constructor creates the main frame (window) and the three panels (left, right-north, and right-south).
	 * 
     *   
	 */
	public RGGameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setVisible(true);
	
		//creating Map View panel. It is the left panel on the screen
		JPanel mapViewPanel = new JPanel();
		mapViewPanel.setBounds(0, 0, 900, 720);
		contentPane.add(mapViewPanel);
		mapViewPanel.setVisible(true);
		mapViewPanel.add(mapView);
		
		//creating Phase View Panel. It is the upper right panel on the screen
		JPanel phaseViewPanel = new JPanel();
		phaseViewPanel.setBounds(900, 0, 380, 450);
		contentPane.add(phaseViewPanel);
		phaseViewPanel.setVisible(true);
		phaseViewPanel.add(phaseView);
		
		//creating Players World Domination View panel. It is the upper right panel on the screen
		JPanel dominationViewPanel = new JPanel();
		dominationViewPanel.setBounds(900, 450, 380, 200);
		contentPane.add(dominationViewPanel);
		dominationViewPanel.setVisible(true);
		dominationViewPanel.add(dominationView);
		
		//creating the observers
		RGGame game=RGGame.getGame();
		game.addObserver(mapView);
		game.addObserver(phaseView);
		game.addObserver(dominationView);
		
		//allocating armies to every player in players' data structure
		game.setPhase("Setup");
		game.initializeGame();
	}
}
