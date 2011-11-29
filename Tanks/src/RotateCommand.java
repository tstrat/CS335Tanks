
public class RotateCommand extends Command {

	private double rotation;
	
	public RotateCommand(int player, double rotation) {
		super(player);
		
		this.rotation = rotation;
	}
	
	public double getRotation() {
		return rotation;
	}
	
}
