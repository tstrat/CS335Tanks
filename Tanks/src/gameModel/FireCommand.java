package gameModel;
/**
 * Creates a new FireCommand controlling if a player has fired. 
 *
 */
public class FireCommand extends Command {

	/**
	 * The constructor for a new FireCommand. Sets the player number
	 * 
	 * @param Player - player number
	 */
	public FireCommand(int Player) {
		super(Player);
	}
	
	/**
	 * Causes an Actor, which must be a Tank, to fire its gun.
	 * 
	 * @param a An Actor which must be of type Tank or a subclass.
	 */
	@Override
	public void applyTo(Actor a) {
		if (!(a instanceof Tank))
			throw new IllegalArgumentException("FireCommand only works on Tanks!");
		
		((Tank)a).fireMissile();
	}

}
