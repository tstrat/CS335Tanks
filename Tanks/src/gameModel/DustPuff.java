package gameModel;

/**
 * Creates a graphical dust cloud that follows missiles after they have been shot by a tank.
 *
 */
public class DustPuff extends DustCloud {

	/**
	 * A constructor for a new DustPuff object. Sets its location and it's tics to 100.
	 * Tics are how long the Puff lasts.
	 * 
	 * @param w - The current game world.
	 * @param x - The DustPuff's x-coordinate.
	 * @param y - DustPuff's y-coordinate.
	 */
	public DustPuff(World w, double x, double y) {
		super(w, x, y);
	}

	private DrawObject draw = new DrawFadingObject("dustpuff.png");
	
	/**
	 * Gets the DrawObject used to draw this DustPuff. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this DustPuff, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Adds the DustPuff to the world
	 * 
	 * @param w The World that the Actor belongs to, is being added to.
	 * @param x X-position in pixels.
	 * @param y Y-position in pixels.
	 */
	public static void add(World w, double x, double y) {
		if (TRand.random() < .5)
			new DustPuff(w, x, y);
	}
}
