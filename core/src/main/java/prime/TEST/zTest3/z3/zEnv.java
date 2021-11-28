package prime.TEST.zTest3.z3;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.RAUM._Environment;

public class zEnv extends _Environment {

	public zEnv() {
		super("zEnv");
	}

	
	@Override
	public Vector3 getUnit()
	{
		return new Vector3(32,32,32);
	}
	
}
