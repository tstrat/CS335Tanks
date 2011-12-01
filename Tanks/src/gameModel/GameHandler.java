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
public class GameHandler implements CommandReceiver, ActionListener {
	
	private World w;
	private List<Command> commands;
	private Timer timer;
	
	public GameHandler() {
		w = new World();
		timer = new Timer(20, this);
		timer.start();
	}

	@Override
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

	/**
	 * The recieveCommand is how the GameHandler receives commands
	 * from an external source so that it can relay them to the world.
	 * If the command is a pause command, the GameHandler will pause
	 * the game. Otherwise, it will add the command to the list of 
	 * commands to send to the World
	 * 
	 * @param c The Command to send to this GameHandler.
	 */
	@Override
	public void receiveCommand(Command c) {
		commands.add(c);
	}

	private void pause() {
		timer.stop();
		// TODO: creates window that will restart the timer
		// This should be done in the GUI, not here.
	}
	
	/**
	 * Determines whether the game is paused.
	 * 
	 * @return True if the game is paused, false otherwise.
	 */
	public boolean isPaused() {
		return !timer.isRunning();
	}

}
