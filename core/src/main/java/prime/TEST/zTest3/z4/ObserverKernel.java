package prime.TEST.zTest3.z4;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class ObserverKernel extends _Entity {
//renders the transform

	protected Transform transform;
	protected Camera perspective;

	public Vector3 direction = new Vector3(0, 0, 1);
	public Vector3 up = new Vector3(0, 1, 0);

	public ObserverKernel(_Environment env, Camera cam) {
		this.transform = new Transform();
		this.perspective = cam;
		this.Environment = env;

		this.direction = cam.direction.cpy();
		this.up = cam.up.cpy();

		this.transform.SetLocalScale(new Vector3(1, 1, 1));
		this.transform.SetLocalRotation(VectorUtils.upcast(this.direction));
		this.transform.SetLocalPosition(cam.position.cpy());

	}

	public void update() {

		Vector3 unit = this.Environment.getUnit();
		Vector3 pos = this.perspective.position.cpy();
		Vector3 dir = this.perspective.direction.cpy();
		Vector3 up = this.perspective.up.cpy();
		this.up = up;
		this.direction = dir;

		// Vector3 unitForward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		// Vector3 unitRight = pos.cpy().add(dir.cpy().rotate(up.cpy(),
		// -90).scl(unit.cpy()));
		// Vector3 unitUp = pos.cpy().add(up.cpy().scl(unit.cpy()));

		this.transform.SetLocalScale(new Vector3(1, 1, 1));
		this.transform.SetLocalRotation(VectorUtils.upcast(dir));
		this.transform.SetLocalPosition(pos);

		// Log("ObserverKernel -");
		// Log(this.transform);

	}

	public void render() {
		this.renderPrimaryTrns();
		this.renderShape();
	}

	private void renderPrimaryTrns() {
		Vector3 unit = this.Environment.getUnit();
		Vector3 pos = this.perspective.position.cpy();
		Vector3 dir = this.perspective.direction.cpy();
		Vector3 up = this.perspective.up.cpy();

		Vector3 forward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 right = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(unit.cpy()));
		up = pos.cpy().add(up.cpy().scl(unit.cpy()));

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

	private void renderShape() {
		Vector3 unit = this.Environment.getUnit();
		Vector3 pos = this.transform.GetLocalPosition().cpy();
		Vector3 dir = this.direction.cpy();
		Vector3 up = this.up.cpy();

		Vector3 unitForward = pos.cpy().add(dir.cpy().scl(unit.cpy()));
		Vector3 unitRight = pos.cpy().add(dir.cpy().rotate(up.cpy(), -90).scl(unit.cpy()));
		Vector3 unitUp = pos.cpy().add(up.cpy().scl(unit.cpy()));

		Array<Transform> Ts = Geom.genShape(this.transform, up, new Vector3(32, 32, 32), 6);

		for (Transform t : Ts) {
			Color F = Color.MAGENTA.cpy();
			Vector3 tPos = t.GetParent().GetPosition().cpy().add(t.GetLocalPosition().cpy());
			Vector3 tDir = tPos.cpy().add(VectorUtils.downcast(t.GetLocalRotation()).scl(unit));
			F.r = 1 - (1 / (Ts.indexOf(t, true) + 1));
			F.g = (1 / (Ts.indexOf(t, true) + 1));
			F.a = 0.5f;

			Sketcher.setColor(F);
			// Sketcher.Drawer.circle(tPos.x, tPos.y, 1 + (Ts.indexOf(t, true)));
			Sketcher.Drawer.circle(tPos.x, tPos.y, 1);
			Sketcher.setColor(Color.BLACK);
			Sketcher.Drawer.filledCircle(tDir.x, tDir.y, 1);
			Sketcher.setColor(F);
			Sketcher.Drawer.line(VectorUtils.downcast(tPos), VectorUtils.downcast(tDir));

			F = Color.PINK.cpy();
			tPos = t.GetLocalPosition();
			tDir = VectorUtils.downcast(t.GetLocalRotation().cpy()).add(tPos.cpy()).scl(2);
			F.r = 1 - (1 / (Ts.indexOf(t, true) + 1));
			F.g = (1 / (Ts.indexOf(t, true) + 1));
			F.a = 0.5f;

			Sketcher.setColor(F);
			Sketcher.Drawer.circle(tPos.x, tPos.y, 1 + (Ts.indexOf(t, true)));
			Sketcher.setColor(Color.BLACK);
			Sketcher.Drawer.filledCircle(tDir.x, tDir.y, 1);
			Sketcher.setColor(F);
			Sketcher.Drawer.line(VectorUtils.downcast(tPos), VectorUtils.downcast(tDir));

		}
		this.transform.clearChildren();
		for (Transform t : Ts)
			t.clear();
		Ts.clear();
	}

}
