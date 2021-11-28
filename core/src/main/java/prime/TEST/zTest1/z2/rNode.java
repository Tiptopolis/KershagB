package prime.TEST.zTest1.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.HashMap;
import java.util.Map.Entry;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.N_M.Prototype.aVector;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class rNode extends _Entity {

	int depth = 0;
	int bredth = 2;
	float dst = 0;
	Vector2 parentIndex = new Vector2(0, 0);// self lol
	Vector2 edgeIndex = new Vector2(0, 0);
	public Transform transform;
	public rNode parent;
	public HashMap<Vector3, rNode> children;
	public Rect body;

	public HashMap<String, Vector2> neighbors;
	public boolean isVisible = false;
	boolean wombo = false;
	// Color is average color of child-nodes

	Color fill = Color.LIGHT_GRAY;
	Color line = Color.DARK_GRAY;

	public rNode(_Environment e, Transform t) {
		super(e, "rNode");
		this.transform = t;
		this.children = new HashMap<Vector3, rNode>();

		Vector3 cell = e.getUnit();
		this.body = new Rect();
		this.neighbors = new HashMap<String, Vector2>();
		// if(0,0), refering to Parent's adjacency matrix in that direction
		this.neighbors.put("U", new Vector2(0, 0));
		this.neighbors.put("D", new Vector2(0, 0));
		this.neighbors.put("L", new Vector2(0, 0));
		this.neighbors.put("R", new Vector2(0, 0));
	}

	public rNode(rNode parent, Vector2 index) {
		this(parent.Environment, parent.transform);
		this.body = new Rect();
	}

	public static rNode newSub(rNode parent, int index) {
		switch (index) {
		case 0: // bottom-Left
			return new rNode(parent, new Vector2(-1, -1));
		case 1:
			return new rNode(parent, new Vector2(-1, 1));
		case 2:
			return new rNode(parent, new Vector2(1, 1));
		case 3:
			return new rNode(parent, new Vector2(1, -1));
		}
		return parent;
	}

	public void update() {
		this.body.centerAt(this.transform.GetLocalPosition());
		

	}

	public void render() {
		float a = 0;
		if (this.depth != 0)
			a = 1 / this.depth;
		this.fill = new Color(1, 1, 0, a);

		if (this.isVisible) {
			Sketcher.setColor(this.line);
			if (wombo)
				Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.circle(this.body.getCenter().x, this.body.getCenter().y,
					((this.body.width / 2) + (this.body.height / 2)) / 2);
		}
		if (!isSubdivided()) {

			Sketcher.setColor(this.fill);
			Sketcher.Drawer.filledRectangle(this.body.getRectangle());
			Sketcher.setColor(this.line);
			Sketcher.Drawer.rectangle(this.body.getRectangle());
		} else
			for (Entry<Vector3, rNode> E : this.children.entrySet()) {
				E.getValue().render();
			}
	}

	public boolean isSubdivided() {
		return !this.children.isEmpty();
	}

	public void subdivide(int depth, int bredth) {

	}

	public rNode getChildAt(Vector3 at) {
		// x,y,i,d
		// x coord, y coord, index, depth;

		for (Entry<Vector3, rNode> E : this.children.entrySet()) {
			Vector3 pos = E.getKey();
			if (MathUtils.isEqual(at.x, pos.x, 0.01f) && MathUtils.isEqual(at.y, pos.y, 0.01f)
					&& MathUtils.isEqual(at.z, pos.z, 0.01f)) {
				return E.getValue();
			}
		}
		return this;
	}

	public void mapTo(Rect area, boolean normalized) {

		this.mapTo(area, this.Environment.getUnit(), normalized);
	}

	public void mapTo(Rect area, Vector3 unitSize, boolean normalized) {

		if (!normalized)
			for (int x = 0; x < area.width / unitSize.x; x += unitSize.x) {
				for (int y = 0; y < area.height / unitSize.y; y += unitSize.y) {
					rNode r = new rNode(this.Environment, new Transform());
					r.body = new Rect(x, y, unitSize.x, unitSize.y);
					r.transform.SetParent(this.transform);
					r.parentIndex = new Vector2(x, y);
					this.children.put(new Vector3(x, y, 0), r);
					r.transform.SetLocalPosition(
							new Vector3((x * unitSize.x) + (unitSize.x / 2), (y * unitSize.y) + (unitSize.y / 2), 1));
				}
			}
		else
			for (int x = 0; x < area.width; x++) {
				for (int y = 0; y < area.height; y++) {
					rNode r = new rNode(this.Environment, new Transform());
					r.body = new Rect(x * unitSize.x, y * unitSize.x, unitSize.x, unitSize.y);
					r.transform.SetParent(this.transform);
					r.parentIndex = new Vector2((int) x, (int) y);
					this.children.put(new Vector3((int) x, (int) y, 0), r);
					r.transform.SetLocalPosition(
							new Vector3((x * unitSize.x) + (unitSize.x / 2), (y * unitSize.y) + (unitSize.y / 2), 1));
				}
			}

	}

	public String toLog() {
		String log = "";

		// log +="\n";
		log += (this.parentIndex);

		return log;
	}
}
