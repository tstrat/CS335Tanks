package gameModel;

import java.awt.Rectangle;

public class Explosion extends Actor implements Collidable {

	public Explosion(double x, double y, double rotation) {
		super(x, y, rotation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub

	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	private Rectangle boundaries = new Rectangle(draw.getwidth(), draw.getHeight());
	public Rectangle getCollisionBox() {
		boundaries.setLocation((int)x, (int)y);
		return boundaries;
	}
	
	@Override
	public void act() {
	}
}
