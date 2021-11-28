package prime._PRIME.RAUM._GEOM.Prototype;

import static prime._PRIME.uAppUtils.Log;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.Maths;
import prime._PRIME.C_O.VectorUtils;

public class aLine {
	public aVertex from;
	public aVertex to;

	public aLine(aVertex from, aVertex to) {
		this.from = from;
		this.to = to;
	}

	public aLine(Vector3 from, Vector3 to) {
		this.from = new aVertex(from);
		this.to = new aVertex(to);
	}

	public aLine(Vector3 at, Vector3 dir, float length) {
		this.from = new aVertex(at);
		this.to = new aVertex(at.cpy().add(dir.cpy().scl(length)));
	}

	public Vector3 getMidpoint() {
		Array<Vector3> shape = interpolatePoints(this);
		int ind = shape.size;
		// Log(">" + ind);
		int mid = 1;
		// Log(">>>" + ind / 2);

		if (Maths.isEven(ind)) {
			mid = ind / 2;
			// Log(">>>" + ind / 2);

		} else {
			mid = (int) ((ind - 0.5f) / 2);
			// Log(">>>" + ((ind - 1) / 2));

		}

		if (mid <= 0)
			return (this.from.get.cpy().sub(this.to.get.cpy())).scl(0.5f);

		return shape.get(mid);
	}

	public Vector3 getNormalPositive() {
		Vector3 mid = this.getMidpoint();
		Vector3 dir = VectorUtils.dir(this.from.get.cpy(), this.to.get.cpy());
		Vector3 rN = mid.cpy().add(dir.cpy().crs(0, 0, 1).nor().scl(MathUtils.PI));
		return rN;
	}

	public Vector3 getNormalNegative() {
		Vector3 mid = this.getMidpoint();
		Vector3 dir = VectorUtils.dir(this.from.get.cpy(), this.to.get.cpy());
		Vector3 lN = mid.cpy().add(dir.cpy().crs(0, 0, -1).nor().scl(MathUtils.PI));
		return lN;
	}

	public Vector3 getCentroid() {
		// barycenter; average of verts
		return this.getMidpoint();
	}

	
	
	
	public static Array<Vector3> interpolatePoints(aLine l) {
		Vector3 dst = VectorUtils.dst(l.from.get.cpy(), l.to.get.cpy());
		int div = (int) ((dst.len() / 3) * 2);
		return interpolatePoints(l, div);
	}

	public static Array<Vector3> interpolatePoints(aLine l, float freq) {
		return interpolatePoints(l, (int) (1f / freq));
	}

	public static Array<Vector3> interpolatePoints(aLine l, int div) {

		return Geom.interpolatePoints(l.from.get, l.to.get, div);
	}

	public void clear() {
		this.from = null;
		this.to = null;
	}

	@Override
	public String toString() {
		String s = "";
		s += "{" + this.from.get + "->" + this.to.get + "}";

		return s;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof aLine)) {
			return false;
		}

		aLine L = (aLine) other;
		if (this.from == L.from && this.to == L.to)
			return true;

		return false;
	}
}
