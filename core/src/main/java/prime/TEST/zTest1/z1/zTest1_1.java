package prime.TEST.zTest1.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Line;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest1_1 extends uApp {

	public uFpsAdapter Explorer;
	public zEnv Environment;

	Transform obj = new Transform();

	_Property<Float> time;
	_Property<Float> mooch;

	public VisKernel ExplorerKernel;
	public VisKernel MapKernel; // quadTree this bitch

	@Override
	public void create() {
		super.create();
		Log("                  ------>>>>>>>>>");

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Environment = new zEnv(this.Explorer.perspective.camera, new Vector3(64, 64, 64));

		this.ExplorerKernel = new VisKernel(this.Explorer.getTransform().cpy(), this.Environment);

		time = new _Property<Float>("Time", 0f);
		mooch = new _Property<Float>("Time-er", 0f);
		time.connect("Listener", mooch);
	}

	@Override
	public void update(float deltaTime) {
		time.set(deltaTime);
		if (this.Explorer.getCameraPosition().z <= ((this.Environment.getUnit().z / 2)) - 0.1f)
			this.Explorer.getCameraPosition().z = (this.Environment.getUnit().z / 2);
		this.Explorer.update();
		this.Environment.update(deltaTime);

		// Log("T: " + time.get());
		// Log();
		// Log("-er: " + mooch.get());
		// Log();
		// Log();

		// obj.SetParent(this.Explorer.getTransform());

		Vector3 unit = this.Environment.getUnit();
		float Z = this.Environment.apparentSize(obj, this.Explorer.perspective.camera);

		Vector3 u = VectorUtils.div(this.Environment.getUnit(), new Vector3(2, 2, 2));// unit radius
		float U = unit.len() / 3;
		float z = (u.len() / Explorer.getCameraPosition().cpy().sub(obj.GetLocalPosition().cpy()).len());
		z = (z * (this.Environment.getUnit().len() / 3)) / 2;
		z = z * (this.Environment.getUnit().len() / 3);
		z *= 0.5f;

		obj.SetLocalPosition(
				this.Explorer.getCameraPosition().cpy().add(this.Explorer.getCameraDirection().cpy().scl(U / 2)));
		this.ExplorerKernel.root.transform.SetLocalPosition(this.Explorer.getCameraPosition());
		// Log(this.ExplorerKernel.root.neighbors);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Sketcher.setLineWidth(CAMERA.Camera.zoom);

		Sketcher.setProjectionMatrix(CAMERA.getProjection());
		Sketcher.begin();
		this.drawDebugPts(true);
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT))
			this.ExplorerKernel.draw(true);
		Sketcher.end();

		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();

		Sketcher.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		Sketcher.Drawer.filledRectangle(0, 0, Width, Height);

		this.drawDebugPts(true);
		this.ExplorerKernel.draw(false);
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.rectangle(0, 0, 32, 32);
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		this.drawDebugPts(false);
		this.Environment.render();
		Sketcher.end();

	}

	@Override
	public void dispose() {
		super.dispose();
		// this.Environment.dispose();
	}

	///////
	///////
	private void drawDebugPts(boolean ground) {

		// ground = false;

		Vector3 pos = Explorer.getCameraPosition();
		Vector3 dir = Explorer.getCameraDirection();
		Vector3 scrn = new Vector3(Width / 2, Height / 2, 0);
		Vector3 prj = new Vector3(this.Explorer.unproject(scrn.cpy()));

		Vector3 unit = this.Environment.getUnit();
		Vector3 u = VectorUtils.div(this.Environment.getUnit(), new Vector3(2, 2, 2));// unit radius
		float U = unit.len() / 3; // avg len vec3
		// Log(this.Explorer.getCameraPosition().z);
		if (!ground) {
			Vector3 dst = VectorUtils.dst(pos.cpy(), pos.cpy().add(dir.cpy().scl(unit.cpy())));
			this.Explorer.project(prj);

			Vector3 pj = pos.cpy().sub(dst.cpy().scl(unit.cpy()));
			float z = (u.len() / Explorer.getCameraPosition().cpy().sub(pj.cpy()).len());
			z = (z * (this.Environment.getUnit().len() / 3)) / 2;
			z = z * (this.Environment.getUnit().len() / 3);
			// ("<< " + ((z*2)-1)); //projected UnitSize/Distance scalar
			// Log("z:A: " + z + " " + CAMERA.Camera.zoom + " : " + z * CAMERA.Camera.zoom);
			this.Explorer.project(pj);

			Sketcher.setColor(Color.RED);
			Sketcher.Drawer.circle(prj.x, prj.y, 32);
			Sketcher.setColor(Color.YELLOW);
			Sketcher.Drawer.circle(prj.x, prj.y, 16);
			Sketcher.setColor(Color.GREEN);
			Sketcher.Drawer.circle(pj.x, pj.y, (z * 2) - 1);
			Sketcher.setColor(Color.BLUE);
			Sketcher.Drawer.circle(scrn.x, scrn.y, 8);
			// Sketcher.setColor(Color.WHITE);
			// Vector2 c = new Vector2(Width/2, Height/2);
			// Sketcher.Drawer.rectangle(c.x-(16*z), c.y-(16*z),32*z,32*z);
		}

		boolean cir = true;
		boolean sqr = true;
		boolean line = true;

		if (ground) {
			U /= 2;
			Vector3 dst = VectorUtils.dst(pos.cpy(), pos.cpy().add(dir.cpy().scl(unit.cpy())));
			this.Explorer.unproject(prj);
			Vector3 pj = pos.cpy().sub(dst.cpy().scl(unit.cpy()));
			float z = (u.len() / Explorer.getCameraPosition().cpy().sub(pj.cpy()).len());
			z = (z * (this.Environment.getUnit().len() / 3)) / 2;
			z = z * (this.Environment.getUnit().len() / 3);
			z *= 0.5f;
			float Z = this.Environment.apparentSize(this.obj, this.Explorer.perspective.camera);
			// Log("z:B: " + Z + " " + CAMERA.Camera.zoom + " : " + Z * CAMERA.Camera.zoom);
			// Log(" -> [" + (U / z) + "] " + U + ";"+z + "| ");
			// Log(" -< [" + (U *Z) + "] " + U + ";"+Z + "| ");
			if (cir) {
				Sketcher.Drawer.setColor(Color.BLACK);
				Sketcher.Drawer.circle(this.Explorer.getCameraPosition().x, this.Explorer.getCameraPosition().y, U / z);
				Sketcher.Drawer.setColor(Color.GREEN);
				Sketcher.Drawer.circle(this.obj.GetLocalPosition().x, this.obj.GetLocalPosition().y, 3 * U * Z);
				Sketcher.Drawer.setColor(Color.BLUE);
				Sketcher.Drawer.circle(this.obj.GetLocalPosition().x, this.obj.GetLocalPosition().y, 2 * U * Z);
				Sketcher.Drawer.setColor(Color.RED);
				Sketcher.Drawer.circle(this.obj.GetLocalPosition().x, this.obj.GetLocalPosition().y, U * Z);

				Sketcher.Drawer.setColor(Color.YELLOW);
				Sketcher.Drawer.circle(this.obj.GetLocalPosition().x, this.obj.GetLocalPosition().y, 3 * z);
				Sketcher.Drawer.setColor(Color.MAGENTA);
				Sketcher.Drawer.circle(this.obj.GetLocalPosition().x, this.obj.GetLocalPosition().y, 2 * z);
				Sketcher.Drawer.setColor(Color.TEAL);
				Sketcher.Drawer.circle(this.obj.GetLocalPosition().x, this.obj.GetLocalPosition().y, z);

			}
			if (sqr) {

				Sketcher.Drawer.setColor(Color.WHITE);
				Sketcher.Drawer.rectangle(this.Explorer.getCameraPosition().x - (U / z) / 2,
						this.Explorer.getCameraPosition().y - (U / z) / 2, U / z, U / z);
				Sketcher.Drawer.setColor(Color.PINK);
				Sketcher.Drawer.rectangle(this.Explorer.getCameraPosition().x - (z * 2) / 2,
						this.Explorer.getCameraPosition().y - (z * 2) / 2, z * 2, z * 2);
				Sketcher.Drawer.setColor(Color.ORANGE);
				Sketcher.Drawer.rectangle(this.Explorer.getCameraPosition().x - (z / 2),
						this.Explorer.getCameraPosition().y - (z / 2), z, z);

			}
			if (line) {
				Color l;
				pos = Explorer.getCameraPosition();
				dir = Explorer.getCameraDirection();

				Line L1 = new Line(pos.cpy(), pos.cpy().add(dir.cpy().scl(U)));
				l = Color.YELLOW.cpy();
				l.a = 0.25f;
				L1.setColor(l);
				L1.vertexSize = CAMERA.Camera.zoom;
				L1.drawShape();

				Line L2 = new Line(pos.cpy(), pos.cpy().add(dir.cpy().scl(U / z)));
				l = Color.MAGENTA.cpy();
				l.a = 0.25f;
				L2.vertexSize = CAMERA.Camera.zoom;
				L2.setColor(l);
				L2.drawShape();

				Line L3 = new Line(pos.cpy(), pos.cpy().add(dir.cpy().scl(z)));
				l = Color.TEAL.cpy();
				l.a = 0.25f;
				L3.vertexSize = CAMERA.Camera.zoom;
				L3.setColor(l);
				L2.drawShape();
				// Log(" L1->:" + L1.len());
				// Log(" L2->:" + L2.len());
				// Log(" L3->:" + L3.len());
			}
		}

	}

}
