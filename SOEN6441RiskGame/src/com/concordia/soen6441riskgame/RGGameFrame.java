package com.concordia.soen6441riskgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;

public class RGGameFrame extends JFrame {

	private JPanel contentPane;
	private RGMapView mapView=new RGMapView();
	private RGPhaseView phaseView=new RGPhaseView();
	private RGDominationView dominationView=new RGDominationView();

	/**
	 * Create the frame.
	 */
	public RGGameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1088, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setVisible(true);
	
		//creating Phase View Panel. It is the upper right panel on the screen
		JPanel phaseViewPanel = new JPanel();
		phaseViewPanel.setBounds(905, 0, 400, 550);
		contentPane.add(phaseViewPanel);
		phaseViewPanel.setVisible(true);
		phaseViewPanel.add(phaseView);
		
		//creating Players World Domination View panel. It is the upper right panel on the screen
		JPanel dominationViewPanel = new JPanel();
		dominationViewPanel.setBounds(905, 550, 400, 95);
		contentPane.add(dominationViewPanel);
		dominationViewPanel.setVisible(true);
		dominationViewPanel.add(dominationView);
		
		//creating Map View panel. It is the left panel on the screen
		JPanel mapViewPanel = new JPanel();
		mapViewPanel.setBounds(0, 0, 905, 645);
		contentPane.add(mapViewPanel);
		mapViewPanel.setVisible(true);
		mapViewPanel.add(mapView);
		
		//creating the observers
		RGGame game=RGGame.getGame();
		game.addObserver(mapView);
		game.addObserver(phaseView);
		game.addObserver(dominationView);
		RGPlayer players=RGPlayer.getPlayers();
		players.addObserver(mapView);
		players.addObserver(phaseView);
		players.addObserver(dominationView);
		
		//allocating armies to every player in players' data structure
		game.setPhase("Setup");
		game.initializeGame();
		
		
		
	}
}
