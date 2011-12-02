package gameModel;

public class Terrain extends Collidable {

	public Terrain(World w, double x, double y, double rotation) {
		super(w, x, y, rotation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collide(Collidable c) {
	}
	
	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("missile.png");	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	@Override
	public void act() {		
	}

}
