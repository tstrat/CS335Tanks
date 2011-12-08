package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MapEditor.MapEditor;

import server.TanksServer;

public class BasicFrame extends JFrame implements ActionListener {
	
	private String host;
	private JButton singlePlay = new JButton("SinglePlayer");
	private JButton multiPlay = new JButton("MultiPlayer");
	private JButton mapEditing = new JButton("Map Editor");
	private JButton exitG = new JButton ("Exit Game");
	private JPanel mainP = new JPanel();
	
	public BasicFrame(String host) {
		super("Tanks basic display");
		this.host = host;
		add(mainP);
		mainP.setPreferredSize(new Dimension(800, 600));
		mainP.setLayout(null);
		setup();
		
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
		
		BasicFrame frame = new BasicFrame(host);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == singlePlay){
			String fName = JOptionPane.showInputDialog("Enter in Map name to load:");
			
			dispose();
			JFrame tdHolder = new JFrame("Tanks");
			tdHolder.add(new TanksDisplay(host, fName));
			tdHolder.pack();
			tdHolder.setResizable(false);
			tdHolder.setVisible(true);
			tdHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
