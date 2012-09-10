package gameModel;


/**
 * 
 * This command saves the instance of the tank, and then synchronizes it with the other players
 * in the network.
 *
 */
public class SyncCommand extends Command {

	/**
	 * i <3 me sum dat serialVersionUIDs.
	 */
	private static final long serialVersionUID = -776866404145015658L;
	
	private double x;
	private double y;
	private double rotation;
	private int health;
	
	/**
	 * Stores the data from the given tank parameter.
	 * 
	 * @param tank - a Player's tank
	 */
	public SyncCommand(Tank tank) {
		super(tank.getPlayerNumber());
		
		x = tank.getX();
		y = tank.getY();
		rotation = tank.getRotation();
		health = tank.getHealth();
	}
	
	/**
	 * Calls the actor's sync method, passing in the data from the tank recieved in the constructor.
	 * @see gameModel.Actor.sync()
	 */
	@Override
	public void applyTo(Actor a) {
		a.sync(x, y, rotation, health);
	}

}
