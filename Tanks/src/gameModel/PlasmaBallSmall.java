package gameModel;

public class PlasmaBallSmall extends Missile {
	
	private int framesOld;

	public PlasmaBallSmall(World w, double x, double y, double rotation,Tank t) {
		super(w, x, y, rotation, t);
		damage = 150;
		framesOld = 0;
		speed = 8;
		bounces = 1;
		maxBounces = 2;
	}
	
	public void act() {
		bounce();
		if (framesOld % 5 == 0)
			SmallPlasmaTrail.add(w, x, y);
		framesOld++;
		super.act();
	}


	public void explode() {
		new SmallPlasmaTrail(w, x, y);
		exists = false;
	}
	
	private DrawObject draw = new DrawSingleFrameObject("plasma.png");
	private int drawPriority = 11;
	
	/**
	 * Gets the DrawObject used to draw this DustCloud. This can return null,
	 * in which case nothing should be drawn.
	 * 
	 * @return A DrawObject representing this DustCloud, or null.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	/**
	 * Returns the priority of this clouds draw. A higher priority object is drawn over
	 * a lower priority object in the main GUI.
	 */
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority ;
	}

	

}
