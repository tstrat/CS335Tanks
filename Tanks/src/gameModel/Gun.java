package gameModel;

public class Gun extends Actor {

	private int damage;
	private double shellSpeed;
/*	private int coolDown;
	private int cDTimer;*/
	
	public Gun(int i) {
		if(i == 1) 
			setGunStandard();
		//cDTimer = 0;
		exists = true;
	}

	/*
	 * We can make an enum concerning guns,
	 * as was done with Tank.
	 */
	private void setGunStandard() {
		damage = 80;
		shellSpeed = 7.5;
		//coolDown = 30;
	}

	public void fireMissile(World w) {
		Missile m = new Missile(damage, shellSpeed);
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


}
