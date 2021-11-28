package prime._PRIME.C_O;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import prime._PRIME.C_O.Prototype.BoundShape;
import prime._PRIME.C_O.Prototype.Transform;

public class Geom {
	public static final float kEpsilon = 0.00001F;
	public static final float kEpsZoom = 4.23e-4F;
	// *Undocumented*
	public static final float kEpsilonNormalSqrt = 1e-15F;

	public static Vector2[] regularPolygon(Vector2 origin, Vector2 radius, int n) {
		Vector2[] points = new Vector2[n];
		float angle = MathUtils.PI2 / n;
		int i = 0;
		for (float a = 0; a < MathUtils.PI2; a += angle) {
			float fX = (float) (origin.x + (radius.x * Math.cos(a)));
			float fY = (float) (origin.y + (radius.y * Math.sin(a)));
			points[i] = (new Vector2(fX, fY));
			i++;
		}
		return points;
	}

	public static ArrayList<Vector2> regPolygon(Vector2 origin, Vector2 radius, int n) {
		ArrayList<Vector2> points = new ArrayList<Vector2>();
		points.ensureCapacity(n);
		float angle = MathUtils.PI2 / n;
		int i = 0;
		for (float a = 0; a < MathUtils.PI2; a += angle) {
			float fX = (float) (origin.x + (radius.x * Math.cos(angle * i)));
			float fY = (float) (origin.y + (radius.y * Math.sin(angle * i)));
			points.add(new Vector2(fX, fY));
			i++;
		}
		return points;
	}

	public static ArrayList<Vector3> regPolygon(Vector3 origin, Vector3 radius, int n) {
		ArrayList<Vector3> points = new ArrayList<Vector3>();
		points.ensureCapacity(n);
		float angle = MathUtils.PI2 / n;
		for (float a = 0; a < MathUtils.PI2; a += angle) {
			float fX = (float) (origin.x + (radius.x * Math.cos(a)));
			float fY = (float) (origin.y + (radius.y * Math.sin(a)));
			points.add(new Vector3(fX, fY, 0));
		}
		return points;
	}

	// most accurate so far lol
	public static Array<Transform> genShape(Transform t, Vector3 up, Vector3 unit, int n) {

		Array<Transform> points = new Array<Transform>(true, n, Transform.class);
		float angle = MathUtils.PI2 / n;
		Vector3 dir = VectorUtils.downcast(t.GetLocalRotation());

		for (int i = 0; i < n; i++) {

			Transform nT = new Transform();
			nT.SetParent(t);

			Vector3 Dir = dir.cpy().rotate(up, (angle * i) * MathUtils.radDeg);

			nT.SetLocalScale(unit.cpy());
			nT.SetLocalRotation(VectorUtils.upcast(Dir.cpy()));
			Vector3 pos = t.GetPosition().cpy().add(Dir.cpy().scl(unit.cpy()));
			// nT.SetPosition(pos);
			nT.SetLocalPosition(Dir.cpy().scl(unit));
			points.add(nT);

		}

		return points;

	}

	public static ArrayList<Vector3> generatePolygon(Vector3 origin, Vector3 radius, Vector3 dir, int n) {

		// Log("");
		Vector3 localForward = dir.cpy().nor();
		Vector3 localUp = localForward.cpy().crs(new Vector3(0, 1, 0)).nor();
		Vector3 localRight = localForward.cpy().crs(localUp.cpy().nor());

		if (dir.x == 0)
			dir.x = 0.01f;
		if (dir.y == 0)
			dir.y = 0.01f;

		ArrayList<Vector3> points = new ArrayList<Vector3>();
		points.ensureCapacity(n);
		float angle = MathUtils.PI2 / n;
		dir = dir.scl(MathUtils.radDeg);

		for (int i = 0; i < n; i++) {
			Vector3 r = (origin.cpy().add(radius.cpy()
					.scl(dir.cpy().nor().rot(
							new Matrix4().setToRotationRad((dir.cpy().crs(new Vector3(0, 1, 0))).nor(), (angle * i)))
							.nor())));
			points.add(r);

		}
		return points;
	}

