package prime.TEST.zTest5.RDR;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

import prime.TEST.zTest5.z1.ObserverKernel;
import prime.TEST.zTest5.z1.zEnt;
import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.GeosetProjector;
import prime._PRIME.SYS.NIX._Entity;

public class PerspecRendererX {
	public ObserverKernel perspective;
	private SnapshotArray<_Entity> members;

	private PerspectiveCamera leftEye;
	private PerspectiveCamera rightEye;
	public HashMap<String, Color> palette = new HashMap<String, Color>();

	public PerspecRendererX(ObserverKernel kernel) {
		this.perspective = kernel;
		this.members = new SnapshotArray<_Entity>();
		this.buildEyes();
		// this.buildColors();
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
		// centralVtex
		// Transform
		// Faces
		// Lines
		// Vertices
		// centralVtex
		this.render(this.perspective.observer, -1);
		this.render(this.rightEye, 0);
		this.render(this.leftEye, 1);
		//this.perspective.toDraw.clear();

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

					int e = (int) ((b * i) + j);

					zEnt E = (zEnt) this.members.get(e);
					if (E != null) {
						// L.render();
						if(phase == -1)
							c = Color.BLACK;
						if (phase == 0)
							c = Color.RED;
						if (phase == 1)
							c = Color.BLUE;

						// Vector3 pos = E.position().cpy();
						// Vector3 prj = this.perspective.observer.project(pos.cpy());

						// float z = (uSize / reference.cpy().sub(pos.cpy()).len());
						// z = (z * (unit.len() / 3)) / 2;
						// z = z * (unit.len() / 3);
						aGeoset G = E.geom.shape().geom;

						if (!G.faces.isEmpty()) {
							ArrayList<aFace> sortFaces = new ArrayList<aFace>(G.faces.size);
							for (aFace F : G.faces)
								sortFaces.add(F);
							Collections.sort(sortFaces, faceDistanceComparator(this.perspective.observer.position));

							for (int q = 0; q < sortFaces.size() - 1; i++)
								this.renderFace(sortFaces.get(q), eye, c);
						}
						else {
							aFace F = new aFace(G.lines.toArray());
							this.renderFace(F,eye,c);
							F.clear();
						}
					}
				}
				Sketcher.end();
			}

		}
	}

	private void renderFace(aFace F, Camera eye, Color c) {
		// sort lines & verts by dst from eye
		// seperate by those in front of center & behind it
		// if current draw index == median index value, draw poly
		// odd red, even black

		// Polygon P = F.toPoly();
		Polygon P = GeosetProjector.projectPoly(eye, F);

		ArrayList<aLine> sortLines = new ArrayList<aLine>(F.lines.size);
		for (int i = 0; i < F.lines.size; i++)
			sortLines.add(F.lines.get(i));
		Collections.sort(sortLines, lineDistanceComparator(this.perspective.observer.position));
		int size = sortLines.size() - 1;
		for (int l = 0; l < size; l++) {
			aLine L = sortLines.get(l);
			if (l >= (size / 2) - 1 && l <= (size / 2) + 1)
				Sketcher.Drawer.polygon(P);
			this.renderLine(L, eye, c);
		}
	}

	private void renderLine(aLine l, Camera eye, Color c) {
		// interpolate from->to
		Vector3 unit = this.perspective.environment.getUnit();
		float uSize = unit.len() / 3;
		Vector3 reference = eye.position.cpy();

		boolean F1 = eye.frustum.planes[0]
				.testPoint(l.from.get) == Plane.PlaneSide.Front;
		boolean S1 = (eye.frustum.sphereInFrustum(l.from.get,
				unit.len() * 3));
		boolean F2 = eye.frustum.planes[0]
				.testPoint(l.to.get) == Plane.PlaneSide.Front;
		boolean S2 = (eye.frustum.sphereInFrustum(l.to.get, unit.len() * 3));
		boolean F = F1 && F2;
		boolean S = S1 && S2;
		
		if (F && S) {
			 float zFrom = (uSize / reference.cpy().sub(l.from.get.cpy()).len());
			 zFrom = (zFrom * (unit.len() / 3)) / 2;
			 zFrom = zFrom * (unit.len() / 3);
			 float zTo = (uSize / reference.cpy().sub(l.to.get.cpy()).len());
			 zTo = (zTo * (unit.len() / 3)) / 2;
			 zTo = zTo * (unit.len() / 3);
			 
			Vector3 fPrj = eye.project(l.from.get.cpy());
			Vector3 tPrj = eye.project(l.to.get.cpy());
			Sketcher.setColor(c);
			Sketcher.Drawer.line(new Vector2(fPrj.x, fPrj.y), new Vector2(tPrj.x, tPrj.y));
			c.a = .25f / 2;
			Sketcher.setColor(c);
			Sketcher.Drawer.filledCircle(fPrj.x, fPrj.y, zFrom / 16);
			Sketcher.Drawer.filledCircle(tPrj.x, tPrj.y, zTo / 16);
		}


	}
	
	private void interpLine(aLine l, Camera eye)
	{
		Vector3 unit = this.perspective.environment.getUnit();
		float uSize = unit.len() / 3;
		Vector3 reference = eye.position.cpy();
				
		Array<Vector3> interp = Geom.interpolatePoints(l.from.get, l.to.get);
		ArrayList<Vector3> sortVerts = new ArrayList<Vector3>(interp.size);
		for (int i = 0; i < interp.size; i++)
			sortVerts.add(interp.get(i));
		Collections.sort(sortVerts, vectDistanceComparator(eye.position));
		
		for (int v = 0; v < sortVerts.size(); v++) {
			
			boolean F1 = this.perspective.observer.frustum.planes[0]
					.testPoint(l.from.get) == Plane.PlaneSide.Front;
			boolean S1 = (this.perspective.observer.frustum.sphereInFrustum(l.from.get,
					unit.len() * 3));
			
			
			Vector3 V = sortVerts.get(v);			
			float z = (uSize / reference.cpy().sub(V.cpy()).len());
			z = (z * (unit.len() / 3)) / 2;
			z = z * (unit.len() / 3);
			Vector3 prj = eye.project(V.cpy());
			Sketcher.Drawer.filledCircle(prj.x, prj.y, z);
		}
	}

	private void drawVert() {

	}

	public static Comparator<Vector3> vectDistanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<Vector3>() {
			@Override
			public int compare(Vector3 p0, Vector3 p1) {
				double ds0 = VectorUtils.dst(p0, finalP).len();
				double ds1 = VectorUtils.dst(p1, finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}

	public static Comparator<aVertex> vertDistanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<aVertex>() {
			@Override
			public int compare(aVertex p0, aVertex p1) {
				double ds0 = VectorUtils.dst(p0.get, finalP).len();
				double ds1 = VectorUtils.dst(p1.get, finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}

	public static Comparator<aLine> lineDistanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<aLine>() {
			@Override
			public int compare(aLine lA, aLine lB) {
				// double ds0 = VectorUtils.dst(((zEnt) p0).position(), finalP).len();
				// double ds1 = VectorUtils.dst(((zEnt) p1).position(), finalP).len();

				double lA0 = VectorUtils.dst(lA.from.get, finalP).len();
				double lA1 = VectorUtils.dst(lA.to.get, finalP).len();
				double lB0 = VectorUtils.dst(lB.from.get, finalP).len();
				double lB1 = VectorUtils.dst(lB.to.get, finalP).len();
				double ds0 = (lA0 + lA1) / 2;
				double ds1 = (lB0 + lB1) / 2;

				return Double.compare(ds1, ds0);
			}

		};
	}

	public static Comparator<aFace> faceDistanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<aFace>() {
			@Override
			public int compare(aFace fA, aFace fB) {
				// double ds0 = VectorUtils.dst(((zEnt) p0).position(), finalP).len();
				// double ds1 = VectorUtils.dst(((zEnt) p1).position(), finalP).len();

				double dA = 0;
				double dB = 0;
				for (aLine l : fA.lines) {
					dA += VectorUtils.dst(l.from.get, finalP).len();
					dA += VectorUtils.dst(l.to.get, finalP).len();
				}
				dA /= fA.lines.size;

				for (aLine l : fB.lines) {
					dB += VectorUtils.dst(l.from.get, finalP).len();
					dB += VectorUtils.dst(l.to.get, finalP).len();
				}
				dB /= fB.lines.size;

				return Double.compare(dB, dA);
			}

		};
	}
}
