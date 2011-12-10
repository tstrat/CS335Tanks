package gameModel;

/**
 * 
 * @author Messiah Kane
 *
 *		The class TNTBarrel defines a collidable that explodes upon any contact
 *		with another collidable in the world.
 *
 */

public class TNTBarrel extends Collidable {

	private static int drawPriority = 10;

	/**
	 * Constructs TNTBarrel
	 * 
	 * @see Actor.Actor(World, double, double, double)
	 * 
	 */
	public TNTBarrel(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}

	/**
	 * Explodes when it collides with any other collidable. If a rocket collides
	 * into it, that rocket is destroyed.
	 */
	@Override
	public void collide(Collidable c) {
		if(c instanceof Rocket)
			((Rocket) c).explode();
		explode();
	}
	

	/**
	 * When called, creates a violent explosion at the TNTBarrel's location, and
	 * sets the TNTBarrel's existence to false, so that it is removed.
	 */
	public void explode() {
		if(exists)
			Explosion.createExplosion(w, x, y, 5, 120, 10);
		exists = false;
	}

	/**
	 * Empty act method; Doesn't do anything per frame.
	 */
	@Override
	public void act() {
	}
	
	/**
	 * Returns the priority of this Obstacle draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority ;
	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Obstacle.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("TNT.png");
	
	/**
	 * Returns the DrawObject of the Obstacle, which controls how the HealthBar is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	

}
