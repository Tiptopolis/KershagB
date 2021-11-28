package prime.TEST.SHD3;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.HashMap;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import prime.uChumpEngine;
import prime._PRIME.GdxFileUtils;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class SHD3 extends uApp {

	// PROBLEM -
	// Property reading itself as source upon ListenerUpdate message
	PerspectiveCamera p;
	public lEnv Environment;
	public uFpsAdapter Explorer;

	ShaderProgram shader;
	String VERT;
	String FRAG;

	_Property<Float> time;
	_Property<Float> mooch;

	@Override
	public void create() {
		super.create();
		Log("                  ------>>>>>>>>>");

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Environment = new lEnv(this.Explorer.perspective.camera, new Vector3(24, 24, 24), 1024 * 10);

		time = new _Property<Float>("Time", 0f);
		mooch = new _Property<Float>("Time-er", 0f);
		time.connect("Listener", mooch);
		this.shaderSetup();
	}

	@Override
	public void update(float deltaTime) {
		time.set(deltaTime);
		this.Explorer.update(deltaTime);
		this.Environment.update(deltaTime);
		Log("T: " + time.get());
		// Log();
		// Log("-er: " + mooch.get());
		// Log();
		// Log();

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Sketcher.currentBatch.setShader(uChumpEngine.DEFAULT_SHADER);
		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();

		Sketcher.setColor(Color.LIGHT_GRAY);
		Sketcher.Drawer.filledRectangle(0, 0, Width, Height);
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.rectangle(0, 0, 32, 32);

		Sketcher.end();

		Sketcher.currentBatch.setShader(shader);
		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		// Sketcher.begin();
		// something something batch size & shader combo something something
		// Log(this.Environment.toDraw.size);

		this.Environment.render(true);

		Sketcher.currentBatch.setShader(uChumpEngine.DEFAULT_SHADER);
		this.Environment.render(false);
		Sketcher.begin();
		this.renderDebugPts();
		Sketcher.end();
		// Sketcher.end();

	}

	@Override
	public void dispose() {
		super.dispose();
		this.Environment.dispose();
		this.shader.dispose();
	}

	public void shaderSetup() {
		ShaderProgram.pedantic = false;

		String path = "/assets/TST/SHD/3";

		ArrayList<FileHandle> fGet = GdxFileUtils.getFilesFrom("." + path);
		HashMap<String, FileHandle> fMap = GdxFileUtils.mapFiles(fGet);

		VERT = fMap.get("Vert").readString();
		FRAG = fMap.get("Frag").readString();

		shader = new ShaderProgram(VERT, FRAG);
		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}

		if (shader.getLog().length() != 0)
			System.out.println(shader.getLog());

		shader.bind();
		this.Environment.shader = this.shader;
	}

	@Override
	public Vector3 getUnit() {
		return this.Environment.getUnit();
	}

	private void renderDebugPts() {

		Vector3 pos;
		Vector3 dir;
		Vector3 scrn;
		Vector3 prj;
		Vector3 unit = this.getUnit();
		Vector3 u = VectorUtils.div(this.Environment.getUnit(), new Vector3(2, 2, 2));

		pos = Explorer.getCameraPosition();
		dir = Explorer.getCameraDirection();
		scrn = new Vector3(Width / 2, Height / 2, 0);
		prj = new Vector3(this.Explorer.unproject(scrn.cpy()));

		Vector3 dst = VectorUtils.dst(pos.cpy(), pos.cpy().add(dir.cpy().scl(unit.cpy())));
		this.Explorer.project(prj);

		Vector3 pj = pos.cpy().sub(dst.cpy().scl(unit.cpy()));
		float z = (u.len() / Explorer.getCameraPosition().cpy().sub(pj.cpy()).len());
		z = (z * (this.Environment.getUnit().len() / 3)) / 2;
		z = z * (this.Environment.getUnit().len() / 3);
		// ("<< " + ((z*2)-1)); //projected UnitSize/Distance scalar

		this.Explorer.project(pj);

		Sketcher.setColor(Color.RED);
		Sketcher.Drawer.circle(prj.x, prj.y, 32);
		Sketcher.setColor(Color.YELLOW);
		Sketcher.Drawer.circle(prj.x, prj.y, 16);
		Sketcher.setColor(Color.GREEN);
		Sketcher.Drawer.circle(pj.x, pj.y, (z * 2) - 1);
		Sketcher.setColor(Color.BLUE);
		Sketcher.Drawer.circle(scrn.x, scrn.y, 8);

	}

}
