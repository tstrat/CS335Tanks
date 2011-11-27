
public class Obstacle extends Drawable implements Collidable{
	
	protected int health;
	protected int maxHealth;
	
	public Obstacle(int x, int y, String img) {
		super(x, y, img);
	}
	
	public Obstacle(int x, int y, String img, int h) {
		super(x, y, img);
		maxHealth = h;
		health = h;
	}
	
	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub
		
	}
	
	public int getHealth() {
		return health;
	}

}
