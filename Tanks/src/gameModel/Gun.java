package gameModel;

import java.awt.Rectangle;

public class Gun extends Actor {

	private int damage;
	private double shellSpeed;
	
	public Gun(double x, double y, double rotation, int i) {
		super(x, y, rotation);
		if(i == 1) 
			setGunStandard();
		//cDTimer = 0;
		exists = true;
	}

	/**
	 * We can make an enum concerning guns,
	 * as was done with Tank.
	 */
	private void setGunStandard() {
		damage = 80;
		shellSpeed = 7.5;
		//coolDown = 30;
	}

	public void fireMissile(World w) {
		Missile m = new Missile(x, y, rotation, damage, shellSpeed);
		m.setX(x);
		m.setY(getY());
		m.setRotation(getRotation());
		w.addActor(m);
		//cDTimer += coolDown;
	}

	@Override
	public void act() {
/*		if(cDTimer > 0)
			cDTimer--;*/
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("gunStan.png");
	@Override
	public DrawObject getDraw() {
		return draw;
	}


}
