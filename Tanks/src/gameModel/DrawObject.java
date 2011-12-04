package gameModel;

import java.awt.Graphics;

/**
 * Draws an object on the screen. This interface is used to generalize drawing behavior,
 * so it doesn't have to be reimplemented for each Actor class.
 * 
 * @author Parker Snell
 */
public interface DrawObject {

	/**
	 * Draws the object, in an implementation-defined way.
	 * 
	 * @param g The Graphics to draw to (this will always be a Graphics2D).
	 * @param x The x-position of the object.
	 * @param y The y-position of the object.
	 * @param rotation The rotation of the object.
	 */
	void draw(Graphics g, double x, double y, double rotation);

	/**
	 * Returns the height of the thing to be drawn.
	 * 
	 * @return The height.
	 */
	int getHeight();

	/**
	 * Returns the width of the thing to be drawn.
	 * 
	 * @return The width.
	 */
	int getWidth();
	
}
