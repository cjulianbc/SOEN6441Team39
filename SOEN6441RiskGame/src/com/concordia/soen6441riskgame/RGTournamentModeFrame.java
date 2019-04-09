package com.concordia.soen6441riskgame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Class that creates the frame for the tournament mode. This frame contains two Jlists i.e Available maps and
 *  Available players which gets populated on frame load. 
 *  
 *  User can select the maps and players from the available lists and move them to the selected lists using "Move" button.
 * 
 * User can select number of games from the dropdown and enter the maximum number of turns in the textfield. 
 * 
 * 
 * @author Abhishek, Aamrean
 * @version 1.0
 * @since   3.0
 *
 */
public class RGTournamentModeFrame extends JFrame {

	/**
	 * Panel where the Tournament setup is placed.
	 */
	private JPanel contentPane;
	/**
	 * Created to store the file where a Risk® map is stored.
	 */
	private RGFile file=RGFile.getGameFile();
	/**
	 * Created to store the information of the current game.
	 */
	private RGGame game=RGGame.getGame();
	
	
	private JTextField textFieldTurns;

	/**
	 * This method is used to read the map files from the directory.
	 * 
	 * @return map files
	 * 
	 */
	
	public ArrayList<String> readDirectory() {
		String path="C:\\Users\\cesar\\Documents\\Maps\\";
		ArrayList<String> files=new ArrayList<String>();
		File folder=new File(path);
		File[] listOfFiles=folder.listFiles();
		for (File file : listOfFiles) {
			if(file.getName().endsWith(".map")||file.getName().endsWith(".MAP")) {
				files.add(file.getName());
			}
		}
		return files;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGTournamentModeFrame frame = new RGTournamentModeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This constructor set the frame and add available maps and players to the lists.
     *   
     *   
	 */
	public RGTournamentModeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 50, 576, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lblMapEditor = new JLabel("Available maps");
		lblMapEditor.setBounds(10, 23, 86, 29);
		contentPane.add(lblMapEditor);
		
		
		
		ArrayList<String> availableMaps=new ArrayList<String>();
		
		availableMaps=this.readDirectory();
		DefaultListModel modelAvailableMaps = new DefaultListModel();
		for(int i=0; i<availableMaps.size(); i++ ) {
			modelAvailableMaps.add(i, availableMaps.get(i));
		}
		
		JList listAvailableMaps = new JList(modelAvailableMaps);
		
		JScrollPane scrollPane = new JScrollPane(listAvailableMaps);
		scrollPane.setBounds(10, 53, 115, 129);
		contentPane.add(scrollPane);
		
		JLabel lblSelectedMaps = new JLabel("Selected maps");
		lblSelectedMaps.setBounds(240, 23, 86, 29);
		contentPane.add(lblSelectedMaps);
		
		DefaultListModel modelSelectedMaps = new DefaultListModel();
		JList listSelectedMaps = new JList(modelSelectedMaps);
		listSelectedMaps.setBounds(240, 53, 115, 129);
		contentPane.add(listSelectedMaps);
		
		JButton btnMoveMaps = new JButton("Move");
		btnMoveMaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> x=listAvailableMaps.getSelectedValuesList();
				for (String string : x) {
					modelSelectedMaps.addElement(string);
					modelAvailableMaps.removeElement(string);
				}
				
			}
		});
		btnMoveMaps.setBounds(147, 115, 79, 25);
		contentPane.add(btnMoveMaps);
		
		
		DefaultListModel modelAvailablePlayers = new DefaultListModel();
		JList listAvailablePlayers = new JList(modelAvailablePlayers);
		listAvailablePlayers.setBounds(10, 246, 115, 129);
		contentPane.add(listAvailablePlayers);
		modelAvailablePlayers.addElement("aggressive");
		modelAvailablePlayers.addElement("benevolent");
		modelAvailablePlayers.addElement("random");
		modelAvailablePlayers.addElement("cheater");
		
		
		JLabel lblAvailablePalyers = new JLabel("Available Players");
		lblAvailablePalyers.setBounds(9, 216, 110, 29);
		contentPane.add(lblAvailablePalyers);
		
		DefaultListModel modelSelectedPlayers = new DefaultListModel();
		JList listSelectedPlayers = new JList(modelSelectedPlayers);
		listSelectedPlayers.setBounds(240, 246, 115, 129);
		contentPane.add(listSelectedPlayers);
		
		JButton btnMovePlayers = new JButton("Move");
		btnMovePlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> x=listAvailablePlayers.getSelectedValuesList();
				for (String string : x) {
					modelSelectedPlayers.addElement(string);
					modelAvailablePlayers.removeElement(string);
				}
			}
		});
		btnMovePlayers.setBounds(147, 308, 79, 25);
		contentPane.add(btnMovePlayers);
		
		
		
		JLabel lblSelectedPlayers = new JLabel("Selected Players");
		lblSelectedPlayers.setBounds(240, 216, 115, 29);
		contentPane.add(lblSelectedPlayers);
		
		JComboBox comboBoxGames = new JComboBox();
		comboBoxGames.setBounds(10, 427, 68, 22);
		contentPane.add(comboBoxGames);
		comboBoxGames.addItem("1");
		comboBoxGames.addItem("2");
		comboBoxGames.addItem("3");
		comboBoxGames.addItem("4");
		comboBoxGames.addItem("5");
		
		JLabel lblChooseNoOf = new JLabel("Choose no. of games");
		lblChooseNoOf.setBounds(10, 398, 134, 16);
		contentPane.add(lblChooseNoOf);
		
		JLabel lblChooseMaxNo = new JLabel("Choose max no. of turns");
		lblChooseMaxNo.setBounds(197, 398, 158, 16);
		contentPane.add(lblChooseMaxNo);
		
		textFieldTurns = new JTextField();
		textFieldTurns.setBounds(197, 427, 116, 22);
		contentPane.add(textFieldTurns);
		textFieldTurns.setColumns(10);
		
		JButton btnStartTournament = new JButton("Start Tournament");
		btnStartTournament.setBounds(10, 489, 134, 23);
		
		btnStartTournament.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				List<String> maps=new ArrayList<String>();
				for(int i=0;i<listSelectedMaps.getModel().getSize();i++) {
					maps.add((String)listSelectedMaps.getModel().getElementAt(i));
				}
				List<String> players=new ArrayList<String>();
				for(int i=0;i<listSelectedPlayers.getModel().getSize();i++) {
					players.add((String)listSelectedPlayers.getModel().getElementAt(i));
				}
				int numberOfGames=Integer.parseInt((String)comboBoxGames.getSelectedItem());
				int numberOfTurns=Integer.parseInt((String)textFieldTurns.getText());
				
				
				if(game.validateTurns(numberOfTurns)) {
					if(game.validateNumberOfMaps(maps.size())) {
						if(game.validateNumberOfPlayers(players.size())) {
							RGTournamentManager rgtournamnetmanager=new RGTournamentManager(maps, players, numberOfGames, numberOfTurns);
							rgtournamnetmanager.startTournament();
						}
						else {
							JOptionPane.showMessageDialog(null, "Number of players should be between 2 and 4", "Alert Message", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Number of Maps should be between 1 and 5", "Alert Message", JOptionPane.WARNING_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Number of turns should be between 10 and 50", "Alert Message", JOptionPane.WARNING_MESSAGE);
				}
				catch(NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "Number of turns should be a number between 10 and 50", "Alert Message", JOptionPane.WARNING_MESSAGE);
				}
				}});
		contentPane.add(btnStartTournament);
		
	}
}
