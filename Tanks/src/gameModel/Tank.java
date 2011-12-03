package gameModel;

import java.util.ArrayList;

public class Tank extends Obstacle {

	private int player;
	private double speed;
	private Gun gun;
	private double oldX, oldY;
	public Tank(World w, double x, double y, double rotation, int player) {
		super(w, x, y, rotation);
		oldX = x;
		oldY = y;
		this.player = player;
		this.health = 20000;
		this.speed = 1;
		this.gun = new Gun(w, x, y, rotation, 1);
		w.addActor(this);
		w.addActor(this.gun);
		new ArrayList<Missile>();
	}

	@Override
	public int getPlayerNumber() {
		return player;
	}

	
	@Override
	public void receiveCommand(Command c) {
		if (c instanceof MoveCommand) {
			double delta = ((MoveCommand) c).getX();
			oldX = x;
			oldY = y;
			x += delta * Math.cos(rotation);
			y += delta * Math.sin(rotation);
		} else if (c instanceof RotateCommand) {
			rotation += ((RotateCommand) c).getRotation();
		} else if (c instanceof FireCommand) {
			fire();
		} else if (c instanceof RotateGunCommand) {
			this.gun.rotateTowards(((RotateGunCommand) c).getX(), ((RotateGunCommand) c).getY());
		} else if (c instanceof RotateGunCommand2) {
			this.gun.rotate(((RotateGunCommand2) c).getRotation());
		}
	}
	
	@Override
	public void act() {
		this.gun.setX(x);
		this.gun.setY(y);
		super.act();
		stayInBounds();
	}
	
	private void stayInBounds() {
		if(x < 0)
			x = 0;
		else if(x > 800)
			x = 800;
		if(y < 0)
			y = 0;
		else if(y > 600)
			y = 600;
		
	}

	/**
	 * This should be called when the 'W' key is pressed. for the tank, it moves
	 * mySpeed pixels forward.
	 */
	public void button_W() {
		x += speed * Math.cos(rotation);
		y += speed * Math.sin(rotation);
	}

	/**
	 * This should be called when the 'W' key is pressed. for the tank, it moves
	 * mySpeed pixels backwards.
	 */
	public void button_S() {
		x -= speed * Math.cos(rotation);
		y -= speed * Math.sin(rotation);
	}

	/**
	 * This should be called when the 'W' key is pressed. for the tank, it turns
	 * the tank left 1 degree.
	 */
	public void button_A() {
		rotation += .05;
	}

	/*
	 * This should be called when the 'W' key is pressed. for the tank, it turns
	 * the tank right 1 degree.
	 */
	public void button_D() {
		rotation -= .05;
	}

	public void fire() {
		gun.fireMissile();
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("tankStan.png");
	
	@Override
	public DrawObject getDraw() {
		return draw;
	}
	
	@Override
	public boolean exists() {
		if(health <= 0) {
			this.gun.destroy();
			return false;
		}
		return true;
		
	}
	
	@Override
	public void collide(Collidable c) {
		if(c instanceof Obstacle) {
			x = oldX;
			y = oldY;
		}
	}
	


}
