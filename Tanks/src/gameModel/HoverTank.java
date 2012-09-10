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

	/**
	 * Creates a new Hover tank.  Sets health/max health baseSpeed and speed, as well as the health bar
	 * and the ShotGun object that goes with the tank.  Its mine cooldown is set at 75
	 * 
	 * @param w The World that this Obstacle belongs to.
	 * @param x The initial x-position.
	 * @param y The initial y-position.
	 * @param rotation The initial rotation.
	 * 
	 * @param player  The player that the tank belongs to.
	 */
	
	public HoverTank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation, player);
		this.maxHealth = 1400;
		this.health = 1400;
		this.gun = new ShotGun(w, x, y, rotation);
		baseSpeed = 4.5;
		speed = 4.5;
		new HealthBar(w, this);	
		mineMaxCD = 75;
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
	
	/**
	 * Calls the tanks act method.
	 * 
	 * @see gameModel.Tank.act()
	 */
	
	public void act() {
		super.act();
		rotation = gun.getRotation();
		canStrafe = true;
		canMove = true;

	}
	
	/**
	 * Based on the double passed in, the tank will either strafe right or left.
	 * 
	 * @param r Rotation double that decides which way to strafe.
	 */
	@Override
	public void rotate(double r) {
		if (r > 0) {
			strafeRight();
		}
		else {
			strafeLeft();
		}

	}
	
	/**
	 * This caused the tank to move to the right while still pointed in the direction
	 * of the mouse.  It will move in a circle around the mouse.
	 */
	
	private void strafeRight() {
		if (canStrafe) {
			oldX = x;
			oldY = y;
			x += speed * .75 * Math.cos(rotation + Math.PI / 2);
			y += speed * .75 * Math.sin(rotation + Math.PI / 2);
		}
		canMove = false;
	}
	
	/**
	 * This caused the tank to move to the left while still pointed in the direction
	 * of the mouse.  It will move in a circle around the mouse.
	 */
	
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
	
	@Override
	public void modSpeed(int n) {
	}
	
	/**
	 *  When the cool down is 0, the tank can lay a SpiderMine.  When that happens, the
	 *  mine cool down is reset to its max. 
	 */
	
	@Override
	public void layMine() {
		if(mineCD > 0)
			return;
		mineCD += mineMaxCD;
		new SpiderMine(w, x, y, rotation, this);
	}

}
