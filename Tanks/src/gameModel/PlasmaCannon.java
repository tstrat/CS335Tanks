package gameModel;

public class PlasmaCannon extends Gun {
	
	private static int drawPriority = 20;

	public PlasmaCannon(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		damage = 300;
		shellSpeed = 4.5;
		cD = 85;
		cDTimer = 0;
		exists = true;
	}

	@Override
	public void fireMissile(Tank t) {
		if (cDTimer == 0) {
			new PlasmaMissileLarge(w, x, y, rotation, t);
			cDTimer += cD;
		}
	}

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
