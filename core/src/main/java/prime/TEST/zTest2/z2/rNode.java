package prime.TEST.zTest2.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class rNode extends _Entity {

	public Transform transform;
	public Rect body;

	public HashMap<Vector3, rNode> children;

	public rNode(_Environment e) {
		this(e, new Transform());
		this.children = new HashMap<Vector3, rNode>();
	}

	public rNode(_Environment e, Transform t) {
		super(e, "rNode");
		Vector3 unit = e.getUnit();
		this.transform = t;
		this.body = new Rect(0, 0, unit.x, unit.y);

	}

	public void render() {
		
		
		
		if (!this.isSubdivided()) {
			Color f = new Color(1,1,1,1);
			Color l = new Color(0,0,0,1);
						
			Sketcher.setColor(f);
			Sketcher.Drawer.filledRectangle(this.body.getRectangle());
			Sketcher.setColor(l);
			Sketcher.Drawer.rectangle(this.body.getRectangle());
		}

	}

	public boolean isSubdivided() {
		return !this.children.isEmpty();
	}

}
