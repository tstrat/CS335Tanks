
public class Missile extends Actor implements Collidable{
	
	private int damage;
	private double speed;
	
	public Missile(int d, double s) {
		damage = d;
		speed = s;
		exists = true;
	}

	@Override
	public void act() {
		double theta = rotation / 2 * Math.PI;
		x += speed * Math.cos(theta);
		y += speed * Math.sin(theta);
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

}
