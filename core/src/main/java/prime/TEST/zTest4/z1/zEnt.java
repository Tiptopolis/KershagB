package prime.TEST.zTest4.z1;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM._GEOM.gNode;
import prime._PRIME.SYS.NIX._Entity;

public class zEnt extends _Entity{

	public Transform transform;
	public gNode geom;
	
	public zEnt(_Environment e)
	{
		super(e,"zEnt");
		this.transform = new Transform();
		this.geom = new gNode(this.transform);
		this.Environment=e;
		e.Members.add(this);
		
	}
	
	public zEnt(_Environment e, Transform t)
	{
		super(e,"zEnt");
		this.transform = t;
		this.geom = new gNode(this.transform);
		this.Environment=e;
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


	// TRANSFORM
	//////
}
