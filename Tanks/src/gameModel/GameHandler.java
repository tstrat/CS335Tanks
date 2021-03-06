package gameModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

/**
 * To be expanded later. GameHandler contains a World. 
 * Every time interval it tells the World WHAT to do,
 * but not HOW to do things. That is up for the implementer
 * of World to decide.
 */

public class GameHandler implements CommandReceiver, ActionListener {
	
	private World w;
	private List<Command> commands;
	private Timer timer;
	
	/**
	 * Constructs a new GameHandler. You must construct this after you have added the tanks,
	 * otherwise as soon as the first tank is added, it will think that player has won.
	 * 
	 * @param w - the game world
	 * 
	 */
	
	public GameHandler(World w) {
		this.w = w;
		commands = new ArrayList<Command>();
		timer = new Timer(20, this);
		timer.start();
	}

	/**
	 * This method provides a way to update the game world, based on collision detection and player commands.
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// First, tell the world to handle collisions
		w.handleCollisions();
		w.cleanup();
		
		// Next, tell the world to relay the commands from
		// the server to its tanks, then clears the command
		// list after it has the world use them
		w.command(commands);
		commands.clear();
		
		// Lastly, tells world to make its actors act.
		w.makeActorsAct();
		if(w.getWinner() != 0)
			timer.stop();
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

	/**
	 * Determines whether the game is paused.
	 * 
	 * @return True if the game is paused, false otherwise.
	 */
	
	public boolean isPaused() {
		return !timer.isRunning();
	}

}
