package prime._PRIME.RAUM._GEOM.Prototype;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;

public class aVertex {

	protected Transform transform;
	public Vector3 get;

	public aVertex(int x, int y, int z) {
		this(new Vector3(x, y, z));
	}

	public aVertex(float x, float y, float z) {
		this(new Vector3(x, y, z));
	}

	public aVertex(Vector3 at) {
		this.transform = new Transform();
		this.transform.SetPosition(at);
		this.get = at;
	}

	public aVertex(Transform t) {
		this.transform = t;
		this.get = t.GetPosition();
	}

	public aVertex(Transform t, Vector3 at, boolean local) {
		this.transform = new Transform();
		this.transform.SetParent(t);
		if (local)
			this.transform.SetLocalPosition(at);
		else
			this.transform.SetPosition(at);
	}

	public static Array<aVertex> construct(Vector3... vectors) {
		Array<aVertex> arr = new Array<aVertex>(true, vectors.length, Vector3.class);
		for (int i = 0; i < vectors.length; i++) {
			arr.add(new aVertex(vectors[i]));
		}

		return arr;
	}

	public void set(Transform at) {
		this.transform = at;
		this.get = this.transform.GetPosition();
	}

	public void set(Vector3 at) {
		this.set(at, false);
	}

	public void set(Vector3 at, boolean local) {
		this.get = at;
		if (local)
			this.transform.SetLocalPosition(at);
		else
			this.transform.SetPosition(at);
	}

	public aVertex cpy() {
		return new aVertex(new Vector3(this.get.x, this.get.y, this.get.z));
	}

	public Transform getTransform() {
		this.get = this.transform.GetPosition();
		return this.transform;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other instanceof aVertex) {
			aVertex V = (aVertex) other;
			return this.get.epsilonEquals(V.get, 0.001f);
		}
		if (other instanceof Vector3) {
			Vector3 V = (Vector3) other;
			return this.get.epsilonEquals(V, 0.001f);
		}
		return false;
	}

	public void clear() {
		this.get = null;
		this.transform = null;

	}

}
