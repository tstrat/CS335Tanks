package gameModel;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class is responsible for drawing the health bar over an Tank.
 * 
 * @author Seungwoo Sun
 */
public class DrawHealthObject implements DrawObject{
	
	/**
	 * The current health value.
	 */
	private int health;
	
	/**
	 * The maximum health value. This can change, but is always
	 * greater than or equal to the current value.
	 */
	private int max;
	
	public DrawHealthObject(int health, int max) {
		this.health = health;
		this.max = max;

	}

	/**
	 * Draws the health bar.
	 * 
	 * @see DrawObject.draw(Graphics, double, double, double)
	 */
	@Override
	public void draw(Graphics g, double x1, double y1, double rotation) {
		int x = (int) x1;
		int y = (int) y1;
		g.drawRect(x - 11, y, 31, 5);
		Color old = g.getColor();
		g.setColor(Color.GREEN);
		int hWid = (int)((health * 30) / max);
		g.fillRect(x - 10, y + 1, hWid, 4);
		g.setColor(Color.RED);
		g.fillRect((int)x - 10 + hWid, (int)y + 1, 30 - hWid, 4);
		g.setColor(old);
	}
	
	/**
	 * Changes the current and maximum health values. Max should never be less than health.
	 * 
	 * @param health The current health value.
	 * @param max The maximum health value.
	 */
	public void updateHealth(int health, int max) {
		this.health = health;
		this.max = max;
	}

	/**
	 * The height of the health bar is not important, so this returns 0.
	 * 
	 * @return Don't worry about it.
	 */
	@Override
	public int getHeight() {
		return 0;
	}

	/**
	 * The width is just about as important as the height, so this also returns 0.
	 * 
	 * @return Don't worry about it.
	 */
	@Override
	public int getWidth() {
		return 0;
	}

}
