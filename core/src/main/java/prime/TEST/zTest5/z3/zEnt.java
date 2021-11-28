package prime.TEST.zTest5.z3;

import java.util.Comparator;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime.TEST.SHD3.LuciferParticle;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM._GEOM.gNode;
import prime._PRIME.SYS.NIX._Entity;

public class zEnt extends _Entity {

	public Transform transform;
	public gNode geom;

	public zEnt(_Environment e) {
		super(e, "zEnt");
		this.transform = new Transform();
		this.geom = new gNode(this.transform);
		this.Environment = e;
		e.Members.add(this);
		// this.transform.SetScale(e.getUnit().scl(0.5f));
	}

	public zEnt(_Environment e, Transform t) {
		super(e, "zEnt");
		this.transform = t;
		this.geom = new gNode(this.transform);
		this.Environment = e;
		e.Members.add(this);
	}

//////
	// TRANSFORM
	public void position(Vector3 position) {
		this.position(position, true);
	}

	public void position(Vector3 position, boolean local) {
		if (local)
			this.transform.SetLocalPosition(position);
		else
			this.transform.SetPosition(position);
	}

	public Vector3 position() {
		return this.position(true);
	}

	public Vector3 position(boolean local) {
		if (local)
			return this.transform.GetLocalPosition();
		else
			return this.transform.GetPosition();
	}

	public Vector3 scale() {
		return this.scale(true);
	}

	public Vector3 scale(boolean local) {
		if (local)
			return this.transform.GetLocalScale();
		else
			return this.transform.GetScale();
	}

	public void scale(Vector3 scl) {
		this.scale(scl, true);
	}

	public void scale(Vector3 scl, boolean local) {
		if (local)
			this.transform.SetLocalScale(scl);
		else
			this.transform.SetScale(scl);
	}

	public Quaternion rotation() {
		return this.rotation(false);
	}

	public Quaternion rotation(boolean local) {
		if (local)
			return this.transform.GetLocalRotation();
		else
			return this.transform.GetRotation();
	}

	public Vector3 direction() {
		return this.direction(false);
	}

	public Vector3 direction(boolean local) {
		if (local)
			return VectorUtils.downcast(this.transform.GetLocalRotation());
		else
			return VectorUtils.downcast(this.transform.GetRotation());
	}
	// TRANSFORM
	//////

	public static Comparator<_Entity> distanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<_Entity>() {
			@Override
			public int compare(_Entity p0, _Entity p1) {
				double ds0 = VectorUtils.dst(((zEnt) p0).position(), finalP).len();
				double ds1 = VectorUtils.dst(((zEnt) p1).position(), finalP).len();
				return Double.compare(ds0, ds1);
			}

		};
	}
}
