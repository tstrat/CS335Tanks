package gameModel;

/**
 * This class defines the base of a tree obstacle. The stump is an indestructible
 * obstacle, and is connected to tree leaves. Whenever the trunk takes damage, there
 * is a small chance the leaves above will catch fire. The leaves do not collide with
 * anything, but are drawn over all objects, making them great for cover, until they
 * catch fire.
 * 
 * 
 * @author Messiah Frickin' Kane
 *
 */
public class TreeStump extends Obstacle {
	TreeLeaves tL;
	private static int drawPriority = 10;

	public TreeStump(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		tL = new TreeLeaves(w, x, y);
		health = 10;
		maxHealth = 10;
	}

	/**
	 * The TreeStimp does not take damage in its receiveDamage method,
	 * meaning it cannot be harmed. However, upon taking damage, there
	 * is a chance its leaves will catch fire.
	 */
	@Override
	public void receiveDamage(int d) {
		if (TRand.random() < .035 && d > 0)
			tL.setOnFire();
	}

	/**
	 * Empty collide method.
	 */
	@Override
	public void collide(Collidable c) {

	}
	
	/**
	 * Empty act method.
	 */
	@Override
	public void act() {

	}
	
	/**
	 * Returns the drawPriority. A higher drawPriority will cause this actor to be
	 * drawn on top of other actors.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority ;
	}

	private static DrawObject draw = new DrawSingleFrameObject("treeStump.png");
	
	/**
	 * Returns a DrawObject that can draw a TreeStump.
	 */
	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
