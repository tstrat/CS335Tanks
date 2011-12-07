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
		w.addActor(new ExplosionSoundEffect(w, x, y));
		for(int i = 0; i < fireCount; i ++) {
			w.addActor(new FireBall(w, x, y, 2 * Math.PI * Math.random(), Math.random() * radius/ 20,  damage));
		}
	}

	private static class ExplosionSoundEffect extends Actor {

		public ExplosionSoundEffect(World w, double x, double y) {
			super(w, x, y, 0);
		}

		/**
		 * Removes this object right after it is created.
		 */
		@Override
		public void act() {
			exists = false;
		}

		/**
		 * This is unimportant since this object doesn't get drawn.
		 * 
		 * @return Don't worry about it.
		 */
		@Override
		public int getDrawPriority() {
			// TODO Auto-generated method stub
			return 0;
		}

		/**
		 * An ExplosionSoundEffect does not need to be drawn.
		 * 
		 * @return Null.
		 */
		@Override
		public DrawObject getDraw() {
			return null;
		}
		
		/**
		 * Gets the SoundPlayer for the explosion sound effect.
		 * 
		 * @return A SoundPlayer loaded with an explosion sound.
		 */
		@Override
		public SoundPlayer getSoundPlayer() {
			return new SoundPlayer(getClass().getResource("explode.mp3").getFile());
		}
		
	}

}
