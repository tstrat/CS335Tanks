package gameModel;

/**
 * Creates a Command for rotating the gun on a tank with mouse controls
 * 
 *
 */
public class RotateGunCommand2 extends Command {

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
	
}
