package gameModel;

public class MudPatch extends Terrain {
	
	private static int drawPriority = 1;
	private static DrawObject draw = new DrawSingleFrameObject("mud.png");

	public MudPatch(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
	}

	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank)
			((Tank) c).modSpeed(-150);
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
