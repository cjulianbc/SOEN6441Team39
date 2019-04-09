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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Font;

/**
 * Class that creates the frame to display the table showing the results for the tournament mode.
 * 
 * 
 * @author Aamrean, Abhishek
 * @version 1.0
 * @since   3.0
 *
 */
public class RGTournamentResultFrame extends JFrame {

	/**
	 * Panel where result table is placed.
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
	
	private JTable table;

	/**
	 * Main method is used to create a new frame.
	 * 
	 * 
	 * @param args command-line arguments for this application.
	 * 
	 */
	

	
	/**
	 * This constructor set the frame and show table displaying the tournament results.
     *   
     * @param results winner list for each game.
     * @param maps list containing selected maps.
     * @param games total number of games played on each map.
     *   
	 */
	public RGTournamentResultFrame(ArrayList<String> results, List<String> maps, int games) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 50, 576, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultTableModel tableModel = new DefaultTableModel(maps.size()+1,games+1);
		table = new JTable(tableModel);
		for(int i=0;i<maps.size();i++) {
			table.setValueAt(maps.get(i), i+1, 0);
		}
		for(int i=0;i<games;i++) {
			table.setValueAt("Game"+(i+1), 0, i+1);
		}int z=0;
		for(int i=1;i<=maps.size();i++) {
			int x=1;
			for(int j=z;j<z+games;j++) {
				table.setValueAt(results.get(j), i, x);
				x++;
			}
			z+=games;
		}
		table.setBounds(46, 127, 427, 165);
		contentPane.add(table);
		
		JLabel lblTournamentResults = new JLabel("Tournament Results");
		lblTournamentResults.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTournamentResults.setBounds(169, 83, 162, 31);
		contentPane.add(lblTournamentResults);
		
		
		
		
		
		
		
	}
}
