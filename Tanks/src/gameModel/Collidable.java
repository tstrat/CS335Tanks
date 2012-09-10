package gameModel;


import java.awt.Rectangle;

/**
 * This is a specialization of Actor that may collide with other objects
 * in the World.
 * 
 * @author Seungwoo Sun
 */

public abstract class Collidable extends Actor{

	/**
	 * The box in which, if other Collidables enter this space,
	 * they are considered to be colliding with this object.
	 */
	
	protected Rectangle boundaries;
	
	/**
	 * Constructs a Collidable.
	 * 
	 * @see Actor.Actor(World, double, double, double)
	 * 
	 * @param w The World that this Collidable belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 */
	
	public Collidable(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		boundaries = new Rectangle();
	}

	/**
	 * Called when this Collidable collides with another object.
	 * 
	 * @param c The object to collide with.
	 */
	
	public abstract void collide(Collidable c);
	
	/**
	 * Gets the collision box, which is recalculated each time this is called.
	 * 
	 * @see Collidable.boundaries
	 * 
	 * @return The current collision box.
	 */
	
	public Rectangle getCollisionBox() {
		boundaries.setSize((int)(getDraw().getWidth() * .85), (int)(getDraw().getHeight() * .85));
		boundaries.setLocation((int)(x - .425 * getDraw().getWidth()), (int)(y - .425 * getDraw().getWidth()));
		return boundaries;
	}
}
