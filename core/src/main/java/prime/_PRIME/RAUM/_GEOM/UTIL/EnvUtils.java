package prime._PRIME.RAUM._GEOM.UTIL;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;

public class EnvUtils {

	public static final float Eps = 0.001f;

	public static Vector3 zScalar(_Environment e, Camera c) {

		return zScalar(e, c.position, c.direction);
	}

	public static Vector3 zScalar(_Environment e, Vector3 pos, Vector3 dir) {
		Vector3 unit = e.getUnit();
		Vector3 dst = VectorUtils.dst(pos.cpy(), pos.cpy().add(dir.cpy().scl(unit.cpy())));
		Vector3 uRad = VectorUtils.div(e.getUnit(), new Vector3(2, 2, 2));
		Vector3 pj = pos.cpy().sub(dst.cpy().scl(unit.cpy()));

		float z = (uRad.len() / pos.cpy().sub(pj.cpy()).len());
		z = (z * (unit.len() / 3)) / 2;
		z = z * (unit.len() / 3);

		return new Vector3(z, z, z);
	}

	public static Array<Vector3> cullFrustVec3(Camera C, Array<Vector3> V) {
		Array<Vector3> result = new Array<Vector3>(true, 0, Vector3.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i);
			if (C.frustum.sphereInFrustum(p, Eps)) {
				result.add(p);
			}
		}

		return result;

	}

	public static Array<Vector3> cullFrustVec3(Camera C, Array<Vector3> V, float eps) {
		Array<Vector3> result = new Array<Vector3>(true, 0, Vector3.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i);
			if (C.frustum.sphereInFrustum(p, eps)) {
				result.add(p);
			}
		}

		return result;

	}

	public static Array<aVertex> cullFrustaVert(Camera C, Array<aVertex> V, float eps) {
		Array<aVertex> result = new Array<aVertex>(true, 0, aVertex.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i).get;
			if (C.frustum.sphereInFrustum(p, eps)) {
				result.add(V.get(i));
			}
		}

		return result;

	}

	public static Array<Transform> cullFrustaTrns(Camera C, Array<Transform> V, float eps) {
		Array<Transform> result = new Array<Transform>(true, 0, Transform.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i).GetPosition();
			if (C.frustum.sphereInFrustum(p, eps)) {
				result.add(V.get(i));
			}
		}

		return result;

	}

	public Array<Vector3> cullForwardVec3(Camera C, Array<Vector3> V) {
		Array<Vector3> result = new Array<Vector3>(true, 0, Vector3.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i);
			if (C.frustum.planes[0].testPoint(p) == Plane.PlaneSide.Front) {
				result.add(p);
			}
		}

		return result;
	}

	public Array<aVertex> cullForwardVert(Camera C, Array<aVertex> V) {
		Array<aVertex> result = new Array<aVertex>(true, 0, aVertex.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i).get;
			if (C.frustum.planes[0].testPoint(p) == Plane.PlaneSide.Front) {
				result.add(V.get(i));
			}
		}

		return result;
	}

	public Array<Transform> cullForwardTrns(Camera C, Array<Transform> V) {
		Array<Transform> result = new Array<Transform>(true, 0, Transform.class);
		for (int i = 0; i < V.size; i++) {
			Vector3 p = V.get(i).GetPosition();
			if (C.frustum.planes[0].testPoint(p) == Plane.PlaneSide.Front) {
				result.add(V.get(i));
			}
		}

		return result;
	}

	public static Comparator<Vector3> distanceComparatorVec3(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<Vector3>() {
			@Override
			public int compare(Vector3 p0, Vector3 p1) {
				double ds0 = VectorUtils.dst(p0, finalP).len();
				double ds1 = VectorUtils.dst(p1, finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}

	public static Comparator<aVertex> distanceComparatorVert(aVertex point) {
		final Vector3 finalP = point.get.cpy();
		return new Comparator<aVertex>() {
			@Override
			public int compare(aVertex p0, aVertex p1) {
				double ds0 = VectorUtils.dst(p0.get, finalP).len();
				double ds1 = VectorUtils.dst(p1.get, finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}

	public static Comparator<Transform> distanceComparatorTrns(Transform point) {
		final Vector3 finalP = point.GetPosition().cpy();
		return new Comparator<Transform>() {
			@Override
			public int compare(Transform p0, Transform p1) {
				double ds0 = VectorUtils.dst(p0.GetPosition(), finalP).len();
				double ds1 = VectorUtils.dst(p1.GetPosition(), finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}

}
