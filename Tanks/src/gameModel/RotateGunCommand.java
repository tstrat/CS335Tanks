package gameModel;

/**
 * Creates a Command for rotating the gun on a tank with keyboard controls
 * 
 *
 */
public class RotateGunCommand extends Command {

	private int x;
	private int y;
	
	/**
	 * Constructor for a new RotateGunCommand. Sets Player number and location.
	 * 
	 * @param Player - player number
	 * @param x - X coordinate
	 * @param y - Y coordinate 
	 */
	public RotateGunCommand(int Player, int x, int y) {
		super(Player);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns this RotateGunCommand's x-position.
	 * 
	 * @return The current x-position.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns this RotateGunCommand's y-position.
	 * 
	 * @return The current y-position.
	 */
	public int getY() {
		return y;
	}

}
