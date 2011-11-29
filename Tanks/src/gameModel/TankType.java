package gameModel;

public enum TankType {
	
	STANDARD;
	
	public int health() {
		switch (this) {
		case STANDARD:
			return 500;
		default:
			return 500;
		}
	}
	
	public double speed() {
		switch (this) {
		case STANDARD:
			return 1;
		default:
			return 1;
		}
	}
	
	public Gun gun() {
		switch (this) {
		case STANDARD:
			return new Gun(1);
		default:
			return new Gun(1);
		}
	}
		
}
