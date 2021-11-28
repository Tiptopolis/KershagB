package prime.TEST.zTest3.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.gNode;
import prime._PRIME.RAUM._GEOM_X.A_I.aLineX;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest3_2 extends uApp {

	public uFpsAdapter Explorer;
	zEnv Environment;

	_Property<Float> time;
	_Property<Float> mooch;
	public static Color PHASE = Color.CLEAR;

	zShape Z;

	@Override
	public void create() {
		super.create();

		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Environment = new zEnv();

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

	}

	@Override
	public void render() {
		super.render();
		Sketcher.setProjectionMatrix(CAMERA.getProjection());
		Sketcher.begin();
		PHASE = Color.RED.cpy();
		this.debugRect();

		aLineX l = new aLineX(this.Explorer.getCameraPosition(), this.Explorer.getCameraPosition().cpy()
				.add(this.Explorer.getCameraDirection().cpy().scl(this.Environment.getUnit())));
		renderBS();
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.line(VectorUtils.downcast(l.from.get), VectorUtils.downcast(l.to.get));

		Sketcher.end();

		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		PHASE = Color.BLUE.cpy();
		this.debugRect();
		Sketcher.setColor(Color.BLACK);
		l = new aLineX(this.Explorer.getCameraPosition(), this.Explorer.getCameraPosition().cpy()
				.add(this.Explorer.getCameraDirection().cpy().scl(this.Environment.getUnit())));
		Sketcher.Drawer.line(VectorUtils.downcast(l.from.get), VectorUtils.downcast(l.to.get));
		renderBS();
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		PHASE = Color.GREEN.cpy();
		this.debugRect();
		Sketcher.setColor(Color.BLACK);
		l = new aLineX(this.Explorer.getCameraPosition(), this.Explorer.getCameraPosition().cpy()
				.add(this.Explorer.getCameraDirection().cpy().scl(this.Environment.getUnit())));
		Sketcher.Drawer.line(VectorUtils.downcast(l.from.get), VectorUtils.downcast(l.to.get));
		renderBS();
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

	Vector3 at = new Vector3();
	Vector3 orbit = new Vector3(8, 8, 0);

	private void renderBS() {
		Color C = PHASE.cpy();
		// Transform T = new Transform();

		Sketcher.setColor(Color.GRAY);
		Sketcher.Drawer.filledCircle(at.x, at.y, 8);
		// orbit.rotate(new Vector3(0,0,1), 90);
		orbit.rotate(new Vector3(0, 0, 1), 1);
		Sketcher.Drawer.circle(orbit.x, orbit.y, orbit.z + 4);

		Vector3 dir = this.Explorer.getCameraDirection().cpy();
		Transform T = this.Explorer.getTransform().cpy();
		T.SetLocalScale(new Vector3(1, 1, 1));
		T.SetLocalRotation(VectorUtils.upcast(dir));
		// Log("TestTrns: \n" + T);
		// Log(T.GetLocalRotation());
		// Log(" >>" + this.Explorer.getCameraDirection().z);
		renderTrn(T);

		gNode N = new gNode();
		
		// Log(N);
	}

	private void renderTrn(Transform T) {
		Vector3 pos = T.GetLocalPosition().cpy();
		Vector3 dir = VectorUtils.downcast(T.GetLocalRotation());
		Vector3 scl = T.GetLocalScale();

		Vector3 unit = this.Environment.getUnit();

		Vector3 unitForward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 up = this.Explorer.getCameraUp();

		Sketcher.setColor(new Color(0, 0, 0, 0.5f));
		Sketcher.Drawer.filledCircle(pos.x, pos.y, unit.z);
		Sketcher.setColor(Color.ORANGE);
		Sketcher.Drawer.circle(unitForward.x, unitForward.y, ((unit.len() / 3)));

		// Vector3 rightRot = unitForward.cpy();
		// rightRot.rotate(dir.cpy(), 90); //interesting results

		// Vector3 rightRot = pos.cpy().add(unit.cpy().rotate(dir, 90));
		// Vector3 rightRot = pos.cpy().add(dir.cpy().crs(new Vector3(0, 0,
		// 1)).nor().scl(unit.cpy()));
		Vector3 rightRot = pos.cpy().add(dir.rotate(up.cpy(), -90).scl(unit.cpy()));

		Sketcher.setColor(Color.YELLOW);
		Sketcher.Drawer.circle(rightRot.x, rightRot.y, 2);

		Vector3 upRot = pos.cpy().add(up.cpy().nor().scl(unit.cpy()));
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.circle(upRot.x, upRot.y, 2);

		Array<Transform> Ts = Geom.genShape(T, this.Explorer.getCameraUp(), this.Environment.getUnit(), 6);

		for (Transform t : Ts) {
			Color F = Color.MAGENTA.cpy();
			Vector3 tPos = t.GetPosition();
			F.r = 1 - (1 / (Ts.indexOf(t, true) + 1));
			F.g = (1 / (Ts.indexOf(t, true) + 1));
			F.a = 0.5f;

			Sketcher.setColor(F);
			Sketcher.Drawer.circle(tPos.x, tPos.y, 1 + (Ts.indexOf(t, true)));

			F = Color.PINK.cpy();
			tPos = t.GetLocalPosition();
			F.r = 1 - (1 / (Ts.indexOf(t, true) + 1));
			F.g = (1 / (Ts.indexOf(t, true) + 1));
			F.a = 0.5f;

			Sketcher.setColor(F);
			Sketcher.Drawer.circle(tPos.x, tPos.y, 1 + (Ts.indexOf(t, true)));
			
			Log(" **"+Ts.indexOf(t, true));
			Log(t);
		}
		
		

	}

}
