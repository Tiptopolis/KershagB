package prime._PRIME.N_M.UTIL;

import static prime._PRIME.N_M.UTIL.N_Operator.*;

public class N_Resolver {

	public static Number resolveTo(Number n, Number Z) {
		// basically converts Z to type n, float to int rounds
		return N_Operator.add(N_Operator.mul(N_Resolver.resolve(n), 0), Z);
	}

	public static Number resolve(Object n) {

		if (n == null || !(Number.class.isAssignableFrom(n.getClass()))) {
			return Float.NaN;
		}

		if (n instanceof Number)
			return resolve((Number) n);
		
		if (n instanceof CharSequence)
			return resolve((String) n);

		
		return Float.NaN;

	}

	public static Number resolve(String s) {
		if (s.contains("."))
			try {
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {
				return Float.NaN;
			}
		else
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return Float.NaN;
			}
	}

	public static Number stringInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return Float.NaN;
		}
	}

	public static Number stringFlt(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return Float.NaN;
		}
	}

	public static Number resolve(Number n) {
		if (n == null)
			return Float.NaN;

		if ((Float.class.isAssignableFrom(n.getClass()))) {

			return resolveF(n);
		}

		if ((Integer.class.isAssignableFrom(n.getClass()))) {

			return resolveI(n);
		}
		if ((Double.class.isAssignableFrom(n.getClass()))) {

			return resolveD(n);
		}

		if ((Short.class.isAssignableFrom(n.getClass()))) {

			return resolveS(n);
		}

		if ((Long.class.isAssignableFrom(n.getClass()))) {

			return resolveL(n);
		}

		if ((Byte.class.isAssignableFrom(n.getClass()))) {

			return resolveL(n);
		}

		return n;
	}

	public static Number as(Object n, Number Z) {
		Number N = resolve(n);
		return as(N, Z);
	}

	public static Number as(Number n, Number Z) {
		// basically converts n to type Z, float to int rounds
		return add(mul(resolve(Z), 0), n);
	}

	//////////////////////////////

	public static float resolveF(Number n) {
		return n.floatValue();
	}

	public static float[] resolveF(Number... n) {
		float[] res = new float[n.length];
		for (int i = 0; i < n.length; i++) {
			res[i] = n[i].floatValue();
		}

		return res;
	}

	public static float[] resolveF(Object... n) {
		float[] res = new float[n.length];
		for (int i = 0; i < n.length; i++) {
			res[i] = (float) N_Resolver.resolve(n[i]);
		}

		return res;
	}

	public static int resolveI(Number n) {
		return n.intValue();
	}

	public static int[] resolveI(Number... n) {
		int[] res = new int[n.length];
		for (int i = 0; i < n.length; i++) {
			res[i] = n[i].intValue();
		}

		return res;
	}

	public static double resolveD(Number n) {
		return n.doubleValue();
	}

	public static short resolveS(Number n) {
		return n.shortValue();
	}

	public static long resolveL(Number n) {
		return n.longValue();
	}

	public static byte resolveB(Number n) {
		return n.byteValue();
	}

	public static int c(char c) {
		return (int) stringInt("" + c);
	}

	public static Number[] scanFrom(Object[] other) {
		Number[] Result = new Number[other.length];

		for (int i = 0; i < other.length; i++) {

			Number N = resolve(other[i]);
			Result[i] = N;

		}

		return Result;
	}

	public static Number[] toDigits(Number N) {
		Number[] Result = new Number[N.intValue()];
		for (int i = 0; i <= Result.length; i++) {
			Result[i] = i;
		}

		return Result;
	}

}
