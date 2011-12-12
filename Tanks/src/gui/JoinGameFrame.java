package gui;

import gameModel.WorldCreator;
import gameModel.WorldCreator.AIPair;
import gameModel.WorldCreator.TankPair;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class JoinGameFrame extends JFrame {

	private String host;
	private JList tankList;

	public JoinGameFrame(String host) {
		super("Join a Game");
		this.host = host;
		tankList = new JList(new String[]{"Standard Tank", "Heavy Tank", "Hover Tank"});
		tankList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		tankList.setSelectedIndex(0);
		JScrollPane scrollPane = new JScrollPane(tankList);
		scrollPane.setSize(150, 200);
		scrollPane.setLocation(0,0);
		
		add(scrollPane);
		JButton readyBut = new JButton("Start!");
		readyBut.addActionListener(new JoinStartActionListener());
		readyBut.setSize(150, 200);
		readyBut.setLocation(150, 0);
		add(readyBut);
		setLayout(null);
		setPreferredSize(new Dimension(300, 200));
		pack();
		setVisible(true);
	}
	
	private class JoinStartActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String tName = (String) tankList.getSelectedValue();
			
			if(tName != null && !tName.equals("")){	
				dispose();
				JFrame tdHolder = new JFrame("Tanks");
				
				// Construct a World.
				WorldCreator wc = new WorldCreator("");
				
				// Add the single player.
				wc.addTank(new TankPair("P1", tName));
				
				tdHolder.add(new TanksDisplay(host, wc));
				tdHolder.pack();
				tdHolder.setResizable(false);
				tdHolder.setVisible(true);
				tdHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		}
		
	}
}
