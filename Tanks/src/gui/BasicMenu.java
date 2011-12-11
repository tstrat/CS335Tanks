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
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import MapEditor.MapEditor;

import server.TanksServer;

public class BasicMenu extends JFrame implements ActionListener {
	
	private String host;
	private JButton singlePlay = new JButton("SinglePlayer");
	private JButton multiPlay = new JButton("MultiPlayer");
	private JButton mapEditing = new JButton("Map Editor");
	private JButton exitG = new JButton ("Exit Game");
	private JPanel mainP = new JPanel();
	private JList mapList;
	private JButton ready = new JButton("Begin!");
	private JFrame mapFrame = new JFrame("Map Chooser");
	
	public BasicMenu(String host) {
		super("Tanks basic display");
		this.host = host;
		add(mainP);
		mainP.setPreferredSize(new Dimension(800, 600));
		mainP.setLayout(null);
		setup();
		addMaps();
		
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setup(){
		sets(singlePlay, 0, 300, 405, 155);
		sets(multiPlay, 405, 300, 405, 155);
		sets(mapEditing, 0, 455, 405, 155);
		sets(exitG, 405, 455, 405, 155);
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
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
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
			ready.setSize(150, 200);
			ready.setLocation(150, 0);
			mapPanel.setLayout(null);
			mapPanel.setPreferredSize(new Dimension(300, 200));
			mapPanel.setVisible(true);
			mapFrame.pack();
			mapFrame.setVisible(true);
			JScrollPane scrollPane = new JScrollPane(mapList);
			scrollPane.setSize(150, 200);
			scrollPane.setLocation(0,0);
			mapPanel.add(scrollPane);
			
		}
		
		if(e.getSource() == ready){
			String fName = (String) mapList.getSelectedValue();
			
			if(fName != null && !fName.equals("")){	
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
