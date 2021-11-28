package prime._PRIME.UI._Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.UI.iMonad;
import prime._PRIME.UI._Camera.A_I.aCamera;
import prime._PRIME.UI._Camera.A_I.pCameraAdapter;



public class pCamera extends aCamera implements iMonad {

	// public pCameraAdapter controller;

	public PerspectiveCamera camera;
	public Viewport viewport;
	public Rect view;
	public float zoom = 1f;
	public Vector3 right = new Vector3(1, 0, 0);

	public pCamera(pCameraAdapter controller) {

		this.controller = controller;
		this.camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.camera = this.camera;
		this.camera.position.set(0f, 0f, 64f);
		this.camera.lookAt(0, 0, 8f);

		this.camera.rotate(new Vector3(1, 0, 0), 90);
		this.camera.rotate(new Vector3(0, 0, 1), -45);
		this.setFOV(1f, 300f);
		this.camera.direction.set(this.camera.direction).crs(camera.up).nor();
		//this.view.set(this.getOrigin().x, this.getOrigin().y, this.camera.viewportWidth, this.camera.viewportHeight);
		this.update();
	}

	public void update() {
		this.camera.update();
		this.right = this.camera.direction.cpy().crs(this.camera.up.cpy()).nor();
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
	}

	public void resize(int width, int height) {
		this.camera.update();
		//this.viewport.update(width, height);
		this.camera.viewportWidth=width;
		this.camera.viewportHeight=height;
		this.view = new Rect(this.camera.position.x, this.camera.position.y, this.camera.viewportWidth,
				this.camera.viewportHeight);
		this.camera.update();
	}

	public Vector3 getSize() {
		return new Vector3(this.camera.viewportWidth, this.camera.viewportHeight, (this.camera.far - this.camera.near));
	}

	public void setSize(Vector3 size) {

		this.camera.viewportWidth = size.x;
		this.camera.viewportHeight = size.y;
		this.camera.fieldOfView = size.z;
	}

	public Vector3 getPosition() {
		// center
		return this.camera.position;
	}

	public void setPosition(Vector3 position) {
		this.camera.position.set(position);
		this.camera.update();
	}

	public Vector3 getOrigin() {
		// bottom-left
		return new Vector3(this.getPosition().x - (this.camera.viewportWidth / 2),
				this.getPosition().y - (this.camera.viewportHeight / 2),
				this.getPosition().z - ((this.camera.far - this.camera.near) / 2));
	}

	public void setOrigin(Vector3 v) {

		Vector3 size = this.getSize().cpy().scl(0.5f);
		this.setPosition(new Vector3(v.x - size.x, v.y - size.y, v.z - size.z));
	}

	public Vector3 getDirection() {
		return this.camera.direction;
	}

	public Vector3 getUp() {
		return this.camera.up;
	}

	public void setDirection(Vector3 dir) {
		this.camera.direction.set(dir);
		this.camera.update(true);
	}

	public float getZoom() {
		return this.camera.fieldOfView;
	}

	public void setFOV(float near, float far) {
		this.camera.near = near;
		this.camera.far = far;
		this.camera.update(true);
	}

	public void setFOV(float fov) {
		this.camera.fieldOfView = fov;
		this.camera.update();
	}

	public Matrix4 getProjection() {
		return this.camera.combined;
	}

	public Vector3 project(Vector3 v) {
		return this.camera.project(v);
	}

	public Vector3 unproject(Vector3 v) {
		return this.camera.unproject(v);
	}

	@Override
	public Transform getTransform() {
		return super.getTransform();
	}

	@Override
	public void transformUpdated() {
		super.transformUpdated();

	}

	public Camera getBaseCamera() {
		return this.camera;
	}

	@Override
	public String toLog() {
		// TODO Auto-generated method stub
		return null;
	}



}
