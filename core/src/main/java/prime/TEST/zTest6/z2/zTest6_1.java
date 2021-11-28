package prime.TEST.zTest6.z2;

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
import com.badlogic.gdx.math.Quaternion;
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
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.GeosetProjector;
import prime._PRIME.RAUM._GEOM.UTIL.LineUtils;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Entity;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest6_1 extends uApp {

	public uFpsAdapter Explorer;
	zEnv Environment;

	_Property<Float> time;
	_Property<Float> mooch;
	public static Color PHASE = Color.CLEAR;

	ObserverKernel obs;
	VisKernel vis;
	zRenderer rnd;
	float far = 0;

	zEnt p1;
	zEnt c1;
	//

	@Override
	public void create() {
		super.create();

		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.far = this.Explorer.perspective.camera.far;
		this.Environment = new zEnv();
		this.obs = new ObserverKernel(this.Environment, this.Explorer.perspective.camera);
		this.vis = new VisKernel(this.Environment, this.Explorer.getTransform(), this.Explorer.perspective.camera);
		this.rnd = new zRenderer(obs, vis);
		this.p1 = new zEnt(this.Environment);
		this.c1 = new zEnt(this.Environment);

		c1.transform.SetParent(p1.transform);
		p1.position(new Vector3(100, 100, 32));
		c1.position(new Vector3(32, 0, 8), true);

		// VectorUtils v;

		Vector3 pAx = p1.position().cpy();
		pAx.z = 0;
		Vector3 cAx = c1.position().cpy();
		cAx.z = 0;
		// this.c1.rotation(VectorUtils.upcast(VectorUtils.dir(c1.position(),
		// p1.position())));
		// this.p1.rotation(VectorUtils.upcast(VectorUtils.dir(p1.position(), new
		// Vector3())));
		this.c1.rotation(VectorUtils.upcast(VectorUtils.dir(cAx, pAx)));
		this.p1.rotation(VectorUtils.upcast(VectorUtils.dir(pAx, new Vector3())));

		this.buildDebugShapes();

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (this.Explorer.getCameraPosition().z <= ((this.Environment.getUnit().z / 2)) - 0.1f)
			this.Explorer.getCameraPosition().z = (this.Environment.getUnit().z / 2);

		this.Explorer.perspective.camera.far = this.far * CAMERA.Camera.zoom;
		this.Explorer.update();
		this.obs.update();

		this.updateTrns();

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

			this.obs.render();
			// this.renderDebugGeoset();
			this.renderDebugShapes(); // <-
			this.renderDebugTransforms();
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

		this.rnd.debugRender();

		//

		Sketcher.end();
		// Log(this.p1.shape.toLog());
	}

	@Override
	public void dispose() {
		super.dispose();
		this.p1.shape.dispose();

		// this.B.dispose();
	}

	private void debugRect() {
		Sketcher.setColor(PHASE);
		Sketcher.Drawer.filledRectangle(0, 0, 32, 32);

	}

	private void updateTrns() {

		Vector3 pPos = this.p1.position(true);
		Vector3 cPos = this.c1.position(true);

		// works properly:)
		this.p1.position(pPos.rotate(new Vector3(0, 0, 1), 1), true);
		this.c1.position(cPos.rotate(new Vector3(0, 0, 1), 3), true);

		// this.p1.rotation(VectorUtils.upcast(VectorUtils.dir(p1.position(), new
		// Vector3())));
		// this.c1.rotation(VectorUtils.upcast(VectorUtils.dir(c1.position(),
		// p1.position())), true);
		Vector3 pAx = p1.position().cpy();
		pAx.z = 0;
		Vector3 cAx = c1.position().cpy();
		cAx.z = 0;
		this.p1.rotation(VectorUtils.upcast(VectorUtils.dir(pAx, new Vector3())));
		this.c1.rotation(VectorUtils.upcast(VectorUtils.dir(cAx, pAx)), true);
	}

	private void buildDebugShapes() {

		// this.B = new _BoundShape(p1, new aGeoset());
		// Array<Transform> ts = Geom.genShape(p1.transform,
		// this.Explorer.getCameraUp(), new Vector3(32, 32, 32), 6);
		Array<Transform> ts = Geom.genShape(p1.transform, new Vector3(0, 0, 1), new Vector3(32, 32, 1), 6);

		// this.B.geom.addVertex(ts.toArray());
		this.p1.shape.geom.addVertex(ts.toArray());
		this.p1.shape.geom.diag();
		this.p1.shape.geom.hull();
		this.p1.shape.geom.skin();
		ts.clear();

		ts = Geom.genShape(c1.transform, new Vector3(0, 0, 1), new Vector3(32, 32, 1), 6);
		this.c1.shape.geom.addVertex(ts.toArray());
		this.c1.shape.geom.diag();
		this.c1.shape.geom.hull();
		this.c1.shape.geom.skin();
		ts.clear();

	}

	private void renderDebugShapes() {

		for (aVertex v : this.p1.shape.geom.vertices) {

			Transform V = v.getTransform();
			Sketcher.setColor(Color.PURPLE);
			Sketcher.Drawer.circle(V.GetPosition().x, V.GetPosition().y, 4);
			Sketcher.Drawer.line(new Vector2(V.GetParent().GetPosition().x, V.GetParent().GetPosition().y),
					new Vector2(V.GetPosition().x, V.GetPosition().y));
			Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.circle(V.GetLocalPosition().x, V.GetLocalPosition().y, 4);
			Sketcher.Drawer.line(new Vector2(0, 0), new Vector2(V.GetLocalPosition().x, V.GetLocalPosition().y));
		}
		for (aVertex v : this.c1.shape.geom.vertices) {

			Transform V = v.getTransform();// getTransform updates aVertex
			Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.circle(V.GetPosition().x, V.GetPosition().y, 4);
			Sketcher.Drawer.line(new Vector2(V.GetParent().GetPosition().x, V.GetParent().GetPosition().y),
					new Vector2(V.GetPosition().x, V.GetPosition().y));
			Sketcher.setColor(Color.PURPLE);
			Sketcher.Drawer.circle(V.GetLocalPosition().x, V.GetLocalPosition().y, 4);
			Sketcher.Drawer.line(new Vector2(0, 0), new Vector2(V.GetLocalPosition().x, V.GetLocalPosition().y));
		}
	}

	private void renderDebugTransforms() {
		Vector3 unit = this.Environment.getUnit();
		float unitLen = unit.len() / 3;

		for (_Entity E : this.Environment.Members) {
			if (E instanceof zEnt) {
				zEnt e = (zEnt) E;

				Vector3 pos = e.transform.GetPosition().cpy();
				Vector3 dir = VectorUtils.downcast(e.transform.GetLocalRotation());
				Vector3 up = new Vector3(0, 0, 1);

				Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
				// Vector3 far = pos.cpy().add(dir.cpy().scl(this.perspective.far/unitLen));
				Vector3 far = pos.cpy().add(dir.cpy().scl(this.Explorer.perspective.camera.far));
				// Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(),
				// -90).scl(unit.cpy()));
				Vector3 right = pos.cpy().add(dir.cpy().crs(up.cpy()).nor().scl(unit.cpy()));
				up = pos.cpy().add(up.cpy().scl(unit.cpy()));

				// Sketcher.setColor(Color.PINK);
				// Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(far.x, far.y));
				// Sketcher.Drawer.circle(far.x, far.y, 2);

				Sketcher.setColor(Color.BLACK);
				Sketcher.Drawer.circle(pos.x, pos.y, unit.z);
				Sketcher.setColor(Color.BLACK);
				Sketcher.Drawer.filledCircle(pos.x, pos.y, 1);
				Sketcher.setColor(Color.RED);
				Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(forward.x, forward.y));
				Sketcher.Drawer.circle(forward.x, forward.y, 2);
				Sketcher.setColor(Color.BLUE);
				Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(right.x, right.y));
				Sketcher.Drawer.circle(right.x, right.y, 2);
				Sketcher.setColor(Color.GREEN);
				Sketcher.Drawer.line(new Vector2(pos.x, pos.y), new Vector2(up.x, up.y));
				Sketcher.Drawer.circle(up.x, up.y, 2);
			}
		}

	}

	// works, but out-moded
	private void renderDebugGeoset() {
		Array<Transform> ts = Geom.genShape(c1.transform, this.Explorer.getCameraUp(), new Vector3(32, 32, 32), 6);

		Sketcher.setColor(new Color(0, 0, 0, 0.5f));
		Sketcher.Drawer.filledCircle(c1.position().x, c1.position().y, 2);
		for (Transform V : ts) {
			Sketcher.setColor(new Color(0, 0, 0, 0.5f));
			Sketcher.Drawer.filledCircle(V.GetPosition().x, V.GetPosition().y, 2);
			Sketcher.Drawer.line(new Vector2(V.GetParent().GetPosition().x, V.GetParent().GetPosition().y),
					new Vector2(V.GetPosition().x, V.GetPosition().y));
			Sketcher.setColor(Color.TEAL);
			Sketcher.Drawer.filledCircle(V.GetLocalPosition().x, V.GetLocalPosition().y, 2);
			Sketcher.Drawer.line(new Vector2(0, 0), new Vector2(V.GetLocalPosition().x, V.GetLocalPosition().y));
		}

		for (Transform V : ts) {
			V.clear();
		}
		ts.clear();
	}

}
