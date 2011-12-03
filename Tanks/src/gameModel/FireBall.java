package gameModel;

public class FireBall extends Collidable {
	
	private int lifeTime;
	private double speed;
	
	public FireBall(World w, double x, double y, double rotation, double speed) {
		super(w, x, y, rotation);
		lifeTime = 20;
		this.speed = speed;
	}

	@Override
	public void collide(Collidable c) {
		if(c instanceof Obstacle) {
			((Obstacle) c).receiveDamage(2);
		}

	}

	@Override
	public void act() {
		lifeTime--;
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);

	}
	
	@Override
	public boolean exists() {
		return lifeTime > 0;
	}

	private DrawObject draw = new DrawFadingObject("fireBall.png");
	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
