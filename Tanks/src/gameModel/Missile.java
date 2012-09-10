package gameModel;


/**
 * 
 * 		The Missile class defines the collidables fired by the Gun classes. 
 * 		They collide with obstacles and damage them.
 *
 */

public abstract class Missile extends Collidable{
	
	/**
	 * How much damage a missile does.
	 */	
	
	protected int damage;
	
	/**
	 * How fast a missile moves.
	 */	
	
	protected double speed;
	
	
	/**
	 * A tracker of how many times a missile has "bounced" against different surfaces.
	 * After two "bounces", it explodes.
	 */	
	
	protected int bounces;
	
	/**
	 * How many times a missile can bounce before it explodes
	 */
	
	protected int maxBounces;
	

	protected Tank t;
	
		
	/**
	 * A constructor for a new missile object. Sets its location, its damage, and its speed.
	 * 
	 * @see Actor.Actor(World, double, double, double)
	 * 
	 * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the missile will travel.
	 * @param d - How much damage this Rocket will do.
	 * @param s - How fast this Rocket will move.
	 */
	
	public Missile(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation);
		bounces = 0;
		exists = true;
		this.t = t;
	}

	/**
	 * Updates the missile's position based on its speed.
	 * Also, calls DustCloud to make sure the missile has
	 * a cloud graphic following it. 
	 */
	
	@Override
	public void act() {
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
		
	}
	
	/**
	 * If the missile has reached the edge of the map, makes it bounce
	 * off that wall.  If it has already bounced 2 times, it explodes.
	 */	
	
	public void bounce() {
		if(x < 0) {
			rotation = Math.PI - rotation;
			bounces++;
		} else if(x > 800) {
			rotation = Math.PI - rotation;
			bounces++;
		}
		if(y > 600) {
			rotation = -rotation;
			bounces++;
		} else if(y < 0){
			rotation = -rotation;
			bounces++;
		}
		if(bounces > maxBounces)
			explode();
	}


	/**
	 * The collide method dictates how it interacts with objects it is colliding with. It first
	 * checks to make sure the Missile hasn't just been made (so it doesn't collide with the tank
	 * that fired it). If it hasn't, it will damage any obstacle it collides with, and explode if
	 * it came in contact with either an obstacle or a missile.
	 */
	
	@Override
	public void collide(Collidable c) {
		if (c instanceof Obstacle && (!c.equals(t) || bounces > 0)) {
			((Obstacle)c).receiveDamage(damage);
			explode();	
		}		
	}
	
	/**
	 * explode creates a new explosion object at the missile's location and sets its existence
	 * to false.
	 */
	
	public void explode() {
		exists = false;
	}


	


}
