package gameModel;

/**
 * This piece of Terrain speeds up the tanks that cross over it.
 */

public class SpeedPatch extends Terrain {
	
	private static int drawPriority = 1;
	private static DrawObject draw = new DrawSingleFrameObject("speedPatch.png");
	
	/**
	 * Sets up the patch of Terrain on the world.
	 * 
	 * @param w - World
	 * @param x - x cooridinate of the Terrain Object.
	 * @param y - y cooridinate of the Terrain Object.
	 * @param rotation - the rotation of the Terrain Object.
	 */
	
	public SpeedPatch(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}
	
	/**
	 * If a Tank runs over this Terrain, the tanks speed is increased temporarily.
	 */
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank)
			((Tank) c).modSpeed(125);
	}
	
	/**
	 * Returns the draw priority of the terrain so it is drawn correctly. 
	 */
	
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}
	
	/**
	 * Returns the image of the terrain object in a DrawObject
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
