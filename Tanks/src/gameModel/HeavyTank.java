package gameModel;

public class HeavyTank extends Tank {

	public HeavyTank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation, player);
		this.maxHealth = 3600;
		this.health = 3600;
		this.gun = new ShotGun(w, x, y, rotation);
		speed = 1.8;
		w.addActor(this);
		w.addActor(this.gun);
		w.addActor(new HealthBar(w, this));	
	}
	
	/**
	 * The DrawObject that defines how the GUI draws the Tank.
	 */
	private static DrawObject draw = new DrawSingleFrameObject("tankHeav.png");
	
	/**
	 * Returns the DrawObject of the tank, which controls how the Tank is drawn.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
