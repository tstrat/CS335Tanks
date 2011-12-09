package gameModel;

public abstract class LargeMissile extends Missile {

	protected boolean isLarge;
	
	public LargeMissile(World w, double x, double y, double rotation, Tank t) {
		super(w, x, y, rotation, t);
		isLarge = true;
	}
	
	public boolean isLarge() {
		return isLarge;
	}


}
