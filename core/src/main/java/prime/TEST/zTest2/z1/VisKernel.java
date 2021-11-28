package prime.TEST.zTest2.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.Comparator;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import static prime._METATRON.Metatron.*;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class VisKernel extends _Entity {

	
	rNode root;
	
	public uFpsAdapter Camera;
	public Transform obj; // direction indicator
	private rNode dummy;

	public Array<rNode> visible;
	public Array<rNode> toDraw;

	float far = 0;
	
	
	public VisKernel(_Environment E) {
		super(E, "Observer");
		this.Camera = new uFpsAdapter(CAMERA);
		this.Camera.init();
		this.far = this.Camera.perspective.camera.far;
		this.obj = new Transform();
		this.visible = new Array<rNode>(true, 0, rNode.class);// only add non-subdivided?
		this.obj.SetParent(this.Camera.getTransform());
		
		this.root = new rNode(E, new Transform());
		this.root.transform.SetLocalScale(new Vector3(10,10,10));
		this.root.subdivide(10);
		this.dummy = new rNode(E, new Transform());
	}

	public void update() {
		this.Camera.perspective.camera.far = this.far*CAMERA.Camera.zoom;
		if (this.getPosition().z <= ((this.Environment.getUnit().z / 2)) - 0.1f)
			this.getPosition().z = (this.Environment.getUnit().z / 2);

		Vector3 pos = this.Camera.getTransform().GetLocalPosition();
		Vector3 dir = this.Camera.getCameraDirection();

		this.root.transform.SetLocalPosition(pos);
		this.dummy.transform.SetLocalPosition(pos);
		
		Vector3 unit = this.Environment.getUnit();
		Vector3 u = VectorUtils.div(this.Environment.getUnit(), new Vector3(2, 2, 2));// unit radius
		float U = unit.len() / 3;

		float z = (u.len() / this.getPosition().cpy().sub(this.obj.GetLocalPosition().cpy()).len());
		z = (z * (this.Environment.getUnit().len() / 3)) / 2;
		z = z * (this.Environment.getUnit().len() / 3);
		z *= 0.5f;

		Log(" C== >> \n" + this.Camera.getTransform().toString());
		Log("");
		//Log(this.obj.GetParent().GetPosition() + "  :  " + this.obj.GetParent().GetLocalPosition());
		//Log(" O== >> \n" + this.obj.toString());
		Log(this.root.body);

		this.Camera.update();

		// this.obj.SetLocalPosition(pos.cpy().add(dir.cpy().scl(z)));
		this.obj.SetLocalPosition(pos.cpy().add(dir.cpy().scl((U / 2))));
		this.dummy.dirty = true;
		this.dummy.update();		
		
		this.root.dirty=true;		
		this.root.update();

	}

	public void render() {
		this.dummy.draw();
		this.root.draw();
	}

	public Vector3 getPosition() {
		return this.Camera.getCameraPosition();
	}

	public Vector3 getDirection() {
		return this.Camera.getCameraDirection();
	}

	public Vector3 project(Vector3 p) {
		return this.Camera.project(p);
	}

	public Vector3 unproject(Vector3 p) {
		return this.Camera.unproject(p);
	}

	public Matrix4 getProjection() {
		return this.Camera.getProjection();
	}

	public static Comparator<rNode> distanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<rNode>() {
			@Override
			public int compare(rNode p0, rNode p1) {
				double ds0 = VectorUtils.dst(p0.position(), finalP).len();
				double ds1 = VectorUtils.dst(p1.position(), finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}
}
