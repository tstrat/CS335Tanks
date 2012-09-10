package gameModel;


/**
 * The Heavy Tank is a tank that has a PlasmaCannon turret.  It has more health than the standard
 * tank, and is slower movement-wise.
 */

public class HeavyTank extends Tank {

	/**
	 * Creates a new Heavy tank.  Sets health/max health baseSpeed and speed, as well as the health bar
	 * and the plasma cannon object that goes with the tank.
	 * 
	 * @param w The World that this Obstacle belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 * 
	 * @param player  The player that the tank belongs to.
	 */
	
	public HeavyTank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation, player);
		this.maxHealth = 3600;
		this.health = 3600;
		this.gun = new PlasmaCannon(w, x, y, rotation);
		baseSpeed = 2.2;
		speed = 2.2;
		new HealthBar(w, this);	
	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Tank.
	 */
	
	private static DrawObject draw = new DrawSingleFrameObject("tankHeav.png");
	
	/**
	 * Returns the DrawObject of the tank, which controls how the Tank is drawn.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Calling this method creates a new SnareMine object at the location of the tank.
	 * After laying the tank the mine cooldown timer is reset so that the player must wait
	 * to lay more mines.
	 */
	
	@Override
	public void layMine() {
		if(mineCD > 0)
			return;
		mineCD += mineMaxCD;
		new SnareMine(w, x, y, rotation, this);
	}

}
