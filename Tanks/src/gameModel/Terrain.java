package gameModel;


	/**
	 *  Creates a Terrain that actors can interact with in different ways.
	 *  Usually harmful, but sometimes helpful Terrain can be found at locations
	 *  around the world.
	 *
	 */
public abstract class Terrain extends Collidable {
	protected static int drawPriority = 1;
	
	/**
	 * A constructor for a new Terrain object. Sets its location and it's rotation.
	 * 
	 * @param w - The current game world.
	 * @param x - The DustCloud's x-coordinate.
	 * @param y - DustCloud's y-coordinate.
	 * @param rotation - sets the rotation of the terrain
	 */
	public Terrain(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}
	
	/**
	 * Most terrains probably do nothing each turn.
	 */	
	@Override
	public void act() {
	}
	
}
