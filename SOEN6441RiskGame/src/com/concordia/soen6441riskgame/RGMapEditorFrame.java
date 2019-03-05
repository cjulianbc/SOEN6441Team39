package com.concordia.soen6441riskgame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

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
 * The starting frame let the user to create a map from scratch. If the user wants to edit a map, he/she can open an existing map, and the map information
 * will show on the two textAreas. One textArea for the continents, and the other for the countries and their adjacent countries.
 * 
 * A map can be saved (button "Save Map") and validated (button "Validate"). Before saving, a map is validated.
 * 
 * The option Play will open the Players' frame
 * 
 * 
 * @author Julian Beltran
 * @version 1.0
 * @since   1.0
 *
 */
public class RGMapEditorFrame extends JFrame {

	/**
	 * Panel where the Editor Map is placed.
	 */
	private JPanel contentPane;
	/**
	 * Created to store the file where a Risk® map is stored.
	 */
	private RGFile file=new RGFile();
	/**
	 * Created to store the information of the current game.
	 */
	private RGGame game=new RGGame();
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
		setBounds(100, 100, 619, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblContinents = new JLabel("CONTINENTS: (Name of the continent = Control value)");
		lblContinents.setBounds(34, 49, 353, 23);
		contentPane.add(lblContinents);
		
		JLabel lblMapEditor = new JLabel("MAP EDITOR");
		lblMapEditor.setBounds(10, 11, 86, 29);
		contentPane.add(lblMapEditor);
		
		JLabel lblCountries = new JLabel("COUNTRIES: (Name of the country, Map coord X, Map coord Y, Continent, Adjacent countries)");
		lblCountries.setBounds(34, 255, 618, 23);
		contentPane.add(lblCountries);
		
		JLabel lblExampleCountry = new JLabel("Example: (Alaska,70,126,North America,Northwest Territory,Alberta,Kamchatka)");
		lblExampleCountry.setBounds(34, 400, 569, 14);
		contentPane.add(lblExampleCountry);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 67, 323, 127);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(34, 274, 534, 127);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnNewButton = new JButton("Save Map");
		btnNewButton.setBounds(34, 450, 89, 23);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
				//The application can only save a valid map	
				if(file.validateMap(textArea.getText(), textArea_1.getText())) {
				
					if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
					{	
						File filename=fileChooser.getSelectedFile();
						BufferedWriter outFile = new BufferedWriter(new FileWriter(filename));
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
		lblExampleContinent.setBounds(34, 194, 212, 14);
		contentPane.add(lblExampleContinent);
		
		JButton btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				file.validateMap(textArea.getText(),textArea_1.getText());
			}
		});
		btnValidate.setBounds(132, 449, 97, 25);
		contentPane.add(btnValidate);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMap = new JMenu("Map Editor");
		menuBar.add(mnMap);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file.openFile(); //opening an existing Risk® map
				try {
					StringBuilder content=file.getContent("[Continents]");
					textArea.setText(String.valueOf(content));
					content=file.getContent("[Territories]");
					textArea_1.setText(String.valueOf(content));
					
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
		
		JMenuItem mntmPlay = new JMenuItem("Play");
		mntmPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file.openFile();
				try {
					StringBuilder content=file.getContent("[Territories]");
					game.createGraph(content);
					content=file.getContent("[Continents]");
					game.createContinents(content);
					RGPlayersFrame playersFrame=new RGPlayersFrame(file, game); //creating a new frame where players can be created
					playersFrame.setVisible(true);	
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPlayRisk.add(mntmPlay);
	}
}
