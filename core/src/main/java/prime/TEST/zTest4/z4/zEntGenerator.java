package prime.TEST.zTest4.z4;

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

		scl.setToRandomDirection().scl(e.getUnit());
		dir.setToRandomDirection();
		Vector3 size = e.getSize();
		float x = rGen.nextFloatInclusive(size.x);
		float y = rGen.nextFloatInclusive(size.y);
		float z = rGen.nextFloatInclusive(size.z);
		pos.set(x, y, z);

		zEnt newEnt = new zEnt(e);
		newEnt.transform.SetScale(scl);
		newEnt.transform.SetRotation(VectorUtils.upcast(dir));
		newEnt.transform.SetPosition(pos);
		fillNew(new Quaternion(0, 0, 0, 0), newEnt);
	}

	public static void fillNew(Quaternion op, zEnt ent) {
		Vector3 ranDir = new Vector3().setToRandomDirection();
		Vector3 ranup = new Vector3().setToRandomDirection();
		
		
		switch ((int) op.x) {
		case 0:
			aGeoset G = new aGeoset();
			G.addVertex(Geom.genShape(ent.transform, new Vector3(0,1,0), ent.Environment.getUnit(), 3).toArray());
			ent.geom = new gNode(ent.transform,G);
			break;		
		}
		switch ((int) op.y) {
		case 0:
			break;
		}
		switch ((int) op.z) {
		case 0:
			break;
		}
		switch ((int) op.w) {
		case 0:
			break;
		}
	}

}
