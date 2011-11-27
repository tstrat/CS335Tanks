import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;


public abstract class Drawable implements Comparable<Drawable> {

	private String imageName;
	private AffineTransform affine;
	protected double rotation;
	private Point location;

	
	public Drawable(int x, int y, String img) {
		imageName = img;
		affine = new AffineTransform();
		affine.translate(x, y);
		rotation = 0;
		location = new Point();
	}
	
	public int compareTo(Drawable d) {
		//TODO: some ordering system, with terrain with lowest;
		// if an object has higher compareTo, it is drawn on top
		return 0;
	}
	
	public void draw(Graphics2D g2d, ImageObserver o) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
		Image image = ii.getImage();
		g2d.drawImage(image, affine, o);
	}
	
	public Point getLocation() {
		affine.rotate(-1 * rotation);
		location.x = (int)affine.getTranslateX();
		location.y = (int)affine.getTranslateY();
		affine.rotate(rotation);
		return location;		
	}
	
}
