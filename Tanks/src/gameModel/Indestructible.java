package gameModel;

/**
 * Indestructible is a type of Obstacle.  This specific type of obstacle cannot be
 * destroyed by means of explosions.
 *
 */
public class Indestructible extends Obstacle {

	private static int drawPriority = 11;

	/**
	 * Creates a indestructable object, then sets it health and maxhealth.
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param rotation
	 */
	
	public Indestructible(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		health = 30;
		maxHealth = 30;
	}

	@Override
	public void collide(Collidable c) {}
	
	@Override
	public void receiveDamage(int d) {}

	@Override
	public void act() {}
	
	/**
	 * The DrawObject that defines how the GUI draws the Obstacle.
	 */
	
	private static DrawObject draw = new DrawSingleFrameObject("indestructible.png");
	
	/**
	 * Returns the DrawObject of the Obstacle, which controls how the HealthBar is drawn.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * Returns the priority of this Obstacle draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	
	@Override
	public int getDrawPriority() {
		return drawPriority ;
	}

}
