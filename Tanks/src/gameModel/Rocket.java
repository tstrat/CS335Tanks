package gameModel;

/**
 * 
 * 		The Rocket class defiens a special kind of missile that creates an explosion on contact
 * 		and leaves a trail of DustClouds as it moves.
 *
 */

public class Rocket extends LargeMissile {
	
	/**
	 * The priority this Actor has when drawn over other Actors. a higher priority
	 * means it is drawn over the lower priority Actors.
	 */
	
	private static int drawPriority = 11;
	
	/**
	 * Sets the maxBounces to 2, and framesInactive to 8.
	 * 
	 * @see Missile. Missile(World, double, double, double, int, double)
	 * 
	 * @param w - The current game world.
	 * @param x - The Rocket's x-coordinate.
	 * @param y - The Rocket's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the Rocket will travel in.
	 * @param d - How much damage this missile will do.
	 * @param s - How fast this Rocket will move.
	 */
	
	public Rocket(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
		maxBounces = 2;
		speed = 8;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The Rocket bounces when appropriate, moves as any missile would, and creates an exhaust trail every
	 * act iteration.
	 */
	
	public void act() {
		bounce();
		super.act();
		DustCloud.add(w, x, y);
	}
	
	/**
	 * explode creates a new explosion object at the Rocket's location and sets its existence
	 * to false.
	 */
	
	public void explode() {
		if(exists)
			Explosion.createExplosion(w, x, y, 3, 20, 9);		
		super.explode();
	}
	
	/**
	 * The collide method dictates how it interacts with objects it is colliding with. It first
	 * checks to make sure the Missile hasn't just been made (so it doesn't collide with the tank
	 * that fired it). If it hasn't, it will damage any obstacle it collides with, and explode if
	 * it came in contact with either an obstacle or a missile.
	 */
	
	@Override
	public void collide(Collidable c) {
		if (c.equals(t) && bounces == 0)
			return;	
		if (c instanceof LargeMissile) {
			if(((LargeMissile) c).isLarge())
				explode();	
		}	
		super.collide(c);
	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Missile.
	 */
	
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");
	
	/**
	 * Returns the DrawObject of the Rocket, which controls how the Rocket is drawn.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * Returns the priority of this gun's draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

}
