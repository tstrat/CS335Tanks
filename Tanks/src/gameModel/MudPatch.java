package gameModel;

/**
 * 
 * The MudPatch object causes Tank objects to slow down when moving across it.
 * 
 */

public class MudPatch extends Terrain {
	
	private static int drawPriority = 1;
	private static DrawObject draw = new DrawSingleFrameObject("mud.png");
	
	/**
	 * Creates the MudPatch which is a type of Terrain Object.
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param rotation
	 */
	
	public MudPatch(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}
	
	/**
	 * Collision only words for tanks.  This causes the tanks speed to
	 * be temporarily reduced.
	 * 
	 * @param c - Collidable that is crossing the patch location.
	 */
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank)
			((Tank) c).modSpeed(-150);
	}
	
	/**
	 * Returns the priority of how it is drawn on the world
	 * 
	 * @return drawPriority
	 */
	
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}
	
	/**
	 * Returns the Draw object with its image file in it.
	 * 
	 * @return draw
	 */
	
	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		return draw;
	}

}
