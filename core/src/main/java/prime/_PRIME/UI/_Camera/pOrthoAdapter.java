package prime._PRIME.UI._Camera;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._PRIME.DefaultResources.*;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.UI._Camera.A_I.pCameraAdapter;



public class pOrthoAdapter extends pCameraAdapter {

	// follows OrthoCamera,faces towards mouse relative to center
	public OrthoController primary;

	public pOrthoAdapter(OrthoController primary) {
		super(primary.root, ": Ortho_Controller");
		primary.subadapters.add(this);
		this.primary = primary;

	}

	@Override
	public void init() {
		super.init();
		this.setCameraPosition(this.primary.Camera.getOfficialPosition());
	}

	public void update() {
		if (this.exists && this.isOn) {
			Vector3 cPrj = primary.Camera.getOfficialPosition();
			Vector3 mPos = new Vector3(MouseX, MouseY, 32);
			Vector3 mPrj = primary.Camera.camera.unproject(mPos.cpy());

			mPos.z = 32;
			cPrj.z = 32;

			Vector3 angle = VectorUtils
					.dir(new Vector3(MouseX, Height - MouseY, 32), new Vector3(Width / 2, Height / 2, 32)).nor();

			this.setCameraPosition(cPrj.cpy());
			this.setCameraDirection(angle.cpy().cpy().scl(-1));

		}
	}

}
