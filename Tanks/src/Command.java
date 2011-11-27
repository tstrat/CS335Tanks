
public class Command {

	private int myPlayer;
	private int myOrder;
	
	public Command(int order, int Player) {
		myPlayer = Player;
		myOrder = order;
	}

	public int getOrder() {
		return myOrder;
	}
	
	public int getPlayer() {
		return myPlayer;
	}

}
