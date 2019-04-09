package com.concordia.soen6441riskgame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * Class that creates the starting frame of the application. This frame contains a menu bar with 2 options: 
 * 
 * 1) Map Editor: This menu contains the options: Open and Exit.
 * 2) Game: This menu contains the option: Play
 * 
 * The starting frame let the user to create a map from scratch. If the user wants to edit a map, he/she can open an existing map and the map information
 * will be shown on three textAreas. One text area for the name of the map image file, another for the continents, and the other for the countries 
 * and their adjacent countries.
 * 
 * A map can be saved (button "Save Map") and validated (button "Validate"). Before saving, a map is validated.
 * 
 * The option Play will open the Players' frame.
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGMapEditorFrame extends JFrame{

	/**
	 * Created to store map file.
	 */
	private RGFile gameFile=RGFile.getGameFile();
	
	/**
	 * Created to store the current game (only one game is possible).
	 */
	private RGGame game=RGGame.getGame();
	
	/**
	 * Panel where the Editor Map is placed.
	 */
	
	private JPanel contentPane;
	/**
	 * Created to show the window where the user can open or save a file.
	 */
	
	private JFileChooser fileChooser=new JFileChooser();

	/**
	 * Main method is used to create a new frame.
	 * 
	 * 
	 * @param args command-line arguments for this application.
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGMapEditorFrame frame = new RGMapEditorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This constructor set the frame and add all the objects a player needs to create, edit, validate, and save a Risk® map.
     *   
     *   
	 */
	public RGMapEditorFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 50, 619, 634);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblContinents = new JLabel("CONTINENTS: (Name of the continent = Control value)");
		lblContinents.setBounds(31, 131, 353, 23);
		contentPane.add(lblContinents);
		
		JLabel lblMapEditor = new JLabel("MAP EDITOR");
		lblMapEditor.setBounds(10, 11, 86, 29);
		contentPane.add(lblMapEditor);
		
		JLabel lblCountries = new JLabel("COUNTRIES: (Name of the country, Map coord X, Map coord Y, Continent, Adjacent countries)");
		lblCountries.setBounds(31, 317, 618, 23);
		contentPane.add(lblCountries);
		
		JLabel lblExampleCountry = new JLabel("Example: (Alaska,70,126,North America,Northwest Territory,Alberta,Kamchatka)");
		lblExampleCountry.setBounds(31, 462, 569, 14);
		contentPane.add(lblExampleCountry);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 149, 323, 127);
		contentPane.add(scrollPane);
		
		JTextArea textArea_2 = new JTextArea();
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(31, 336, 534, 127);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnNewButton = new JButton("Save Map");
		btnNewButton.setBounds(31, 512, 89, 23);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//The application can only save a valid map	
					if(gameFile.validateMap(textArea.getText(), textArea_1.getText())) {

						if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
						{	
							File filename=fileChooser.getSelectedFile();
							BufferedWriter outFile = new BufferedWriter(new FileWriter(filename));
							outFile.write("[Map]"); 
							outFile.newLine();
							outFile.write("author=ConcordiaSOEN6441Team39");
							outFile.newLine();
							outFile.write("image=");
							textArea_2.write(outFile);
							outFile.newLine();
							outFile.newLine();
							outFile.write("[Continents]"); 
							outFile.newLine();
							textArea.write(outFile);
							outFile.newLine();
							outFile.write("[Territories]");
							outFile.newLine();
							textArea_1.write(outFile);
							outFile.close();
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnNewButton);
		
		JLabel lblExampleContinent = new JLabel("Example: (North America=5) ");
		lblExampleContinent.setBounds(31, 276, 212, 14);
		contentPane.add(lblExampleContinent);
		
		JButton btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFile.validateMap(textArea.getText(),textArea_1.getText());
			}
		});
		btnValidate.setBounds(129, 511, 97, 25);
		contentPane.add(btnValidate);
		
		JLabel lblMapImageimage = new JLabel("MAP IMAGE: (Name of the image)");
		lblMapImageimage.setBounds(31, 43, 353, 23);
		contentPane.add(lblMapImageimage);
		
		textArea_2.setBounds(31, 62, 321, 29);
		contentPane.add(textArea_2);
		
		JLabel lblExampleimageworldbmp = new JLabel("Example: (world.bmp) ");
		lblExampleimageworldbmp.setBounds(31, 91, 212, 14);
		contentPane.add(lblExampleimageworldbmp);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMap = new JMenu("Map Editor");
		menuBar.add(mnMap);
		
		JMenuItem mntmOpen = new JMenuItem("Open Map");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFile.openFile(); //opening an existing Risk® map
				try {
					StringBuilder content=gameFile.getContent("[Continents]");
					textArea.setText(String.valueOf(content));
					content=gameFile.getContent("[Territories]");
					textArea_1.setText(String.valueOf(content));
					content=gameFile.getMapImageFileName("[Map]");
					textArea_2.setText(String.valueOf(content));
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mnMap.add(mntmOpen);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnMap.add(mntmExit);
		
		JMenu mnPlayRisk = new JMenu("Game");
		mnPlayRisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(mnPlayRisk);
		
		JMenuItem mntmPlay = new JMenuItem("Play Single Game");
		mntmPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {;
				gameFile.openFile();
				try {
					StringBuilder content=gameFile.getContent("[Territories]");
					game.createGraph(content);
					content=gameFile.getContent("[Continents]");
					game.createContinents(content);
					RGPlayersFrame playersFrame=new RGPlayersFrame(); //creating a new frame where players can be created
					playersFrame.setVisible(true);	
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JMenuItem mntmOpenGame = new JMenuItem("Open Game");
		mntmOpenGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RGGame game=RGGame.getGame();
				RGPlayer players=RGPlayer.getPlayers();
				RGFile gameFile=RGFile.getGameFile();
				LocalDateTime now = LocalDateTime.now();  
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
				try {
					FileInputStream file = new FileInputStream(new File("Risk"+dtf.format(now)+".ser"));
					ObjectInputStream input = new ObjectInputStream(file);
					
					RGGraph graphFromFile = (RGGraph) input.readObject();
					RGGraph countryItemsFromFile = (RGGraph) input.readObject();
					RGGraph continentItemsFromFile = (RGGraph) input.readObject();
					RGGraph cardItemsFromFile = (RGGraph) input.readObject();
					RGGame gameFromFile = (RGGame) input.readObject();
					String phaseFromFile= (String) input.readObject();
					String attackStatusFromFile= (String) input.readObject();
					boolean cardGivenFromFile= (boolean) input.readObject();
					boolean allOutModeForAttackPhaseFromFile= (boolean) input.readObject();
					RGPlayerStrategy playerStrategyFromFile= (RGPlayerStrategy) input.readObject();
					boolean savedGameFromFile= (boolean) input.readObject();
					
					ArrayList<String> setOfPlayersFromFile = (ArrayList<String>) input.readObject();
					RGGraph playerItemsFromFile = (RGGraph) input.readObject();
					ArrayList<String> colorsFromFile = (ArrayList<String>) input.readObject();
					RGPlayer playersFromFile = (RGPlayer) input.readObject();
					RGGraph playerBehaviorsFromFile = (RGGraph) input.readObject();
					
					File fileFromFile = (File) input.readObject();
					RGFile gameFileFromFile = (RGFile) input.readObject();
					String imageFilePathFromFile= (String) input.readObject();
					
					input.close();
					file.close();
		
					game.setGraph(graphFromFile);
					game.setCountryItems(countryItemsFromFile);
					game.setContinentItems(continentItemsFromFile);
					game.setCardItems(cardItemsFromFile);
					game.setGame(gameFromFile);
					game.setPhase(phaseFromFile);
					game.setAttackStatus(attackStatusFromFile);
					game.setCardGiven(cardGivenFromFile);
					game.setAllOutModeForAttackPhase(allOutModeForAttackPhaseFromFile);
					game.setPlayerStrategy(playerStrategyFromFile);
					game.setSavedGame(savedGameFromFile);
					
					players.setSetOfPlayers(setOfPlayersFromFile);
					players.setPlayerItems(playerItemsFromFile);
					players.setColors(colorsFromFile);
					players.setPlayer(playersFromFile);
					players.setPlayerBehaviors(playerBehaviorsFromFile);
					
					gameFile.setFile(fileFromFile);
					gameFile.setGameFile(gameFileFromFile);
					gameFile.setImageFilePath(imageFilePathFromFile);

					players=RGPlayer.getPlayers();
					System.out.print(players.getColors());
					
					RGGameFrame gameFrame=new RGGameFrame();//creating the game frame where players can play Risk® game
					gameFrame.setVisible(true);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		mnPlayRisk.add(mntmOpenGame);
		mnPlayRisk.add(mntmPlay);
		
		JMenuItem mntmPlayTournament = new JMenuItem("Play Tournament");
		mnPlayRisk.add(mntmPlayTournament);
		mntmPlayTournament.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RGTournamentModeFrame tournament = new RGTournamentModeFrame();
				tournament.setVisible(true);
			}
		});
	}
	
}
