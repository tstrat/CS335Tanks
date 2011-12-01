package gameModel;

import java.awt.Rectangle;

public class Obstacle extends Actor implements Collidable{
	
	protected int health;
	protected int maxHealth;
	private Rectangle boundaries;
	
	public Obstacle(double x, double y, double rotation) {
		super(x, y, rotation);
		exists = true;
		boundaries = new Rectangle(draw.getWidth(), draw.getHeight());
	}
	
	public Obstacle(double x, double y, double rotation, int health) {
		this(x, y, rotation);
		
		maxHealth = health;
		this.health = health;
		boundaries = new Rectangle(draw.getWidth(), draw.getHeight());
	}
	
	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub
		
	}
	
	public int getHealth() {
		return health;
	}

	@Override
	public void act() {
		if(health <= 0)
			exists = false;
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
	

	public Rectangle getCollisionBox() {
		boundaries.setLocation((int)x, (int)y);
		return boundaries;
	}


}
