package gameModel;

public class TreeStump extends Obstacle {
	TreeLeaves tL;

	public TreeStump(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		tL = new TreeLeaves(w, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void receiveDamage(int d) {
		if (TRand.random() < .01)
			tL.setOnFire();

	}

	@Override
	public void collide(Collidable c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void act() {
		

	}

}
