
public class MoveCommand extends Command {

	private double x;
	private double y;
	
	public MoveCommand(int player, double x, double y) {
		super(player);
		
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}
