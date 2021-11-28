package prime._PRIME.N_M;

import prime._PRIME.DefaultResources;

public interface iCypher {

	public static String Cypher(int index, int iMod, String rdx) {

		String R = "";
		String r = "";

		int rem = 0;
		if (index == 0)
			return "0";

		for (int i = index; i > 0; i /= (iMod)) {

			int nextRdx = ((i % iMod % (rdx.length())));
			// int nextRdx = ((i % iMod));

			r += rdx.charAt(nextRdx) + "";

		}

		// reverse it
		byte[] strAsByteArray = r.getBytes();
		byte[] result = new byte[strAsByteArray.length];

		for (int i = 0; i < strAsByteArray.length; i++)
			result[i] = strAsByteArray[strAsByteArray.length - i - 1];

		R = new String(result);

		return R;
	}

	public static String Cypher(int index, String rdx) {

		String R = "";
		String r = "";

		int rem = 0;
		if (index == 0)
			return ""+rdx.charAt(0);

		for (int i = index; i > 0; i /= rdx.length()) {

			int nextRdx = i % rdx.length();

			r += rdx.charAt(nextRdx) + "";

		}

		// reverse it
		byte[] strAsByteArray = r.getBytes();
		byte[] result = new byte[strAsByteArray.length];

		for (int i = 0; i < strAsByteArray.length; i++)
			result[i] = strAsByteArray[strAsByteArray.length - i - 1];

		R = new String(result);

		return R;
	}

	public static String Cypher(int index, String... rdx) {

		String R = "";
		String r = "";

		int rem = 0;
		if (index == 0)
			return "0";

		for (int i = index; i > 0; i /= rdx.length) {

			int nextRdx = i % rdx.length;

			r += rdx[nextRdx] + "";

		}

		// reverse it
		byte[] strAsByteArray = r.getBytes();
		byte[] result = new byte[strAsByteArray.length];

		for (int i = 0; i < strAsByteArray.length; i++)
			result[i] = strAsByteArray[strAsByteArray.length - i - 1];

		R = new String(result);

		return R;
	}

////////////

public static String HexToDigit(String index) {

	Integer I = Integer.parseInt(index, 16);

	return "" + I;

}

public static Number HexToNumber(String hexval) {
	hexval = "0x" + hexval;
//String decimal="0x00000bb9";
	Long number = Long.decode(hexval);
//.......       System.out.println("String [" + hexval + "] = " + number);
	return number;
	// 3001
}

public static String Hex(int index) {
	String rdx = DefaultResources.HexDigits;

	int num = index;
	String R = "";
	String r = "";

	int rem = 0;
	if (index == 0)
		return "0";

	for (int i = index; i > 0; i /= rdx.length()) {

		int nextRdx = i % rdx.length();

		r += rdx.charAt(nextRdx) + "";
	}

	// reverse it
	byte[] strAsByteArray = r.getBytes();
	byte[] result = new byte[strAsByteArray.length];

	for (int i = 0; i < strAsByteArray.length; i++)
		result[i] = strAsByteArray[strAsByteArray.length - i - 1];

	R = new String(result);

	return R;
}

public static String VecLabel(int index) {
	String rdx = DefaultResources.VECTOR_LABELS;

	int num = index;
	String R = "";
	String r = "";

	int rem = 0;
	if (index == 0)
		return "x";

	for (int i = index; i > 0; i /= rdx.length()) {

		int nextRdx = 0;

		if (i != 0) {
			nextRdx = i % rdx.length();
		}

		r += rdx.charAt(nextRdx) + "";

	}

	// reverse it
	byte[] strAsByteArray = r.getBytes();
	byte[] result = new byte[strAsByteArray.length];

	for (int i = 0; i < strAsByteArray.length; i++)
		result[i] = strAsByteArray[strAsByteArray.length - i - 1];

	R = new String(result);

	return R;
}

public static String Digit(int index) {
	String rdx = DefaultResources.DIGITS;

	int num = index;
	String R = "";
	String r = "";

	int rem = 0;
	if (index == 0)
		return "0";

	for (int i = index; i > 0; i /= rdx.length()) {

		int nextRdx = i % rdx.length();

		r += rdx.charAt(nextRdx) + "";

	}

	// reverse it
	byte[] strAsByteArray = r.getBytes();
	byte[] result = new byte[strAsByteArray.length];

	for (int i = 0; i < strAsByteArray.length; i++)
		result[i] = strAsByteArray[strAsByteArray.length - i - 1];

	R = new String(result);

	return R;
}

public static String Digit(int index, int iMod) {
	String rdx = DefaultResources.DIGITS;

	int num = index;
	String R = "";
	String r = "";

	int rem = 0;
	if (index == 0)
		return "0";

	for (int i = index; i > 0; i /= (iMod)) {

		int nextRdx = ((i % iMod % (rdx.length())));
		// int nextRdx = ((i % iMod));

		r += rdx.charAt(nextRdx) + "";

	}

	// reverse it
	byte[] strAsByteArray = r.getBytes();
	byte[] result = new byte[strAsByteArray.length];

	for (int i = 0; i < strAsByteArray.length; i++)
		result[i] = strAsByteArray[strAsByteArray.length - i - 1];

	R = new String(result);

	return R;
}

public static String Binary(int index) {
	String rdx = "01";

	int num = index;
	String R = "";
	String r = "";

	int rem = 0;
	if (index == 0)
		return "0";

	for (int i = index; i > 0; i /= rdx.length()) {

		int nextRdx = i % rdx.length();

		r += rdx.charAt(nextRdx) + "";

	}

	// reverse it
	byte[] strAsByteArray = r.getBytes();
	byte[] result = new byte[strAsByteArray.length];

	for (int i = 0; i < strAsByteArray.length; i++)
		result[i] = strAsByteArray[strAsByteArray.length - i - 1];

	R = new String(result);

	return zTo(R, 9, '0');
}

public static String zTo(String s, int index, char override) {
	// prepends String s with the override character [index]# of times, returns s
	String P = "";
	for (int i = 0; i < (index - (s.length() + 1)); i++) {
		P += "" + override;
	}
	s = P + s;

	return s;
}

	public static String[] toStream(String s) {
		String[] res = new String[s.length()];
		char[] bits = s.toCharArray();
		for (int i = 0; i < s.length(); i++) {

			res[i] = "" + bits[i];
		}
		return res;
	}

	public static String fromStream(String[] s) {
		String S = "";
		for (int i = 0; i < s.length; i++) {
			S += s[i];
		}

		return S;
	}

	

	
	/*
	 * public static String[] purge(String[]s) { Array<String> S = new
	 * Array<String>(true,0,String.class); for(int i = 0; i < s.length; i++) {
	 * if(N_Resolver.resolveStrTo(s[i],(int)0)!=null); S.add(s[i]); } return
	 * S.toArray(); }
	 */

}
