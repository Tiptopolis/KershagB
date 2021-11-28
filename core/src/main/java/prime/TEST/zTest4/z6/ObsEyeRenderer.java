package prime.TEST.zTest4.z6;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.Maths;
import prime._PRIME.SYS.NIX._Entity;

public class ObsEyeRenderer {

	public ObserverKernel perspective;
	private SnapshotArray<_Entity> members;

	private PerspectiveCamera leftEye;
	private PerspectiveCamera rightEye;

	public ObsEyeRenderer(ObserverKernel kernel) {
		this.perspective = kernel;
		this.members = new SnapshotArray<_Entity>();
		this.buildEyes();
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
		this.render(this.leftEye, Color.RED);
		this.render(this.rightEye, Color.BLUE);
	}

	public void render(Camera eye, Color phase) {

		Vector3 unit = this.perspective.environment.getUnit();
		float uSize = unit.len() / 3;
		Vector3 reference = eye.position;

		// Log("<" + this.members.size);
		int max = this.perspective.maxVisible;
		float b = this.perspective.batchSize;
		int s = this.members.size;
		float mod = s % b;
		int rem = (int) ((s / b) + 1);
		// Log(eye.position + " -> " + eye.direction);
		// Log(this.perspective.observer.position + " => " +
		// this.perspective.observer.direction);

		for (int i = 0; i < (int) rem; i++) {
			{
				Sketcher.begin();

				for (int j = 0; j < b; j++) {

					if ((b * i) + j > Math.min(s - 1, max)) {
						break;
					}

					int f = (int) ((b * i) + j);

					zEnt L = (zEnt) this.members.get(f);
					if (L != null) {
						// L.render();
						Color c = phase.cpy();

						Vector3 pos = L.position().cpy();
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
						c.a = 0.5f;
						Sketcher.setColor(c);
						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);
						c.a = 0.5f;
						Sketcher.setColor(c);
						Sketcher.Drawer.circle(prj.x, prj.y, z2);

					
						
					}

				}
				Sketcher.end();
			}

			this.perspective.toDraw.clear();
		}
	}

	private Camera copy(Camera c) {
		PerspectiveCamera res = new PerspectiveCamera();

		res.position.set(c.position.cpy());
		res.direction.set(c.direction.cpy());
		res.up.set(c.up.cpy());
		res.far = c.far;
		res.near = c.near;
		res.viewportHeight = c.viewportHeight;
		res.viewportWidth = c.viewportWidth;

		res.update(true);

		return res;

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