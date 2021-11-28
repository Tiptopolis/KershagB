package prime.TEST.zTest5.RDR;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.SnapshotArray;

import prime.TEST.zTest5.z1.ObserverKernel;
import prime.TEST.zTest5.z1.zEnt;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.GeosetProjector;
import prime._PRIME.SYS.NIX._Entity;

public class PerspecRenderer {
	// render actual normal & apparent normal

	public ObserverKernel perspective;
	private SnapshotArray<_Entity> members;

	private PerspectiveCamera leftEye;
	private PerspectiveCamera rightEye;
	public HashMap<String, Color> palette = new HashMap<String, Color>();

	public PerspecRenderer(ObserverKernel kernel) {
		this.perspective = kernel;
		this.members = new SnapshotArray<_Entity>();
		this.buildEyes();
		// this.buildColors();
	}

	public void update() {
		this.members.clear();
		this.members = new SnapshotArray<_Entity>(this.perspective.toDraw.toArray());

		// update eyes
		float far = this.perspective.observer.far;
		Vector3 unit = this.perspective.environment.getUnit();
		Vector3 pos = this.perspective.observer.position.cpy();
		Vector3 dir = this.perspective.observer.direction.cpy();
		Vector3 up = this.perspective.observer.up.cpy();
		Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(unit.cpy().scl(0.001f)));
		Vector3 left = pos.cpy().add(dir.cpy().rotate(up.cpy(), 90).scl(unit.cpy().scl(0.001f)));

		Vector3 lookDir = pos.cpy().add(dir.cpy().scl(unit.cpy().scl(far)));

		this.leftEye.direction.set(dir);
		this.rightEye.direction.set(dir);
		this.leftEye.up.set(up);
		this.rightEye.up.set(up);
		this.leftEye.position.set(left);
		this.rightEye.position.set(right);

		this.leftEye.lookAt(lookDir);
		this.rightEye.lookAt(lookDir);

	}

	boolean swap = false;

	public void render() {
		swap = !swap;
		this.leftEye.update();
		this.rightEye.update();

		this.render(this.perspective.observer, -1);

		if (swap) {
			this.render(this.rightEye, 0);
			this.render(this.leftEye, 1);
		} else {
			this.render(this.leftEye, 1);
			this.render(this.rightEye, 0);
		}
		this.perspective.toDraw.clear();

	}

	public void render(Camera eye, int phase) {

		Vector3 unit = this.perspective.environment.getUnit();
		float uSize = unit.len() / 3;
		Vector3 reference = eye.position;

		int max = this.perspective.maxVisible;
		float b = this.perspective.batchSize;
		int s = this.members.size;
		float mod = s % b;
		int rem = (int) ((s / b) + 1);
		Color c = Color.CLEAR;
		for (int i = 0; i < (int) rem; i++) {
			{
				Sketcher.begin();

				for (int j = 0; j < b; j++) {

					if ((b * i) + j > Math.min(s - 1, max)) {
						break;
					}

					int f = (int) ((b * i) + j);

					zEnt E = (zEnt) this.members.get(f);
					if (E != null) {
						// L.render();
						if (phase == -1)
							c = Color.BLACK;
						if (phase == 0)
							c = Color.RED;
						if (phase == 1)
							c = Color.BLUE;

						Vector3 pos = E.position().cpy();
						Vector3 prj = this.perspective.observer.project(pos.cpy());

						float z2 = (uSize / reference.cpy().sub(pos.cpy()).len());
						z2 = (z2 * (unit.len() / 3)) / 2;
						z2 = z2 * (unit.len() / 3);

						// inner/outter colors
						Sketcher.setColor(1, 1, 1, 1);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2 / 2);
						Sketcher.setColor(0, 0, 0, 0);// alpha mask layer?
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);

						prj = eye.project(pos.cpy());
						Sketcher.setLineWidth(z2 / MathUtils.PI2);
						c.a = 0.25f / 2;
						Sketcher.setColor(c);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);
						c.a = 0.25f / 2;
						Sketcher.setColor(c);
						Sketcher.Drawer.circle(prj.x, prj.y, z2);

						aGeoset G = E.geom.shape().geom;
						aGeoset P = GeosetProjector.project(eye, G);
					
						// gotta cull ShapeNodes &&&&& individual vertex nodes
						Sketcher.setColor(c);
						for (int nf = 0; nf < P.faces.size; nf++) {
							aFace Actual = G.faces.get(nf);
							boolean drawFace = true;
							
							for(aVertex V : Actual.vertices)
							{
								boolean F1 = this.perspective.observer.frustum.planes[0]
										.testPoint(V.get) == Plane.PlaneSide.Front;
								boolean S1 = (this.perspective.observer.frustum.sphereInFrustum(V.get,
										unit.len() * 3));
								if(!F1 || !S1)
									drawFace = false;
							}
							
							if (drawFace) {
								aFace F = P.faces.get(nf);
								Polygon p = F.toPoly();
								Sketcher.Drawer.filledPolygon(p);
								
								for(aLine L : F.lines)
								{
									Sketcher.Drawer.line(L.from.get.x, L.from.get.y, L.to.get.x,L.to.get.y);
								}
								
								for (aVertex V : P.vertices) {
									Sketcher.Drawer.circle(V.get.x, V.get.y, z2 / 8);
								}
							}
							
							
						}						

						// Log(G.toLog());
						// Log(P.toLog());

						// inner/outter colors
						Sketcher.setColor(1, 1, 1, 1);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2 / 2);
						Sketcher.setColor(0, 0, 0, 0);// alpha mask layer?
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);

						prj = eye.project(pos.cpy());
						Sketcher.setLineWidth(z2 / MathUtils.PI2);
						c.a = 0.25f / 2;
						Sketcher.setColor(c);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);
						c.a = 0.25f / 2;
						Sketcher.setColor(c);
						Sketcher.Drawer.circle(prj.x, prj.y, z2);
					}
				}
				Sketcher.end();
			}

		}
	}

	private void buildEyes() {
		this.leftEye = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.leftEye.position.set(this.perspective.observer.position.cpy());
		this.leftEye.direction.set(this.perspective.observer.direction.cpy());
		this.leftEye.up.set(this.perspective.observer.up.cpy());
		this.leftEye.update();
		//
		this.rightEye = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.rightEye.position.set(this.perspective.observer.position.cpy());
		this.rightEye.direction.set(this.perspective.observer.direction.cpy());
		this.rightEye.up.set(this.perspective.observer.up.cpy());
		this.rightEye.update();
	}

}
