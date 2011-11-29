package gameModel;

public abstract class Actor {
	
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
	
}