package gameModel;

public class Missile extends Collidable{
	
	/**
	 * How much damage a missile does.
	 */
	
	private int damage;
	
	/**
	 * How fast a missile moves.
	 */
	
	private double speed;
	private int framesOld;
	
	/**
	 * A tracker of how many times a missile has "bounced" against different surfaces.  After two "bounces", it explodes.
	 */
	
	private int bounces;
	private static int drawPriority = 11;
	
	/**
	 * A constructor for a new missile object. Sets its location, its damage, and its speed.
	 * 
	 * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Effects the direction the missile will travel in.
	 * @param d - How much damage this missile will do.
	 * @param s - How fast this missile will move.
	 */
	
	public Missile(World w, double x, double y, double rotation, int d, double s) {
		super(w, x, y, rotation);
		bounces = 0;
		damage = d;
		speed = s;
		exists = true;
		framesOld = 0;
	}

	/**
	 * Updates the missile's position based on its speed.
	 * Also, calls DustCloud to make sure the missile has
	 * a cloud graphic following it. 
	 */
	
	@Override
	public void act() {
		bounce();
		framesOld++;
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
		DustCloud.add(w, x, y);
	}
	
	/**
	 * If the missile has reached the edge of the map, makes it bounce
	 * off that wall.  If it has already bounced 2 times, it explodes.
	 */
	
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
