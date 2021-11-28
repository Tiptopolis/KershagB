package prime._PRIME.UI._Camera.A_I;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Camera;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM.iSpace;
import prime._PRIME.SYS._Events.aEventManager;
import prime._PRIME.UI.iMonad;

public abstract class aCameraController extends InputAdapter implements iMonad {

	public String name = "aCamController";
	public boolean exists = false;
	public boolean isOn = false;

	public iMonad root;
	public iSpace viewedSpace;
	public boolean moveLock = false;
	public boolean mouseFocus = true;
	public boolean zoomLock = false;

	public aCamera Camera;
	public static aCamera DefaultCamera;
	public Vector2 CameraForce = new Vector2();
	public Vector2 CameraSpeed = new Vector2();

	public Vector2 CameraProjectionOffset = new Vector2();
	public CameraCollisionMode CollisionMode = CameraCollisionMode.NONE;

	public Vector2 mouseWorldPrev = new Vector2();
	public Vector2 mouseWorldPos = new Vector2(); // current
	public Vector2 mouseWorldDelta = new Vector2();
	public Vector2 mouseScreenPrev = new Vector2();
	public Vector2 mouseScreenPos = new Vector2();
	public Vector2 mouseScreenDelta = new Vector2();

	// used for dragging
	protected Vector3 curr = new Vector3();
	protected Vector3 last = new Vector3(-1, -1, -1);
	protected Vector3 delta = new Vector3();

	public ArrayList<InputProcessor> subadapters = new ArrayList<InputProcessor>();

	protected Transform transform;
	public boolean logInput = false;

	public aCameraController(iMonad root) {
		this(root, root.getName());
	}

	public aCameraController(iMonad root, String name) {
		this.name = name + ":NewCameraController";
		this.root = root;
		this.exists = true;
		if (root instanceof InputMultiplexer)
			((InputMultiplexer) root).addProcessor(this);
		// this.CameraForce = new Vector2(0, 0);
	}

	public void init() {
		// Override
		this.isOn = true;
	}

	public void update(float deltaTime) {
		if (this.exists()) {
			this.updateInput();
			this.moveCamera();
			this.updateMouseProjection();
			this.Camera.update();
			for (InputProcessor a : this.subadapters) {
				if (a instanceof pCameraAdapter) {
					pCameraAdapter p = (pCameraAdapter) a;
					p.update();
				}
			}
			// if (this.viewedMap != null) {
			// this.CameraProjectionOffset.set(this.Camera.getPosition().x %
			// this.viewedMap.unitSize,
			// this.Camera.getPosition().y % this.viewedMap.unitSize);
			// } else
			this.CameraProjectionOffset.set(0, 0);
		}
	}

	public void resize(int width, int height) {

		if (this.exists()) {
			// Log(" ... " + width + " , " + height);
			this.Camera.resize(width, height);
			// Log(this.Camera.getSize());
			this.update(0);
		}
		// maintain relative position
	}

	//
	//
	//

	public Vector3 getCameraSize() {
		return this.Camera.getSize();
	}

	public Vector3 getCameraPosition() // camera-world center position
	{
		return this.Camera.getPosition();
	}

	public void setCameraPosition(float x, float y) {
		this.Camera.setPosition(x, y);
		this.transformUpdated();
	}

	public Vector3 getCameraOrigin() // camera-world 0,0
	{

		return this.Camera.getOrigin();

	}

	public Vector3 getCameraDirection() {
		return this.Camera.getDirection();
	}

	public void setCameraOrigin(float x, float y) {
		this.Camera.setPosition(x, y);
		this.transformUpdated();
	}

	// center on point
	public void moveCameraTo(Vector2 to) {
		// external movement controls
		this.Camera.setPosition(to);
		this.transformUpdated();
	}

	public void moveCameraBy(Vector2 by) {
		// external movement controls
		Vector3 oldPos = this.Camera.getOfficialPosition();
		Vector2 newPos = new Vector2(oldPos.x + by.x, oldPos.y + by.y);
		this.Camera.setPosition(newPos);
		this.transformUpdated();
	}

	//
	//
	//

	protected void moveCamera() {
		// internal move update
		if (!this.moveLock)
			this.moveCameraBy(this.CameraForce);
	}

