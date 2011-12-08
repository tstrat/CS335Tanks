package gameModel;

/**
 * This class is a replacement for Math.random and similar, to allow
 * synchronized random numbers across all networked copies of the game.
 * 
 * @author Parker Snell, Seungwoo Sun
 */
public class TRand {
	/**
	 * A drop-in replacement for Math.random().
	 * 
	 * @return A random number between 0 and 1.
	 */
	public static double random() {
		return Math.random();
	}
	
	/**
	 * Convenience method; returns an integer in the range [0, max).
	 * 
	 * @param max The upper bound.
	 * @return A random integer.
	 */
	public static int randInt(int max) {
		return (int)(random() * max);
	}
	
	/**
	 * Convenience method; returns an integer in the range [min, max).
	 * 
	 * @param min The lower bound.
	 * @param max The upper bound.
	 * @return A random integer.
	 */
	public static int randInt(int min, int max) {
		return randInt(max - min) + min;
	}
}
