package gameModel;

public class PlasmaBallSmall extends Missile {

	public PlasmaBallSmall(World w, double x, double y, double rotation, int d,
			double s) {
		super(w, x, y, rotation, d, s);
		maxBounces = 1;
		framesInactive = 0;
	}
	
	public void act() {
		bounce();
		if (framesOld % 5 == 0)
			SmallPlasmaTrail.add(w, x, y);
		
		super.act();
	}


	public void explode() {
		w.addActor(new SmallPlasmaTrail(w, x, y));
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
