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
	
	private String host;
	private JButton singlePlay = new JButton("SinglePlayer");
	private JButton multiPlay = new JButton("MultiPlayer");
	private JButton mapEditing = new JButton("Map Editor");
	private JButton exitG = new JButton ("Exit Game");
	private JPanel mainP = new JPanel();
	private JList mapList, tankList, aiList, aiMPlist;
	private JButton ready = new JButton("Begin!");
	private JButton readyM = new JButton("Begin!");
	private JFrame mapFrame = new JFrame("Map and Tank Chooser");
	private TanksServer server;
	
	public BasicMenu() {
		super("Tanks basic display");
		add(mainP);
		mainP.setPreferredSize(new Dimension(800, 600));
		mainP.setLayout(null);
		setup();
		addMaps();
		addTanks();
		
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
		
		
		ready.addActionListener(new ReadyActionListener());
		readyM.addActionListener(new ReadyMActionListener());
	}
	
	public void addTanks(){
		ArrayList<String> theTanks = new ArrayList<String>();
		theTanks.add("Standard Tank");
		theTanks.add("Heavy Tank");
		theTanks.add("Hover Tank");
		tankList = new JList(theTanks.toArray());
		tankList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		tankList.setSelectedIndex(0);
		
		ArrayList<String> theAi = new ArrayList<String>();
		theAi.add("1 AI");
		theAi.add("2 AI");
		theAi.add("3 AI");
		aiList = new JList(theAi.toArray());
		aiList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		aiList.setSelectedIndex(0);
		
		ArrayList<String> theAiMp = new ArrayList<String>();
		theAiMp.add("0 AI");
		theAiMp.add("1 AI");
		theAiMp.add("2 AI");
		aiMPlist = new JList(theAiMp.toArray());
		aiMPlist.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		aiMPlist.setSelectedIndex(0);
	}
	
	public void addMaps(){
		File temp = new File("temp.tmp");
				
		File folder = new File(temp.getAbsolutePath().substring(0, temp.getAbsolutePath().length() - 8));
		ArrayList<String> theMaps = new ArrayList<String>();
		for(File file: folder.listFiles()){
			if(file.getName().endsWith(".txt")){
				theMaps.add(file.getName().substring(0, file.getName().length() - 4));
			}
		}
		if(theMaps.contains("null"))
			theMaps.remove("null");
		
		temp.delete();
		
		mapList = new JList(theMaps.toArray());
		mapList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		mapList.setSelectedIndex(0);
	}
	
	public void sets(Component o, int x, int y, int h, int w){
		mainP.add(o);
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(h, w);
	}
	

	private class ReadyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String fName = (String) mapList.getSelectedValue();
			String tName = (String) tankList.getSelectedValue();
			String ai = (String) aiList.getSelectedValue();
			int aiNums = Integer.parseInt(ai.substring(0,1));
			
			if(fName != null && !fName.equals("") && tName != null && !tName.equals("")){	
				dispose();
				mapFrame.dispose();
				JFrame tdHolder = new JFrame("Tanks");
				
				// Construct a World.
				WorldCreator wc = new WorldCreator(fName);
				
				// Add the single player.
				wc.addTank(new TankPair("P1", tName));
				
				// Add the AIs, starting at player 2.
				for (int i = 0; i < aiNums; ++i)
					wc.addAI(new AIPair(2 + i));
				
				tdHolder.add(new TanksDisplay(wc));
				tdHolder.pack();
				tdHolder.setResizable(false);
				tdHolder.setVisible(true);
				tdHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		}
		
	}
	
	private class ReadyMActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String fName = (String) mapList.getSelectedValue();
			String tName = (String) tankList.getSelectedValue();
			String ai = (String) aiMPlist.getSelectedValue();
			int aiNums = Integer.parseInt(ai.substring(0,1));
			
			if(fName != null && !fName.equals("") && tName != null && !tName.equals("")){	
				dispose();
				mapFrame.dispose();
				JFrame tdHolder = new JFrame("Tanks");
				
				WorldCreator wc = new WorldCreator(fName);
				
				// We leave the player number null. It will be filled in by the server.
				wc.addTank(new TankPair(null, tName));
				
				// The number here doesn't matter, because it will be filled in by the server later.
				for (int i = 0; i < aiNums; ++i)
					wc.addAI(new AIPair(0));
				
				tdHolder.add(new TanksDisplay(host, wc));

				tdHolder.pack();
				tdHolder.setResizable(false);
				tdHolder.setVisible(true);
				tdHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		}
		
	}
	
	private class SinglePlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
