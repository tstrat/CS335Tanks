package gameModel;

public class Missile extends Collidable{
	
	private int damage;
	private double speed;
	private int framesOld;
	
	public Missile(World w, double x, double y, double rotation, int d, double s) {
		super(w, x, y, rotation);
		damage = d;
		speed = s;
		exists = true;
		framesOld = 0;
	}

	@Override
	public void act() {
		bounce();
		framesOld++;
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
		DustCloud.add(w, x, y);
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
		if(framesOld > 10){
			if(c instanceof Obstacle) {
				this.explode();
				((Obstacle) c).receiveDamage(damage);
			}
			else if(c instanceof Missile) {
				this.explode();
				((Missile) c).explode();
			}
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
	


}
