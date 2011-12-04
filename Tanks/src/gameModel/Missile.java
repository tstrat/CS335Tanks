package gameModel;

public class Missile extends Collidable{
	
	/**
	 * How much damage a missile does.
	 */	
	private int damage;
	
	/**
	 * How fast a missile moves.
	 */	
	private double speed;
	
	/**
	 * How many tics old the missile is
	 */
	private int framesOld;
	
	/**
	 * A tracker of how many times a missile has "bounced" against different surfaces.  After two "bounces", it explodes.
	 */	
	private int bounces;
	
	/**
	 * The prority this Actor has when drawn over other Actors. a higher priority
	 * means it is drawn over the lower priority Actors.
	 */
	private static int drawPriority = 11;
	
	/**
	 * A constructor for a new missile object. Sets its location, its damage, and its speed.
	 * 
	 * @see Actor.Actor(World, double, double, double)
	 * 
	 * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Effects the direction the missile will travel in.
	 * @param d - How much damage this missile will do.
	 * @param s - How fast this missile will move.
	 */
	
	public Missile(World w, double x, double y, double rotation, int d, double s) {
		super(w, x, y, rotation);
		bounces = 0;
		damage = d;
		speed = s;
		exists = true;
		framesOld = 0;
	}

	/**
	 * Updates the missile's position based on its speed.
	 * Also, calls DustCloud to make sure the missile has
	 * a cloud graphic following it. 
	 */	
	@Override
	public void act() {
		bounce();
		framesOld++;
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
		DustCloud.add(w, x, y);
	}
	
	/**
	 * If the missile has reached the edge of the map, makes it bounce
	 * off that wall.  If it has already bounced 2 times, it explodes.
	 */	
	public void bounce() {
		if(x < 0) {
			setRotation(-(Math.PI + getRotation()));
			bounces++;
		} else if(x > 800) {
			setRotation(-(Math.PI + getRotation()));
			bounces++;
		}
		if(y > 600) {
			setRotation(-(getRotation()));
			bounces++;
		} else if(y < 0){
			setRotation(-(getRotation()));
			bounces++;
		}
		if(bounces > 2)
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
		if(framesOld > 8){
			if(c instanceof Obstacle) {
				this.explode();
				((Obstacle) c).receiveDamage(damage);
			}
			else if(c instanceof Missile) {
				this.explode();
				((Missile) c).explode();
			}
		}
			
	}
	
	/**
	 * explode creates a new explosion object at the missile's location and sets its existence
	 * to false.
	 */
	public void explode() {
		if(exists)
			new Explosion(w, x, y, 3, 50, 6);
		
		exists = false;
	}

	/**
	 * The DrawObject that defines how the GUI draws the Missile.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");
	
	/**
	 * Returns the DrawObject of the missile, which controls how the Missile is drawn.
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
		// TODO Auto-generated method stub
		return drawPriority;
	}
	


}
