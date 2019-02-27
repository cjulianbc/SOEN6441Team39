package com.concordia.soen6441project;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

public class RGGameFrameOLD extends JFrame {

	private JPanel contentPane;
	RGFile file;
	RGGame game;
	RGPlayer player;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RGGameFrame frame = new RGGameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public RGGameFrameOLD(RGFile file, RGGame game, RGPlayer player) {
		this.file=file;
		this.game=game;
		this.player=player;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(744, 11, 256, 318);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		layeredPane.add(panel, "name_2929072235304300");
		panel.setLayout(null);
		
		JLabel lblReinforcementPhase = new JLabel("REINFORCEMENT PHASE");
		lblReinforcementPhase.setBounds(60, 11, 150, 19);
		panel.add(lblReinforcementPhase);
		
		JLabel lblJulian = new JLabel("Julian");
		lblJulian.setBounds(21, 60, 49, 14);
		panel.add(lblJulian);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(" ");
		textArea.setBounds(60, 41, 35, 33);
		panel.add(textArea);
		
		JLabel lblArmiesToPlace = new JLabel("Armies to place:");
		lblArmiesToPlace.setBounds(21, 90, 103, 14);
		panel.add(lblArmiesToPlace);
		
		JLabel lblSelectCountry = new JLabel("Select Country:");
		lblSelectCountry.setBounds(21, 115, 103, 14);
		panel.add(lblSelectCountry);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(21, 128, 114, 19);
		panel.add(comboBox);
		
		JButton btnPlace = new JButton("Place");
		btnPlace.setBounds(143, 126, 59, 23);
		panel.add(btnPlace);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 11, 726, 572);
		contentPane.add(scrollPane);
		
		JLabel lblSds = new JLabel("sds");
		scrollPane.setViewportView(lblSds);
		
		String phase=game.getPhase();
		if(phase=="Setup")
			setUpPhase();
	}
	
	public void paint(Graphics g) {		
		String xCoord;
		String yCoord;
		String xCoordEnd;
		String yCoordEnd;
		String armies;
		String owner;
		String color;
		ArrayList<String> vertex = game.getVertex();;
		for(int k=0;k<vertex.size();k++)
		{
			xCoord=this.game.getXCoord(vertex.get(k));
			yCoord=this.game.getYCoord(vertex.get(k));
			armies=this.game.getArmies(vertex.get(k));
			owner=this.game.getOwner(vertex.get(k));
			color=player.getPlayerColor(owner);			
			
			JTextArea txtArea = new JTextArea();
			txtArea.setText(armies);
			txtArea.setBounds(Integer.valueOf(xCoord)+35, Integer.valueOf(yCoord)+10, 18, 18);	
			
			switch (color) {
			case "green":
				txtArea.setBackground(Color.green);
			    break;
			case "magenta":
				txtArea.setBackground(Color.magenta);
			    break;
			case "cyan":
				txtArea.setBackground(Color.cyan); 
			    break;
			case "pink":
				txtArea.setBackground(Color.pink);
			    break;
			case "orange":
				txtArea.setBackground(Color.orange); 
			    break;
			case "blue":
				txtArea.setBackground(Color.blue); 
			    break;
			default:
			    break;
			}
			contentPane.add(txtArea);
			
			ArrayList<String> edges = game.getEdges(vertex.get(k));
			for(int j=0;j<edges.size();j++)
			{
				xCoordEnd=this.game.getXCoord(edges.get(j));
				yCoordEnd=this.game.getYCoord(edges.get(j));
				g.drawLine(Integer.valueOf(xCoord)+50, Integer.valueOf(yCoord)+50,Integer.valueOf(xCoordEnd)+50, Integer.valueOf(yCoordEnd)+50);
			}
		}
	}
	
	void setUpPhase()
	{
		
	}
}