	public static ArrayList<Vector3> generatePolygon(Transform transform, int n) {
		ArrayList<Vector3> points = new ArrayList<Vector3>();
		Vector3 origin = transform.GetLocalPosition().cpy();
		Vector3 radius = transform.GetScale().cpy().scl(0.5f);
		// Vector3 dir = (transform.getDirection().cpy());
		Vector3 dir = VectorUtils.downcast(transform.GetLocalRotation());

		Vector3 localForward = dir.cpy().nor();
		Vector3 localUp = localForward.cpy().crs(new Vector3(0, 1, 0)).nor();
		Vector3 localRight = localForward.cpy().crs(localUp.cpy().nor());
		localUp = localForward.cpy().crs(localRight.cpy()).nor();

		points.ensureCapacity(n);
		float angle = MathUtils.PI2 / n;

		for (int i = 0; i < n; i++) {
			Vector3 r = (origin.cpy().add(radius.cpy()
					.scl(dir.cpy().nor().rot(
							new Matrix4().setToRotationRad((dir.cpy().crs(new Vector3(0, 1, 0))).nor(), (angle * i)))
							.nor())));
			points.add(r);
		}
		return points;
	}

	//////
	//
	public static ArrayList<Vector3> generateLine(Vector3 origin, Vector3 length, Vector3 dir) {

		ArrayList<Vector3> result = new ArrayList<Vector3>();
		result.add(origin.cpy());

		float radiansX = (float) Math.toRadians((dir.x));
		float radiansY = (float) Math.toRadians((dir.y));
		float radiansZ = (float) Math.toRadians((dir.z));

		float x = (float) ((Math.cos(radiansX) * length.x) + origin.x);
		float y = (float) ((Math.sin(radiansY) * length.y) + origin.y);
		float z = (float) ((Math.tan(radiansZ) * length.z) + origin.z);

		result.add(new Vector3(x, y, z));
		return result;
	}

	public static Vector3 genLine(Vector3 origin, Vector3 length, Vector3 dir) {

		Vector3 result;
		float radiansX = (float) Math.toRadians((dir.x));
		float radiansY = (float) Math.toRadians((dir.y));
		float radiansZ = (float) Math.toRadians((dir.z));

		float x = (float) ((Math.cos(radiansX) * length.x) + origin.x);
		float y = (float) ((Math.sin(radiansY) * length.y) + origin.y);
		float z = (float) ((Math.tan(radiansZ) * length.z) + origin.z);

		result = new Vector3(x, y, z);
		return result;
	}

	public static ArrayList<Vector3> lineTo(Vector3 origin, Vector3 to) {

		Vector3 dir = VectorUtils.dir(origin.cpy(), to.cpy());
		Vector3 dst = to.cpy().sub(origin.cpy());

		ArrayList<Vector3> result = new ArrayList<Vector3>();
		result.add(origin.cpy());

		float radiansX = (float) Math.toRadians((dir.x));
		float radiansY = (float) Math.toRadians((dir.y));
		float radiansZ = (float) Math.toRadians((dir.z));

		float x = (float) ((Math.cos(radiansX) * dst.x) + origin.x);
		float y = (float) ((Math.sin(radiansY) * dst.y) + origin.y);
		float z = (float) ((Math.tan(radiansZ) * dst.z) + origin.z);

		result.add(to);
		return result;
	}

	public static ArrayList<Vector3> lineTo(Vector3 origin, Vector3 to, int div) {
		ArrayList<Vector3> r = new ArrayList<Vector3>();

		if (div < 2)
			div = 2;
		Vector3 dst = VectorUtils.dst(origin.cpy(), to.cpy());
		Vector3 dv = new Vector3(div, div, div);
		Vector3 inc = VectorUtils.div(dst, dv);

		Vector3 iDiv = VectorUtils.div(inc.cpy(), dv.cpy());
		// Log("=> " + dst);
		// Log("=/ " + dv);
		// Log("=# " + inc);
		Vector3 next = origin.cpy();
		for (int c = 0; c < div; c++) {
			r.add(next);
			Vector3 n = next.cpy().add(inc.cpy().add(iDiv.cpy()));
			next = n;
		}

		return r;
	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b) {
		Vector3 dst = VectorUtils.dst(a, b);
		int div = (int) ((dst.len() / 3) * 2);
		return interpolatePoints(a, b, div);
	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b, float freq, boolean continuous) {
		if (continuous) {
			Vector3 dst = VectorUtils.dst(a, b);
			int div = (int) (((dst.len() / 3) * 2) * freq);
			return interpolatePoints(a, b, div);
		} else

			return interpolatePoints(a, b, (int) (1f / freq));

	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b, float freq, boolean continuous, Vector3 scl) {
		if (continuous) {
			Vector3 dst = VectorUtils.dst(a, b);
			int div = (int) (((dst.len() / 3) * 2) * freq);
			return interpolatePoints(a, b, div, scl);
		} else

			return interpolatePoints(a, b, (int) (1f / freq), scl);

	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b, int div) {
		Array<Vector3> r = new Array<Vector3>();

		Vector3 dst = VectorUtils.dst(a.cpy(), b.cpy());
		Vector3 dv = new Vector3(div, div, div);
		Vector3 inc = VectorUtils.div(dst, dv);

		Vector3 iDiv = VectorUtils.div(inc.cpy(), dv.cpy());

		Vector3 next = a.cpy();
		for (int c = 0; c < div; c++) {
			r.add(next);
			Vector3 n = next.cpy().add(inc.cpy().add(iDiv.cpy()));
			next = n;
		}

		return r;

	}

