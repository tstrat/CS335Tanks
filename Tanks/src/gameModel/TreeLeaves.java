package gameModel;

public class TreeLeaves extends Actor {

	private boolean onFire;
	private int burningFor;
	private static int drawPriority = 26;

	public TreeLeaves(World w, double x, double y) {
		super(w, x, y, TRand.random());
		burningFor = 0;
	}

	@Override
	public void act() {
		if (onFire && burningFor < 500) {
			if (Math.random() < .03) {
				new FireBall(w, x - 40 + 80 * TRand.random(), y - 40 + 80
						* TRand.random(), TRand.random() * Math.PI * 2,
						TRand.random(), 3);
			}
			burningFor++;
		}

	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOnFire() {
		onFire= true;
		
	}

}
