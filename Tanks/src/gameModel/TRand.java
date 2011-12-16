package gameModel;

/**
 * This class is a replacement for Math.random and similar, to allow
 * synchronized random numbers across all networked copies of the game.
 * It uses the complementary-multiply-with-carry algorithm, adapted
 * from some C code on Wikipedia.
 * 
 * @author Parker Snell, Seungwoo Sun
 */
public class TRand {
	private static final int PHI = 0x9e3779b9;
	private static int[] q;
	
	private static int c;
	
	private static int i;
	
	static
	{
		// Seed the randomizer for local play.
		q = new int[4096];
		c = 365436;
		i = 4095;
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
		long t, a = 18782L;
		long x, r = 0xfffffffeL;
		i = (i + 1) & 4095;
		t = a * q[i] + c;
		c = (int) (t >>> 32);
		x = (t + c) & 0x7fffffff;
		if (x < c) {
			++x;
			++c;
		}
		q[i] = (int)(r - x);

		return (q[i] & 0x7fffffff) % max;
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
		int x = (int)l ^ (int)(l >> 32);
		q[0] = x;
		q[1] = x + PHI;
		q[2] = x + PHI + PHI;
		for (int idx = 3; idx < q.length; ++idx)
			q[idx] = q[idx-3] ^ q[idx-2] ^ PHI ^ idx;
	}
}
