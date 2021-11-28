package prime.TEST.zTest4.z6;

import static prime._PRIME.uSketcher.Sketcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.GeosetProjector;
import prime._PRIME.SYS.NIX._Entity;

public class ObsEyeShapeRenderer {
	// render actual normal & apparent normal

	public ObserverKernel perspective;
	private SnapshotArray<_Entity> members;

	private PerspectiveCamera leftEye;
	private PerspectiveCamera rightEye;
	public HashMap<String, Color> palette = new HashMap<String, Color>();

	public ObsEyeShapeRenderer(ObserverKernel kernel) {
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

	public void render() {
		this.leftEye.update();
		this.rightEye.update();
		this.render(this.perspective.observer, -1);
		this.render(this.rightEye, 0);
		this.render(this.leftEye, 1);
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
						c.a = 0.25f;
						Sketcher.setColor(c);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);
						c.a = 0.25f;
						Sketcher.setColor(c);
						Sketcher.Drawer.circle(prj.x, prj.y, z2);

						aGeoset G = E.geom.shape().geom;
						aGeoset P = GeosetProjector.project(eye, G);
						// gotta cull ShapeNodes &&&&& individual vertex nodes
						for (int v = 0; v < G.vertices.size; v++) {
							aVertex V = G.vertices.get(v);
							boolean F = this.perspective.observer.frustum.planes[0]
									.testPoint(V.get) == Plane.PlaneSide.Front;
							boolean S = (this.perspective.observer.frustum.sphereInFrustum(V.get, unit.len() * 3));

							if (F && S) {
								V = G.vertices.get(v);
								V = P.vertices.get(v);
								c.a = .25f;
								Sketcher.setColor(c);
								Sketcher.Drawer.line(new Vector2(prj.x, prj.y), new Vector2(V.get.x, V.get.y));
								Sketcher.Drawer.filledCircle(V.get.x, V.get.y, z2 / 32);
							}
						}
						for (int l = 0; l < G.lines.size; l++) {
							aLine L = G.lines.get(l);
							boolean F1 = this.perspective.observer.frustum.planes[0]
									.testPoint(L.from.get) == Plane.PlaneSide.Front;
							boolean S1 = (this.perspective.observer.frustum.sphereInFrustum(L.from.get,
									unit.len() * 3));
							boolean F2 = this.perspective.observer.frustum.planes[0]
									.testPoint(L.to.get) == Plane.PlaneSide.Front;
							boolean S2 = (this.perspective.observer.frustum.sphereInFrustum(L.to.get, unit.len() * 3));
							boolean F = F1 && F2;
							boolean S = S1 && S2;
							if (F && S) {
								Vector3 fPrj = eye.project(L.from.get.cpy());
								Vector3 tPrj = eye.project(L.to.get.cpy());
								Sketcher.setColor(c);
								Sketcher.Drawer.line(new Vector2(fPrj.x, fPrj.y), new Vector2(tPrj.x, tPrj.y));
								c.a = .25f;
								Sketcher.setColor(c);
								Sketcher.Drawer.filledCircle(fPrj.x, fPrj.y, z2 / 16);
								Sketcher.Drawer.filledCircle(tPrj.x, tPrj.y, z2 / 16);
							}
						}

						// inner/outter colors
						Sketcher.setColor(1, 1, 1, 1);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2 / 2);
						Sketcher.setColor(0, 0, 0, 0);// alpha mask layer?
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);

						prj = eye.project(pos.cpy());
						Sketcher.setLineWidth(z2 / MathUtils.PI2);
						c.a = 0.25f;
						Sketcher.setColor(c);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);
						c.a = 0.25f;
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
