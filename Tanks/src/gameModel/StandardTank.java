package gameModel;

/**
 * 
 * @author Seungwoo Sun
 * 
 *		This class defines the tanks for the game "Tanks!". It is an Actor
 *		that is player and AI controllable, and takes commands given by the
 *		player to either move or fire. The Tank is linked to this player (and
 *		will only take that player's commands), and also has a health bar and
 *		a rotatable turret from which it fires.
 *
 */
public class StandardTank extends Tank {
	/**
	 * This is the constructor from the tank. It calls the superconstructor of
	 * Obstacle to handle the world, location, and position of the newly
	 * instantiated tank, and adds itself, its newly instantiated gun, and its
	 * newly instantiated healthbar to the world.
	 * 
	 * @param w
	 * 			The World to add the Tank, its Gun, and its HealthBar to.
	 * @param x
	 * 			The x coordinate of the tank.
	 * @param y
	 * 			The y coordinate of the tank.
	 * @param rotation
	 * 			The angle the tank is pointing.
	 * @param player
	 * 			The player that controls the given tank.
	 */
	public StandardTank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation, player);
		this.maxHealth = 2500;
		this.health = 2500;
		this.gun = new RocketGun(w, x, y, rotation);
		speed = 3;
		new HealthBar(w, this);		
	}

	/**
	 * The DrawObject that defines how the GUI draws the Tank.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("tankStan.png");
	
	/**
	 * Returns the DrawObject of the tank, which controls how the Tank is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	@Override
	public void layMine() {
		if(mineCD > 0)
			return;
		mineCD += mineMaxCD;
		new ExplosiveMine(w, x, y, rotation, this);
	}
	

}
