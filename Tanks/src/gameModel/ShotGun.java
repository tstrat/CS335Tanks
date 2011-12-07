package gameModel;

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

	public ShotGun(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		damage = 70;
		simultRounds = 5;
		shellSpeed = 15;
		cD = 30;
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
	public void fireMissile() {
		if(cDTimer == 0) {
			for(int i = 0; i < simultRounds; i++) {
				double rand = Math.random() * .15;
				BounceShard m = new BounceShard(w, x, y, rand + rotation + .04 * (i - simultRounds / 2), damage, shellSpeed);
				w.addActor(m);
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
