package gameModel;

import java.awt.Rectangle;

public class Missile extends Actor implements Collidable{
	
	private int damage;
	private double speed;
	
	public Missile(World w, double x, double y, double rotation, int d, double s) {
		super(w, x, y, rotation);
		damage = d;
		speed = s;
		exists = true;
	}

	@Override
	public void act() {
		bounce();
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
	}
	
	public void bounce() {
		if(x < 0)
			setRotation(-(Math.PI + getRotation()));
		else if(x > 800)
			setRotation(-(Math.PI + getRotation()));
		if(y > 600)
			setRotation(-(getRotation()));
		if(y < 0)
			setRotation(-(getRotation()));
		
	}



	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub
		if(c instanceof Obstacle) {
			this.explode();
			((Obstacle) c).receiveDamage(damage);
		}
		else if(c instanceof Missile) {
			this.explode();
			((Missile) c).explode();
		}
			
	}
	
	public void explode() {
		//TODO: makes a new explosion object
		// This is problematic, as are all SFX;
		// Since they all require knowing the 
		// world
		
		exists = false;
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	private Rectangle boundaries = new Rectangle(draw.getWidth(), draw.getHeight());
	public Rectangle getCollisionBox() {
		boundaries.setLocation((int)x, (int)y);
		return boundaries;
	}

}
