package gameModel;

/**
 * 
 * 		The SpikePit class defines a type of terrain that damages any tanks on top of it.
 *
 */
public class SpikePit extends Terrain {

	/**
	 * how much damage the SpikeTile deals to obstacles above it per collision call.
	 */
	private int damage;

	/**
	 * Creates a SpikePit with the given damage.
	 * 
	 * @see Terrain.Terrain(World, double, double, double)
	 * 
	 * @param w			The World to add it in
	 * @param x			The x coordinate to place the SpikeTile
	 * @param y			The y coordinate to place the SpikeTile
	 * @param rotation	The rotation the SpikeTile is set at.
	 * @param damage	The amount of damage the spikeTile will do.
	 */
	public SpikePit(World w, double x, double y, double rotation, int damage) {
		super(w, x, y, rotation);
		this.damage = damage;
		exists = true;
	}

	/**
	 * When the SpikeTile collides with an Obstacle, it deals a certain amount of
	 * damage to it, based on the damage field.
	 */
	@Override
	public void collide(Collidable c) {
		if (c instanceof Obstacle) {
			((Obstacle) c).receiveDamage(damage);
		}
	}

	private static DrawObject draw = new DrawSingleFrameObject("spikePit.png");

	/**
	 * Gets the DrawObject used to draw this SpikeTile. This can return null, in
	 * which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this SpikeTile, or null.
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
		return drawPriority;
	}

}