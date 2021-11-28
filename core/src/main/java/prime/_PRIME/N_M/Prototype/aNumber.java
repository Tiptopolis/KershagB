package prime._PRIME.N_M.Prototype;

import prime._PRIME.N_M.N_;

public class aNumber extends N_ {
	public static Type Number = new Type("Number", Number.class);

	public aNumber() {
		super();
	}

	public aNumber(Number n) {
		super(n);
	}

	@Override
	public Object Class() {
		return Number;
	}

}
