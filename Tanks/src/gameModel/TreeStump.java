package gameModel;

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
		if (TRand.random() < .005)
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
