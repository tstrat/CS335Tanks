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
	private int player;
	
	/**
	 * The Gun owned by the Tank.
	 */
	private Gun gun;
	
	/**
	 * The position of the Tank in the previous frame. The Tank reverts to this
	 * position of the tank's current location is invalid.
	 */
	private double oldX, oldY;

	/**
	 * This is the constructor from the tank. It calls the superconstructor of
	 * Obstacle to handle the world, location, and position of the newly
	 * instantiated tank, and adds itself, its newly instantiated gun, and its
	 * newly instantiated healthbar to the world.
	 * 
	 * @param w
	 * 			The World to add the Tank, its Gun, and its HealthBar to.
	 * @param x
	 * 			The x coordinate of the tank.
	 * @param y
	 * 			The y coordinate of the tank.
	 * @param rotation
	 * 			The angle the tank is pointing.
	 * @param player
	 * 			The player that controls the given tank.
	 */
	public Tank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation);
		oldX = x;
		oldY = y;
		this.player = player;
		this.maxHealth = 2500;
		this.health = 2500;
		this.gun = new Gun(w, x, y, rotation);
		w.addActor(this);
		w.addActor(this.gun);
		w.addActor(new HealthBar(w, this));
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
	 * The recieveCommand method takes commands from the player that controls this Tank and interprets them.
	 * It finds out which specific command was issued to the tank and then makes the tank execute the action
	 * corresponding to that command.
	 * 
	 * @param c
	 * 			The command to interpret and execute.
	 */
	@Override
	public void receiveCommand(Command c) {
		if (c instanceof MoveCommand) {
			double delta = ((MoveCommand) c).getX();
			oldX = x;
			oldY = y;
			x += delta * Math.cos(rotation);
			y += delta * Math.sin(rotation);
		} else if (c instanceof RotateCommand) {
			rotation += ((RotateCommand) c).getRotation();
			this.gun.rotate(((RotateCommand) c).getRotation());
		} else if (c instanceof FireCommand) {
			gun.fireMissile();
		} else if (c instanceof RotateGunCommand) {
			this.gun.rotateTowards(((RotateGunCommand) c).getX(),
					((RotateGunCommand) c).getY());
		} else if (c instanceof RotateGunCommand2) {
			this.gun.rotate(((RotateGunCommand2) c).getRotation());
		}
	}

	/**
	 * The act method is called every time tick by the World. First, the Tank makes sure that its coordinates are
	 * within a 800x600 rectangle. It then sets its turret xy coordinates to its own xy coordinates. Then it calls
	 * the superclass Obstacle's act method.
	 */
	@Override
	public void act() {
		stayInBounds();
		this.gun.setX(x);
		this.gun.setY(y);
		super.act();
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
	 * The DrawObject that defines how the GUI draws the Tank.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("tankStan.png");
	
	/**
	 * Returns the DrawObject of the tank, which controls how the Tank is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

	/**
	 * Returns whether the tank is in existence or not (if it isn't, the World needs to emove Tank from its Actor
	 * List). This is determined by whether the tank's health is greater than zero. If the tank dies. it explodes
	 * violently and its gun is destroyed too.
	 */
	@Override
	public boolean exists() {
		if (health <= 0) {
			this.gun.destroy();
			new Explosion(w, x, y, 8, 100, 2);
			return false;
		}
		return true;

	}

	/**
	 * The tank's collision method causes its location to reset to the position from before it moved if its new location
	 * causes it to collide with an obstacle.
	 */
	@Override
	public void collide(Collidable c) {
		if (c instanceof Obstacle) {
			x = oldX;
			y = oldY;
		}
	}

}
