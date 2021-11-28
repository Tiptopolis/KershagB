package prime._PRIME.RAUM._GEOM.UTIL;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;

public class LineUtils {

	public static String howEquals(aLine A, aLine B) {
		// indicates wether & how points are connected
		String result = "NaN";

		boolean isA = false;
		boolean isB = false;
		boolean hasA = false;
		boolean hasB = false;

		String partA = "X";
		String partB = "X";

		if (A == B)
			return "A==B";

		if (A.from.equals(B.from))
			partA = "-";

		if (A.from == B.from)
			partA = "=";

		if (A.to.equals(B.to))
			partB = "-";

		if (A.to == B.to)
			partB = "=";

		result = partA + partB;

		return "[A" + result + "B]";
	}

	
	
	//////
	//Redundant from Geom & aLine, for my own convenience '_'
	
	public static Array<Vector3> interpolatePoints(aLine l, int div) {

		return Geom.interpolatePoints(l.from.get, l.to.get, div);
	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b) {

		return Geom.interpolatePoints(a, b);
	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b, float t) {

		return Geom.interpolatePoints(a, b, t, true);
	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b, float t, Vector3 unit) {

		return Geom.interpolatePoints(a, b, t, true, unit);
	}

}
