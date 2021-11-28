package prime._PRIME.N_M.NMP;

import static prime._PRIME.uAppUtils.Log;

import prime._PRIME.N_M.iCypher;
import prime._PRIME.N_M.Prototype.aVector;
import prime._PRIME.N_M.UTIL.N_Resolver;



public class Hex extends aVector<Number> {

	public Hex() {
		super(16, 0, false);
		this.Value = Integer.valueOf(0);

		String B = Integer.toBinaryString(0);
		Log(N_Resolver.stringInt(B));

		for (int i = 0; i < 16; i++) {
			String s = iCypher.Hex(i);
			this.labels.set(i, s);
		}
		for (int i = 0; i < B.length() && i < 16; i++) {

			this.elements.set(i, N_Resolver.c(B.charAt((B.length() - 1) - i)));
		}
	}

	public Hex(int val) {
		super(16, 0, false);
		this.Value = Integer.valueOf(val);

		String B = Integer.toBinaryString(val);
		Log(N_Resolver.stringInt(B));

		for (int i = 0; i < 16; i++) {
			String s = iCypher.Hex(i);
			this.labels.set(i, s);
		}
		for (int i = 0; i < B.length() && i < 16; i++) {

			this.elements.set(i, N_Resolver.c(B.charAt((B.length() - 1) - i)));
		}
	}

	@Override
	public Number get(String n) {
		Number index = 0;

		try {
			index = Integer.parseInt(n, 16);

		} catch (NumberFormatException e) {
			return Float.NaN;
		}

		return this.get(index.intValue());
	}

	@Override
	public String toString() {
		String bin = "";
		for (int i = 0; i < this.elements.size; i++) {
			bin += this.elements.get((this.elements.size - 1) - i);
		}
		return "[" + this.Value + "]:" + bin;
	}

}
