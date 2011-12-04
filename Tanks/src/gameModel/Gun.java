package gameModel;

/**
 * 
 * @author Seungwoo Sun
 * 
 * 		This class defines the turret that the tank uses to fire missiles. This
 * 		Gun is an Actor that moves according to the Tank's location, but can
 * 		rotate independently of it. It fires by the Player's command (via the tank
 * 		that owns this gun).
 *
 */
public class Gun extends Actor {
	
	/**
	 * The damage of the missiles the gun fires
	 */
	private int damage;	
	/**
	 * The speed of the missiles the gun fires
	 */
	private double shellSpeed;	
	/**
	 * The amount of time (measured in act() iterations) before the gun can fire.
	 */
	private int cDTimer;
	/**
	 * The amound of time between each shot if the tank is continuously firing.
	 */
	private int cD;
	/**
	 * The prority this Actor has when drawn over other Actors. a higher priority
	 * means it is drawn over the lower priority Actors.
	 */
	private static int drawPriority = 20;

	/**
	 * The constructor of the Gun. It sts its world, x and y coordinates, and its
	 * rotation to the parameters provided. It is set to fire the missiles at a speed
	 * of 8 pixels per act iteration, with a cool down of 45 act iterations between
	 * each shot.
	 * 
	 * @param w
	 * 			The world the Gun exists in.
	 * @param x
	 * 			The x coordinate of the Gun.
	 * @param y
	 * 			The y coordinate of the Gun.
	 * @param rotation
	 * 			The angle the Gun is pointing at.
	 */
	public Gun(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		damage = 300;
		shellSpeed = 8;
		cD = 45;
		cDTimer = 0;
		exists = true;
	}

	/**
	 * The fireMissile method is how the gun fires missiles. It checks the remaining
	 * cooldown on the gun, and if it is zero, it will fire and reset the cooldown to
	 * the max cooldown, and adds a new Missile at the gun's location, with its
	 * rotation.
	 */
	public void fireMissile() {
		if (cDTimer == 0) {
			Missile m = new Missile(w, x, y, rotation, damage, shellSpeed);
			w.addActor(m);
			cDTimer += cD;
		}
	}

	/**
	 * Every act iteration, the cDTimer increments down, to a minimum of zero.
	 */
	@Override
	public void act() {
		if (cDTimer > 0)
			cDTimer--;

	}

	/**
	 * rotateTowards rotates the gun towards a point, given by its coordinate x
	 * and y.
	 * 
	 * @param x
	 * 			The x coordinate of the point Gun is orienting itself towards.
	 * @param y
	 * 			The y coordinate of the point Gun is orienting itself towards.
	 */
	public void rotateTowards(double x, double y) {
		if (rotation < 0)
			rotation += 2 * Math.PI;
		else if (rotation > 2 * Math.PI)
			rotation -= 2 * Math.PI;
		
		double dx = x - this.x;
		double dy = y - this.y;
		double theta = Math.atan(dy / dx);
		
		if (dx < 0 && dy > 0) {
			theta = 1 * Math.PI + theta;
		} else if (dx < 0 && dy < 0) {
			theta = Math.PI + theta;
		} else if (dx > 0 && dy < 0) {
			theta = 2 * Math.PI + theta;
		}
		
		if (theta > rotation)
			rotation += .2;
		if (theta < rotation)
			rotation -= .2;
	}

	/**
	 * The DrawObject that defines how the GUI draws the Gun.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("gunStan.png");

	/**
	 * Returns the DrawObject of the gun, which controls how the Gun is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * rotates the Gun by the paramter rotation radians.
	 * 
	 * @param rotation
	 * 			the radians to rotate the gun.
	 */
	public void rotate(double rotation) {
		this.rotation += rotation;
	}
	
	/**
	 * destroy sets the Gun's existence to false.
	 */
	public void destroy() {
		exists = false;
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
