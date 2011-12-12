package gui;

import gameModel.WorldCreator;
import gameModel.WorldCreator.TankPair;

import javax.swing.JFrame;

import server.TanksServer;

public class BasicFrame extends JFrame {

	public BasicFrame(String host) {
		super("Tanks basic display");
		
		WorldCreator wc = new WorldCreator("");
		wc.addTank(new TankPair("P1", "Standard Tank"));
		add(new TanksDisplay(host, wc));
		
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
}
