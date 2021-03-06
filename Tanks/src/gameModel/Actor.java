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
	 * If the Actor has an associated SoundPlayer, the sound will play when the object
	 * is constructed.
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
		w.addActor(this);
		exists = true;
	}
	
	/**
	 * Called every "turn", to do some implementation-defined action.
	 */
	
	public abstract void act();
	
	/**
	 * Moves forward by a given value.
	 * 
	 * @param delta The amount in pixels to move forward.
	 */
	
	protected final void moveForward(double delta) {
		x += delta * Math.cos(rotation);
		y += delta * Math.sin(rotation);
	}
	
	/**
	 * Moves the Actor forward. Can be overridden to change speed.
	 * 
	 * @see Actor.moveBackward
	 */
	
	public void moveForward() {
		moveForward(1);
	}
	
	/**
	 * Moves backward by a given value.
	 * 
	 * @param delta The amount in pixels to move backward.
	 */
	
	protected final void moveBackward(double delta) {
		moveForward(-delta);
	}
	
	/**
	 * Moves the Actor backward. Can be overridden to change speed.
	 * 
	 * @see Actor.moveForward
	 */
	
	public void moveBackward() {
		moveBackward(1);
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
	 * @param d The amount to rotate to the right, in radians.
	 */
	
	public void rotate(double d) {
		this.rotation += d;
	}
	
	/**
	 * rotateTowards rotates the Actor towards a point, given by its coordinate x
	 * and y.
	 * 
	 * @param x
	 * 			The x coordinate of the point Actor is orienting itself towards.
	 * @param y
	 * 			The y coordinate of the point Actor is orienting itself towards.
	 */
	
	public void rotateTowards(double x, double y) {
		double r = Math.atan2(y - this.y, x - this.x);
		if(r - rotation > Math.PI)
			rotation += 2 * Math.PI;
		else if (r - rotation < - Math.PI)
			rotation -= 2 * Math.PI;
		if(r > rotation+ 0.025)
			rotation += .05;
		else if (r < rotation - .025)
			rotation -= .05;	
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
	 * By default, an Actor applies this Command to itself.
	 * Override this method if you don't want this behavior.
	 * 
	 * @param c The Command to process.
	 */
	
	@Override
	public void receiveCommand(Command c) {
		c.applyTo(this);
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
	 * Changes the exists value to false, after which the object will be
	 * garbage collected by its World.
	 */
	
	public void destroy() {
		exists = false;
	}
	
	/**
	 * Compares Actors based on their draw priority. Those with greater priority
	 * are considered to be greater than other Actors.
	 * 
	 * @param o The other Actor to compare with.
	 */
	
	@Override
	public final int compareTo(Actor o) {
		return getDrawPriority() - o.getDrawPriority();
	}
	
	/**
	 * Synchronizes this Actor's position with another Actor.
	 * Normally used by Guns/Tanks, or other "compound" Actors.
	 * 
	 * @param a The other Actor to synchronize with.
	 */
	
	public void syncPosition(Actor a) {
		x = a.x;
		y = a.y;
	}
	
	/**
	 * Resets the Actor's x, y, rotation, and health. Unfortunately, our server has
	 * some issues, and we don't have the time to redesign it correctly, so we're stuck
	 * with syncing everything every second or so.
	 * 
	 * @param x The new x coordinate.
	 * @param y The new y coordinate.
	 * @param rotation The new rotation.
	 * @param health Ignored. Override this method if you need it.
	 */
	
	public void sync(double x, double y, double rotation, int health) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
}
