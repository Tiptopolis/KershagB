package prime.TEST.zTest6.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.GeosetProjector;
import prime._PRIME.SYS.NIX._Entity;

public class zRenderer {

	// gets culled, sorted, & projected shapes from VisKernel
	//

	public ObserverKernel ObsK;
	public VisKernel VisK;

	public zRenderer(ObserverKernel O, VisKernel V) {
		this.ObsK = O;
		this.VisK = V;
	}

	public void render() {
		this.render(ObsK.perspective);
	}

	public void render(Camera eye) {
		// get from toDraw cache once its opperational

		Vector3 unit = this.ObsK.environment.getUnit();
		float unitLen = unit.len() / 3;
		Vector3 reference = eye.position;
	}

	public void debugRender() {
		Camera eye = ObsK.perspective;
		Vector3 unit = this.ObsK.environment.getUnit();
		float unitLen = unit.len() / 3;
		Vector3 reference = eye.position;

		for (_Entity E : this.ObsK.environment.Members) {
			if (E instanceof zEnt) {
				zEnt e = (zEnt) E;

				_BoundShape B = e.shape;
				aGeoset G = B.geom;
				aGeoset prjG = GeosetProjector.project(eye, G);
				Vector3 org = G.origin.get;
				Vector3 scr = eye.project(org.cpy());
				Vector3 pos = e.position().cpy();
				Vector3 prj = eye.project(pos.cpy());
				float z = (unitLen / reference.cpy().sub(pos.cpy()).len());
				z = (z * (unit.len() / 3)) / 2;
				z = z * (unit.len() / 3);

				boolean F1 = eye.frustum.planes[0].testPoint(pos) == Plane.PlaneSide.Front;
				boolean S1 = (eye.frustum.sphereInFrustum(pos, unit.len() * 3));
				if (F1 && S1) {

					Sketcher.setColor(1, 0, 1, 0.5f);

					// update verts by doint this lol
					for (aVertex v : G.vertices) {
						Transform T = v.getTransform();

					}

					Sketcher.setColor(Color.BLACK);
					Sketcher.Drawer.filledCircle(scr.x, scr.y, 2);

					Sketcher.setColor(1, 0, 1, 0.5f);
					Sketcher.Drawer.filledCircle(prj.x, prj.y, z / 2);
					Sketcher.setColor(1, 1, 1, 0.5f);
					Sketcher.Drawer.filledCircle(prj.x, prj.y, z / 3);

					// RED
					for (int f = 0; f < G.faces.size; f++) {

						aFace F = prjG.faces.get(f); // apparent
						aFace aF = G.faces.get(f); // actual

						Vector3 a = F.getCentroid();

						F1 = eye.frustum.planes[0].testPoint(a) == Plane.PlaneSide.Front;
						S1 = (eye.frustum.sphereInFrustum(a, unit.len() * 3));
						if (F1 && S1) {
							z = (unitLen / reference.cpy().sub(a.cpy()).len());
							z = (z * (unit.len() / 3)) / 2;
							z = z * (unit.len() / 3);

							Sketcher.setColor(1, 0, 0, 0.5f);
							Sketcher.Drawer.filledCircle(a.x, a.y, z / 4);
						}
						for (int l = 0; l < aF.lines.size; l++) {
							aLine L = F.lines.get(l);
							aLine aL = aF.lines.get(l);

							Array<Vector3> pts = Geom.interpolatePoints(aL.from.get, aL.to.get);

							for (int i = 0; i < pts.size; i++) {
								Log();
								Log(i + " :: " + (i + 1) % pts.size);

								Vector3 v = pts.get(i).cpy();
								Vector3 next = pts.get((i + 1) % pts.size).cpy();
								F1 = eye.frustum.planes[0].testPoint(v) == Plane.PlaneSide.Front;
								S1 = (eye.frustum.sphereInFrustum(v, unit.len() * 3));
								boolean Fn = eye.frustum.planes[0].testPoint(next) == Plane.PlaneSide.Front;
								boolean Sn = eye.frustum.pointInFrustum(next);

								if (F1 && S1) {
									z = (unitLen / reference.cpy().sub(v.cpy()).len());
									z = (z * (unit.len() / 3)) / 2;
									z = z * (unit.len() / 3);

									eye.project(v);
									Vector3 n = eye.project(next.cpy());
									Sketcher.setColor(1, 0, 1, 0.5f);
									Sketcher.Drawer.filledCircle(v.x, v.y, z / unitLen);
									Sketcher.setColor(0, 1, 0, ((1 / unitLen) * z) / 2);
									Sketcher.Drawer.circle(v.x, v.y, z / unitLen);

									if (Fn && Sn) {
										Sketcher.setColor(0, 0, 1, 0.5f);
										Sketcher.Drawer.line(new Vector2(v.x, v.y), new Vector2(n.x, n.y),z / (unitLen / 2));
									}
								}
							}

							pts.clear();

						}
					}

				}
				prjG.dispose();

			}

		}
	}

}
