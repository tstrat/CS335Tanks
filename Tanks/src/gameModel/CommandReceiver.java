package gameModel;

/**
 * Anything implementing CommandReceiver can be the recipient of a Command.
 * In general, if the object is not an Actor, it will simply pass the Command
 * on to someone else (ex. client/server, GameHandler).
 * 
 * @author Parker Snell
 */

public interface CommandReceiver {

	/**
	 * Receives a command. What is done with the command is implementation-defined.
	 * 
	 * @param c The Command to receive.
	 */
	
	public void receiveCommand(Command c);
	
}
