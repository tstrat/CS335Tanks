package gameModel;

public class Wall extends Obstacle{

	public Wall(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		this.health = 5000;
		this.maxHealth = 5000;
		exists = true;
	}
	
	private static DrawObject draw = new DrawSingleFrameObject("wall2.png");

	/**
	 * Gets the DrawObject used to draw this Wall. This can return null, in
	 * which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this Wall, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
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
