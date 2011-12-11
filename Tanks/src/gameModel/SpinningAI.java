package gameModel;

/**
 * Defines an AI that just goes to the middle of the screen and then shoots in circles.
 * 
 * @author Parker Snell
 */
public class SpinningAI extends AIController {
	
	/**
	 * Constructs a Spinning AI
	 * 
	 * @see AIController
	 */
	public SpinningAI(World w, Tank tank, CommandReceiver receiver) {
		super(w, tank, receiver);
	}

	/**
	 * In this AI's act method, it attempts to move towards the center of the map.
	 * When it does, it stops and rotates its gun and shoots aimlessly, and will
	 * likely kill itself before harming you very much.
	 */
	@Override
	public void act() {
		double tankx, tanky;
		tankx = tank.getX();
		tanky = tank.getY();
		
		// If we're not in the center, rotate toward the center and move.
		if (tankx < 300 || tankx > 500 || tanky < 250 || tanky > 350) {
			receiver.receiveCommand(new RotateTowardsCommand(player, 400, 300));
			receiver.receiveCommand(new MoveCommand(player, false));
		}
		else {
			// We are in the center. Just move the gun around and shoot.
			receiver.receiveCommand(new RotateGunCommand2(player, 0.05));
			receiver.receiveCommand(new FireCommand(player));
		}
	}

}
