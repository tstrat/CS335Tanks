package gameModel;

/**
 * Creates a graphical dust cloud that follows missiles after they have been shot by a tank.
 * 
 * @author (Of the Javadoc) Sean
 *
 */

public class DustCloud extends Actor {

	public int ticsLeft;
	private static int drawPriority = 12;
	
	/**
	 * A constructor for a new DustCloud object. Sets its location and it's tics to 100.
	 * Tics are how long the Cloud lasts.
	 * 
	 * @param w - The current game world.
	 * @param x - The DustCloud's x-coordinate.
	 * @param y - DustCloud's y-coordinate.
	 */
	public DustCloud(World w, double x, double y) {
		super(w, x, y, Math.random());
		ticsLeft = 100;
	}

	/**
	 * Updates the DustCloud's life span based on ticks.
	 */	
	@Override
	public void act() {
		ticsLeft--;
	}

	private DrawObject draw = new DrawFadingObject("dustCloud.png");
	
	/**
	 * Gets the DrawObject used to draw this DustCloud. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this DustCloud, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Checks if the DustCloud has ran out of ticks, if so
	 * DustCloud should cease to exist.
	 */
	@Override
	public boolean exists() {
		return ticsLeft > 0;
	}

	/**
	 * Adds the DustCloud to the world
	 * 
	 * @param w The World that the Actor belongs to, is being added to.
	 * @param x X-position in pixels.
	 * @param y Y-position in pixels.
	 */
	public static void add(World w, double x, double y) {
		if (Math.random() < 0.5)
			w.addActor(new DustCloud(w, x, y));
	}

	/**
	 * Returns the priority of this gun's draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}
}
