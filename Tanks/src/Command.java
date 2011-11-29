
public abstract class Command {

	private int myPlayer;
	
	public Command(int Player) {
		myPlayer = Player;
	}
	
	public int getPlayer() {
		return myPlayer;
	}

}
