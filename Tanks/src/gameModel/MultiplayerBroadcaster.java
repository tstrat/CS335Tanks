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
	
	public MultiplayerBroadcaster(GameHandler gh, TanksClient client) {
		this.gh = gh;
		this.client = client;
	}
	
	@Override
	public void receiveCommand(Command c) {
		gh.receiveCommand(c);
		client.sendCommand(c);
	}

}
