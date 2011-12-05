package gameModel;

/**
 * @author Seungwoo Sun
 * 
 *		The Explosion class simply instantiates a group of fireBalls according to its
 *		createExplosion method.
 *
 */
public class Explosion {

	/**
	 * The static method createExplosion creates an explosion of fireballs according to
	 * the passed parameters. The fireballs move at a random speed and direction from the
	 * center, up to a certain point depending on the radius of the explosion.
	 * 
	 * @param w
	 * 			The World to add the FireBall objects to
	 * @param x
	 * 			The x coordinate of the center of the explosion
	 * @param y
	 * 			The y coordinate of the center of the explosion
	 * @param fireCount
	 * 			The number of fireballs to create in this explosion
	 * @param radius
	 * 			The max distance/2 the FireBall objects can travel from the center
	 * @param damage
	 * 			The damage per tick of each FireBall object.
	 */
	public static void createExplosion(World w, double x, double y, int fireCount, double radius, int damage) {
		for(int i = 0; i < fireCount; i ++) {
			w.addActor(new FireBall(w, x, y, 2 * Math.PI * Math.random(), Math.random() * radius/ 20,  damage));
		}
	}


}
