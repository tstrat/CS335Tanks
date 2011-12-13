package gui;

import gameModel.WorldCreator;
import gameModel.WorldCreator.AIPair;
import gameModel.WorldCreator.TankPair;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.TanksServer;

public class MapSelectionFrame extends JFrame {
	
	private JButton ready = new JButton("Begin!");
	private JList mapList, tankList, aiList, aiMPlist;
	
	private String host;
	private TanksServer server;
	private TanksMainFrame frame;
	
	public MapSelectionFrame(boolean isHosting, TanksMainFrame f) {
		super("Maps and Tank Chooser");
		frame = f;
		if(isHosting)
			host = "localhost";
		setFrame();
	}
	


	
	public void setFrame() {
		addMaps();
		addTanks();
		
		JPanel mapPanel = new JPanel();
		add(mapPanel);
		mapPanel.add(ready);
		JScrollPane scrollPane = new JScrollPane(mapList);
		JScrollPane scrollPane2 = new JScrollPane(tankList);
		JScrollPane scrollPane3;
		if (host == null)
			scrollPane3 = new JScrollPane(aiList);
		else
			scrollPane3 = new JScrollPane(aiMPlist);
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
		pack();
		setVisible(true);
		setLocation(400, 200);
		ready.addActionListener(new ReadyActionListener());
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
	
	private class ReadyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String fName = (String) mapList.getSelectedValue();
			String tName = (String) tankList.getSelectedValue();
			String ai = (String) aiList.getSelectedValue();
			int aiNums = Integer.parseInt(ai.substring(0,1));
			if (host != null)
				--aiNums;
			
			if(fName != null && !fName.equals("") && tName != null && !tName.equals("")){	
				dispose();
				JFrame tdHolder = new JFrame("Tanks");
				
				// Construct a World.
				WorldCreator wc = new WorldCreator(fName);
				
				// Add the single player.
				wc.addTank(new TankPair("P1", tName));
				
				// Add the AIs, starting at player 2.
				for (int i = 0; i < aiNums; ++i)
					wc.addAI(new AIPair(2 + i));
				TanksDisplay tp;
				if(host == null)
					frame.setPanel(new TanksDisplay(frame, wc));
				else
					frame.setPanel(new TanksDisplay(host, frame, wc));
			}
			
		}
		
	}

}
