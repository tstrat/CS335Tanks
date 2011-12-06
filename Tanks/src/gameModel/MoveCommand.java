package gameModel;
/**
 * Creates a Command for moving the tank with keyboard controls
 *
 */
public class MoveCommand extends Command {

	/**
	 * Sometimes when I'm bored I count from this number to 0.
	 */
	private static final long serialVersionUID = -4006440351943291177L;
	
	private boolean isBackward;
	
	/**
	 * Creates a new constructor for the MoveCommand. Sets player number and location.
	 * 
	 * @param player Player number
	 * @param isBackward True if the Actor should move backward, false otherwise.
	 */
	public MoveCommand(int player, boolean isBackward) {
		super(player);
		
		this.isBackward = isBackward;
	}

	/**
	 * Cause the Actor to move.
	 * 
	 * @param a The Actor that should move.
	 */
	@Override
	public void applyTo(Actor a) {
		if (isBackward)
			a.moveBackward();
		else
			a.moveForward();
	}
	
}
