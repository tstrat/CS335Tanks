package gameModel;

/**
 * Creates a fireball that spawns from an explosion.
 * An explosion spawns fireballs from every direction once it happens.
 * 
 */

public class FireBall extends Collidable {
	
	private int lifeTime;
	private double speed;
	private int damage;
	private static int drawPriority = 25;
	
	/**
	 * A constructor for a new FireBall object. Sets its location, rotation, speed, damage, and life time.
	 * 
	 * @param w - The current game world.
	 * @param x - The DustCloud's x-coordinate.
	 * @param y - DustCloud's y-coordinate.
	 * @param rotation - sets the rotation (direction) the fireball will travel
	 * @param speed - How fast the fireball moves
	 * @param damage - How much damage the fireball deals
	 */
	public FireBall(World w, double x, double y, double rotation, double speed, int damage) {
		super(w, x, y, rotation);
		lifeTime = 20;
		this.speed = speed;
		this.damage = damage;
	}

	/**
	 * The collide method dictates how it interacts with objects it is colliding with. It
	 * will damage any obstacle it collides with.
	 */
	@Override
	public void collide(Collidable c) {
		if(c instanceof Obstacle) {
			((Obstacle) c).receiveDamage(damage);
		}

	}

	/**
	 * Updates the FireBall's position based on it's speed.
	 * Also decreases it's lifetime if it has yet to collide with
	 * anything.
	 */	
	@Override
	public void act() {
		lifeTime--;
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
	}
	
	/**
	 * Checks if the FireBalls lifeTime has reached zero.
	 * If so FireBall should cease to exist.
	 */
	@Override
	public boolean exists() {
		return lifeTime > 0;
	}

	private DrawObject draw = new DrawFadingObject("fireBall.png");
	
	/**
	 * Gets the DrawObject used to draw this FireBall. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this FireBall, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Returns the priority of this fireball's draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

}
