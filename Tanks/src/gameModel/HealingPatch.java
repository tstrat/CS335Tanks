package gameModel;

public class HealingPatch extends Terrain {



	private boolean broken;
	private int smoked;

	public HealingPatch(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		broken = false;
		smoked = 0;

		// TODO Auto-generated constructor stub
	}

	/**
	 * The collide method dictates how terrain interacts with other objects
	 * colliding into it.
	 */
	@Override
	public void collide(Collidable c) {
		if(!broken) {
			if (c instanceof Obstacle) {
				((Obstacle) c).receiveDamage(-3);
			}
		}
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("healingpatch.png");

	/**
	 * Gets the DrawObject used to draw this Terrain. This can return null, in
	 * which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this Terain, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * Returns the priority of this Terrain's draw. A higher priority object is
	 * drawn over a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}
	
	@Override
	public void act() {
		if(broken && smoked < 150) {
			if(Math.random() < .2) {
				new DustPoof(w, x - 40 + 80*Math.random(), y - 40 + 80*Math.random());
			}
			smoked++;
		}
	}

	public void breakPatch() {
		broken = true;		
	}

}
