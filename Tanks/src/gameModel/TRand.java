package gameModel;

import java.util.Random;

/**
 * This class is a replacement for Math.random and similar, to allow
 * synchronized random numbers across all networked copies of the game.
 * It uses the complementary-multiply-with-carry algorithm, adapted
 * from some C code on Wikipedia.
 * 
 * @author Parker Snell, Seungwoo Sun
 */
public class TRand {
	
	private static final Random r = new Random();
	
	private static final int PHI = 0x9e3779b9;
	private static int[] q;
	
	private static int c;
	
	private static int i;
	
	static
	{
		// Seed the randomizer for local play.
		seed(System.nanoTime());
	}
	
	/**
	 * A drop-in replacement for Math.random().
	 * 
	 * @return A random number between 0 and 1.
	 */
	public static double random() {
		return (double)randInt(Integer.MAX_VALUE) / Integer.MAX_VALUE;
	}
	
	/**
	 * Convenience method; returns an integer in the range [0, max).
	 * 
	 * @param max The upper bound.
	 * @return A random integer.
	 */
	public static int randInt(int max) {
		return r.nextInt(max);
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
	
	/**
	 * Sets a seed for the random so that all random events can be synchronized.
	 * @param l - Long value to change seed value.
	 */
	public static void seed(long l) {
		r.setSeed(l);
	}
}
