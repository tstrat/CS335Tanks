package gameModel;

public class TNTBarrel extends Collidable {

	private static int drawPriority = 10;

	public TNTBarrel(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}

	@Override
	public void collide(Collidable c) {
		if(c instanceof Rocket)
			((Rocket) c).explode();
		exists = false;
	}
	


	public boolean exists() {
		if(!exists)
			Explosion.createExplosion(w, x, y, 5, 120, 10);
		return exists;
	}

	@Override
	public void act() {
	}
	
	/**
	 * Returns the priority of this Obstacle draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority ;
	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Obstacle.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("TNT.png");
	
	/**
	 * Returns the DrawObject of the Obstacle, which controls how the HealthBar is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	

}
