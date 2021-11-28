package prime.TEST.zTest1.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.HashMap;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class rNode extends _Entity {

	int depth = 0;
	float dst = 0;
	Vector2 parentIndex = new Vector2(0, 0);// self lol
	int maxSub = 4;
	public Transform transform;
	public rNode parent;
	public Array<rNode> children;
	public Rect body;

	public HashMap<String, Vector2> neighbors;
	// Color is average color of child-nodes

	public rNode(_Environment e, Transform t) {
		super(e, "rNode");
		this.transform = t;
		this.children = new Array<rNode>(false, maxSub, rNode.class);

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
		zEnv z = (zEnv) this.Environment;
		Camera c = z.Observer;
		this.dst = VectorUtils.dst(this.transform.GetLocalPosition().cpy(), c.position.cpy()).len()
				/ (z.getUnit().len() / 3);
	}

	public void render() {

		Sketcher.setColor(Color.GRAY);
		if (this.children.isEmpty()) {

			Sketcher.Drawer.rectangle(this.body.getRectangle());
		} else
			for (rNode n : children) {
				n.render();
			}
	}

	public void subdivide() {

	}
}
