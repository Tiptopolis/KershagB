package prime._PRIME.C_O;

import static prime._PRIME.uAppUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class RNG {

	public static long nLehmer = 0;

	public static int nSeed(int x, int y) {
		return y << 16 | x;
	}

	//
	// pass in array of color
	// seperate by >>32
	public static long Lehmer32() {
		// nLehmer = seed
		// noise-color channels// noise-layer
		nLehmer += 0xe120fc15; // : -517932011
		long tmp;
		tmp = (long) nLehmer * 0x4a39b70d; // : 1245296397
		long m1 = (tmp >> 32) ^ tmp;
		tmp = (long) m1 * 0x12fad5c9; // : 318428617
		long m2 = (tmp >> 32) ^ tmp;

		return m2;
	}

	// generates a 32-bit seed from Vector to use in gLehmer
	public static long sLehmer32(long x, long y) {
		//Log("");
		//Log(" " + x + " , " + y);
		//Log(" x_ " + x + " " + (x & 0xFFFF) + " " + ((x & 0xFFFF) << 16));
		//Log(" _" + ((x & 0xFFFF) << 32));
		//Log(" y_ " + y + " " + (y & 0xFFFF) + " " + ((y & 0xFFFF) << 16));
		//Log("__" + ((x & 0xFFFF) << 16 | (y & 0xFFFF)));
		// set seed based off x,y
		// blends a vector into a single number
		return (x & 0xFFFF) << 16 | (y & 0xFFFF);
	}

	public static long sLehmer32(long x, long y, long z) {
		//Log("");
		//Log(" " + x + " , " + y + " , " + z);
		//Log(" x_ " + x + " " + (x & 0xFFFF) + " " + ((x & 0xFFFF) << 16));
		//Log(" _" + ((x & 0xFFFF) << 32));
		//Log(" y_ " + y + " " + (y & 0xFFFF) + " " + ((y & 0xFFFF) << 16));
		//Log(" z_ " + z + " " + (z & 0xFFFF) + " " + ((z & 0xFFFF) << 16));
		//Log("___" + ((x & 0xFFFF) << 16 | (y & 0xFFFF) << 8 | (z & 0xFFFF)));
		//Log("___" + ((x & 0xFFFF) << 16 | (y & 0xFFFF) << 8 | (z & 0xFFFF)));
		
		return (x & 0xFFFF) << 16 | (y & 0xFFFF) << 8 | (z & 0xFFFF);
	}

	public static long gLehmer32(long seed) {
		seed += 0xe120fc15; // : -517932011
		long tmp;
		tmp = (long) seed * 0x4a39b70d; // : 1245296397
		long m1 = (tmp >> 32) ^ tmp;
		tmp = (long) m1 * 0x12fad5c9; // : 318428617
		long m2 = (tmp >> 32) ^ tmp;

		return m2;
	}

	public static int rndInt(int min, int max) {
		return (int) ((Lehmer32() % (max - min)) + min);
	}

	public static int rndIntSeed(Vector2 seed, Vector2 range) {
		int x = (int) seed.x;
		int y = (int) seed.y;
		long s = sLehmer32(x, y);
		long g = gLehmer32(s);

		int min = (int) range.x;
		int max = (int) range.y;

		return (int) ((g % (max - min)) + min);
	}

	public static double rndDouble(double min, double max) {
		// broken, might be used for adding variance to rndInt
		// return ((double)Lehmer32() / ((double)(0x7FFFFFFF)) * (max - min) + min);

		// return (double) ((Lehmer32() % (max - min)) + min);

		// return (double) (((Lehmer32() / 1.0d)*((max - min)) + min));

		// return (double) (((Lehmer32() % (max - min)) + min)) / ((double)
		// (0x7FFFFFFF));
		return (double) (((Lehmer32() % (max - min)) + min));

	}

}
