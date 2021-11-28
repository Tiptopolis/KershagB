package prime.TEST.zTest4.z6;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.gNode;
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.SYS.NIX._Entity;
import squidpony.squidmath.RNG;

public class zEntGenerator {

	public static void generate(_Environment e, int num) {

		for (int i = 0; i < num; i++) {
			randomNew(e);
		}

	}

	public static void randomNew(_Environment e) {

		RNG rGen = new RNG();

		Vector3 scl = new Vector3();
		Vector3 dir = new Vector3();
		Vector3 pos = new Vector3();

		scl.setToRandomDirection();
		dir.setToRandomDirection();
		Vector3 size = e.getSize();
		float x = rGen.nextFloatInclusive(size.x);
		float y = rGen.nextFloatInclusive(size.y);
		float z = rGen.nextFloatInclusive(size.z);
		pos.set(x, y, z);

		zEnt newEnt = new zEnt(e);
		newEnt.transform.SetRotation(VectorUtils.upcast(dir));
		newEnt.geom.up = new Vector3(0, 1, 0).crs(dir.cpy());
		newEnt.transform.SetPosition(pos);

		// opA, N
		int n = rGen.nextIntHasty(4);
		fillNew(n, newEnt);

	}

	public static void fillNew(int op, zEnt ent) {
		Vector3 ranDir = new Vector3().setToRandomDirection();
		Vector3 ranup = new Vector3().setToRandomDirection();
		aGeoset G = new aGeoset();
		switch ((int) op) {
		case 0:

			G.addVertex(Geom.genShape(ent.transform, new Vector3(ent.geom.up), new Vector3(1, 1, 1), 3).toArray());
			ent.geom = new gNode(ent.transform, G);
			break;
		case 1:
			G.addVertex(Geom.genShape(ent.transform, new Vector3(ent.geom.up), new Vector3(1, 1, 1), 4).toArray());
			ent.geom = new gNode(ent.transform, G);
			break;
		case 2:
			G.addVertex(Geom.genShape(ent.transform, new Vector3(ent.geom.up), new Vector3(1, 1, 1), 6).toArray());
			ent.geom = new gNode(ent.transform, G);
			break;
		case 3:
			G.addVertex(Geom.genShape(ent.transform, new Vector3(ent.geom.up), new Vector3(1, 1, 1), 20).toArray());
			ent.geom = new gNode(ent.transform, G);
			break;
		}
	}

}
