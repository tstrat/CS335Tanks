package gameModel;

public class DustCloud extends Actor {

	public int ticsLeft;
	private static int drawPriority = 12;
	
	public DustCloud(World w, double x, double y) {
		super(w, x, y, Math.random());
		ticsLeft = 100;
	}

	@Override
	public void act() {
		ticsLeft--;
	}

	private DrawObject draw = new DrawFadingObject("dustCloud.png");
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	@Override
	public boolean exists() {
		return ticsLeft > 0;
	}

	public static void add(World w, double x, double y) {
		if (Math.random() < 0.5)
			w.addActor(new DustCloud(w, x, y));
	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}
}
