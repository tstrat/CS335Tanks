package gameModel;

public class HealingBeacon extends Obstacle {

	private static int drawPriority = 13;

	private HealingPatch hP;
	
	public HealingBeacon(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		health = 1500;
		maxHealth = 1500;
		hP = new HealingPatch(w, x, y, rotation);
	}

	@Override
	public void collide(Collidable c) {
		
	}

	@Override
	public void act() {

	}
	
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
