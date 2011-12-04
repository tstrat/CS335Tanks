package gameModel;

/**
 * This is the main superclass for all Actors (anything at all that appears in a World).
 * 
 * @author Parker Snell, Seungwoo Sun
 */
public abstract class Actor implements CommandReceiver, Comparable<Actor> {
	
	/**
	 * When set to false, this Actor will essentially be garbage collected by the World.
	 */
	protected boolean exists;
	
	/**
	 * Coordinates of the Actor within a World, in pixels..
	 */
	protected double x, y;
	
	/**
	 * Rotation of the Actor, in radians.
	 */
	protected double rotation;
	
	/**
	 * The World that this Actor belongs to.
	 */
	protected World w;
	
	/**
	 * Constructs an Actor. At this point, the World does not know about this actor.
	 * 
	 * @param w The World that the Actor belongs to.
	 * @param x X-position in pixels.
	 * @param y Y-position in pixels.
	 * @param rotation Rotation in radians.
	 */
	public Actor(World w, double x, double y, double rotation) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		
	}
	
	/**
	 * Called every "turn", to do some implementation-defined action.
	 */
	public abstract void act();
	
	/**
	 * Changes this Actor's x-position.
	 * 
	 * @param x The new x-position.
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Returns this Actor's x-position.
	 * 
	 * @return The current x-position.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Changes this Actor's y-position.
	 * 
	 * @param y The new y-position.
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Returns this Actor's y-position.
	 * 
	 * @return The current y-position.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Changes this Actor's rotation.
	 * 
	 * @param d The new rotation, in radians.
	 */
	public void setRotation(double d) {
		this.rotation = d;
	}
	
	/**
	 * Returns this Actor's rotation.
	 * 
	 * @return The current rotation, in radians.
	 */
	public double getRotation() {
		return rotation;
	}
	
	/**
	 * Determines drawing priority. Actors with higher priorities appear
	 * on top of those with lower numbers.
	 * 
	 * @return The draw priority of this Actor.
	 */
	public abstract int getDrawPriority();
	
	/**
	 * Gets the DrawObject used to draw this Actor. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this Actor, or null.
	 */
	public abstract DrawObject getDraw();
	
	/**
	 * For non-player objects (the default), this should return 0,
	 * a value that will never be assigned to a player. Player objects
	 * (tanks) should override this to return whichever player number
	 * they are, so that they will receive the relevant commands.
	 * 
	 * @return A player number, or 0 if the object doesn't represent a player.
	 */
	public int getPlayerNumber() {
		return 0;
	}
	
	/**
	 * This method is only needed by player classes (the ones that
	 * override getPlayerNumber). It is used to process Commands.
	 * 
	 * @param c The Command to process.
	 */
	@Override
	public void receiveCommand(Command c) {
		// Do nothing.
	}
	
	/**
	 * Used to check if this Actor still exists. If it doesn't, it will be
	 * garbage collected by the World.
	 * 
	 * @return True if the Actor exists, false otherwise.
	 */
	public boolean exists() {
		return exists;
	}
	
	/**
	 * Compares Actors based on their draw priority. Those with greater priority
	 * are considered to be greater than other Actors.
	 * 
	 * @param o The other Actor to compare with.
	 */
	@Override
	public int compareTo(Actor o) {
		// TODO Auto-generated method stub
		return getDrawPriority() - o.getDrawPriority();
	}
}
