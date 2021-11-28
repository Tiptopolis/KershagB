package prime.TEST.zTest2.z2;

import com.badlogic.gdx.graphics.Camera;

import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class VisKernel extends _Entity {

	Camera camera;
	
	
	public VisKernel(_Environment e, Camera c)
	{
		super(e,"VisKernel");
		this.camera = c;
	}
	
}
