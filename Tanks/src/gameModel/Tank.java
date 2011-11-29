package gameModel;

public class Tank extends Obstacle{
	
	private int player;
	private double speed;
	private Gun gun;
	
	public Tank(double x, double y, double rotation, int player, TankType tankType) {
		super(x, y, rotation);
		this.player = player;
		this.health =tankType.health();
		this.speed = tankType.speed();
		this.gun = tankType.gun();	
	}


	public int getPlayer() {
		return player;
	}

	public void receiveCommand(Command c) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * This should be called when the 'W' key is pressed.
	 * for the tank, it moves mySpeed pixels forward.
	 */
	public void button_W() {
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
	}
	
	/*
	 * This should be called when the 'W' key is pressed.
	 * for the tank, it moves mySpeed pixels backwards.
	 */
	public void button_S() {
		x -= speed * Math.cos(rotation);
		y -= speed * Math.sin(rotation);
	}
	
	/*
	 * This should be called when the 'W' key is pressed.
	 * for the tank, it turns the tank left 1 degree.
	 */
	public void button_A() {
		rotation += .05;
	}
	
	/*
	 * This should be called when the 'W' key is pressed.
	 * for the tank, it turns the tank right 1 degree.
	 */
	public void button_D() {
		rotation -= .05;
	}
	
	public void fire(World w) {
		gun.fireMissile(w);
	}

}
