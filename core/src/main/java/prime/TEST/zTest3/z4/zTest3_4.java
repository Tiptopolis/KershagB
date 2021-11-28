package prime.TEST.zTest3.z4;

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
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest3_4 extends uApp {

	public uFpsAdapter Explorer;
	zEnv Environment;

	_Property<Float> time;
	_Property<Float> mooch;
	public static Color PHASE = Color.CLEAR;

	ObserverKernel obs;
	float far = 0;

	_BoundShape B;

	Transform p1;
	Transform c1;

	@Override
	public void create() {
		super.create();

		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Environment = new zEnv();
		this.obs = new ObserverKernel(this.Environment, this.Explorer.perspective.camera);

		this.p1 = new Transform();
		this.c1 = new Transform();

		p1.SetPosition(new Vector3(100, 100, 0));
		c1.SetParent(p1);
		c1.SetLocalPosition(new Vector3(32, 0, 0));
		// c1.SetRotation(VectorUtils.upcast(VectorUtils.dir(c1.GetLocalPosition().cpy(),p1.GetLocalPosition().cpy())));
		this.c1.SetRotation(VectorUtils.upcast(VectorUtils.dir(c1.GetPosition(), p1.GetPosition())));
		this.p1.SetRotation(VectorUtils.upcast(VectorUtils.dir(p1.GetPosition(), new Vector3())));
		this.buildDebugGeoset();
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

			//
			this.rdrLocal();
			this.rdrWorld();
			this.obs.render();
			this.renderDebugGeoset();
			this.renderDebugShape();
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
		this.B.dispose();
	}

	private void debugRect() {
		Sketcher.setColor(PHASE);
		Sketcher.Drawer.filledRectangle(0, 0, 32, 32);

	}

	private void updateTrns() {

		Vector3 pPos = this.p1.GetLocalPosition();
		Vector3 cPos = this.c1.GetLocalPosition();

		// works properly:)
		this.p1.SetLocalPosition(pPos.rotate(new Vector3(0, 0, 1), 1));
		this.c1.SetLocalPosition(cPos.rotate(new Vector3(0, 0, 1), 3));
		this.p1.SetRotation(VectorUtils.upcast(VectorUtils.dir(p1.GetPosition(), new Vector3())));
		this.c1.SetLocalRotation(VectorUtils.upcast(VectorUtils.dir(c1.GetPosition(), p1.GetPosition())));
	}

	private void rdrWorld() {
		Color C = PHASE.cpy();
		C.a = 0.1f;

		Vector3 pPos = this.p1.GetPosition();
		Vector3 cPos = this.c1.GetPosition();

		Sketcher.Drawer.setColor(C);
		Sketcher.Drawer.filledCircle(pPos.x, pPos.y, 32);
		C.a = 0.25f;
		Sketcher.Drawer.filledCircle(cPos.x, cPos.y, 8);
	}

	private void rdrLocal() {
		Color C = new Color(1, 1, 1, 1).sub(PHASE.cpy());
		C.a = 0.2f;

		Vector3 pPos = this.p1.GetLocalPosition();
		Vector3 cPos = this.c1.GetLocalPosition();

		Sketcher.Drawer.setColor(C);
		Sketcher.Drawer.circle(pPos.x, pPos.y, 32);
		C.a = 0.25f;
		Sketcher.Drawer.circle(cPos.x, cPos.y, 8);

	}

	private void buildDebugGeoset() {

		this.B = new _BoundShape(p1, new aGeoset());
		Array<Transform> ts = Geom.genShape(p1, this.Explorer.getCameraUp(), new Vector3(32, 32, 32), 6);

		this.B.geom.addVertex(ts.toArray());
		ts.clear();

	}

	private void renderDebugGeoset() {
		Array<Transform> ts = Geom.genShape(c1, this.Explorer.getCameraUp(), new Vector3(32, 32, 32), 6);

		Sketcher.setColor(new Color(0, 0, 0, 0.5f));
		Sketcher.Drawer.filledCircle(c1.GetPosition().x, c1.GetPosition().y, 2);
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

	private void renderProjGeoset(Camera projector) {

	}

	private void renderDebugShape() {
		//Log(this.B.shape.vertices.size);
		for (aVertex v : this.B.geom.vertices) {
			//Log(v.get);
			//Log(v.getTransform().GetPosition());
			//Log(v.getTransform().GetLocalPosition());
			Transform V = v.getTransform();
			Sketcher.setColor(Color.PURPLE);
			Sketcher.Drawer.circle(V.GetPosition().x, V.GetPosition().y, 4);
			Sketcher.Drawer.line(new Vector2(V.GetParent().GetPosition().x, V.GetParent().GetPosition().y),
					new Vector2(V.GetPosition().x, V.GetPosition().y));
			Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.circle(V.GetLocalPosition().x, V.GetLocalPosition().y, 4);
			Sketcher.Drawer.line(new Vector2(0, 0), new Vector2(V.GetLocalPosition().x, V.GetLocalPosition().y));
		}
	}

}
