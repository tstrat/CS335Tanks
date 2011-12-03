package gameModel;

public class Obstacle extends Collidable{
	
	protected int health;
	protected int maxHealth;
	private static int drawPriority = 10;
	
	public Obstacle(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		exists = true;
	}
	
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
