
public class Obstacle implements Actor, Collidable{
	
	protected int health;
	protected int maxHealth;
	
	double x;
	double y;
	double rotation;
	
	public Obstacle(double x, double y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	@Override
	public double getRotation() {
		return rotation;
	}

}
