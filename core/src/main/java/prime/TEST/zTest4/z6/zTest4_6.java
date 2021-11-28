package prime.TEST.zTest4.z6;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.SYS.uApp;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest4_6 extends uApp {

	public uFpsAdapter Explorer;
	public zEnv Environment;
	public ObserverKernel ExpKernel;
	public ObsRenderer EnvRenderer;
	public ObsEyeRenderer EyeTest;
	public ObsShapeRenderer ShapeTest;
	public ObsEyeShapeRenderer ShapeEyeTest;

	float far;
	public static Color PHASE = Color.CLEAR.cpy();

	@Override
	public void create() {
		super.create();
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.far = this.Explorer.perspective.camera.far;
		this.Environment = new zEnv(new Vector3(64, 64, 64), 32);
		this.ExpKernel = new ObserverKernel(this.Environment, this.Environment.getTransform(),
				this.Explorer.perspective.camera);
		this.EnvRenderer = new ObsRenderer(this.ExpKernel);
		this.EyeTest = new ObsEyeRenderer(this.ExpKernel);
		this.ShapeTest = new ObsShapeRenderer(this.ExpKernel);
		this.ShapeEyeTest = new ObsEyeShapeRenderer(this.ExpKernel);
	}

	@Override
	public void update(float deltaTime) {

		if (this.Explorer.getCameraPosition().z <= ((this.Environment.getUnit().z / 2)) - 0.1f)
			this.Explorer.getCameraPosition().z = (this.Environment.getUnit().z / 2);
		this.Explorer.perspective.camera.far = this.far * CAMERA.Camera.zoom;

		this.Explorer.update();
		this.ExpKernel.update();
		this.EnvRenderer.update();
		this.EyeTest.update();
		this.ShapeTest.update();
		this.ShapeEyeTest.update();
	}

	@Override
	public void render() {

		Sketcher.setLineWidth(1f);
		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		PHASE = Color.BLUE.cpy();
		Sketcher.setColor(0.7f, 0.7f, 0.7f, 0.7f);
		Sketcher.Drawer.filledRectangle(0, 0, 32, 32);
		Sketcher.setColor(Color.WHITE);
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
		// this.EnvRenderer.render();
		//this.EyeTest.render();
		this.ShapeTest.render();
		this.ShapeEyeTest.render();
		Sketcher.setLineWidth(1f);

	}

	@Override
	public void dispose() {
		super.dispose();
		this.ExpKernel.dispose();

	}

}
