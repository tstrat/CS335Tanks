package test;

import gameModel.TRand;

/**
 * Run this class to inspect the behavior of the random number generator.
 * 
 * @author Parker Snell
 */
public class RndTest {

	public static void main(String[] args) {
		System.out.println("Testing random integers between 0 and 100:");
		for (int i = 0; i < 10; ++i) {
			System.out.print(TRand.randInt(100));
			System.out.print("\t");
			System.out.print(TRand.randInt(100));
			System.out.print("\t");
			System.out.println(TRand.randInt(100));
		}
		
		System.out.println("\n--------------------\n");
		
		System.out.println("Testing random doubles between 0 and 1:");
		for (int i = 0; i < 10; ++i) {
			System.out.print(TRand.random());
			System.out.print("\t");
			System.out.print(TRand.random());
			System.out.print("\t");
			System.out.println(TRand.random());
		}
	}

}
