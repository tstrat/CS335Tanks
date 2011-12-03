package gameModel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class DrawSingleFrameObject implements DrawObject {
	
	protected Image img;
	protected AffineTransform at;
	
	// Default constructor for now. This will just draw a black triangle.
	public DrawSingleFrameObject(String imgName) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(imgName));
		img = ii.getImage();
		at = new AffineTransform();

	}
	
	

	@Override
	public void draw(Graphics g, double x, double y, double rotation) {
		at.translate(x, y);
		at.rotate(rotation);
		at.translate( -getWidth() / 2, -getHeight() / 2);
		((Graphics2D)g).drawImage(img, at, null);		
		at.setToIdentity();
	}



	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return img.getWidth(null);
	}



	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return img.getHeight(null);
	}





}
