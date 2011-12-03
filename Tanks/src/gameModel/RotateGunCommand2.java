package gameModel;

public class RotateGunCommand2 extends Command {

	private double rotation;
	
	public RotateGunCommand2(int player, double rotation) {
		super(player);
		
		this.rotation = rotation;
	}
	
	public double getRotation() {
		return rotation;
	}
	
}
