package gameModel;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public interface DrawObject {

	void draw(Graphics g, double x, double y, double rotation);
	
}
