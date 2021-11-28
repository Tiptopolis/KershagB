package prime.TEST.zTest6.z2;

import java.util.Comparator;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.SYS.NIX._Entity;

public class zEnt extends _Entity {

	public Transform transform;
	public _BoundShape shape;

	public zEnt(_Environment e) {
		super(e, "zEnt");
		this.transform = new Transform();
		this.shape = new _BoundShape(this.transform, new aGeoset());
		e.Members.add(this);
	}

	public void position(Vector3 position) {
		this.position(position, false);

	}

	public void position(Vector3 position, boolean local) {
		if (local)
			this.transform.SetLocalPosition(position);
		else
			this.transform.SetPosition(position);

		if (this.shape != null)
			this.shape.position(this.position());
	}

	public Vector3 position() {
		return this.position(false);
	}

	public Vector3 position(boolean local) {
		if (local)
			return this.transform.GetLocalPosition();
		else
			return this.transform.GetPosition();

	}

	public Vector3 scale() {
		return this.scale(false);
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

		// if(this.shape != null)
		// this.shape.scale(scl, local);
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

	public void rotation(Quaternion rotation) {
		this.rotation(rotation, false);
	}

	public void rotation(Quaternion rotation, boolean local) {
		if (local)
			this.transform.SetLocalRotation(rotation);
		else
			this.transform.SetRotation(rotation);

		if (this.shape != null)
			this.shape.rotation(rotation, local);
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
