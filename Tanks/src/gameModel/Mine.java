package gameModel;
/**
 * 
 * @author Messiah Kane
 *
 *		The Mine class defines the mines that are droppable by Tanks. Mines are
 *		collidables that activate upon collision with enemy tanks, and are only visible
 *		to the tank that dropped them prior to activation.
 *
 */

public abstract class Mine extends Collidable {
	
	/**
	 * the tank that dropped the mine
	 */
	
	protected Tank t;
	private int drawPriority = 9;
	
	/**
	 * the player that controls the tank that dropped the mine
	 */
	
	protected int player;
	

	/**
	 * This constructor sets up the collidable mine with a super call to Collidable
	 *	as well as setting the player number and tank of the player that laid the mine.
	 * 
	 * @see gameModel.Collidable  For information on the use of world and coordinate parameters
	 * 
	 * @param w The World that this Collidable belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 * 
	 * @param t  The Tank that laid the mine.
	 */
	
	public Mine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation);
		this.player = t.getPlayerNumber();
		this.t = t;
	}

	/**
	 * Ignore the act method for Mine class. (It does nothing)
	 */
	
	@Override
	public void act() {}

	/**
	 * Returns this mines draw priority
	 * 
	 * @see gameModel.Actor#getDrawPriority()
	 */
	
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

	private static DrawObject draw = new DrawSingleFrameObject("Invisible.png");
	private static DrawObject selectiveDraw = new DrawSingleFrameObject("mineTarget.png");
	
	/** 
	 * This draw method is special to the mine class in that the player who laid the mine
	 * has the privilege of seeing it on the world, however all other players can not
	 * see the mine.
	 */
	
	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		if(w.getPlayer() != player)
			return draw;
		return selectiveDraw;
	}
	
	/**
	 * Checks if the tank is the same player who laid the mine or not.  If a different player
	 * moves over this mine, the mine is activated
	 */
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			activateMine();
		}
	}

	/**
	 * This method will cause the mines effect to trigger, and create an explosion,
	 * dammaging whatever collided with it.
	 */
	
	public abstract void activateMine();

}
