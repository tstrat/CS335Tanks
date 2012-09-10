package gameModel;


/**
 * 
 * The LargeMissle class allows for different missiles to be of different sizes.
 * Plasma Missiles have a large and a small version of the missile, as well as some rockets.
 *
 */

public abstract class LargeMissile extends Missile {

	protected boolean isLarge;
	
	/**
	 * Sets up the missile and declares the isLarge boolean to be true.
	 * 
     * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the missile will travel in.
	 * @param t  - The tank that is tied to this missile object.
	 */
	
	public LargeMissile(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
		isLarge = true;
	}
	
	/**
	 * Returns whether the missile is large or not
	 * 
	 * @see Missile
	 * 
	 * @return isLarge Boolean
	 */
	
	public boolean isLarge() {
		return isLarge;
	}


}
