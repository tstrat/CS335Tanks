package gameModel;

import java.awt.Graphics;

public interface DrawObject {

	void draw(Graphics g, double x, double y, double rotation);

	int getHeight();

	int getWidth();
	
}
