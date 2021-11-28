package prime._PRIME.C_O;

import java.math.BigDecimal;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Maths {

	// EXP4j Expressions
	// fill array with permutations of inputs

	// so #[]{1,2,3}

	// calculatePermutations(sVector/Array inputConstants)
	// pass in an nSized Vector/Array
	// returns Array containing possible arrangements

	// base of the natural logarithm
	// has a limit of(1+1/n)^n as n approaches infinity
	// arises in compound interest
	// Sum of the infinite series
	// Sigma(0-infinity)[for(int n = 0; n < Infinity; n++]
	// {
	// 1/Factorial(n) n!
	// }
	// irrational number
	public static double e = 2.71828f;
	public static double EulersNumber = e;

	public static double E(int n) {
		float result = 0;
		result = 1 / factorial(n);
		return result;
	}

	public static int factorial(int n) {
		// Factorial of n = n! in normal math
		// recursion lol
		if (n == 0)
			return 1;
		else
			return (n * factorial(n - 1));
	}

	public static float roundToNearest(float x, float num) {
		//return x * (num / x);
		return num*(Math.round((float)x/num));
		// 5*(Math.ceil(Math.abs(number/5))); //upper value nearest multiple of 5
		// 5*(Math.floor(Math.abs(number/5)));//lower value nearest multiple of 5
	}

	//greatest common divisor
	public static int gcd(int p, int q) {
		if (q == 0)
			return p;
		if (p == 0)
			return q;

		// p and q even
		if ((p & 1) == 0 && (q & 1) == 0)
			return gcd(p >> 1, q >> 1) << 1;

		// p is even, q is odd
		else if ((p & 1) == 0)
			return gcd(p >> 1, q);

		// p is odd, q is even
		else if ((q & 1) == 0)
			return gcd(p, q >> 1);

		// p and q odd, p >= q
		else if (p >= q)
			return gcd((p - q) >> 1, q);

		// p and q odd, p < q
		else
			return gcd(p, (q - p) >> 1);
	}

	@SuppressWarnings("deprecation")
	public static BigDecimal round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	public static <T> void printAllRecursive(int n, T[] elements, char delimiter) {

		if (n == 1) {
			printArray(elements, delimiter);
		} else {
			for (int i = 0; i < n - 1; i++) {
				printAllRecursive(n - 1, elements, delimiter);
				if (n % 2 == 0) {
					swap(elements, i, n - 1);
				} else {
					swap(elements, 0, n - 1);
				}
			}
			printAllRecursive(n - 1, elements, delimiter);
		}
	}

	public static boolean isEven(int n) {
		// Least Significant Bit of an odd number is always set (1)
		// if ((n.byteValue() ^ 1) == n.byteValue() + 1) {
		// return (true);
		// } else
		// return (false);
		return ((n & 1) == 1) ? false : true;
	}

	public static boolean isOdd(int n) {
		// Least Significant Bit of an odd number is always set (1)
		// if ((n.byteValue() ^ 1) == n.byteValue() + 1) {
		// return (true);
		// } else
		// return (false);
		return ((n & 1) == 1) ? true : false;
	}

	private static <T> void swap(T[] input, int a, int b) {
		T tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}

	// Mapping function for doubles (standard only for floats)
	public static double map(double val, double oMin, double oMax, double nMin, double nMax) {
		return ((val - oMin) / (oMax - oMin)) * (nMax - nMin) + nMin;
	}

	// Mapping function for doubles (standard only for floats)
	public static float map(float val, float oMin, float oMax, float nMin, float nMax) {
		return ((val - oMin) / (oMax - oMin)) * (nMax - nMin) + nMin;
	}
	
	// Mapping function for doubles (standard only for floats)
	public static int map(int val, float oMin, float oMax, float nMin, float nMax) {
		return (int) (((val - oMin) / (oMax - oMin)) * (nMax - nMin) + nMin);
	}


	private static <T> void printArray(T[] input, char d) {
		System.out.print('\n');

		//
		// substring
		//
		for (int i = 0; i < input.length; i++) {
			System.out.print(input[i]);
		}

	}
}
