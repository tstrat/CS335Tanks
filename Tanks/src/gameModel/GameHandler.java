package gameModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

/*
 * To be expanded later. GameHandler contains a World. 
 * Every time interval it tells the World WHAT to do,
 * but not HOW to do things. That is up for the implementer
 * of World to decide.
 */
public class GameHandler implements ActionListener {
	
	private World w;
	private List<Command> commands;
	private Timer timer;
	
	public GameHandler() {
		w = new World();
		timer = new Timer(20, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent arg0) {
		// First, tell the world to handle collisions
		w.handleCollisions();
		
		// Next, tell the world to relay the commands from
		// the server to its tanks, then clears the command
		// list after it has the world use them
		w.command(commands);
		commands.clear();
		
		// Lastly, tells world to make its actors act.
		w.makeActorsAct();		
	}

	/*
	 * The recieveCommand is how the GameHandler receives commands
	 * from an external source so that it can relay them to the world.
	 * If the command is a pause command, the GameHandler will pause
	 * the game. Otherwise, it will add the command to the list of 
	 * commands to send to the World
	 * 
	 * param c:
	 * 				The Command to send to this GameHandler.
	 */
	public void receiveCommand(Command c) {

	}

	private void pause() {
		timer.stop();
		// TODO: creates window that will restart the timer
	}

}
