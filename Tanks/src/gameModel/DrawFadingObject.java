package gameModel;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class DrawFadingObject extends DrawSingleFrameObject {
	private float f;
	private AlphaComposite ac;
	
	public DrawFadingObject(String imgName) {
		super(imgName);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f = 1);
	}
	
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
	public void fade(){
		if(f < .01)
			return;
		f -= .01;
		 ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f); 
	}


}
