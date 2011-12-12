package gui;

import gameModel.World;
import gameModel.WorldCreator;
import gameModel.WorldCreator.AIPair;
import gameModel.WorldCreator.TankPair;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import MapEditor.MapEditor;

import server.TanksServer;

public class BasicMenu extends JFrame {
	
	private JButton singlePlay = new JButton("SinglePlayer");
	private JButton multiPlay = new JButton("MultiPlayer");
	private JButton mapEditing = new JButton("Map Editor");
	private JButton exitG = new JButton ("Exit Game");
	private JPanel mainP = new JPanel();
	
	public BasicMenu() {
		super("Tanks basic display");
		add(mainP);
		mainP.setPreferredSize(new Dimension(800, 600));
		mainP.setLayout(null);
		setup();

		
		pack();
		setResizable(false);
		setVisible(true);
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void setup(){
		sets(singlePlay, 0, 0, 810, 155);
		sets(multiPlay, 0, 155, 810, 155);
		sets(mapEditing, 0, 310, 810, 155);
		sets(exitG, 0, 465, 810, 155);
		
		singlePlay.addActionListener(new SinglePlayActionListener());
		multiPlay.addActionListener(new MultiPlayActionListener());
		mapEditing.addActionListener(new MapEditActionListener());
		exitG.addActionListener(new ExitActionListener());
	}	
		
	
	public void sets(Component o, int x, int y, int h, int w){
		mainP.add(o);
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(h, w);
	}
	
	
	private class SinglePlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new MapSelectionFrame(false);			
		}
		
	}
	
	
	private class MultiPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame hostjoin = new JFrame("Host or Join");

			Object[] options = {"Host", "Join"};
			int n = JOptionPane.showOptionDialog(hostjoin, "Would you like to Host or Join a game?", "Host/Join", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(n == 0) {
				new TanksServer();	
				new MapSelectionFrame(true);
			} else {			
				
				String joinIp = JOptionPane.showInputDialog("Enter IP address to connect to.");
				new JoinGameFrame(joinIp);			
			}
			
		}
		
	}
	
	private class MapEditActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new MapEditor();
		}
		
	}
	
	private class ExitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		new SplashScreen();
	}
	
	
}
