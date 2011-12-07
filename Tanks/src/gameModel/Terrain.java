package gameModel;

	/**
	 *  Creates a Terrain that actors can interact with in different ways.
	 *  Usually harmful, but sometimes helpful Terrain can be found at locations
	 *  around the world.
	 *
	 */
public class Terrain extends Collidable {
	
	private static int drawPriority = 1;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * The collide method dictates how terrain interacts with other
	 * objects colliding into it.
	 */
	@Override
	public void collide(Collidable c) {
	}
	
	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");	
	/**
	 * Gets the DrawObject used to draw this Terrain. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this Terrain, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	/**
	 * Updates how the specific Terrain acts, depending on what type.
	 */	
	@Override
	public void act() {		
	}
	
	/**
	 * Returns the priority of this Terrain's draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

}
