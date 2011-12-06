package gameModel;

/**
 * Creates a Command for rotating the gun on a tank with mouse controls
 * 
 *
 */
public class RotateGunCommand2 extends Command {

	/**
	 * If you change this I will hunt you down and kill you.
	 */
	private static final long serialVersionUID = -741260137836136482L;
	
	private double rotation;
	
	/**
	 * Constructor for a new RotateGunCommand2. Sets Player number and rotation.
	 * 
	 * @param Player - player number
	 * @param rotation - Guns rotation in correspondence with the mouse
	 */
	
	public RotateGunCommand2(int player, double rotation) {
		super(player);
		
		this.rotation = rotation;
	}
	
	/**
	 * Gets the RotateGunCommand2's rotation
	 * 
	 * @return RotateGunCommand2's rotation
	 */
	public double getRotation() {
		return rotation;
	}
	
	/**
	 * Rotates the gun associated with the Actor, which must be a Tank.
	 * 
	 * @param a The Actor (a Tank) whose gun should be rotated.
	 */
	@Override
	public void applyTo(Actor a) {
		if (!(a instanceof Tank))
			throw new IllegalArgumentException("RotateGunCommand2 only works on Tanks!");
		
		((Tank)a).rotateGun(rotation);
	}
	
}
