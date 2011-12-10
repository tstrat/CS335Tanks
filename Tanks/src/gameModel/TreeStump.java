package gameModel;

/**
 * This class defines the base of a tree obstacle. The stump is an indestructible
 * obstacle, and is connected to tree leaves. Whenever the trunk takes damage, there
 * is a small chance the leaves above will catch fire. The leaves do not collide with
 * anything, but are drawn over all objects, making them great for cover, until they
 * catch fire.
 * 
 * NOTE: To make forest fires happen, ideal spacing between trees for a gradual, spreading
 * fire is roughly 100 pixels in eahc direction
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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receiveDamage(int d) {
		if (TRand.random() < .035 && d > 0)
			tL.setOnFire();

	}

	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void act() {
		

	}
	
	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority ;
	}

	private static DrawObject draw = new DrawSingleFrameObject("treeStump.png");
	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
