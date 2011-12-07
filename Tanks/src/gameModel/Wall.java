package gameModel;

public class Wall extends Obstacle{

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
	 * Gets the DrawObject used to draw this Wall. This can return null, in
	 * which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this Wall, or null.
	 */
	@Override
	public DrawObject getDraw() {
		if(health > 2000)
			return draw1;
		else if(health > 1000)
			return draw2;
		return draw3;
	}
	
	@Override
	public void receiveDamage(int dam) {
		int h = health;
		health -= dam;
		if(h > 2000 && health <= 2000)
			for(int i = 0; i < 4; i++)
				new DustPoof(w, x - 20 + 40*Math.random(), y - 20 + 40*Math.random());
		if(h > 1000 && health <= 1000)
			for(int i = 0; i < 5; i++)
				new DustPoof(w, x - 20 + 40*Math.random(), y - 20 + 40*Math.random());
		if(health <= 0)
			for(int i = 0; i < 8; i++)
				new DustPoof(w, x - 20 + 40*Math.random(), y - 20 + 40*Math.random());
		
	}

	@Override
	public void collide(Collidable c) {
		// Does nuffing
	}

	@Override
	public void act() {
		// Doesn't really act either.		
	}

}