	public static Array<Vector3> interpolatePoints(Vector3 a, Vector3 b, int div, Vector3 scl) {
		Array<Vector3> r = new Array<Vector3>();

		Vector3 dst = VectorUtils.dst(a.cpy(), b.cpy());
		Vector3 dv = new Vector3(div, div, div);
		Vector3 inc = VectorUtils.div(dst, dv);

		// Vector3 iDiv = VectorUtils.div(inc.cpy(), dv.cpy().scl(0.5f));
		Vector3 iDiv = VectorUtils.div(inc.cpy(), dv.cpy());

		Vector3 next = a.cpy();
		for (int c = 0; c < div; c++) {
			r.add(next);
			Vector3 n = next.cpy().add(inc.cpy().add((iDiv.cpy())));
			next = n.scl(scl);
		}

		return r;

	}

	// from unity
	// Returns the angle in degrees between /from/ and /to/. This is always the
	// smallest
	public static float angle(Vector3 from, Vector3 to) {
		// sqrt(a) * sqrt(b) = sqrt(a * b) -- valid for real numbers
		float denom = from.len2() * to.len2();
		if (denom < kEpsilonNormalSqrt)
			return 0f;

		float dot = MathUtils.clamp(from.cpy().dot(to.cpy()) / denom, -1, 1);
		return MathUtils.acos(dot) * MathUtils.radDeg;

	}

	// from unity, signed
	// The smaller of the two possible angles between the two vectors is returned,
	// therefore the result will never be greater than 180 degrees or smaller than
	// -180 degrees.
	// If you imagine the from and to vectors as lines on a piece of paper, both
	// originating from the same point, then the /axis/ vector would point up out of
	// the paper.
	// The measured angle between the two vectors would be positive in a clockwise
	// direction and negative in an anti-clockwise direction.
	public static float angle(Vector3 from, Vector3 to, Vector3 axis) {

		float unsigned = angle(from, to);

		float crsX = from.y * to.z - from.z * to.y;
		float crsY = from.z * to.x - from.x * to.z;
		float crsZ = from.x * to.y - from.y * to.x;

		float sign = Math.signum(axis.x * crsX + axis.y * crsY + axis.z * crsZ);
		return unsigned * sign;

	}

	public static Vector3 slope(Vector3 a, Vector3 b) {

		return a.cpy().sub(b.cpy()).nor();

	}

	// mostly untested
	public static float calculateAngle(Vector3 from, Vector3 to) {
		// <x-x
		// <y-y
		// <z-z
		float dot = from.dot(to);
		float mag = from.len() * to.len(); // magnitude of line drawn
		float cos = dot / mag;

		return (float) Math.acos(cos);
	}

	// mostly untested
	public static Vector3 calculateAngles(Vector3 from, Vector3 to) {
		// <x-x
		// <y-y
		// <z-z
		float dot = from.dot(to);
		float mag = from.len() * to.len(); // magnitude of line drawn
		float cos = (float) Math.acos(dot / mag);
		float sin = (float) Math.asin(dot / mag);
		float tan = (float) Math.atan(dot / mag);

		Vector3 result = new Vector3().add(cos, sin, tan);
		return result;
	}

	// works as expected
	public static Vector3 calculateOrbit(float currentOrbitDegrees, float distanceFromCenterPoint,
			Vector3 centerPoint) {
		float radians = (float) Math.toRadians((currentOrbitDegrees));

		float x = (float) ((Math.cos(radians) * distanceFromCenterPoint) + centerPoint.x);
		float y = (float) ((Math.sin(radians) * distanceFromCenterPoint) + centerPoint.y);
		float z = (float) ((Math.tan(radians) * distanceFromCenterPoint) + centerPoint.z);

		return new Vector3(x, y, z);
	}

