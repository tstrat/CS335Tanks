package gameModel;

public class LargePlasmaTrail extends Actor {
	
	private int ticsLeft;
	private static int drawPriority = 12;
	
	LargePlasmaTrail(World w, double x, double y) {
		super(w, x, y, 0);
		ticsLeft = 100;
		// TODO Auto-generated constructor stub
	}

	public static void add(World w, double x, double y) {
		if (Math.random() < 0.2)
			w.addActor(new LargePlasmaTrail(w, x, y));
	}
	
	@Override
	public void act() {
		ticsLeft--;
	}
	
	/**
	 * Checks if the DustCloud has ran out of ticks, if so
	 * DustCloud should cease to exist.
	 */
	@Override
	public boolean exists() {
		return ticsLeft > 0;
	}
	
	private DrawObject draw = new DrawFadingObject("plasma2.png");
	
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
		return drawPriority;
	}

}
