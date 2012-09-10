package gameModel;


/**
 * 
 * @author Messiah Kane
 *
 *		The TreeLeaves class defines the actor that is created upon construction of
 *		a tree stump. The TreeLEaves can occasionally catch fire when the treeStump
 *		that contains these leaves is damaged. 
 *
 */
public class TreeLeaves extends Actor {

	/**
	 * This is true if these elaves are on fire
	 */
	private boolean onFire;
	/**
	 * Indicates how long the leaves have been burning.
	 */
	private int burningFor;
	private static int drawPriority = 26;

	public TreeLeaves(World w, double x, double y) {
		super(w, x, y, TRand.random());
		burningFor = 0;
	}

	/**
	 * When the tree is on fire, it constantly throws around fireballs. Otherwise 
	 * it does nothing.
	 */
	@Override
	public void act() {
		if (onFire && burningFor < 500) {
			if (TRand.random() < .2) {
				new FireBall(w, x - 60 + 120 * TRand.random(), y - 60 + 120
						* TRand.random(), TRand.random() * Math.PI * 2,
						TRand.random(), 2);
			}
			burningFor++;
		}

	}
	
	/**
	 * If the leaves have been burning for 500 time units, they no longer eixst. Otherwise,
	 * they do.
	 */
	public boolean exists() {
		return burningFor < 500;
	}

	/**
	 * Returns the drawPriority. A higher drawPriority will cause this actor to be
	 * drawn on top of other actors.
	 */
	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

	private DrawObject draw1 = new DrawSingleFrameObject("treeleaves.png");
	private DrawObject draw2 = new DrawFadingObject("treeleaves.png", 500);
	/**
	 * If the leaves are on fire, return a DrawFadingObject, otherwise return a DrawSingleFrameObject
	 */
	@Override
	public DrawObject getDraw() {
		if(!onFire)
			return draw1;
		return draw2;
	}
	
	/**
	 * This method is called when a tree is supposed to ignite.  It allows other methods to draw
	 * the correct image of the burning tree.
	 */
	public void setOnFire() {
		onFire= true;
		
	}

}
