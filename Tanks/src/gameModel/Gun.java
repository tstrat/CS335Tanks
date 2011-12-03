package gameModel;

public class Gun extends Actor {

	private int damage;
	private double shellSpeed;
	private int cDTimer;
	private int cD;
	private static int drawPriority = 20;

	public Gun(World w, double x, double y, double rotation, int i) {
		super(w, x, y, rotation);
		if (i == 1)
			setGunStandard();
		cD = 45;
		cDTimer = 0;
		exists = true;
	}

	private void setGunStandard() {
		damage = 300;
		shellSpeed = 7.5;
		// coolDown = 30;
	}

	public void fireMissile() {
		if (cDTimer == 0) {
			Missile m = new Missile(w, x, y, rotation, damage, shellSpeed);
			w.addActor(m);
			cDTimer += cD;
		}
	}

	@Override
	public void act() {
		if (cDTimer > 0)
			cDTimer--;

	}

	public void rotateTowards(double x, double y) {
		if (rotation < 0)
			rotation += 2 * Math.PI;
		else if (rotation > 2 * Math.PI)
			rotation -= 2 * Math.PI;
		double dx = x - this.x;
		double dy = y - this.y;
		double theta = Math.atan(dy / dx);
		if (dx < 0 && dy > 0) {
			theta = 1 * Math.PI + theta;
			System.out.println(theta * 180 / Math.PI);
		} else if (dx < 0 && dy < 0) {
			theta = Math.PI + theta;
			System.out.println(theta * 180 / Math.PI);
		} else if (dx > 0 && dy < 0) {
			theta = 2 * Math.PI + theta;
			System.out.println(theta * 180 / Math.PI);
		} else {
			System.out.println(theta * 180 / Math.PI);
			
		}
		System.out.println(": " + rotation* 180 / Math.PI);
		if (theta > rotation)
			rotation += .2;
		if (theta < rotation)
			rotation -= .2;
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("gunStan.png");

	@Override
	public DrawObject getDraw() {
		return draw;
	}

	public void rotate(double rotation) {
		this.rotation += rotation;
		
	}
	
	public void destroy() {
		exists = false;
	}
	
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

}
