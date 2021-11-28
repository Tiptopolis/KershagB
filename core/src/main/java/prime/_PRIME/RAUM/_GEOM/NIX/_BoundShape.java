package prime._PRIME.RAUM._GEOM.NIX;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.Prototype.BoundShape;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;

public class _BoundShape {

	BoundShape B;
	public Transform transform;
	public aGeoset geom;
	public _DrawData info;

	public _BoundShape() {
		this.transform = new Transform();
		this.geom = new aGeoset();
		this.info = new _DrawData();
	}

	public _BoundShape(aGeoset g) {
		// probably a bad idea lol
		this.transform = new Transform();
		if (g.origin == null)
			g.origin = new aVertex(this.transform);
		
		g.hull();
		
		this.geom = g;
		this.info = new _DrawData();
	}

	public _BoundShape(Transform t, aGeoset g) {
		this.transform = t;
		if (g.origin == null)
			g.origin = new aVertex(this.transform);
		else
			//g.origin.set(t);
		g.diag();
		g.hull();
		g.skin();
		this.geom = g;
		this.info = new _DrawData();
	}
	
	

	

	///////
	// TRANSFORM

	// Position
	
	public void position(Vector3 at) {
		this.position(at, false);
	}
	public void position(Vector3 at, boolean local) {
		Vector3 curr = this.transform.GetPosition().cpy();
		if (local)
			this.transform.SetLocalPosition(at);
		else
			this.transform.SetPosition(at);

		Vector3 delta = this.transform.GetPosition().cpy().sub(curr);

		for (aVertex v : this.geom.vertices) {
			v.get.add(delta);
		}
		
		this.geom.origin.set(this.transform.GetPosition().cpy());
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

	// Rotation
	public Quaternion rotation() {
		return this.rotation(false);
	}

	public Quaternion rotation(boolean local) {
		if (local)
			return this.transform.GetLocalRotation();
		else
			return this.transform.GetRotation();
	}

	public void rotation(Quaternion rot) {
		this.rotation(rot, false);
	}

	public void rotation(Quaternion rot, boolean local) {
		if (local)
			this.transform.SetLocalRotation(rot);
		else
			this.transform.SetRotation(rot);
	}

	public void rotate(Quaternion axAng) {
		this.rotate(axAng, false);
	}

	public void rotate(Quaternion axAng, boolean local) {
		Vector3 dir = new Vector3(axAng.x, axAng.y, axAng.z);
		float angle = axAng.w;
		this.rotate(dir, angle, local);
	}

	public void rotate(Vector3 axis, float angle, boolean local) {
		Quaternion rot = this.transform.GetRotation();

		if (local)
			rot = this.transform.GetLocalRotation();

		Vector3 dir = new Vector3(rot.x, rot.y, rot.z);
		Vector3 delta = dir.add(axis.cpy().scl(angle * MathUtils.degRad));

		for (aVertex v : this.geom.vertices) {

		}
	}
	// Scale

	// TRANSFORM
	///////

	public void dispose() {

		if (!this.geom.vertices.isEmpty())
			this.geom.dispose();
		this.info = null;
		this.geom = null;
		this.transform = null;
	}
	
	public String toLog()
	{
		return this.geom.toLog();
	}

}