mapFrame = new JFrame("Maps and Tank Chooser");
			
			JPanel mapPanel = new JPanel();
			mapFrame.add(mapPanel);
			mapPanel.add(ready);
			JScrollPane scrollPane = new JScrollPane(mapList);
			JScrollPane scrollPane2 = new JScrollPane(tankList);
			JScrollPane scrollPane3 = new JScrollPane(aiList);
			scrollPane.setSize(150, 200);
			scrollPane.setLocation(0,0);
			mapPanel.add(scrollPane);
			scrollPane2.setSize(150, 200);
			scrollPane2.setLocation(150,0);
			mapPanel.add(scrollPane2);
			scrollPane3.setSize(150, 200);
			scrollPane3.setLocation(300,0);
			mapPanel.add(scrollPane3);
			ready.setSize(150, 200);
			ready.setLocation(450, 0);
			mapPanel.setLayout(null);
			mapPanel.setPreferredSize(new Dimension(600, 200));
			mapPanel.setVisible(true);
			mapFrame.pack();
			mapFrame.setVisible(true);
			mapFrame.setLocation(400, 200);
			
		}
		
	}
	
	private class MultiPlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame hostjoin = new JFrame("Host or Join");
			String joinIp;

			Object[] options = {"Host", "Join"};
			int n = JOptionPane.showOptionDialog(hostjoin, "Would you like to Host or Join a game?", "Host/Join", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(n == 0){
				host = "localhost";
				server = new TanksServer();
				
				mapFrame = new JFrame("Maps and Tank Chooser");
				
				JPanel mapPanel = new JPanel();
				mapFrame.add(mapPanel);
				mapPanel.add(readyM);
				JScrollPane scrollPane = new JScrollPane(mapList);
				JScrollPane scrollPane2 = new JScrollPane(tankList);
				JScrollPane scrollPane3 = new JScrollPane(aiMPlist);
				scrollPane.setSize(150, 200);
				scrollPane.setLocation(0,0);
				mapPanel.add(scrollPane);
				scrollPane2.setSize(150, 200);
				scrollPane2.setLocation(150,0);
				mapPanel.add(scrollPane2);
				scrollPane3.setSize(150, 200);
				scrollPane3.setLocation(300,0);
				mapPanel.add(scrollPane3);
				readyM.setSize(150, 200);
				readyM.setLocation(450, 0);
				mapPanel.setLayout(null);
				mapPanel.setPreferredSize(new Dimension(600, 200));
				mapPanel.setVisible(true);
				mapFrame.pack();
				mapFrame.setVisible(true);
				mapFrame.setLocation(400, 200);
			}
			
			if(n == 1){
				joinIp = JOptionPane.showInputDialog("Enter IP address to connect to.");
				host = joinIp;
				
				mapFrame = new JFrame("Maps and Tank Chooser");
				
				JPanel mapPanel = new JPanel();
				mapFrame.add(mapPanel);
				mapPanel.add(readyM);
				JScrollPane scrollPane = new JScrollPane(mapList);
				JScrollPane scrollPane2 = new JScrollPane(tankList);
				JScrollPane scrollPane3 = new JScrollPane(aiMPlist);
				scrollPane.setSize(150, 200);
				scrollPane.setLocation(0,0);
				mapPanel.add(scrollPane);
				scrollPane2.setSize(150, 200);
				scrollPane2.setLocation(150,0);
				mapPanel.add(scrollPane2);
				scrollPane3.setSize(150, 200);
				scrollPane3.setLocation(300,0);
				mapPanel.add(scrollPane3);
				readyM.setSize(150, 200);
				readyM.setLocation(450, 0);
				mapPanel.setLayout(null);
				mapPanel.setPreferredSize(new Dimension(600, 200));
				mapPanel.setVisible(true);
				mapFrame.pack();
				mapFrame.setVisible(true);
				mapFrame.setLocation(400, 200);
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
