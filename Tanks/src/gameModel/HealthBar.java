package gameModel;


/**
 * An indicator of how much "health" a tank has remaining.
 * @author Sean
 *
 */

public class HealthBar extends Actor {
	private Tank o;
	private static int drawPriority = 25;
	
	/**
	 * Constructor for the health bar.  Takes the current game world and a tank, and draws a new health bar.
	 * @param w - the current game world
	 * @param o - a tank actor
	 */
	
	public HealthBar(World w, Tank o) {
		super(w, o.getX(), o.getY() + 30, 0);
		this.o = o;
		draw = new DrawHealthObject(o.getHealth(), o.getMaxHealth());
	}
	
	/**
	 * Returns true if the tank has not been destroyed.
	 */
	
	public boolean exists() {
		return o.getHealth() > 0;
	}

	/**
	 * Calls updateBar() every turn.
	 */
	
	@Override
	public void act() {
		updateBar();
	}

	
	/**
	 * Sets the location of the HealthBar to 30 pixels above the tank.
	 */
	private void updateBar() {
		x = o.getX();
		y = o.getY() - 30;
	}
	
	/**
	 * Returns the priority of this HealthBar draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

	
	/**
	 * The DrawObject that defines how the GUI draws the HealthBar.
	 */
	private DrawObject draw;
	
	/**
	 * Returns the DrawObject of the HealthBar, which controls how the HealthBar is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		((DrawHealthObject)draw).updateHealth(o.getHealth(), o.getMaxHealth());
		return draw;
	}

}
