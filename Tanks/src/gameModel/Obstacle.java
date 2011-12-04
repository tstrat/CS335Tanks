package gameModel;

public class Obstacle extends Collidable{
	
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
	 * Creates a new obstacle in the game world, specifically for tanks.
	 * Sets health, location, and direction (rotation).
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
	
	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub
		
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public void act() {
	}
	
	public void receiveDamage(int d) {
		health -= d;
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("tankStan.png");
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	@Override
	public boolean exists() {
		return (health > 0);
	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

}
