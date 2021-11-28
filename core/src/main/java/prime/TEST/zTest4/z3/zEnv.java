package prime.TEST.zTest4.z3;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.RAUM._Environment;

public class zEnv extends _Environment{
	public Vector3 size;

	
	
	
	
	
	public zEnv(Vector3 size, int numEnt) {
		super("zEnv");
		this.Dimension = new uVector(1, 1, 1, 1);
		this.size = size;
		//zEntGenerator.generate(this, numEnt);
	
	}
}
