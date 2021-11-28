package prime.TEST.zTest4.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import static prime._METATRON.Metatron.*;

import prime._PRIME.SYS.uApp;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest4_1 extends uApp {

	public uFpsAdapter Explorer;
	public zEnv Environment;
	float far;

	public static Color PHASE = Color.CLEAR.cpy();
	public ObserverKernel ExplorerKernel;
	public OBS_Renderer ExplorerRenderer;

	@Override
	public void create() {
		super.create();
		this.Explorer = new uFpsAdapter(CAMERA);		
		this.Explorer.init();
		this.Environment = new zEnv(new Vector3(64,64,64),32);
		this.ExplorerKernel = new ObserverKernel(this.Environment, this.Explorer.getTransform(), this.Explorer.perspective.camera);
		this.ExplorerRenderer = new OBS_Renderer();
		this.Environment.registerObserver(this.ExplorerKernel);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (this.Explorer.getCameraPosition().z <= ((this.Environment.getUnit().z / 2)) - 0.1f)
			this.Explorer.getCameraPosition().z = (this.Environment.getUnit().z / 2);
		this.Explorer.perspective.camera.far = this.far * CAMERA.Camera.zoom;

		this.Environment.update(deltaTime);
		this.Explorer.update();
		this.ExplorerKernel.update(deltaTime);
		//Log(this.Environment.toLog());
	}

	@Override
	public void render() {
		super.render();
		
		if (Gdx.input.isKeyPressed(Keys.TAB)) {
			Sketcher.setColor(Color.BLACK);
			Sketcher.setProjectionMatrix(CAMERA.getProjection());
			Sketcher.begin();
			PHASE = Color.RED.cpy();
			
			Sketcher.end();
		}
		
		
		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		PHASE = Color.BLUE.cpy();
		Sketcher.setColor(0.7f,0.7f,0.7f,0.7f);
		Sketcher.Drawer.filledRectangle(-32, -32, 32, 32);
		Sketcher.setColor(Color.WHITE);
		Sketcher.Drawer.rectangle(-32, -32, 32, 32);
		Sketcher.setColor(Color.LIGHT_GRAY);
		Sketcher.Drawer.rectangle(-16, -16, 16, 16);
		Sketcher.setColor(Color.DARK_GRAY);
		Sketcher.Drawer.rectangle(-8, -8, 8, 8);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.rectangle(-4, -4, 4, 4);
		//
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		PHASE = Color.GREEN.cpy();		
		Sketcher.setColor(Color.BLACK);

		this.ExplorerRenderer.render(this.ExplorerKernel);
		//

		Sketcher.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.ExplorerKernel.running = false;
	}

}
