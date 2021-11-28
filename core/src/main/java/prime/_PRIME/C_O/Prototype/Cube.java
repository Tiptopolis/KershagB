package prime._PRIME.C_O.Prototype;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Maths;
import prime._PRIME.C_O.VectorUtils;

public class Cube {

	public Vector3 min;
	public Vector3 max;
	public Vector3 size;

	public Cube(Vector3 origin, Vector3 size) {
		this.min = origin;
		this.size = size;
		this.max = this.min.cpy().add(this.size.cpy());
	}

	public void set(Vector3 pos, Vector3 size, boolean orgCnt) {
		this.size.set(size);
		if (orgCnt)
			this.origin(pos);
		else
			this.center(pos);
	}

	public Vector3 origin() {
		return this.min;
	}

	public void origin(Vector3 at) {
		this.min = at;
		this.max = this.min.cpy().add(this.size.cpy());
	}

	public Vector3 center() {
		return (this.min.cpy().add(this.size.cpy().scl(0.5f)));
	}

	public void center(Vector3 v) {
		Vector3 half = this.size.cpy().scl(0.5f);
		Vector3 org = v.cpy().sub(half.cpy());
		this.min.set(org);
		this.max = this.min.cpy().add(this.size.cpy());
	}

	public Vector3 size() {
		return this.size;
	}

	public void size(Vector3 size) {
		// from origin
		Vector3 o = this.origin();
		this.size.set(size);
		this.origin(o);
	}

	public void scale(Vector3 scale) {
		// from center
		Vector3 c = this.center();
		this.size.set(scale);
		this.center(c);
	}

	public Cube origined(Vector3 at) {
		this.origin(at);
		return this;
	}

	public Cube centered(Vector3 at) {
		this.center(at);
		return this;
	}

	public Cube scaled(Vector3 scale) {
		this.scale(scale);
		return this;
	}

	public Cube sized(Vector3 size) {
		this.size(size);
		return this;
	}

	public Vector3 getUV(Vector3 uvw) {
		// range of 0-1
		float u = Maths.map(uvw.x, 0, 1, 0, this.size.x);
		float v = Maths.map(uvw.y, 0, 1, 0, this.size.y);
		float w = Maths.map(uvw.z, 0, 1, 0, this.size.z);
		return new Vector3(u, v, w);

	}
}
