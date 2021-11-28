package prime;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import prime.TEST.ConTest1.ConTest1;
import prime.TEST.ConTest2.ConTest2;
import prime.TEST.ConTest3.ConTest3;
import prime.TEST.ConTest4.ConTest4;
import prime.TEST.LFO_HARD.L1.LFO_H_1;
import prime.TEST.LFO_SOFT.L1.LFO_S_1;
import prime.TEST.SHD1.SHD1;
import prime.TEST.SHD2.SHD2;
import prime.TEST.SHD3.SHD3;
import prime.TEST.a1DEFAULT.a1DEFAULT_1;
import prime.TEST.zTest1.z1.zTest1_1;
import prime.TEST.zTest1.z2.zTest1_2;
import prime.TEST.zTest2.z1.zTest2_1;
import prime.TEST.zTest2.z2.zTest2_2;
import prime.TEST.zTest3.z1.zTest3_1;
import prime.TEST.zTest3.z2.zTest3_2;
import prime.TEST.zTest3.z3.zTest3_3;
import prime.TEST.zTest3.z4.zTest3_4;
import prime.TEST.zTest4.z1.zTest4_1;
import prime.TEST.zTest4.z2.zTest4_2;
import prime.TEST.zTest4.z3.zTest4_3;
import prime.TEST.zTest4.z4.zTest4_4;
import prime.TEST.zTest4.z5.zTest4_5;
import prime.TEST.zTest4.z6.zTest4_6;
import prime.TEST.zTest5.z1.zTest5_1;
import prime.TEST.zTest5.z2.zTest5_2;
import prime.TEST.zTest5.z3.zTest5_3;
import prime.TEST.zTest5.z4.zTest5_4;
import prime.TEST.zTest6.z1.zTest6_0;
import prime.TEST.zTest6.z2.zTest6_1;
import prime._METATRON.Metatron;
import prime._PRIME.uAppUtils;
import prime._PRIME.uSketcher;
import prime._PRIME.SYS.iApplet;
import prime._PRIME.SYS._Events.Prototype.Message;
import prime._PRIME.UI.aViewContext;
import prime._PRIME.UI._Camera.OrthoController;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class uChumpEngine extends ApplicationAdapter implements iApplet {

	public static Metatron METATRON;
	public static ShaderProgram DEFAULT_SHADER;

	@Override
	public void create() {

		uSketcher s = new uSketcher();
		DEFAULT_SHADER = Sketcher.getBatch().getShader();
		METATRON = Metatron.TheMetatron;
		CAMERA = new OrthoController(METATRON);
		CAMERA.init();
		Sketcher.setProjectionReference(CAMERA.getBaseCamera());

		// METATRON.launch(new ConTest1());
		// METATRON.launch(new ConTest2());
		// METATRON.launch(new ConTest3());
		// METATRON.launch(new ConTest4());
		// METATRON.launch(new ConTest4());

		// METATRON.launch(new LFO_S_1());
		// METATRON.launch(new LFO_H_1());
		// METATRON.launch(new SHD2());
		// METATRON.launch(new SHD3()); //<-base normal 3d example

		// METATRON.launch(new zTest1_1());
		// METATRON.launch(new zTest1_2());

		// METATRON.launch(new zTest2_1());
		// METATRON.launch(new zTest2_2());

		// METATRON.launch(new zTest3_1());
		// METATRON.launch(new zTest3_2());
		// METATRON.launch(new zTest3_3());
		// METATRON.launch(new zTest3_4()); //<-base normal transform test

		// METATRON.launch(new zTest4_1());
		// METATRON.launch(new zTest4_2());
		// METATRON.launch(new zTest4_3());
		// METATRON.launch(new zTest4_4());
		// METATRON.launch(new zTest4_5());
		// METATRON.launch(new zTest4_6());

		// METATRON.launch(new zTest5_1()); //<-base normal mesh, rendering not as
		// stable as Core6_LFO_5
		// METATRON.launch(new zTest5_2());
		// METATRON.launch(new zTest5_3());
		// METATRON.launch(new zTest5_4()); //<-newer one

		// METATRON.launch(new zTest6_0());
		METATRON.launch(new zTest6_1());

		// METATRON.launch(new a1DEFAULT_1());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uAppUtils.update(0);
		float deltaTime = Gdx.graphics.getDeltaTime();
		// batch.begin();
		// batch.draw(image, 165, 180);
		// batch.end();

		METATRON.update(deltaTime);
		CAMERA.update(deltaTime);

		Sketcher.setProjectionMatrix(CAMERA.getProjection());
		Sketcher.begin();
		Sketcher.setColor(Color.WHITE);
		Sketcher.Drawer.rectangle(0, 0, 32, 32);
		Sketcher.setColor(Color.LIGHT_GRAY);
		Sketcher.Drawer.rectangle(0, 0, 16, 16);
		Sketcher.setColor(Color.DARK_GRAY);
		Sketcher.Drawer.rectangle(0, 0, 8, 8);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.rectangle(0, 0, 4, 4);
		// Sketcher.getBatch().draw(image, 165, 180);
		Sketcher.end();

	}

	@Override
	public void resize(int width, int height) {
		uAppUtils.resize();
		CAMERA.resize(Width, Height);
		CAMERA.update(0);
		if (CURRENT != null)
			CURRENT.resize(width, height);

	}

	@Override
	public void dispose() {

		Sketcher.dispose();
		METATRON.dispose();
	}

	///////
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(int deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public aViewContext domain() {
		// TODO Auto-generated method stub
		return Metatron.CURRENT;
	}

	@Override
	public void terminate() {
		this.dispose();

	}

	@Override
	public boolean handle(Message m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean running() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addEventListener(String type, EventListener listener, boolean useCapture) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEventListener(String type, EventListener listener, boolean useCapture) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void broadcast() {
		// TODO Auto-generated method stub

	}

	@Override
	public void broadcast(Object at) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}