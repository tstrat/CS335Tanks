package gameModel;


/**
 * 
 * 		The Wall class defiens a high health obstacle shaped like a square, making it ideal
 * 		for creating wals out of objects of this class.
 *
 */
public class Wall extends Obstacle{

	/**
	 * 
	 * Creates an Obstacle with 3000 health.
	 * 
	 * @see Obstacle.Obstacle(World, double, double, double)
	 * 
	 */
	public Wall(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		this.health = 3000;
		this.maxHealth = 3000;
		exists = true;
	}
	
	private static DrawObject draw1 = new DrawSingleFrameObject("wall2.png");
	private static DrawObject draw2 = new DrawSingleFrameObject("wallDamaged.png");
	private static DrawObject draw3 = new DrawSingleFrameObject("wallDamaged2.png");
	
	/**
	 * Gets the DrawObject used to draw this Wall. This will return a different DrawObject
	 * based on the Wall's remaining health.
	 * 
	 * @return A DrawObject representing this Wall
	 */
	@Override
	public DrawObject getDraw() {
		if(health > 2000)
			return draw1;
		else if(health > 1000)
			return draw2;
		return draw3;
	}
	
	/**
	 * The receiveDamage method imply subracts the damage from the wall's health. If negative
	 * damage has resulted in greater than its maxHealth, it is set back to the maxHealth. Whenever
	 * the health dips below certain Thresholds, the wall releaces some DustPoofs.
	 */
	@Override
	public void receiveDamage(int dam) {
		int h = health;
		health -= dam;
		if(health > maxHealth)
			health = maxHealth;
		if(h > 2000 && health <= 2000)
			for(int i = 0; i < 4; i++)
				new DustPoof(w, x - 20 + 40*TRand.random(), y - 20 + 40*TRand.random());
		if(h > 1000 && health <= 1000)
			for(int i = 0; i < 5; i++)
				new DustPoof(w, x - 20 + 40*TRand.random(), y - 20 + 40*TRand.random());
		if(health <= 0)
			for(int i = 0; i < 8; i++)
				new DustPoof(w, x - 20 + 40*TRand.random(), y - 20 + 40*TRand.random());
		
	}

	/**
	 * Empty collide method
	 */
	@Override
	public void collide(Collidable c) {
		// Does nuffing
	}

	/**
	 * Empty act method
	 */
	@Override
	public void act() {
		// Doesn't really act either.		
	}

}
