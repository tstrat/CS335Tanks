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
			if (Math.random() < .2) {
				new FireBall(w, x - 60 + 120 * TRand.random(), y - 60 + 120
						* TRand.random(), TRand.random() * Math.PI * 2,
						TRand.random(), 2);
			}
			burningFor++;
		}

	}
	
	public boolean exists() {
		return burningFor < 500;
	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

	private DrawObject draw1 = new DrawSingleFrameObject("treeleaves.png");
	private DrawObject draw2 = new DrawFadingObject("treeleaves.png", 500);
	@Override
	public DrawObject getDraw() {
		if(!onFire)
			return draw1;
		return draw2;
	}

	public void setOnFire() {
		onFire= true;
		
	}

}
