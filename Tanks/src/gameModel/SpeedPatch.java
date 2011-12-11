package gameModel;

public class SpeedPatch extends Terrain {
	
	private static int drawPriority = 1;
	private static DrawObject draw = new DrawSingleFrameObject("speedPatch.png");

	public SpeedPatch(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}

	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank)
			((Tank) c).modSpeed(100);
	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return drawPriority;
	}

	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		return draw;
	}

}
