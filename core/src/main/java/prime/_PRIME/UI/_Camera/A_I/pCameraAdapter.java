package prime._PRIME.UI._Camera.A_I;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM.iSpace;
import prime._PRIME.UI.iMonad;
import prime._PRIME.UI._Camera.pCamera;



public abstract class pCameraAdapter extends aCameraController implements iMonad {
	// addon extension to OrthoCamera?

	public iMonad root;
	public iSpace viewedSpace;

	public pCamera perspective;

	public boolean moveLock = false;
	public boolean zoomLock = false;

	// used for dragging
	protected Vector3 curr = new Vector3();
	protected Vector3 last = new Vector3(-1, -1, -1);
	protected Vector3 delta = new Vector3();

	public pCameraAdapter(iMonad root) {
		super(root, root.getName() + ":NewPerspectiveController");

	}

	public pCameraAdapter(iMonad root, String name) {

		super(root, name + ":NewPerspective");
	}

	@Override
	public void init() {

		this.perspective = new pCamera(this);
		this.Camera = this.perspective;
		
		super.init();
		// 4x3mul quat direction
		// this.update();
	}

	public void update() {
		if (this.exists && isOn) {
			this.updateInput();
			this.updateMouseTranslation();
			this.perspective.update();
		}

	}

	public void resize(int width, int height) {
		if(this.perspective!=null)
		this.perspective.resize(width, height);
	}

	public Vector3 getCameraPosition() {
		return this.perspective.getPosition();
	}

	public void setCameraPosition(Vector3 position) {
		// center
		this.perspective.setPosition(position);
	}

	public Vector3 getCameraSize() {
		return this.perspective.getSize();
	}

	public void setCameraSize(Vector3 size) {
		this.perspective.setSize(size);
	}

	public void setViewOrigin(Vector3 point) {
		this.perspective.setOrigin(point);
	}

	public Vector3 getCameraDirection() {
		return this.perspective.getDirection();
	}

	public void setCameraDirection(Vector3 dir) {
		this.perspective.setDirection(dir);
	}

	public Vector3 getCameraUp() {
		return this.perspective.getUp();
	}

	public void lookAt(Vector3 point) {
		this.perspective.camera.lookAt(point);
		this.update(0);
	}

	public void rotate(Vector3 axis, float angle) {
		this.perspective.camera.rotate(axis, angle);
		this.update(0);
	}
	
	@Override
	public Camera getPerspective()
	{
		return this.perspective.camera;
	}

	public Vector3 getViewOrigin() {
		// bottom-left
		return this.perspective.getOrigin();
	}

	public void moveCameraTo() {

	}

	public void moveCameraBy() {

	}

	protected void moveCamera() {

	}

	public void updateInput() {

	}

	protected void updateMouseTranslation() {

	}

	public Matrix4 getProjection() {
		return this.perspective.getProjection();
	}

	public Vector3 project(Vector3 v) {
		return this.perspective.project(v);
	}

	public Vector3 unproject(Vector3 v) {
		return this.perspective.unproject(v);
	}

	public Camera getBaseCamera() {
		return this.perspective.camera;
	}

	public Vector3 getCameraOrigin() {
		return this.getViewOrigin();
	}

	public Rect getViewRect() {
		return this.perspective.view;
	}

	//////
	// <MOVEMENT>

	// PANNING

	// ROTATION

	@Override
	public Transform getTransform() {
		return super.getTransform();
	}

	@Override
	public void transformUpdated() {
		super.transformUpdated();

	}



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
		log += this.perspective.camera.direction + "  |  " + this.perspective.camera.up;
		log += "\n";
		log += "CamRect: ";
		log += "\n";
		log += this.getViewRect().toLog();
		log += "\n";

		return log;
	}

}
