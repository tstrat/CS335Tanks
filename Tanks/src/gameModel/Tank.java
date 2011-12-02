package gameModel;

public class Tank extends Obstacle {

	private int player;
	private double speed;
	private Gun gun;

	public Tank(double x, double y, double rotation, int player) {
		super(x, y, rotation);
		this.player = player;
		this.health = 500;
		this.speed = 1;
		this.gun = new Gun(x, y, rotation, 1);
	}

	@Override
	public int getPlayerNumber() {
		return player;
	}

	@Override
	public void receiveCommand(Command c) {
		if (c instanceof MoveCommand) {
			double delta = ((MoveCommand) c).getX();
			
			x += delta * Math.cos(rotation);
			y += delta * Math.sin(rotation);
		} else if (c instanceof RotateCommand) {
			rotation += ((RotateCommand) c).getRotation();
		}
		System.out.println("omg guys");
	}

	/**
	 * This should be called when the 'W' key is pressed. for the tank, it moves
	 * mySpeed pixels forward.
	 */
	public void button_W() {
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
	}

	/**
	 * This should be called when the 'W' key is pressed. for the tank, it moves
	 * mySpeed pixels backwards.
	 */
	public void button_S() {
		x -= speed * Math.cos(rotation);
		y -= speed * Math.sin(rotation);
	}

	/**
	 * This should be called when the 'W' key is pressed. for the tank, it turns
	 * the tank left 1 degree.
	 */
	public void button_A() {
		rotation += .05;
	}

	/*
	 * This should be called when the 'W' key is pressed. for the tank, it turns
	 * the tank right 1 degree.
	 */
	public void button_D() {
		rotation -= .05;
	}

	public void fire(World w) {
		gun.fireMissile(w);
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("tankStan.png");
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	


}
