package gameModel;


/**
 * 
 * This is a rocket turret that the standard tanks use.
 *
 */
public class RocketGun extends Gun {
	
	/**
	 * The prority this Actor has when drawn over other Actors. a higher priority
	 * means it is drawn over the lower priority Actors.
	 */
	
	private static int drawPriority = 20;

	public RocketGun(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		damage = 300;
		shellSpeed = 8;
		cD = 45;
		cDTimer = 0;
		exists = true;
	}
	
	/**
	 * The fireMissile method is how the gun fires rockets. It checks the remaining
	 * cooldown on the gun, and if it is zero, it will fire and reset the cooldown to
	 * the max cooldown, and adds a new Rocket at the gun's location, with its
	 * rotation.
	 */
	
	public void fireMissile(Tank t) {
		if (cDTimer == 0) {
			SoundPlayer.play("rocketlaunch.mp3");
			new Rocket(w, x, y, rotation, t);
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
	 * The DrawObject that defines how the GUI draws the RocketGun.
	 */
	
	private static DrawObject draw = new DrawSingleFrameObject("gunStan.png");

	/**
	 * Returns the DrawObject of the gun, which controls how the Gun is drawn.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
}
