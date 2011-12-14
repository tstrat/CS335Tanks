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
		seed(System.currentTimeMillis());
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
		int x, r = 0xfffffffe;
		i = (i + 1) & 4095;
		t = a * q[i] + c;
		c = (int) (t >> 32);
		x = (int) (t + c);
		if (x < c) {
			++x;
			++c;
		}
		q[i] = r - x;
		return Math.abs(q[i] % max);
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
		int x = (int)l;
		q[0] = x;
		q[1] = x + PHI;
		q[2] = x + PHI + PHI;
		for (int i = 3; i < q.length; ++i)
			q[i] = q[i-3] ^ q[i-2] ^ PHI ^ i;
	}
}
