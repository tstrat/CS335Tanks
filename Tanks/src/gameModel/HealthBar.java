package gameModel;

public class HealthBar extends Actor {
	private Tank o;

	public HealthBar(World w, Tank o) {
		super(w, o.getX(), o.getY() + 30, 0);
		this.o = o;
		draw = new DrawHealthObject(o.getHealth(), o.getMaxHealth());
	}
	
	public boolean exists() {
		return o.getHealth() > 0;
	}

	@Override
	public void act() {
		updateBar();
	}

	private void updateBar() {
		x = o.getX();
		y = o.getY() - 30;
	}

	@Override
	public int getDrawPriority() {
		// TODO Auto-generated method stub
		return 30;
	}

	private DrawObject draw;
	@Override
	public DrawObject getDraw() {
		((DrawHealthObject)draw).updateHealth(o.getHealth(), o.getMaxHealth());
		return draw;
	}

}
