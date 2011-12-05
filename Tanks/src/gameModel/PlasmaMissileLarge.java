package gameModel;

public class PlasmaMissileLarge extends LargeMissile {

	private int lifeSpan;
	private boolean attached;

	public PlasmaMissileLarge(World w, double x, double y, double rotation,
			int d, double s) {
		super(w, x, y, rotation, d, s);
		maxBounces = 2;
		lifeSpan = 300;
		framesInactive = 17;
		attached = false;
		// TODO Auto-generated constructor stub
	}

	public void act() {
		bounce();
		if (lifeSpan == 0)
			explode();
		if (!attached) {
			super.act();
			if (framesOld % 5 == 0)
				LargePlasmaTrail.add(w, x, y);
			if(lifeSpan < 300)
				lifeSpan = 0;
		}
		attached = false;

	}

	public void bounce() {
		if (x < 0 || x > 800 || y < 0 || y > 600) {
			breakApart();
		}
	}

	private void breakApart() {
		for(int i = 0; i < 4; i++)
			w.addActor(new PlasmaBallSmall(w, x, y, rotation - 252 + i * Math.PI / 10, 150, 8));
		explode();
		
	}
	
	public void explode() {
		if(exists)
			w.addActor(new LargePlasmaTrail(w, x, y));
		exists = false;
	}

	private DrawObject draw = new DrawSingleFrameObject("plasma2.png");
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


	/**
	 * The collide method dictates how it interacts with objects it is colliding
	 * with. It first checks to make sure the Missile hasn't just been made (so
	 * it doesn't collide with the tank that fired it). If it hasn't, it will
	 * damage any obstacle it collides with, and explode if it came in contact
	 * with either an obstacle or a missile.
	 */
	@Override
	public void collide(Collidable c) {
		if (framesOld <= framesInactive && bounces == 0)
			return;
		if (c instanceof Obstacle) {
			attach(((Obstacle) c), c.getX(), c.getY());
		}
	}

	private void attach(Obstacle c, double cX, double cY) {
		attached = true;
		if (x + 7 < cX)
			x += 7;
		else if (x - 7 > cX)
			x -= 7;
		else
			x = cX;
		if (y + 7 < cY)
			y += 7;
		else if (y - 7 > cY)
			y -= 7;
		else
			y = cY;
		lifeSpan--;
		c.receiveDamage(3);


	}
}
