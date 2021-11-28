package prime.TEST.zTest3.z4;

import com.badlogic.gdx.graphics.Camera;

import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.gNode;
import prime._PRIME.SYS.NIX._Component;

public class VisKernel extends _Component{

	
	public gNode Shape;
	public Camera Observer;
	public _Environment Environment;
	public aGeoset Visible;
	
	//3d-cull: frst,frnt
	//sort
	//project
	//2d-cull: face,line
	
	public VisKernel()
	{
		
	}
	
}
