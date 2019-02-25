package com.concordia.soen6441project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.JButton;

public class RGGUI {

	private JFrame frame;
	private JFileChooser setfile;
	RGFile file=new RGFile();
	RGGame game=new RGGame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGGUI window = new RGGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RGGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JLabel lblContinents = new JLabel("CONTINENTS: (Name of the continent = Control value)");
		lblContinents.setBounds(34, 49, 353, 23);
		frame.getContentPane().add(lblContinents);
		
		JLabel lblMapEditor = new JLabel("MAP EDITOR");
		lblMapEditor.setBounds(10, 11, 86, 29);
		frame.getContentPane().add(lblMapEditor);
		
		JLabel lblCountries = new JLabel("COUNTRIES: (Name of the country, Map coord X, Map coord Y, Continent, Adjacent countries)");
		lblCountries.setBounds(34, 255, 618, 23);
		frame.getContentPane().add(lblCountries);
		
		JLabel lblExampleCountry = new JLabel("Example: (Alaska,70,126,North America,Northwest Territory,Alberta,Kamchatka)");
		lblExampleCountry.setBounds(34, 400, 569, 14);
		frame.getContentPane().add(lblExampleCountry);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 67, 323, 127);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(34, 274, 534, 127);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JButton btnNewButton = new JButton("Save Map");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					file.saveFile(textArea.getText(),textArea_1.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(34, 450, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblExampleContinent = new JLabel("Example: (North America=5) ");
		lblExampleContinent.setBounds(34, 194, 212, 14);
		frame.getContentPane().add(lblExampleContinent);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMap = new JMenu("Map Editor");
		menuBar.add(mnMap);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file.openFile();
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
					RGPlayersFrame playersFrame=new RGPlayersFrame(file, game);
					playersFrame.setVisible(true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPlayRisk.add(mntmPlay);
		
		JMenu mnExit = new JMenu("Exit");
		menuBar.add(mnExit);
	}
	
}
