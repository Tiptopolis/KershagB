package prime.TEST.zTest6.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.SYS.uApp;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest6_0 extends uApp {

	public uFpsAdapter Explorer;
	public zEnv Environment;

	zEnt p1;
	zEnt c1;

	float far;
	public static Color PHASE = Color.CLEAR.cpy();

	@Override
	public void create() {
		super.create();
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.far = this.Explorer.perspective.camera.far;

		// Env
		// PrsKrn
		// Rdr

		this.Explorer.Camera.camera.near = 0.05f;

		this.genShapes();

	}

	@Override
	public void update(float deltaTime) {

		this.Explorer.perspective.camera.far = this.far * CAMERA.Camera.zoom;

		this.Explorer.update();

	}

	@Override
	public void render() {
		if (Gdx.input.isKeyPressed(Keys.TAB)) {
			Sketcher.setProjectionMatrix(CAMERA.getProjection());
			// this.ShapeTest.render(CAMERA.getBaseCamera());
		}

		Sketcher.setLineWidth(1f);
		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		PHASE = Color.BLUE.cpy();
		Sketcher.setColor(0.7f, 0.7f, 0.7f, 0.7f);
		Sketcher.Drawer.filledRectangle(0, 0, 32, 32);
		Sketcher.setColor(0.9f, 0.9f, 0.9f, 1f);
		Sketcher.Drawer.rectangle(0, 0, 32, 32);
		Sketcher.setColor(Color.LIGHT_GRAY);
		Sketcher.Drawer.rectangle(0, 0, 16, 16);
		Sketcher.setColor(Color.DARK_GRAY);
		Sketcher.Drawer.rectangle(0, 0, 8, 8);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.rectangle(0, 0, 4, 4);
		//
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		// this.testRenderer.render();
		this.debugDrawA();

		Sketcher.setLineWidth(1f);
		// this.ExpKernel.toDraw.clear();
	}

	@Override
	public void dispose() {
		super.dispose();

	}

	public void genShapes() {
		this.p1 = new zEnt();
		this.c1 = new zEnt();

		// this.B = new _BoundShape(p1, new aGeoset());
		Array<Transform> ts = Geom.genShape(p1.transform, this.Explorer.getCameraUp(), new Vector3(32, 32, 32), 6);

		this.p1.shape.geom.addVertex(ts.toArray());
		ts.clear();

		this.c1.transform.SetParent(p1.transform);

		this.p1.position(new Vector3(16, 16, 8));
		
		this.c1.transform.SetParent(this.p1.transform);
		this.c1.position(new Vector3(8, 0, 0),true);
		
		this.c1.rotation(VectorUtils.upcast(VectorUtils.dir(c1.position(), p1.position())));
		this.p1.rotation(VectorUtils.upcast(VectorUtils.dir(p1.position(), new Vector3())));
	}

	public void debugDrawA() {
		Sketcher.begin();

		Sketcher.setColor(Color.BLACK);
		Vector3 unit = new Vector3(8, 8, 8);
		Vector3 P1 = p1.position().cpy();
		Vector3 C1 = c1.position().cpy();

		boolean F1 = this.Explorer.perspective.camera.frustum.planes[0].testPoint(P1) == Plane.PlaneSide.Front;
		boolean S1 = (this.Explorer.perspective.camera.frustum.sphereInFrustum(P1, unit.len() * 3));
		if (F1 && S1) {
			this.Explorer.project(P1);
			Sketcher.setColor(Color.PINK);
			Sketcher.Drawer.circle(P1.x, P1.y, 1);
		}

		F1 = this.Explorer.perspective.camera.frustum.planes[0].testPoint(C1) == Plane.PlaneSide.Front;
		S1 = (this.Explorer.perspective.camera.frustum.sphereInFrustum(C1, unit.len() * 3));
		if (F1 && S1) {
			this.Explorer.project(C1);
			Sketcher.setColor(Color.ORANGE);
			Sketcher.Drawer.circle(C1.x, C1.y, 1);
		}

		Sketcher.end();
	}

}
