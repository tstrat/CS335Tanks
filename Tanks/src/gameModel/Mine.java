package gameModel;

public abstract class Mine extends Collidable {

	protected Tank t;
	private int drawPriority = 9;
	private int player;
	
	public Mine(World w, double x, double y, double rotation, Tank t, int player) {
		super(w, x, y, rotation);
		this.player = player;
		this.t = t;
	}



	@Override
	public void act() {
		
	}

	@Override
	public int getDrawPriority() {
		return drawPriority;
	}

	private static DrawObject draw = new DrawSingleFrameObject("Invisible.png");
	private static DrawObject selectiveDraw = new DrawSingleFrameObject("mineTarget.png");
	@Override
	public DrawObject getDraw() {
		// TODO Auto-generated method stub
		if(w.getPlayer() != player)
			return draw;
		return selectiveDraw;
	}
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Tank && !c.equals(t)) {
			activateMine();
		}
	}



	public abstract void activateMine();

}
