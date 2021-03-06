package gameModel;

/**
 * 
 * This is a turret that creates bounceShards when fired.
 *
 */

public class ShotGun extends Gun {
	
	/**
	 * How many rounds this ShotGun fires with each shot
	 */
	
	private int simultRounds;
	
	/**
	 * The prority this Actor has when drawn over other Actors. a higher priority
	 * means it is drawn over the lower priority Actors.
	 */
	
	private static int drawPriority = 20;
	
	/**
	 * Instantiates shell speed, cool down on fire time, and damage.
	 * 
	 * @param w - The current game world.
	 * @param x - The missile's x-coordinate.
	 * @param y - The missile's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the missile will travel.
	 */
	
	public ShotGun(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		damage = 70;
		simultRounds = 2;
		shellSpeed = 9;
		cD = 15;
		cDTimer = 0;
		exists = true;
	}

	/**
	 * The fireMissile method is how the gun fires bouncing bullets. It checks the remaining
	 * cooldown on the gun, and if it is zero, it will fire and reset the cooldown to
	 * the max cooldown, and adds simultRounds number of bullets at the gun's location, around
	 * its general rotation.
	 */
	
	@Override
	public void fireMissile(Tank t) {
		if(cDTimer == 0) {
			SoundPlayer.play("shotgun.mp3");
			for(int i = 0; i < simultRounds; i++) {
				double rand = TRand.random() * .15;
				new BounceShard(w, x, y, rand + rotation + .06 * (i - simultRounds / 2), t);
			}
			cDTimer += cD;
		}
	}

	/**
	 * Returns the priority of this gun's draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Gun.
	 */
	
	private static DrawObject draw = new DrawSingleFrameObject("shotGun.png");

	/**
	 * Returns the DrawObject of the gun, which controls how the Gun is drawn.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	

}
