package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import MapEditor.MapEditor;

import server.TanksServer;

public class BasicMenu extends JFrame implements ActionListener {
	
	private String host;
	private JButton singlePlay = new JButton("SinglePlayer");
	private JButton multiPlay = new JButton("MultiPlayer");
	private JButton mapEditing = new JButton("Map Editor");
	private JButton exitG = new JButton ("Exit Game");
	private JPanel mainP = new JPanel();
	private JList mapList, tankList;
	private JButton ready = new JButton("Begin!");
	private JFrame mapFrame = new JFrame("Map and Tank Chooser");
	
	public BasicMenu(String host) {
		super("Tanks basic display");
		this.host = host;
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
		sets(singlePlay, 0, 300, 405, 155);
		sets(multiPlay, 405, 300, 405, 155);
		sets(mapEditing, 0, 455, 405, 155);
		sets(exitG, 405, 455, 405, 155);
	}
	
	public void addTanks(){
		ArrayList<String> theTanks = new ArrayList<String>();
		theTanks.add("Standard Tank");
		theTanks.add("Heavy Tank");
		theTanks.add("Hover Tank");
		tankList = new JList(theTanks.toArray());
		tankList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
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
		
		temp.delete();
		
		mapList = new JList(theMaps.toArray());
		mapList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
	}
	
	public void sets(Component o, int x, int y, int h, int w){
		mainP.add(o);
		((JButton)o).addActionListener(this);
		o.setVisible(true);
		o.setLocation(x, y);
		o.setSize(h, w);
	}
	
	public static void main(String[] args) {
		boolean hosting = false;
		String host = null;
		
		// Check for command line arguments:
		// -host = become a host.
		// -join <host> = join a game, hosted by <host>.
		for (int i = 0; i < args.length; ++i) {
			if (args[i].equals("-host")) {
				hosting = true;
				host = "localhost";
			}
			else if (args[i].equals("-join")) {
				++i;
				if (args.length > i)
					host = args[i];
			}
		}
		
		TanksServer server;
		if (hosting) {
			server = new TanksServer();
		}
		
		BasicMenu frame = new BasicMenu(host);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == singlePlay){			
			JPanel mapPanel = new JPanel();
			ready.addActionListener(this);
			mapFrame.add(mapPanel);
			mapPanel.add(ready);
			JScrollPane scrollPane = new JScrollPane(mapList);
			JScrollPane scrollPane2 = new JScrollPane(tankList);
			scrollPane.setSize(150, 200);
			scrollPane.setLocation(0,0);
			mapPanel.add(scrollPane);
			scrollPane2.setSize(150, 200);
			scrollPane2.setLocation(150,0);
			mapPanel.add(scrollPane2);
			ready.setSize(150, 200);
			ready.setLocation(300, 0);
			mapPanel.setLayout(null);
			mapPanel.setPreferredSize(new Dimension(450, 200));
			mapPanel.setVisible(true);
			mapFrame.pack();
			mapFrame.setVisible(true);
			mapFrame.setLocation(400, 200);
		}
		
		if(e.getSource() == ready){
			String fName = (String) mapList.getSelectedValue();
			String tName = (String) tankList.getSelectedValue();
			
			if(fName != null && !fName.equals("") && tName != null && !tName.equals("")){	
				dispose();
				mapFrame.dispose();
				JFrame tdHolder = new JFrame("Tanks");
				tdHolder.add(new TanksDisplay(host, fName));
				tdHolder.pack();
				tdHolder.setResizable(false);
				tdHolder.setVisible(true);
				tdHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		}
		
		if(e.getSource() == mapEditing){
			dispose();
			new MapEditor();
		}
		
		if(e.getSource() == exitG){
			System.exit(0);
		}
		
	}
	
}
