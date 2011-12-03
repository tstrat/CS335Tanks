package gameModel;

public class Explosion {

	public Explosion(World w, double x, double y, int damage, double radius) {
		System.out.println(damage);
		for(int i = 0; i < damage; i += 40) {
			w.addActor(new FireBall(w, x, y, 2 * Math.PI * Math.random(), Math.random() * radius/ 20));
			System.out.println(i);
		}
	}


}
