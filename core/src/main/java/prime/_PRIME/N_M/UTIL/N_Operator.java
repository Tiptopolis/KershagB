package prime._PRIME.N_M.UTIL;

import static prime._PRIME.N_M.UTIL.N_Resolver.*;

import prime._PRIME.N_M.N_;

public class N_Operator extends N_ {

	public static Number add(Number a, Number b) {
		// takes on class of Number a

		if (a instanceof Float) {
			return a.floatValue() + b.floatValue();
		}

		if (a instanceof Integer) {
			return a.intValue() + b.intValue();
		}

		if (a instanceof Short) {
			return a.shortValue() + b.shortValue();
		}

		if (a instanceof Long) {
			return a.longValue() + b.longValue();
		}

		if (a instanceof Double) {
			return a.doubleValue() + b.doubleValue();
		}

		if (a instanceof Byte) {
			return a.byteValue() + b.byteValue();
		}

		return a;
	}

	public static Number sub(Number a, Number b) {
		if (a instanceof Float) {
			return a.floatValue() - b.floatValue();
		}

		if (a instanceof Integer) {
			return a.intValue() - b.intValue();
		}

		if (a instanceof Short) {
			return a.shortValue() - b.shortValue();
		}

		if (a instanceof Long) {
			return a.longValue() - b.longValue();
		}

		if (a instanceof Double) {
			return a.doubleValue() - b.doubleValue();
		}

		if (a instanceof Byte) {
			return a.byteValue() - b.byteValue();
		}

		return a;
	}

	public static Number mul(Number a, Number b) {
		if (a instanceof Float) {
			return a.floatValue() * b.floatValue();
		}

		if (a instanceof Integer) {
			return a.intValue() * b.intValue();
		}

		if (a instanceof Short) {
			return a.shortValue() * b.shortValue();
		}

		if (a instanceof Long) {
			return a.longValue() * b.longValue();
		}

		if (a instanceof Double) {
			return a.doubleValue() * b.doubleValue();
		}

		if (a instanceof Byte) {
			return a.byteValue() * b.byteValue();
		}

		return a;
	}

	public static Number div(Number a, Number b) {
		Number A = a;
		if (b.floatValue() == 0)
			return Float.NaN;

		if (A instanceof Float) {
			return a.floatValue() / b.floatValue();
		}

		if (A instanceof Integer) {
			return a.intValue() / b.intValue();
		}

		if (A instanceof Short) {
			return a.shortValue() / b.shortValue();
		}

		if (A instanceof Long) {
			return a.longValue() / b.longValue();
		}

		if (A instanceof Double) {
			return a.doubleValue() / b.doubleValue();
		}

		if (A instanceof Byte) {
			return a.byteValue() / b.byteValue();
		}

		return a;
	}

	public static Number pow(Number a, Number b) {

		// a^x
		Number A = a;
		if (b.floatValue() == 0)
			b = 1;

		if (A instanceof Float) {
			// Log("-----> " + A + " " + b + " " + (float) Math.pow(a.floatValue(),
			// b.floatValue()));
			return (float) Math.pow(a.floatValue(), b.floatValue());
		}

		if (A instanceof Integer) {
			return Math.pow(a.intValue(), b.intValue());
		}

		if (A instanceof Short) {
			return Math.pow(a.shortValue(), b.shortValue());

		}

		if (A instanceof Long) {
			return Math.pow(a.longValue(), b.longValue());
		}

		if (A instanceof Double) {
			return Math.pow(a.doubleValue(), b.doubleValue());
		}

		if (A instanceof Byte) {
			return Math.pow(a.byteValue(), b.byteValue());
		}

		return a;
	}

	public static Number root(Number a, Number r) {
		Number A = a;
		if (A instanceof Float) {
			return pow(A, div(1d, r));
		}

		if (A instanceof Integer) {
			// cant subdivide integers
			return pow(A.floatValue(), div(1d, r.floatValue()));
		}

		if (A instanceof Short) {
			return pow(A, div(1d, resolve(r)));
		}

		if (A instanceof Long) {
			return pow(A, div(1d, resolve(r)));
		}

		if (A instanceof Double) {
			return pow(A, div(1d, resolve(r)));
		}

		if (A instanceof Byte) {
			return pow(A, div(1d, resolve(r)));
		}

		return a;
	}

