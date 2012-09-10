package gameModel;

/**
 * A large Plasma missile is a missile that does damage over time to targets it collides with.  If the
 * Missile hits a wall, it bursts into several other smaller plasma balls, which each are missiles in
 * their own right.
 *
 */

public class PlasmaMissileLarge extends LargeMissile {

	private int lifeSpan;
	private boolean attached;
	private int framesOld;
	
	/**
	 * Set the number of bounces allowed, the lifespan of the missile, and the speed
	 * as well as calling the LargeMissile superconstructor.
	 * 
	 * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the missile will travel.
	 * @param t - Tanke that shot the missile.
	 */
	
	public PlasmaMissileLarge(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
		framesOld = 0;
		maxBounces = 2;
		lifeSpan = 300;
		attached = false;
		speed = 4.5;
	}
	
	/**
	 * Checks to see if it needs to bounce.  If the missiles lifespan reaches zero, it explodes.
	 * It also creates a LargePlasmaTrail behind it as it moves.
	 */
	
	public void act() {
		bounce();
		if (lifeSpan == 0)
			explode();
		if (!attached) {
			super.act();
			if (framesOld % 5 == 0)
				LargePlasmaTrail.add(w, x, y);
			if(lifeSpan < 300)
				lifeSpan = 0;
		}
		else {
			lifeSpan--;
		}
		framesOld++;
		attached = false;

	}
	
	/**
	 * Hitting a wall causes the Missile to break apart.
	 */
	
	public void bounce() {
		if (x < 0 || x > 800 || y < 0 || y > 600) {
			breakApart();
		}
	}
	
	/**
	 * The Missile will split into 4 different smaller plasma Balls that each shoot off in different
	 * directions, and then the original missile explodes.
	 */
	
	private void breakApart() {
		for(int i = 0; i < 4; i++)
			new PlasmaBallSmall(w, x, y, rotation - 252 + i * Math.PI / 10, t);
		explode();
		SoundPlayer.play("plasmaburst.mp3");
		
	}
	
	/**
	 * If the Misisle exists, it creates a new PlasmaTrail at the coordinates.  Then the missile
	 * ceases to exist.
	 */
	
	public void explode() {
		if(exists)
			new LargePlasmaTrail(w, x, y);
		exists = false;
	}

	private DrawObject draw = new DrawSingleFrameObject("plasma2.png");
	private int drawPriority = 11;
	
	/**
	 * Gets the DrawObject used to draw this DustCloud. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this DustCloud, or null.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Returns the priority of this clouds draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority ;
	}

	/**
	 * The collide method dictates how it interacts with objects it is colliding
	 * with. It first checks to make sure the Missile hasn't just been made (so
	 * it doesn't collide with the tank that fired it). If it hasn't, it will
	 * damage any obstacle it collides with, and explode if it came in contact
	 * with either an obstacle or a missile.
	 */
	
	@Override
	public void collide(Collidable c) {
		if (c.equals(t))
			return;
		if (c instanceof Obstacle) {
			attach(((Obstacle) c), c.getX(), c.getY());
		}
	}
	
	/**
	 * This method causes the ball to become attached to any object it encounters.
	 * For most objects this means it will receive damage until the objects
	 * are destroyed or until the life span of the Missile expires.
	 * 
	 * @param c - Obstacle that it intersects with
	 * @param cX - X coordinate of the Object
	 * @param cY - Y coordinate of the Object
	 */
	
	private void attach(Obstacle c, double cX, double cY) {
		attached = true;
		isLarge = false;
		if (x + 7 < cX)
			x += 7;
		else if (x - 7 > cX)
			x -= 7;
		else
			x = cX;
		if (y + 7 < cY)
			y += 7;
		else if (y - 7 > cY)
			y -= 7;
		else
			y = cY;
		c.receiveDamage(3);


	}
}
