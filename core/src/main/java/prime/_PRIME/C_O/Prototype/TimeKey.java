package prime._PRIME.C_O.Prototype;

import java.util.Map;

public class TimeKey implements Map.Entry<Object, Object> {

	// V:N
	// V$ = N[$] where $ is a number indicating the number of inner elements/ inner
	// Size of
	// (4V2)+1N

	// t%i
	// i/t

	// public I_D I_D;//closed, innerID //simplex [;|;]
	// public ID Q_D; //open, outterID
	// PIN:HOLE

	public Number a = 0;
	public Number t = 0;

	public Number n = 0;
	public Number m = 0;

	public Number i = 0;
	public Number e = 0;

	public Number o = 0;
	public Number d = 0;

	public Number I = 0; // primary output value
	// ILD, IMD, IRD

	public enum I_D {
		// i_Detail
		L(-1), M(0), R(1);

		public final int get;

		private I_D(int g) {
			this.get = g;
		}
	}

	public TimeKey() {

	}

	public TimeKey(int i) {

	}

	public TimeKey(Object o) {

	}

	public Number getKey() {
		return (a.byteValue() + t.byteValue() + n.byteValue() + m.byteValue());
	}

	public Number getValue() {
		return I;
	}

	@Override
	public Object setValue(Object value) {

		this.I = value.toString().toCharArray().length;

		return this;
	}

	public String toString() {

		// return "[" + a + ":" + t + ":" + n + ":" + m + "]";
		return "" + this.I;
	}

	public String toRaw() {
		// I:K&V
		return "[" + this.I + "]|(" + (this.getKey().byteValue() + this.getValue().byteValue() + ")");
	}

	public Number Raw() {
		return (this.getKey().byteValue() + this.getValue().byteValue());
	}

	private String atnmRaw() {
		return "[" + a + ":" + t + ":" + n + ":" + m + "]";
	}

	private String iodeRaw() {
		return "["+e + "_" + o + "_" + d  +"|"+i +"]";
	}

	public String toLog() {

		String log = "";
		log += "<" + this.getClass().getSimpleName() + ">";
		log += "\n   (I|K&V)[" + this.toRaw() + "]";
		log += "\n   (a:t|n:m)[" + this.atnmRaw() + "] ";
		log += "\n   (i|o_d_e)[" + this.iodeRaw() + "] ";
		return log;
	}

}
