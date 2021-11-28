package prime.TEST.zTest3.z3;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime.uChumpEngine;
import prime._METATRON.Metatron;
import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.gNode;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.GeosetProjector;
import prime._PRIME.RAUM._GEOM.UTIL.LineUtils;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest3_3 extends uApp {

	public uFpsAdapter Explorer;
	zEnv Environment;

	_Property<Float> time;
	_Property<Float> mooch;
	public static Color PHASE = Color.CLEAR;

	gNode G;
	aGeoset g;
	ObserverKernel obs;
	float far = 0;

	@Override
	public void create() {
		super.create();

		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Environment = new zEnv();
		this.obs = new ObserverKernel(this.Environment, this.Explorer.perspective.camera);

		g = new aGeoset();
		this.buildDebugGeoset();

		this.G = new gNode(new Transform(), g);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		Log(uChumpEngine.METATRON.getProcessors());

		if (this.Explorer.getCameraPosition().z <= ((this.Environment.getUnit().z / 2)) - 0.1f)
			this.Explorer.getCameraPosition().z = (this.Environment.getUnit().z / 2);
		this.Explorer.perspective.camera.far = this.far * CAMERA.Camera.zoom;

		this.Explorer.update();
		Log(this.g.toLog());
		this.obs.update();
		Log("___");
		// Log(this.g.whatLink(new aLine(g.vertices.get(0), new aVertex(64f, 64f,
		// 0f))));
		// Log(LineUtils.howEquals(g.lines.get(0), new aLine(g.vertices.get(0), new
		// aVertex(64f, 64f, 0f))));
		// Log(LineUtils.howEquals(g.lines.get(0), new aLine(new aVertex(64f, 0f, 0f),
		// new aVertex(64f, 64f, 0f))));
		Log(this.G.toLog());
		Vector3 po = G.getTransform().GetPosition();
		G.getTransform().SetPosition(po.rotate(G.up, 1));

	}

	@Override
	public void render() {
		super.render();

		if (Gdx.input.isKeyPressed(Keys.TAB)) {
			Sketcher.setColor(Color.BLACK);
			Sketcher.setProjectionMatrix(CAMERA.getProjection());
			Sketcher.begin();
			PHASE = Color.RED.cpy();
			this.debugRect();

			//
			this.obs.render();
			this.renderDebugGeoset();
			Sketcher.end();
		}
		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		PHASE = Color.BLUE.cpy();
		this.debugRect();
		Sketcher.setColor(Color.BLACK);

		//
		this.obs.render();
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		PHASE = Color.GREEN.cpy();
		this.debugRect();
		this.renderProjGeoset(this.Explorer.perspective.camera);
		Sketcher.setColor(Color.BLACK);

		//

		Sketcher.end();

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void debugRect() {
		Sketcher.setColor(PHASE);
		Sketcher.Drawer.filledRectangle(0, 0, 32, 32);

	}

	private void rdrWorld(Transform T) {
		Color C = PHASE.cpy();

	}

	private void rdrLocal(Transform T) {
		Color C = new Color(1, 1, 1, 1).sub(PHASE.cpy());
		C.a = 1;

	}

	private void buildDebugGeoset() {

		this.g.addVertex(new Vector3(64, 0, 0));
		this.g.addVertex(new Vector3(64, 64, 0));
		this.g.addVertex(new Vector3(96, 0, 64));
		this.g.lines.add(new aLine(g.vertices.get(0), g.vertices.get(1)));
		this.g.lines.add(new aLine(g.vertices.get(1), g.vertices.get(2)));
		this.g.lines.add(new aLine(g.vertices.get(0), g.vertices.get(2)));
		this.g.addFace(this.g.lines.toArray());
	}

	private void renderDebugGeoset() {
		Sketcher.setColor(Color.BLACK);
		for (aVertex v : this.G.shape().vertices) {
			Sketcher.Drawer.circle(v.get.x, v.get.y, 1);
		}

		Sketcher.setColor(PHASE);
		for (aLine l : this.G.shape().lines) {
			Vector2 a = VectorUtils.downcast(l.from.get);
			Vector2 b = VectorUtils.downcast(l.to.get);
			Sketcher.Drawer.line(a, b);
		}
	}

	private void renderProjGeoset(Camera projector) {

		aGeoset prj = GeosetProjector.project(projector, this.G.shape());
		Log("____________________");
		Log(prj.toLog());
		Log("--------------------");

		Color C = PHASE.cpy();
		C.a = 0.8f;

		Sketcher.setColor(C);
		for (aFace f : prj.faces) {

			Polygon P = f.toPoly();
			Sketcher.Drawer.filledPolygon(P);
		}

		Sketcher.setColor(PHASE);
		for (aLine l : prj.lines) {
			Vector2 a = VectorUtils.downcast(l.from.get);
			Vector2 b = VectorUtils.downcast(l.to.get);
			Sketcher.Drawer.line(a, b);
		}

		Sketcher.setColor(Color.BLACK);
		for (aVertex v : prj.vertices) {
			Sketcher.Drawer.circle(v.get.x, v.get.y, 1);
		}

	}

}
