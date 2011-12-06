package gameModel;

/**
 * 
 * 		The BounceShard class defiens a special kind of missile that starts weak but
 * 		as it bounces it gains energy, dealing more damage and moving faster.
 *
 */
public class BounceShard extends Missile {
	/**
	 * The priority this Actor has when drawn over other Actors. a higher priority
	 * means it is drawn over the lower priority Actors.
	 */
	private static int drawPriority = 11;

	/**
	 * Sets the maxBounces to 3, and framesInactive to 9.
	 * 
	 * @see Missile. Missile(World, double, double, double, int, double)
	 * 
	 * @param w - The current game world.
	 * @param x - The BounceShard's x-coordinate.
	 * @param y - The BounceShard's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the BounceShard will travel in.
	 * @param d - How much damage this BounceShard will do.
	 * @param s - How fast this BounceShard will move.
	 */
	public BounceShard(World w, double x, double y, double rotation, int d,
			double s) {
		super(w, x, y, rotation, d, s);
		maxBounces = 1;
		framesInactive = 4;
	}

	/**
	 * The BounceShard bounces when appropriate, moves as any missile would, and creates an dust trail every
	 * act iteration.
	 */
	public void act() {
		this.bounce();
		super.act();
		DustPuff.add(w, x, y);
	}
	
	/**
	 * If the BounceShards has reached the edge of the map, makes it bounce
	 * off that wall.  If it has already bounced 2 times, it explodes.
	 * BounceHelp defines how BounceShards boucne specifically.
	 */	
	public void bounce() {
		if(x < 0) {
			rotation = Math.PI - rotation;
			bounceHelp();
		} else if(x > 800) {
			rotation = Math.PI - rotation;
			bounceHelp();
		}
		if(y > 600) {
			rotation = -rotation;
			bounceHelp();
		} else if(y < 0){
			rotation = -rotation;
			bounceHelp();
		}
		if(bounces > maxBounces)
			explode();
	}
	
	/**
	 * boucneHelp is called when the BounceShard boucnes off the wall. It increments the
	 * bounce count, speeds up the bullet, and increases its damage.
	 */
	private void bounceHelp() {
		bounces++;
		speed +=0;
		damage += 0;
	}
	
	
	
	/**
	 * The DrawObject that defines how the GUI draws the BounceShard.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("shotbullet.png");
	
	/**
	 * Returns the DrawObject of the missile, which controls how the BounceShard is drawn.
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
	
	/**
	 * The collide method dictates how it interacts with objects it is colliding with. It first
	 * checks to make sure the Missile hasn't just been made (so it doesn't collide with the tank
	 * that fired it). If it hasn't, it will damage any obstacle it collides with, and explode if
	 * it came in contact with either an obstacle or a missile.
	 */
	@Override
	public void collide(Collidable c) {
		if (framesOld <= framesInactive && bounces == 0)
			return;	
		super.collide(c);
	}
	

}
