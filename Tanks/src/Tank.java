
public class Tank extends Obstacle{
	
	private int myPlayer;
	
	public Tank(int x, int y, String img, int player, int tankType) {
		super(x, y, img);
		myPlayer = player;
		// TODO depending on tankType, it will set this 
		// tank's guns, health, etc		
	}


	public int getPlayer() {
		return myPlayer;
	}

	public void receiveCommand(Command c) {
		// TODO Auto-generated method stub
		
	}

}
