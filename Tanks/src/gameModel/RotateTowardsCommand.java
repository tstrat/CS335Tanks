package gameModel;

public class RotateTowardsCommand extends Command {

	/**
	 * Don't look this serialVersionUID in the 7223371365338239262L, it will burn out your eyes!
	 */
	private static final long serialVersionUID = 7223371365338239262L;
	
	private double x;
	private double y;
	
	public RotateTowardsCommand(int Player, double x, double y) {
		super(Player);
		this.x = x;
		this.y = y;
	}

	@Override
	public void applyTo(Actor a) {
		a.rotateTowards(x, y);
	}

}
