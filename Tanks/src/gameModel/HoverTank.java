package gameModel;

/**
 * 
 *		The HoverTank is a special kind of Tank that is very maneuverable, and
 *		can strafe, at the cost of an independently rotatable turret 
 *
 */
public class HoverTank extends Tank {

	private boolean canStrafe;
	private boolean canMove;

	public HoverTank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation, player);
		this.maxHealth = 1400;
		this.health = 1400;
		this.gun = new ShotGun(w, x, y, rotation);
		speed = 4.5;
		w.addActor(this);
		w.addActor(this.gun);
		w.addActor(new HealthBar(w, this));
		// TODO Auto-generated constructor stub
	}

	/**
	 * The DrawObject that defines how the GUI draws the Tank.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("tankLight.png");

	/**
	 * Returns the DrawObject of the tank, which controls how the Tank is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	public void act() {
		super.act();
		rotation = gun.getRotation();
		canStrafe = true;
		canMove = true;

	}

	@Override
	public void rotate(double r) {
		if (r > 0) {
			strafeRight();
		}
		else {
			strafeLeft();
		}

	}

	private void strafeRight() {
		if (canStrafe) {
			oldX = x;
			oldY = y;
			x += speed * .75 * Math.cos(rotation + Math.PI / 2);
			y += speed * .75 * Math.sin(rotation + Math.PI / 2);
		}
		canMove = false;
	}

	private void strafeLeft() {
		if (canStrafe) {
			oldX = x;
			oldY = y;
			x += speed * .75 * Math.cos(rotation - Math.PI / 2);
			y += speed * .75 * Math.sin(rotation - Math.PI / 2);
		}
		canMove = false;
	}
	
	/**
	 * Moves the tank forward at its specified speed.
	 */
	@Override
	public void moveForward() {
		if(canMove) {
			super.moveForward();
			canStrafe = false;
		}
	}
	
	/**
	 * Moves the tank backward at half its forward speed.
	 */
	@Override
	public void moveBackward() {
		if(canMove) {
			oldX = x;
			oldY = y;
			moveBackward(speed/1.5);
			canStrafe = false;
		}
	}

}
