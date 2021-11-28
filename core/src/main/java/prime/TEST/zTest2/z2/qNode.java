package prime.TEST.zTest2.z2;

import java.util.HashMap;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Cube;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class qNode extends _Entity {

	public Transform transform;
	public Cube body;
	
	public qNode parent;
	public Quaternion parentIndex = new Quaternion(0,0,0,0); //0 is self

	public HashMap<Quaternion, qNode> children; // 3d parent index-pos, & depth@.w

	
	public qNode(_Environment env) {
		this(env, new Transform());
	}

	public qNode(_Environment env, Transform t) {
		super(env,"rNode");
		this.transform = t;
		this.Environment = env;
		this.children = new HashMap<Quaternion, qNode>();
	}

	public Vector3 getOrigin()
	{
		return this.body.origin();
	}
	
	public Vector3 getCenter()
	{
		return this.body.center();
	}
	
}
