package gameModel;

import server.TanksClient;

/**
 * This is an in-between layer to support broadcasting messages
 * to a local GameHandler, and also to a Server.
 * 
 * @author Parker Snell
 */
public class MultiplayerBroadcaster implements CommandReceiver {

	private GameHandler gh;
	private TanksClient client;
	
	/**
	 * Saves the instance of the players GameHandler and client class
	 * 
	 * @param gh - Player's GameHandler
	 * @param client - the Player's client class
	 */
	
	public MultiplayerBroadcaster(GameHandler gh, TanksClient client) {
		this.gh = gh;
		this.client = client;
	}
	
	/**
	 * When a command is received by the Broadcaster, it sends the command out to the
	 * player's gameHandler and then sends it to the server through the client socket.
	 */
	
	@Override
	public void receiveCommand(Command c) {
		gh.receiveCommand(c);
		client.sendCommand(c);
	}

}
