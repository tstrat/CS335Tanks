
public class Obstacle extends Actor implements Collidable{
	
	protected int health;
	protected int maxHealth;
	

	public Obstacle(double x, double y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		exists = true;
	}
	
	public Obstacle(double x, double y, double rotation, int health) {
		this(x, y, rotation);
		
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

	@Override
	public void act() {
		if(health <= 0)
			exists = false;
	}
	
	public void receiveDamage(int d) {
		health -= d;
	}

}
