package prime.TEST.zTest2.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static prime._METATRON.Metatron.*;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class rNode extends _Entity {

	// public uVector visData;//(isVis0:1,)

	public Transform transform;
	public Rect body;
	public rNode parent;
	public HashMap<Vector3, rNode> children;

	public Vector2 parentIndex = new Vector2(0, 0);

	public boolean dirty = true;

	public rNode(_Environment e, Transform t) {
		super(e, "rNode");
		Vector3 unit = e.getUnit();
		this.transform = t;
		this.body = new Rect(0, 0, unit.x, unit.y);
		this.children = new HashMap<Vector3, rNode>();
	}

	public void update() {
		if (this.dirty) {
			Vector3 lpos = this.transform.GetLocalPosition();
			Vector3 pos = this.transform.GetPosition();
			Vector3 unit = this.Environment.getUnit();
			Vector3 scl = this.transform.GetLocalScale();
			this.body = new Rect(0, 0, unit.x * scl.x, unit.y * scl.y);
			this.body.centerAt(new Vector2(lpos.x, lpos.y));

			for (Entry<Vector3, rNode> E : this.children.entrySet()) {
				E.getValue().update();
			}

			this.dirty = false;
		}

	}

	public void draw() {
		// Log("!!!");
		if (this.isSubdivided())
			for (Entry<Vector3, rNode> E : this.children.entrySet()) {
				E.getValue().draw();

			}
		else {

			Sketcher.Drawer.setColor(Color.BLACK);
			Sketcher.Drawer.rectangle(this.body.getRectangle());
			Sketcher.Drawer.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			Sketcher.Drawer.filledRectangle(this.body.getRectangle());
		}
	}

	public boolean isSubdivided() {
		return !this.children.isEmpty();
	}

	//////
	//
	public Vector3 position() {
		return this.position(true);
	}

	public Vector3 position(boolean local) {
		if (local)
			return this.transform.GetLocalPosition();
		else
			return this.transform.GetPosition();
	}

	//
	//////
	public rNode get(Vector3 at) {
		for (Entry<Vector3, rNode> E : this.children.entrySet()) {
			if (VectorUtils.isEqual(E.getKey(), at))
				return E.getValue();
		}

		return this;
	}

	public void subdivide(Vector2 sub) {
		this.subdivide((int) sub.x, (int) sub.y);
	}

	public void subdivide(int bredth) {
		this.subdivide(0, bredth);
	}

	public void subdivide(int depth, int bredth) {
		//upgrade to Vect4, including depth & bredth increments
		Vector2 origin = this.body.getOrigin();
		Vector2 size = this.body.getSize();
		Vector2 unit = VectorUtils.downcast(this.Environment.getUnit());
		Vector2 newSize = new Vector2(size.x/bredth, size.y/bredth);
		Vector3 scl = this.transform.GetScale();
		for(int x =0; x < bredth; x++)
		{
			for(int y =0; y < bredth; y++)
			{
				Vector2 newPos = new Vector2(origin.x+(x*newSize.x), origin.y+(y*newSize.y));
				rNode newNode = new rNode(this.Environment, new Transform());
				newNode.transform.SetParent(this.transform);
				newNode.transform.SetLocalPosition(new Vector3(newPos.x+(newSize.x/2),newPos.y+(newSize.y/2),1));
				newNode.transform.SetLocalScale(new Vector3(scl.x/bredth,scl.y/bredth, scl.z/bredth));
				
				this.children.put(new Vector3(x,y,0), newNode);
				
				
			}
		}
		
		if (depth >= 0) {
			for (Entry<Vector3, rNode> E : this.children.entrySet()) {
				E.getValue().subdivide(depth - 1, bredth);
			}
		}
	}

	public void mapTo(Vector2 index) {
		Vector3 unit = this.Environment.getUnit();
		this.mapTo(index, unit);
	}

	public void mapTo(Vector2 index, Vector3 unit) {

		for (int x = 0; x < index.x; x++)
			for (int y = 0; y < index.y; y++) {

			}
	}

}
