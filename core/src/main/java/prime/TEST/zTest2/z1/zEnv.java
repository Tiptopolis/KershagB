package prime.TEST.zTest2.z1;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.RAUM._Environment;

public class zEnv extends _Environment{

	
	public final Vector3 unit = new Vector3(32,32,32);
	
	public zEnv() {
		super("zEnv");
	}
	
	
	
	@Override
	public Vector3 getUnit()
	{
		return this.unit.cpy();
	}

}
