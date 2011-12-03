package gameModel;

public class Explosion {

	public Explosion(World w, double x, double y, int fireCount, double radius, int damage) {
		for(int i = 0; i < fireCount; i ++) {
			w.addActor(new FireBall(w, x, y, 2 * Math.PI * Math.random(), Math.random() * radius/ 20,  damage));
		}
	}


}
