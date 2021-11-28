package prime.TEST.zTest5.z4;

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

import prime._PRIME.C_O.VectorUtils;
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
		// this.members.clear();
		this.members = new SnapshotArray<_Entity>(this.perspective.toDraw.toArray());

		// update eyes
		float far = this.perspective.observer.far;
		Vector3 unit = this.perspective.environment.getUnit();
		Vector3 pos = this.perspective.observer.position.cpy();
		Vector3 dir = this.perspective.observer.direction.cpy();
		Vector3 up = this.perspective.observer.up.cpy();
		Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(unit.cpy().scl(0.0005f)));
		Vector3 left = pos.cpy().add(dir.cpy().rotate(up.cpy(), 90).scl(unit.cpy().scl(0.0005f)));

		Vector3 lookDir = pos.cpy().add(dir.cpy().scl(unit.cpy().scl(far)));

		this.leftEye.far = far;
		this.rightEye.far = far;
		this.leftEye.near = 0.1f;
		this.rightEye.near = 0.1f;

		this.leftEye.direction.set(dir);
		this.rightEye.direction.set(dir);
		this.leftEye.up.set(up);
		this.rightEye.up.set(up);
		this.leftEye.position.set(left);
		this.rightEye.position.set(right);

		this.leftEye.lookAt(lookDir);
		this.rightEye.lookAt(lookDir);

		this.leftEye.update(true);
		this.rightEye.update(true);

		// Log(this.members.size);

	}

	boolean swap = false;

	public void render() {
		swap = !swap;
		this.leftEye.update();
		this.rightEye.update();

		this.render(this.perspective.observer, -1);

		/*
		 * if (swap) { this.render(this.rightEye, 0); this.render(this.leftEye, 1); }
		 * else { this.render(this.leftEye, 1); this.render(this.rightEye, 0); }
		 */
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

						this.renderTransformLocal(E);
						//this.renderTransformGlobal(E);

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
							Color C = c.cpy();
							Sketcher.setColor(C);
							aFace Actual = G.faces.get(nf);
							boolean drawFace = true;

							for (aVertex V : Actual.vertices) {
								boolean F1 = eye.frustum.planes[0].testPoint(V.get) == Plane.PlaneSide.Front;
								boolean S1 = (eye.frustum.sphereInFrustum(V.get, unit.len() * 3));
								if (!F1 || !S1)
									drawFace = false;
							}

							if (drawFace) {
								aFace F = P.faces.get(nf);
								Polygon p = F.toPoly();
								Sketcher.Drawer.filledPolygon(p);
								for (aLine L : F.lines) {

									Sketcher.Drawer.line(L.from.get.x, L.from.get.y, L.to.get.x, L.to.get.y);

									int ind = P.hull.indexOf(L, true);
									int lineDex = P.lines.indexOf(L, true);
									aLine actual = G.lines.get(lineDex);
									// real/apparent

									// Apparent Line normals
									Vector3 aM = L.getMidpoint();
									Vector3 aP = L.getNormalPositive();
									Vector3 aN = L.getNormalNegative();
									Vector3 rM = eye.project(actual.getMidpoint()).nor();
									Vector3 rP = eye.project(actual.getNormalPositive()).nor();
									Vector3 rN = eye.project(actual.getNormalNegative()).nor();

									Color D = Color.GREEN.cpy();
									D.a = 0.33f;
									Sketcher.setColor(D);
									Sketcher.Drawer.circle(aM.x, aM.y, z2 / 16);

									D = Color.YELLOW.cpy();
									D.a = 0.33f;
									Sketcher.setColor(D);
									Sketcher.Drawer.circle(aN.x, aN.y, z2 / 32);

									D = Color.TEAL.cpy();
									D.a = 0.33f;
									Sketcher.setColor(D);
									Sketcher.Drawer.circle(aP.x, aP.y, z2 / 32);

									// Real Line normals
									D = Color.GRAY.cpy();
									D.a = 0.66f;
									Sketcher.setColor(D);

									Sketcher.Drawer.circle(aM.x + rM.x, aM.y + rM.y, z2 / 32);

									D = Color.BLACK.cpy();
									D.a = 0.33f;
									Sketcher.setColor(D);
									Sketcher.Drawer.circle(aN.x + rN.x, aN.y + rN.y, z2 / 64);

									D = Color.WHITE.cpy();
									D.a = 0.33f;
									Sketcher.setColor(D);
									Sketcher.Drawer.circle(aP.x + rP.x, aP.y + rP.y, z2 / 64);

									Sketcher.setColor(C);

								}

								C.a *= 0.25f;
								for (aVertex V : P.vertices) {
									Sketcher.Drawer.filledCircle(V.get.x, V.get.y, z2 / 16);
								}

								Sketcher.setColor(Color.BLACK);
								Vector3 centroid = F.getCentroid();
								Sketcher.Drawer.filledCircle(centroid.x, centroid.y, z2 / 8);
								centroid = this.perspective.observer.project(Actual.getCentroid());
								Sketcher.Drawer.circle(centroid.x, centroid.y, 1);

								Color N = new Color();
								// APPARENT NORMALS; i got the a & p reversed here lol whoops
								Vector3 dir = VectorUtils.downcast(E.transform.GetRotation());
								Vector3 up = dir.cpy().crs(new Vector3(0, 1, 0));
								Vector3 aP = F.getNormalPositive(up);
								Vector3 aN = F.getNormalNegative(up);
								Vector3 rP = this.perspective.observer.project(Actual.getNormalPositive(up)).nor()
										.scl(8);
								Vector3 rN = this.perspective.observer.project(Actual.getNormalNegative(up)).nor()
										.scl(8);

								// centroid = F.getCentroid();
								Sketcher.setColor(Color.MAGENTA);
								Sketcher.Drawer.filledCircle(aP.x, aP.y, z2 / 32);
								Sketcher.setColor(Color.TEAL);
								Sketcher.Drawer.filledCircle(aN.x, aN.y, z2 / 32);
								Sketcher.setColor(Color.PINK);
								Sketcher.Drawer.filledCircle(centroid.x + rP.x, centroid.y + rP.y, z2 / 32);
								Sketcher.setColor(Color.ORANGE);
								Sketcher.Drawer.filledCircle(centroid.x + rN.x, centroid.y + rN.y, z2 / 32);
							}

						}
						Sketcher.setColor(c);
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

	private void renderTransformLocal(zEnt E) {

		Vector3 unit = this.perspective.environment.getUnit().scl(0.25f).scl(0.5f);
		Vector3 pos = E.position().cpy();
		Vector3 dir = E.direction().cpy();
		Vector3 up = dir.cpy().crs(0, 1, 0).nor();

		Vector3 rDir = dir.cpy().rotate(up.cpy(), -90);
		Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 right = pos.cpy().add(rDir.scl(unit.cpy()));
		up = pos.cpy().add(up.cpy().scl(unit.cpy()));

		boolean F = this.perspective.observer.frustum.planes[0].testPoint(forward) == Plane.PlaneSide.Front
				&& this.perspective.observer.frustum.sphereInFrustum(forward, unit.len() * 3);
		boolean U = this.perspective.observer.frustum.planes[0].testPoint(up) == Plane.PlaneSide.Front
				&& this.perspective.observer.frustum.sphereInFrustum(up, unit.len() * 3);
		boolean R = this.perspective.observer.frustum.planes[0].testPoint(right) == Plane.PlaneSide.Front
				&& this.perspective.observer.frustum.sphereInFrustum(right, unit.len() * 3);

		this.perspective.observer.project(pos);
		this.perspective.observer.project(forward);
		this.perspective.observer.project(right);
		this.perspective.observer.project(up);

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pos.x, pos.y, unit.z);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
		if (F) {
			Sketcher.setColor(Color.RED);
			Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
			Sketcher.Drawer.circle(forward.x, forward.y, 2);
		}
		if (U) {
			Sketcher.setColor(Color.GREEN);
			Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
			Sketcher.Drawer.circle(up.x, up.y, 2);
		}
		if (R) {
			Sketcher.setColor(Color.BLUE);
			Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
			Sketcher.Drawer.circle(right.x, right.y, 2);
		}

	}

	// not quite the desired result betweein the two, but adequate for now
	private void renderTransformGlobal(zEnt E) {
		Vector3 unit = this.perspective.environment.getUnit().scl(0.25f).scl(0.5f);
		Vector3 pos = E.position().cpy();
		Vector3 dir = E.direction(true).cpy();
		Vector3 up = dir.cpy().crs(0, 1, 0).nor();

		Vector3 rDir = dir.cpy().rotate(up.cpy(), 90);
		Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 right = pos.cpy().add(rDir.scl(unit.cpy()));
		up = pos.cpy().add(up.cpy().scl(unit.cpy()));

		boolean F = this.perspective.observer.frustum.planes[0].testPoint(forward) == Plane.PlaneSide.Front
				&& this.perspective.observer.frustum.sphereInFrustum(forward, unit.len() * 3);
		boolean U = this.perspective.observer.frustum.planes[0].testPoint(up) == Plane.PlaneSide.Front
				&& this.perspective.observer.frustum.sphereInFrustum(up, unit.len() * 3);
		boolean R = this.perspective.observer.frustum.planes[0].testPoint(right) == Plane.PlaneSide.Front
				&& this.perspective.observer.frustum.sphereInFrustum(right, unit.len() * 3);

		this.perspective.observer.project(pos);
		this.perspective.observer.project(forward);
		this.perspective.observer.project(right);
		this.perspective.observer.project(up);

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pos.x, pos.y, unit.z);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
		if (F) {
			Sketcher.setColor(Color.TEAL);
			Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
			Sketcher.Drawer.circle(forward.x, forward.y, 2);
		}
		if (U) {
			Sketcher.setColor(Color.MAGENTA);
			Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
			Sketcher.Drawer.circle(up.x, up.y, 2);
		}
		if (R) {
			Sketcher.setColor(Color.YELLOW);
			Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
			Sketcher.Drawer.circle(right.x, right.y, 2);
		}
	}

}
