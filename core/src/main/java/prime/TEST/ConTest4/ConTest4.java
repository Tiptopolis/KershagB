package prime.TEST.ConTest4;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class ConTest4 extends uApp {

	// PROBLEM -
	// Property reading itself as source upon ListenerUpdate message

	public lEnv Environment;
	public uFpsAdapter Explorer;

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
		this.Environment = new lEnv(this.Explorer.perspective.camera, new Vector3(24, 24, 24), 1024*2);

		time = new _Property<Float>("Time", 0f);
		mooch = new _Property<Float>("Time-er", 0f);
		time.connect("Listener", mooch);
	}

	@Override
	public void update(float deltaTime) {
		time.set(deltaTime);
		this.Environment.update(deltaTime);
		//Log("T: " + time.get());
		// Log();
		// Log("-er: " + mooch.get());
		// Log();
		// Log();

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();

		Sketcher.setColor(Color.LIGHT_GRAY);
		Sketcher.Drawer.filledRectangle(0, 0, Width, Height);
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.rectangle(0, 0, 32, 32);

		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		//Sketcher.begin();
		// something something batch size & shader combo something something
		//Log(this.Environment.toDraw.size);
		
		this.Environment.render();
		//Sketcher.end();

	}

	@Override
	public void dispose() {
		super.dispose();
		this.Environment.dispose();
	}

}
