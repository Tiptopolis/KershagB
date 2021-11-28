package prime.TEST.zTest1.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class zEnv extends _Environment {

	public Camera Observer;
	
	
	public zEnv(Camera observer, Vector3 size) {
		super("zEnv1");
		this.Dimension = new uVector(size);
		this.Observer = observer;
	}

	public void render() {
		for (_Entity e : this.Members) {
			if (e instanceof rNode) {
				
				
				
				
			}
		}
	}

	@Override
	public Vector3 getUnit() {
		return new Vector3(32, 32, 32);
	}

}
