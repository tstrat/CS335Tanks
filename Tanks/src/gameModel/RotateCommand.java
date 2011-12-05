package gameModel;
/**
 * Creates a Command for rotating the tank with keyboard controls
 *
 */
public class RotateCommand extends Command {

	private double rotation;
	
	/**
	 * Creates a new constructor for the RotateCommand. Sets player number and rotation.
	 * 
	 * @param player - Player number
	 * @param rotation - Rotation of the tank
	 */
	public RotateCommand(int player, double rotation) {
		super(player);
		
		this.rotation = rotation;
	}
	
	/**
	 * Gets the rotation of the Tank from the RotateCommand
	 * 
	 * @return RotateCommand rotation for the tank
	 */
	public double getRotation() {
		return rotation;
	}
	
}
