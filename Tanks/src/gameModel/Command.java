package gameModel;

/**
 * A Command is the basic unit of control in Tanks. It can be passed to
 * any CommandReceiver, including over the network, to make an Actor
 * do some arbitrary action.
 * 
 * @see CommandReceiver
 * 
 * @author Parker Snell
 */
public abstract class Command {

	/**
	 * The player that should respond to this Command.
	 */
	private int myPlayer;
	
	/**
	 * Constructs a command for a specific player.
	 * 
	 * @param Player The player that should respond to this command.
	 */
	public Command(int Player) {
		myPlayer = Player;
	}
	
	/**
	 * Returns the player that should respond to this command.
	 * 
	 * @return I already wrote this like 5 times.
	 */
	public int getPlayer() {
		return myPlayer;
	}

}