	public static Number mod(Number a, Number b) {
		// sign of divisor
		if (a instanceof Float) {
			return Math.signum(N_Resolver.resolveF(b)) * (a.floatValue() % b.floatValue());
		}

		if (a instanceof Integer) {
			return Math.signum(N_Resolver.resolveD(b)) * (a.intValue() % b.intValue());
		}

		if (a instanceof Short) {
			return Math.signum(N_Resolver.resolveD(b)) * (a.shortValue() % b.shortValue());
		}

		if (a instanceof Long) {
			return Math.signum(N_Resolver.resolveD(b)) * (a.longValue() % b.longValue());
		}

		if (a instanceof Double) {
			return Math.signum(N_Resolver.resolveD(b)) * (a.doubleValue() % b.doubleValue());
		}

		if (a instanceof Byte) {
			return Math.signum(N_Resolver.resolveD(b)) * (a.byteValue() % b.byteValue());
		}

		return a;
	}

	public static Number rem(Number a, Number b) {
		// sign of dividend
		if (a instanceof Float) {
			return Math.signum(N_Resolver.resolveD(a)) * (a.floatValue() % b.floatValue());
		}

		if (a instanceof Integer) {
			return Math.signum(N_Resolver.resolveD(a)) * (a.intValue() % b.intValue());
		}

		if (a instanceof Short) {
			return Math.signum(N_Resolver.resolveD(a)) * (a.shortValue() % b.shortValue());
		}

		if (a instanceof Long) {
			return Math.signum(N_Resolver.resolveD(a)) * (a.longValue() % b.longValue());
		}

		if (a instanceof Double) {
			return Math.signum(N_Resolver.resolveD(a)) * (a.doubleValue() % b.doubleValue());
		}

		if (a instanceof Byte) {
			return Math.signum(N_Resolver.resolveD(a)) * (a.byteValue() % b.byteValue());
		}

		return a;
	}

	public static Number abs(Number a) {
		// takes on class of Number a

		if (a instanceof Float) {
			return Math.abs(a.floatValue());
		}

		if (a instanceof Integer) {
			return Math.abs(a.intValue());
		}

		if (a instanceof Short) {
			return Math.abs(a.shortValue());
		}

		if (a instanceof Long) {
			return Math.abs(a.longValue());
		}

		if (a instanceof Double) {
			return Math.abs(a.doubleValue());
		}

		if (a instanceof Byte) {
			return Math.abs(a.byteValue());
		}

		return a;
	}

	public static Number max(Number a, Number b) {
		if (a instanceof Float) {
			return Math.max(a.floatValue(), b.floatValue());
		}

		if (a instanceof Integer) {
			return Math.max(a.intValue(), b.intValue());
		}

		if (a instanceof Short) {
			return Math.max(a.shortValue(), b.shortValue());
		}

		if (a instanceof Long) {
			return Math.max(a.longValue(), b.longValue());
		}

		if (a instanceof Double) {
			return Math.max(a.doubleValue(), b.doubleValue());
		}

		if (a instanceof Byte) {
			return Math.max(a.byteValue(), b.byteValue());
		}

		return a;
	}

	public static Number min(Number a, Number b) {
		if (a instanceof Float) {
			return Math.min(a.floatValue(), b.floatValue());
		}

		if (a instanceof Integer) {
			return Math.min(a.intValue(), b.intValue());
		}

		if (a instanceof Short) {
			return Math.min(a.shortValue(), b.shortValue());
		}

		if (a instanceof Long) {
			return Math.min(a.longValue(), b.longValue());
		}

		if (a instanceof Double) {
			return Math.min(a.doubleValue(), b.doubleValue());
		}

		if (a instanceof Byte) {
			return Math.min(a.byteValue(), b.byteValue());
		}

		return a;
	}

	@Override
	public Object Class() {
		return Number.class;
	}

}
