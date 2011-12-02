package gameModel;

public class DustCloud extends Actor {

	public int ticsLeft;
	public DustCloud(World w, double x, double y) {
		super(w, x, y, 0);
		ticsLeft = 100;
	}

	@Override
	public void act() {
		ticsLeft--;
	}

	static DrawObject draw = new DrawFadingObject("dustCloud.png");
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	@Override
	public boolean exists() {
		return ticsLeft > 0;
	}

	public static void add(World w, double x, double y) {
		w.addActor(new DustCloud(w, x, y));
		System.out.println("yo");
		
	}

}