	protected void updateInput() {
		// override
	}

	protected void updateMouseProjection() {
		// override
	}

	public Matrix4 getProjection() {
		return this.Camera.getProjection();
	}

	public Camera getBaseCamera() {
		return this.Camera.getBaseCamera();
	}
	
	public Camera getPerspective()
	{
		return this.getBaseCamera();
	}

	public enum CameraCollisionMode {
		NONE, CENTER_ENDGE, // the camera's center cannot pass map edge
		EDGE; // camera edge cannot pass map edge
	}

	public void addAdapter(InputAdapter listener) {
		this.subadapters.add(listener);
	}

	public void removeAdapter(InputAdapter listener) {

		this.subadapters.remove(listener);
		if (this.root instanceof InputMultiplexer) {
			InputMultiplexer M = (InputMultiplexer) this.root;
			M.removeProcessor(listener);
		}
	}

	////
	// INPUT_PROCESSOR
	public boolean keyDown(int keycode) {
		if (!this.isOn)
			return false;

		if (this.logInput)
			Log(this.getName() + aEventManager.StdInputEvents.KeyDown.toString() + "(" + keycode + "|"
					+ Keys.toString(keycode) + ")");

		for (InputProcessor a : this.subadapters) {
			a.keyDown(keycode);
		}
		return false;
	}

	public boolean keyUp(int keycode) {
		if (!this.isOn)
			return false;
		for (InputProcessor a : this.subadapters) {
			a.keyUp(keycode);
		}
		return false;
	}

	public boolean keyTyped(char character) {
		if (!this.isOn)
			return false;
		for (InputProcessor a : this.subadapters) {
			a.keyTyped(character);
		}
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!this.isOn)
			return false;
		for (InputProcessor a : this.subadapters) {
			a.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!this.isOn)
			return false;
		for (InputProcessor a : this.subadapters) {
			a.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (!this.isOn)
			return false;
		for (InputProcessor a : this.subadapters) {
			a.touchDragged(screenX, screenY, pointer);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (!this.isOn)
			return false;
		for (InputProcessor a : this.subadapters) {
			a.mouseMoved(screenX, screenY);
		}
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		if (!this.isOn)
			return false;

		for (InputProcessor a : this.subadapters) {
			a.scrolled(amountX, amountY);
		}
		return false;
	}
	// INPUT_PROCESSOR
	////

	@Override
	public boolean exists() {
		if (this.root == null || !this.root.exists()) {
			this.exists(false);
		}

		return this.exists;
	}

	////
	// iMONAD
	@Override
	public Transform getTransform() {

		if (this.transform == null)
			this.transform = new Transform();
		
		this.transform.SetLocalScale(this.getCameraSize());
		if (this.Camera != null)
			this.transform.SetLocalRotation(VectorUtils.upcast(this.Camera.getDirection()));
		else
			this.transform.SetLocalRotation(new Quaternion());

		this.transform.SetLocalPosition(this.getCameraPosition());
		return this.transform;
	}

	@Override
	public void transformUpdated() {

		if (this.transform == null)
			this.transform = new Transform();

		this.transform.SetLocalPosition(this.getCameraPosition());
		this.transform.SetLocalScale(this.getCameraSize());
		if (this.Camera != null)
			this.transform.SetLocalRotation(VectorUtils.upcast(this.getCameraDirection()));
		else
			this.transform.SetLocalRotation(new Quaternion());
	}

	@Override
	public String toLog() {
		String log = "";
		log += this.name;
		log += "\n";
		log += this.getClass().getSimpleName();
		log += "\n";
		log += "ScrOrigin: ";
		log += this.getCameraOrigin(); /// relative to screen
		log += "\n";
		log += "CamCenter: ";
		log += this.getCameraPosition();
		log += "\n";
		log += "CamSize: ";
		log += this.getCameraSize();
		log += "\n";
		log += "CamDirection & Up: ";
		log += this.Camera.camera.direction + "  |  " + this.Camera.camera.up;
		log += "\n";
		log += "CamRect: ";
		log += "\n";
		log += this.Camera.view.toLog();
		log += "\n";

		return log;

	}

}
