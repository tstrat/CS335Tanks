package gameModel;

public abstract class Obstacle extends Collidable{
	
	/**
	 * The amount of health this tank has.
	 */
	
	protected int health;
	
	/**
	 * The maximum amount of health a tank can have.
	 */
	
	protected int maxHealth;
	private static int drawPriority = 10;
	
	
	/**
	 * Creates a new obstacle in the game world.
	 * @param w - The current game world.
	 * @param x - The obstacle's x-position.
	 * @param y - The obstacle's y-position.
	 * @param rotation - The direction this obstacle is facing upon creation.
	 * 
	 * @see Obstacle(World, double, double, double, int)
	 */
	
	public Obstacle(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		exists = true;
	}
	
	/**
	 * Creates a new obstacle in the game world.
	 * Sets health, location, and direction (rotation).
	 * 
	 * @see Actor.Actor(World, double, double, double)
	 * 
	 * @param w - The current game world
	 * @param x - X-position
	 * @param y - y-position
	 * @param rotation - The direction of this obstacle (tank)
	 * @param health - The tank's health
	 */
	
	public Obstacle(World w, double x, double y, double rotation, int health) {
		this(w, x, y, rotation);
		
		maxHealth = health;
		this.health = health;
	}
	
	/**
	 * Returns the remaining health of the Obstacle.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Returns the max health of the Obstacle.
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * This decreases the Obstacle's health by d amount
	 * 
	 * @param d
	 * 			The amount to decrease the Obstacle's health by.
	 */
	public void receiveDamage(int d) {
		health -= d;
	}

	/**
	 * The DrawObject that defines how the GUI draws the Obstacle.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("tankStan.png");
	
	/**
	 * Returns the DrawObject of the Obstacle, which controls how the HealthBar is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * This returns whether the Obstacle is still in existence. This is determined by whether
	 * its health is greater than zero or not.
	 */
	@Override
	public boolean exists() {
		return (health > 0);
	}

	/**
	 * Returns the priority of this Obstacle draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

}
