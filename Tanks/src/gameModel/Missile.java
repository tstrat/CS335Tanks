package gameModel;

public class Missile extends Collidable{
	
	private int damage;
	private double speed;
	private int framesOld;
	private int bounces;
	private static int drawPriority = 11;
	
	public Missile(World w, double x, double y, double rotation, int d, double s) {
		super(w, x, y, rotation);
		bounces = 0;
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
		if(x < 0) {
			setRotation(-(Math.PI + getRotation()));
			bounces++;
		} else if(x > 800) {
			setRotation(-(Math.PI + getRotation()));
			bounces++;
		}
		if(y > 600) {
			setRotation(-(getRotation()));
			bounces++;
		} else if(y < 0){
			setRotation(-(getRotation()));
			bounces++;
		}
		if(bounces > 2)
			explode();
	}



	@Override
	public void collide(Collidable c) {
		if(framesOld > 8){
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
		if(exists)
			new Explosion(w, x, y, 3, 50, 6);
		
		exists = false;
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}
	


}
