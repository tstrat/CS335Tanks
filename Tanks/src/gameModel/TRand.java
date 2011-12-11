package gameModel;

import java.util.Random;

/**
 * This class is a replacement for TRand.random and similar, to allow
 * synchronized random numbers across all networked copies of the game.
 * 
 * @author Parker Snell, Seungwoo Sun
 */
public class TRand {
	private static Random rnd = new Random();
	
	/**
	 * A drop-in replacement for TRand.random().
	 * 
	 * @return A random number between 0 and 1.
	 */
	public static double random() {
		return rnd.nextDouble();
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

	public static void seed(double d) {
		rnd.setSeed((long)(d * 893473904));
	}
}
