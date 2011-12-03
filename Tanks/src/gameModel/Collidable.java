package gameModel;

import java.awt.Rectangle;

public abstract class Collidable extends Actor{

	protected Rectangle boundaries;
	public Collidable(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		boundaries = new Rectangle();
		// TODO Auto-generated constructor stub
	}

	public abstract void collide(Collidable c);
	
	public Rectangle getCollisionBox() {
		boundaries.setSize(getDraw().getWidth(), getDraw().getHeight());
		boundaries.setLocation((int)x, (int)y);
		//System.out.println("Object " + this + " boundaries: " + boundaries.toString());
		return boundaries;
	}
}
