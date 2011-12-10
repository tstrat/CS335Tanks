package gameModel;

public class SyncCommand extends Command {

	/**
	 * i <3 me sum dat serialVersionUIDs.
	 */
	private static final long serialVersionUID = -776866404145015658L;
	
	private double x;
	private double y;
	private double rotation;
	private int health;

	public SyncCommand(Tank tank) {
		super(tank.getPlayerNumber());
		
		x = tank.getX();
		y = tank.getY();
		rotation = tank.getRotation();
		health = tank.getHealth();
	}

	@Override
	public void applyTo(Actor a) {
		a.sync(x, y, rotation, health);
	}

}
