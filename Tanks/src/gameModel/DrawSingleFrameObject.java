package gameModel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

/**
 * Draws an object with only a single frame.
 * 
 * @author Parker Snell, Seungwoo Sun
 */

public class DrawSingleFrameObject implements DrawObject {
	
	/**
	 * The Image to draw.
	 */
	
	protected Image img;
	
	/**
	 * The AffineTransform, representing the rotation of this object.
	 */
	
	protected AffineTransform at;
	
	/**
	 * Initializes the Image and AffineTransform members. Currently, if imgName
	 * points to an invalid image, you will get exceptions.
	 * 
	 * @param imgName A filename, for a valid image.
	 */
	
	public DrawSingleFrameObject(String imgName) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(imgName));
		img = ii.getImage();
		at = new AffineTransform();
	}
	
	/**
	 * Draws the image at a specific location, with a rotation.
	 * 
	 * @see DrawObject.draw(Graphics, double, double, double)
	 */
	
	@Override
	public void draw(Graphics g, double x, double y, double rotation) {
		at.translate(x, y);
		at.rotate(rotation);
		at.translate( -getWidth() / 2, -getHeight() / 2);
		((Graphics2D)g).drawImage(img, at, null);		
		at.setToIdentity();
	}

	/**
	 * Returns the width of the image.
	 * 
	 * @return The width of the image.
	 */
	
	@Override
	public int getWidth() {
		return img.getWidth(null);
	}

	/**
	 * Returns the height of the image.
	 * 
	 * @return The height of the image.
	 */
	
	@Override
	public int getHeight() {
		return img.getHeight(null);
	}

}