	// unsure
	public static Vector3 calculateOrbit(Vector3 currentOrbitDegrees, Vector3 distanceFromCenterPoint,
			Vector3 centerPoint) {
		float radiansX = (float) Math.toRadians((currentOrbitDegrees.x));
		float radiansY = (float) Math.toRadians((currentOrbitDegrees.y));
		float radiansZ = (float) Math.toRadians((currentOrbitDegrees.z));

		float x = (float) ((Math.cos(radiansX) * distanceFromCenterPoint.x) + centerPoint.x);
		float y = (float) ((Math.sin(radiansY) * distanceFromCenterPoint.y) + centerPoint.y);
		float z = (float) ((Math.tan(radiansZ) * distanceFromCenterPoint.z) + centerPoint.z);

		return new Vector3(x, y, z);
	}

	public static void rotateAround(Vector3 position, Vector3 point, Vector3 axis, float angle) {
		Vector3 tmpVec = new Vector3();
		tmpVec.set(point);
		tmpVec.sub(position);
		position.add(tmpVec);
		position.rotate(axis, angle);
		tmpVec.rotate(axis, angle);
		position.add(-tmpVec.x, -tmpVec.y, -tmpVec.z);
	}

	public static Vector3 rotateAroundRtn(Vector3 position, Vector3 point, Vector3 axis, float angle) {
		Vector3 tmpVec = new Vector3();
		tmpVec.set(point);
		tmpVec.sub(position);
		position.add(tmpVec);
		position.rotate(axis, angle);
		tmpVec.rotate(axis, angle);
		position.add(-tmpVec.x, -tmpVec.y, -tmpVec.z);
		return position;
	}

	public Vector3 VectToPolar(Vector3 Vect) {
		// idfk what im doing lol
		// normalized to unit
		// *
		// r/|x=_r*cos(t)
		// /t|
		// *--*
		// y=r*sin(t)
		// r= length of line
		// t = theta angle of line
		// phi = polar angle of line

		// distance formula sqrt(v[(0*t)^2]+v[(1*t)^2]+...v[(n*t)^2])
		// t-thetaSize of vector, iterator increment size, etc

		float p2 = MathUtils.PI2;
		float r = (float) Math.sqrt((Vect.x * Vect.x) + (Vect.y * Vect.y) + (Vect.z * Vect.z));

		Vector3 polar = new Vector3();

		return polar.nor();

	}

	public Vector3 polarToLine(Vector3 polar) {
		// y=mx+b;
		float r = polar.len();// rho
		float t = (float) Math.atan2(polar.y, polar.x);// theta
		float p = (float) Math.atan2(polar.y, polar.z);// phi
		Vector3 lineTo = new Vector3(r, t, p);

		return lineTo;
	}

	// is a->b->c a counter-clockwise turn?
	// +1 if counter-clockwise, -1 if clockwise, 0 if collinear
	public static int ccw(Vector2 a, Vector2 b, Vector2 c) {
		// return a.x*b.y - a.y*b.x + a.y*c.x - a.x*c.y + b.x*c.y - b.y*c.x;
		double area2 = (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
		if (area2 < 0)
			return -1;
		else if (area2 > 0)
			return +1;
		else
			return 0;
	}

	////////////////

	public Array<Vector3> VectorArray() {
		return new Array<Vector3>(true, 0, Vector3.class);
	}

	//////
	// CIRCLE ESTIMATOR
	float PI7 = MathUtils.PI * 7;
	int sideMultiplier = 1;
	int minimumSides = 11;
	int maximumSides = 21;

	public int estimateSidesRequired(float pixelSize, float radiusX, float radiusY) {
		float circumference = (float) (MathUtils.PI2 * Math.sqrt((radiusX * radiusX + radiusY * radiusY) / 2f));
		int sides = (int) (circumference / (16 * pixelSize));
		float a = Math.min(radiusX, radiusY), b = Math.max(radiusX, radiusY);
		float eccentricity = (float) Math.sqrt(1 - ((a * a) / (b * b)));
		sides += (sides * eccentricity * sideMultiplier) / 16;
		return MathUtils.clamp(sides, minimumSides, maximumSides);
	}
}
