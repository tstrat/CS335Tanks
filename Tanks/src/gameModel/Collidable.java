package gameModel;

import java.awt.Rectangle;

public interface Collidable {

	public void collide(Collidable c);
	
	public Rectangle getCollisionBox();
}
