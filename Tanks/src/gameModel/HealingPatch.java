package gameModel;


/**
 *  The HealingPatch Object is of type Terrain.  When a Tank moves over it, it gets broken up
 *  and creates dust clouds underneath it.  The HealingBeacon class creates the patch, and is
 *  responsible for the healing of the object that collides with it.
 */

public class HealingPatch extends Terrain {

	private boolean broken;
	private int smoked;

	/**
	 * Calls the super constructor for terrain, then sets broken to false, and smoked to
	 * 0.  Smoked and broken are used for the act mehtod in creating dust from it being used.
	 * 
	 * @param w The World that this Collidable belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 */
	
	public HealingPatch(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		broken = false;
		smoked = 0;
	}

	/**
	 * The collide method dictates how terrain interacts with other objects
	 * colliding into it.
	 */
	
	@Override
	public void collide(Collidable c) {
		if(!broken) {
			if (c instanceof Obstacle) {
				((Obstacle) c).receiveDamage(-3);
			}
		}
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("healingpatch.png");

	/**
	 * Gets the DrawObject used to draw this Terrain. This can return null, in
	 * which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this Terain, or null.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * Returns the priority of this Terrain's draw. A higher priority object is
	 * drawn over a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}
	
	/** 
	 * If the patch has been broken, the act method causes DustPoof objects to appear on the world for
	 * 150 ticks (as designated by the smoked variable).
	 */
	
	@Override
	public void act() {
		if(broken && smoked < 150) {
			if(TRand.random() < .2) {
				new DustPoof(w, x - 40 + 80*TRand.random(), y - 40 + 80*TRand.random());
			}
			smoked++;
		}
	}
	
	/**
	 * Calling this method sets the broken boolena to true, which makes the patch break up.
	 * A broken patch causes different effects.
	 */
	
	public void breakPatch() {
		broken = true;		
	}

}
