package gameModel;


/**
 * 
 * The PlasmaCannon is a type of Gun turret that is connected to the Heavy Tank.
 * The cannon fire shoots Larger Plasma Missiles.
 *
 */

public class PlasmaCannon extends Gun {
	
	private static int drawPriority = 20;

	/**
	 * The constructor declares the damage of the gun, the shell speed, and cool down time
	 * for each missile.
	 * 
	 * @param w - The current game world.
	 * @param x - The gun's x-coordinate.
	 * @param y - The gun's y-coordinate.
	 * @param rotation - the current tank turret rotation. Affects the direction the missile will travel.
	 */
	
	public PlasmaCannon(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		damage = 300;
		shellSpeed = 4.5;
		cD = 85;
		cDTimer = 0;
		exists = true;
	}
	
	/**
	 * When the cool down is at zero, the tank can create a new Large Plasma Missile.  When
	 * this happens the cool down is reset to max time.
	 */
	
	@Override
	public void fireMissile(Tank t) {
		if (cDTimer == 0) {
			SoundPlayer.play("plasmashot.mp3");
			new PlasmaMissileLarge(w, x, y, rotation, t);
			cDTimer += cD;
		}
	}
	
	/**
	 * Returns the drawPriority which determines how it is layered on the gui.
	 * 
	 * @return drawPriority - int
	 */
	
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

	/**
	 * The DrawObject that defines how the GUI draws the RocketGun.
	 */
	
	private static DrawObject draw = new DrawSingleFrameObject("gunFlak.png");

	/**
	 * Returns the DrawObject of the gun, which controls how the Gun is drawn.
	 */
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
