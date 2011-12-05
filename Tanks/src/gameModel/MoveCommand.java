package gameModel;
/**
 * Creates a Command for moving the tank with keyboard controls
 *
 */
public class MoveCommand extends Command {

	private double x;
	private double y;
	
	/**
	 * Creates a new constructor for the MoveCommand. Sets player number and location.
	 * 
	 * @param player - Player number
	 * @param x - players x-coordinate
	 * @param y - players y-coordinate
	 */
	public MoveCommand(int player, double x, double y) {
		super(player);
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns this MoveCommand's x-position for the tank
	 * 
	 * @return The current x-position.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns this MoveCommand's y-position for the tank
	 * 
	 * @return The current y-position.
	 */
	public double getY() {
		return y;
	}
	
}
