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
public abstract class Gun extends Actor {
	
	/**
	 * The damage of the missiles the gun fires
	 */
	protected int damage;	
	/**
	 * The speed of the missiles the gun fires
	 */
	protected double shellSpeed;	
	/**
	 * The amount of time (measured in act() iterations) before the gun can fire.
	 */
	protected int cDTimer;
	/**
	 * The amound of time between each shot if the tank is continuously firing.
	 */
	protected int cD;
	
	


	/**
	 * The constructor of the Gun. It sets its world, x and y coordinates, and its
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
	}

	/**
	 * The fireMissile method is how the gun fires missiles.
	 */
	public abstract void fireMissile(Tank t);

	/**
	 * Every act iteration, the cDTimer increments down, to a minimum of zero.
	 */
	@Override
	public void act() {
		if (cDTimer > 0)
			cDTimer--;

	}

}
