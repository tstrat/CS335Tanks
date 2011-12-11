package gameModel;

/**
 * This class is a HealingBeacon Object.  This object will heal an obstacle that moves over its
 * collision box.
 */
/**
 * @author Travis
 *
 */
public class HealingBeacon extends Obstacle {

	private static int drawPriority = 13;

	private HealingPatch hP;
	
	
	/**
	 * Calls the super constructor, then sets the health and maxhealth of the beacon.
	 * Then a HealingPatchObject is created.
	 *  
	 * @param w The World that this Collidable belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 */
	public HealingBeacon(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		health = 1500;
		maxHealth = 1500;
		hP = new HealingPatch(w, x, y, rotation);
	}
	
	/*
	 * These next two methods are from the Obstacle class are not used in this class
	 */
	@Override
	public void collide(Collidable c) {}

	@Override
	public void act() {}
	
	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("healingbeacon.png");

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
	 * If the health of the beacon reaches 0, the patch is broken and an explosion is created.
	 * Then the beacons exist returns false and is removed, otherwise it returns true.
	 */
	public boolean exists() {
		if(health <= 0) {
			hP.breakPatch();
			Explosion.createExplosion(w, x, y, 1, 0, 0);
			return false;
		}
		return true;
		
	}
	

	/**
	 * Returns the priority of this Terrain's draw. A higher priority object is
	 * drawn over a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

}
