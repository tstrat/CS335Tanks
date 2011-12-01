package gameModel;

public abstract class Actor implements CommandReceiver {
	
	protected boolean exists;
	protected double x, y;
	protected double rotation;
	
	public abstract void act();
	
	/*
	 * I don't know how these will work, so I made them
	 * not abstract (so no one had to implement them) and
	 * had them return 0.
	 * --Seungwoo Sun
	 */
	public double getWidth() {return 0;}
	public double getHeight() {return 0;}
	
	public void setX(double x) {
		this.x = x;
	}
	public double getX() {
		return x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	public double getY() {
		return y;
	}
	
	public void setRotation(double d) {
		this.rotation = d;
	}
	public double getRotation() {
		return rotation;
	}
	
	public abstract DrawObject getDraw();
	
	/**
	 * For non-player objects (the default), this should return 0,
	 * a value that will never be assigned to a player. Player objects
	 * (tanks) should override this to return whichever player number
	 * they are, so that they will receive the relevant commands.
	 * 
	 * @return A player number, or 0 if the object doesn't represent a player.
	 */
	public int getPlayerNumber() {
		return 0;
	}
	
	/**
	 * This method is only needed by player classes (the ones that
	 * override getPlayerNumber). It is used to process Commands.
	 * 
	 * @param c The Command to process.
	 */
	@Override
	public void receiveCommand(Command c) {
		// Do nothing.
	}
	
}
