package com.concordia.soen6441riskgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Class that creates a frame with three panels: 
 * 
 * 1) On the left panel, players can see the Risk map.
 * 2) On the right-north panel, players can see the Phase View where are able to interact with the application.
 * 3) On the right-south panel, players can see the World Domination View.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGGameFrame extends JFrame implements Serializable {

	/**
	 * This constructor creates the main frame (window) and the three panels (left, right-north, and right-south).
	 * 
     *   
	 */
	public RGGameFrame() {
		JPanel contentPane;
		RGMapView mapView=new RGMapView();
		RGPhaseView phaseView=new RGPhaseView();
		RGDominationView dominationView=new RGDominationView();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1280, 720);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSave = new JMenu("Save");
		menuBar.add(mnSave);
		
		JMenuItem mntmSaveGame = new JMenuItem("Save Game");
		mntmSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RGGame game=RGGame.getGame();
				RGPlayer players=RGPlayer.getPlayers();
				RGFile gameFile=RGFile.getGameFile();
				LocalDateTime now = LocalDateTime.now();  
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");  
				try {
					FileOutputStream file = new FileOutputStream(new File("Risk"+dtf.format(now)+".ser"));
					ObjectOutputStream output = new ObjectOutputStream(file);
					game.setSavedGame(true);
					System.out.print(game.getSavedGame());
					
					output.writeObject(game.getGraph());
					output.writeObject(game.getCountryItems());
					output.writeObject(game.getContinentItems());
					output.writeObject(game.getCardItems());
					output.writeObject(game);
					output.writeObject(game.getPhase());
					output.writeObject(game.getAttackStatus());
					output.writeObject(game.getCardGiven());
					output.writeObject(game.getAllOutModeForAttackPhase());
					output.writeObject(game.getPlayerStrategy());
					output.writeObject(game.getSavedGame());
					
					output.writeObject(players.getSetOfPlayers());
					output.writeObject(players.getPlayerItems());
					output.writeObject(players.getColors());
					output.writeObject(players);
					output.writeObject(players.getPlayerBehaviors());
			
					output.writeObject(gameFile.getFile());
					output.writeObject(gameFile);
					output.writeObject(gameFile.getImageFilePath());
					
					output.close();
					file.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
				}
			}
		});
		mnSave.add(mntmSaveGame);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setVisible(true);
	
		//creating Map View panel. It is the left panel on the screen
		JPanel mapViewPanel = new JPanel();
		mapViewPanel.setBounds(0, 0, 900, 660);
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
		
		//allocating armies to every player in players' data structure
		RGGame game=RGGame.getGame();
		boolean savedGame=game.getSavedGame();
		if (!savedGame) {
			//creating the observers
			game.addObserver(mapView);
			game.addObserver(phaseView);
			game.addObserver(dominationView);
			game.setPhase("Setup");
			game.initializeGame();
		}
		else {
			game.addObserver(mapView);
			game.addObserver(phaseView);
			game.addObserver(dominationView);
			game.savedGameNotifyObservers();
		}
	}
}
