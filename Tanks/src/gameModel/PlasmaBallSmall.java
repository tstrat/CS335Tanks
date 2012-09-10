package gameModel;


/**
 * The PlasmaBallSmall class is very similar to the larger plasma Missile however instead of exploding
 * into multiple missiles on impact, this one will not disperse.  The ball is also a lot smaller
 * in size than the larger counterpart and deals less damage.
 *
 */

public class PlasmaBallSmall extends Missile {
	
	private int framesOld;

	/**
	 * Sets the missiles damage, speed and number of bounces allowed.
	 * It calls the missile super constructor
	 * @see gameModel.Missile
	 * 
	 * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the missile will travel.
	 * @param t - the Tank that shot the missile.
	 */
	
	public PlasmaBallSmall(World w, double x, double y, double rotation,Tank t) {
		super(w, x, y, rotation, t);
		damage = 225;
		framesOld = 0;
		speed = 8;
		bounces = 1;
		maxBounces = 2;
	}
	
	/**
	 * When the act method is called, the missile sees if it needs to bounce, and then creates
	 * a plasma trail behind it.  the framesOld gets incremented and then missile's act method is called
	 * 
	 * @see gameModel.Missile.act()
	 */
	
	public void act() {
		bounce();
		if (framesOld % 5 == 0)
			SmallPlasmaTrail.add(w, x, y);
		framesOld++;
		super.act();
	}

	/**
	 * Creates a plasmaTrail at the same coordinates as the Missile, then the missile ceases
	 * to exist.
	 */
	
	public void explode() {
		new SmallPlasmaTrail(w, x, y);
		exists = false;
	}
	
	private DrawObject draw = new DrawSingleFrameObject("plasma.png");
	private int drawPriority = 11;
	
	/**
	 * Gets the DrawObject used to draw this DustCloud. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this DustCloud, or null.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Returns the priority of this clouds draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority ;
	}

	

}
