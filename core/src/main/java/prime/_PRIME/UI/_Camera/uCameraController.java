package prime._PRIME.UI._Camera;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.UI.iMonad;
import prime._PRIME.UI._Camera.A_I.aCameraController;

public class uCameraController extends aCameraController{

	public uCameraController(iMonad root) {
		super(root);
		
	}
	
	public uCameraController(iMonad root,String name) {
		super(root, name);
	}

	public uCameraController setTransform(Transform t)
	{
		this.transform = t;
		return this;
	}


	
	
}
