package gameModel;
/**
 * 
 * @author Seungwoo Sun
 * 
 *		This class defines the tanks for the game "Tanks!". It is an Actor
 *		that is player and AI controllable, and takes commands given by the
 *		player to either move or fire. The Tank is linked to this player (and
 *		will only take that player's commands), and also has a health bar and
 *		a rotatable turret from which it fires.
 *
 */
public class Tank extends Obstacle {

	/**
	 * The number of the player that owns and controls the Tank.
	 */
	protected int player;
	
	/**
	 * The Gun owned by the Tank.
	 */
	protected Gun gun;
	
	/**
	 * The speed of the Tank
	 */
	protected double speed;
	/**
	 * The position of the Tank in the previous frame. The Tank reverts to this
	 * position of the tank's current location is invalid.
	 */
	protected double oldX, oldY;
	
	protected int mineCD, mineMaxCD;
	
	public Tank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation);
		this.player = player;
		oldX = x;
		oldY = y;
		mineCD = 0;
		mineMaxCD = 500;
	}


	/**
	 * The act method is called every time tick by the World. First, the Tank makes sure that its coordinates are
	 * within a 800x600 rectangle. It then sets its turret xy coordinates to its own xy coordinates. Then it calls
	 * the superclass Obstacle's act method.
	 */
	@Override
	public void act() {
		stayInBounds();
		gun.syncPosition(this);
		if(mineCD > 0)
			mineCD--;
	}
	
	/**
	 * This is the getMethod for the tank's player. This is ues so that the Tank
	 * does not receive commands not sent out by its specific player.
	 */
	@Override
	public int getPlayerNumber() {
		return player;
	}
	
	/**
	 * Moves the tank forward at its specified speed.
	 */
	@Override
	public void moveForward() {
		oldX = x;
		oldY = y;
		moveForward(speed);

	}
	
	/**
	 * Moves the tank backward at half its forward speed.
	 */
	@Override
	public void moveBackward() {
		oldX = x;
		oldY = y;
		moveBackward(speed/2.0);
	}
	
	/**
	 * Rotates the gun by a given amount. This should usually only be called by RotateGunCommand.
	 * 
	 * @param r The rotation in radians.
	 */
	public void rotateGun(double r) {
		gun.rotate(r);
	}
	

	/**
	 * Makes the gun fire a missile.
	 */
	public void fireMissile() {
		gun.fireMissile(this);
	}
	
	public void layMine() {
		if(mineCD > 0)
			return;
		mineCD += mineMaxCD;
	}
	
	/**
	 * Returns whether the tank is in existence or not (if it isn't, the World needs to remove Tank from its Actor
	 * List). This is determined by whether the tank's health is greater than zero. If the tank dies. it explodes
	 * violently and its gun is destroyed too.
	 */
	@Override
	public boolean exists() {
		if (health <= 0) {
			this.gun.destroy();
			Explosion.createExplosion(w, x, y, 8, 100, 2);
			return false;
		}
		return true;

	}
	
	/**
	 * The stayInBounds method 
	 */
	private void stayInBounds() {
		if (x < 0)
			x = 0;
		else if (x > 800)
			x = 800;
		if (y < 0)
			y = 0;
		else if (y > 600)
			y = 600;

	}
	

	/**
	 * The tank's collision method causes its location to reset to the position from before it moved if its new location
	 * causes it to collide with an obstacle.
	 */
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank)
			((Tank) c).bumpback();
		else if(c instanceof Obstacle) {
			bumpback();
		}
	}
	
	public void bumpback() {
		x = oldX;
		y = oldY;
	}
	
	/**
	 * Rotates the gun when the Tank rotates.
	 * 
	 * @param r The amount to rotate by.
	 */
	@Override
	public void rotate(double r) {
		gun.rotate(r);
		super.rotate(r);
	}

	/**
	 * Rotates the gun to point at a specific x and y coordinate.
	 * 
	 * @param x The x-coordinate to rotate toward.
	 * @param y The y-coordinate to rotate toward.
	 */
	public void rotateGunTo(int x, int y) {
		gun.rotateTowards(x, y);
	}

}
