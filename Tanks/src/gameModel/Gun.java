package gameModel;

import java.util.ArrayList;
import java.util.List;

public class Gun extends Actor {

	private int damage;
	private double shellSpeed;
	private int cDTimer;
	private int cD;

	public Gun(World w, double x, double y, double rotation, int i) {
		super(w, x, y, rotation);
		if (i == 1)
			setGunStandard();
		cD = 25;
		cDTimer = 0;
		exists = true;
	}

	private void setGunStandard() {
		damage = 80;
		shellSpeed = 7.5;
		// coolDown = 30;
	}

	public List<Missile> fireMissile() {
		List<Missile> missles = new ArrayList<Missile>();
		if (cDTimer == 0) {
			Missile m = new Missile(w, x, y, rotation, damage, shellSpeed);
			w.addActor(m);
			missles.add(m);
			cDTimer += cD;
		}
		return missles;
	}

	@Override
	public void act() {
		if (cDTimer > 0)
			cDTimer--;
	}

	// TODO: This stuff should be moved to the relevant classes.
	private static DrawObject draw = new DrawSingleFrameObject("gunStan.png");

	@Override
	public DrawObject getDraw() {
		return draw;
	}

}
