package gameModel;

/**
 * Base class for AI. It is an Actor, like anything else on the board.
 * This means its act() method is called every turn, when it will decide
 * what type of commands to generate, if any.
 * 
 * @author Parker Snell
 */
public abstract class AIController extends Actor {

	/**
	 * The player that we generate commands for.
	 */
	protected int player;
	
	/**
	 * The Tank that we are controlling.
	 */
	protected Tank tank;
	
	/**
	 * The World to query for other obstacles, etc.
	 */
	protected World world;
	
	/**
	 * How all the magic happens.
	 */
	protected CommandReceiver receiver;
	
	/**
	 * Constructs an AIController to control a tank.
	 * 
	 * @param w The World to add the controller to.
	 * @param tank The Tank that should be controlled.
	 * @param receiver The CommandReceiver to send all the commands to.
	 */
	public AIController(World w, Tank tank, CommandReceiver receiver) {
		super(w, 0, 0, 0);
		
		player = tank.getPlayerNumber();
		this.tank = tank;
		this.world = w;
		this.receiver = receiver;
	}

	/**
	 * This doesn't matter because it is never drawn.
	 */
	@Override
	public int getDrawPriority() {
		return 0;
	}

	/**
	 * This also doesn't matter because again, the AIController is never drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return null;
	}

}
