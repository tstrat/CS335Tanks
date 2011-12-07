package gameModel;

public class Indestructible extends Obstacle {

	private static int drawPriority = 11;

	public Indestructible(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		health = 30;
		maxHealth = 30;
	}

	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void receiveDamage(int d) {
		
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Obstacle.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("indestructible.png");
	
	/**
	 * Returns the DrawObject of the Obstacle, which controls how the HealthBar is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * Returns the priority of this Obstacle draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority ;
	}

}
