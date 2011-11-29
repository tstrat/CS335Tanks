package gameModel;

import java.awt.Graphics;

public class DrawSingleFrameObject implements DrawObject {
	
	// Default constructor for now. This will just draw a black triangle.
	public DrawSingleFrameObject() {
	}

	@Override
	public void draw(Graphics g, double x, double y, double rotation) {
		int[] xPoints = new int[] { (int)x, (int)x + 50, (int)x };
		int[] yPoints = new int[] { (int)y, (int)y + 25, (int)y + 50 };
		
		g.drawPolygon(xPoints, yPoints, 3);
	}

}
