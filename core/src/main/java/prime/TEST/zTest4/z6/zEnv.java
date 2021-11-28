package prime.TEST.zTest4.z6;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.Comparator;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class zEnv extends _Environment {

	public Vector3 size;

	public zEnv(Vector3 size, int numEnt) {
		super("zEnv");
		this.Dimension = new uVector(1, 1, 1, 1);
		this.size = size;
		zEntGenerator.generate(this, numEnt);
		Log("Entities Generates: " + this.Members.size);
	}

	@Override
	public Vector3 getSize() {
		return this.size;
	}

	@Override
	public Vector3 getUnit() {
		return new Vector3(8, 8, 8);
	}

	public static Comparator<_Entity> distanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<_Entity>() {
			@Override
			public int compare(_Entity p0, _Entity p1) {
				double ds0 = VectorUtils.dst(((zEnt) p0).position(), finalP).len();
				double ds1 = VectorUtils.dst(((zEnt) p1).position(), finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}

}
