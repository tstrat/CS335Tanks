package gameModel;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Draws an object that fades out after a period of time.
 * 
 * @author Seungwoo Sun
 */
public class DrawFadingObject extends DrawSingleFrameObject {
	/**
	 * The current fade percentage (between 0 and 1).
	 */
	private float f;
	
	/**
	 * The Composite representing a semi-transparent image.
	 */
	private AlphaComposite ac;
	
	/**
	 * Constructs a DrawFadingObject for a given image.
	 * 
	 * @param imgName The name of the image file to load.
	 */
	public DrawFadingObject(String imgName) {
		super(imgName);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f = 1);
	}
	
	/**
	 * Draws the image and fades it out each time this is called.
	 * 
	 * @see DrawObject.draw(Graphics, double, double, double)
	 */
	@Override
	public void draw(Graphics g, double x, double y, double rotation){
		at.translate(x, y);
		at.rotate(rotation);
		at.translate( -getWidth() / 2, -getHeight() / 2);
		Graphics2D g2 = (Graphics2D)g;
		Composite old = g2.getComposite();
		g2.setComposite(ac);
		g2.drawImage(img, at, null);
		g2.setComposite(old);
		at.setToIdentity();
		fade();
	}
	
	/**
	 * Fades the object out. If the fade value is already zero, this method does nothing.
	 */
	public void fade() {
		if (f == 0.0f)
			return;
		
		f -= .05f;
		if (f <= 0.05f)
			f = 0.0f;
		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f); 
	}


}
