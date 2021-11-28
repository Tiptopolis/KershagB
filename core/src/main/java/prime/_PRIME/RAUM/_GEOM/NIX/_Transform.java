package prime._PRIME.RAUM._GEOM.NIX;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.SYS.NIX._Component;

public class _Transform extends _Component {

	protected Transform get;
	public Vector3 direction = new Vector3(0, 0, 1);
	public Vector3 up = new Vector3(0, 1, 0);

	public _Transform() {
		super("Transform", _Component.Component);
		this.get = new Transform();

	}

	public _Transform(Transform t)
	{
		this(t,0);
	}
	
	public _Transform(Transform t, int op) {
		super("Transform", _Component.Component);
		switch (op) {
		case 0:
			this.get = t.cpy();
			break;
		case 1:
			this.get = t;
			break;
		case 2:
			this.get = new Transform();
			this.get.SetParent(t);
			break;
		}
	}

	//////
	// Scale

	public void scale(Vector3 scl) {
		this.scale(scl, false);
	}

	public void scale(Vector3 scl, boolean local) {
		if (local)
			this.get.SetLocalScale(scl);
		else
			this.get.SetScale(scl);
	}

	public Vector3 scale() {
		return this.scale(false);
	}

	public Vector3 scale(boolean local) {
		if (local)
			return this.get.GetLocalScale();
		else
			return this.get.GetScale();
	}

	// Scale
	//////

	//////
	// Rotation
	public void rotation(Quaternion q) {
		this.rotation(q, false);
	}

	public void rotation(Quaternion q, boolean local) {
		if (local) {
			this.get.SetLocalRotation(q);
		} else {

			this.get.SetRotation(q);
		}

		// this.direction = VectorUtils.downcast(this.get.GetLocalRotation());
		// this.up = this.direction.cpy().crs(new Vector3(0,q.w,0));//guh?
	}

	public Quaternion rotation() {
		return this.rotation(false);
	}

	public Quaternion rotation(boolean local) {
		if (local)
			return this.get.GetLocalRotation();
		else
			return this.get.GetRotation();

	}

	// Rotation
	//////

	//////
	// Position
	public void position(Vector3 at) {
		this.position(at, false);
	}

	public void position(Vector3 at, boolean local) {
		if (local)
			this.get.SetLocalPosition(at);
		else
			this.get.SetPosition(at);
	}

	public Vector3 position() {
		return this.position(false);
	}

	public Vector3 position(boolean local) {
		if (local)
			return this.get.GetLocalPosition();
		else
			return this.get.GetPosition();
	}
	// Position
	//////

	public Transform getTransform() {
		return this.get;
	}
}
