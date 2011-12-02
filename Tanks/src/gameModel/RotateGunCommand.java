package gameModel;

public class RotateGunCommand extends Command {

	private int x;
	private int y;
	
	public RotateGunCommand(int Player, int x, int y) {
		super(Player);
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
