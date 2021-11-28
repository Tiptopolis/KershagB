package prime.TEST.zTest4.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class OBS_Renderer {

	public void render(ObserverKernel K) {
		Vector3 unit = K.environment.getUnit();
		Camera projection = K.perspective;
		Log(" >> " + K.currentVisible.size);
		Sketcher.setColor(Color.BLACK);
		SnapshotArray<_Entity> dr = new SnapshotArray<_Entity>(K.toDraw);
		for (_Entity E : dr) {
			if (E instanceof zEnt) {
				zEnt e = (zEnt) E;
				Vector3 pos = e.transform.GetPosition().cpy();

				float vertexSize = ((zEnt) E).geom.drawData().vertexSize;

				float z = (vertexSize / projection.position.cpy().sub(pos.cpy()).len());
				z = (z * (unit.len() / 3)) / 2;
				z = z * (unit.len() / 3);
				// Log("> " + z);

				Vector3 prj = K.perspective.project(pos.cpy());
				boolean a = K.perspective.frustum.pointInFrustum(pos.cpy());
				boolean b = K.perspective.frustum.planes[0].testPoint(pos) == Plane.PlaneSide.Front;
				if (a && b) {

					Sketcher.Drawer.filledCircle(prj.x, prj.y, z);
				}
			}
		}
	}

}
