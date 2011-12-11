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
	

	public Mine(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation);
		this.player = t.getPlayerNumber();
		this.t = t;
	}



	@Override
	public void act() {
		
	}

	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

	private static DrawObject draw = new DrawSingleFrameObject("Invisible.png");
	private static DrawObject selectiveDraw = new DrawSingleFrameObject("mineTarget.png");
	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		if(w.getPlayer() != player)
			return draw;
		return selectiveDraw;
	}
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			activateMine();
		}
	}



	public abstract void activateMine();

}
