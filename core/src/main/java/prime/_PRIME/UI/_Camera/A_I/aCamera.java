package prime._PRIME.UI._Camera.A_I;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.UI.iMonad;

public abstract class aCamera implements iMonad {

	public aCameraController controller;

	public Camera camera;
	public Viewport viewport;

	public Rect view;
	public float zoom = 1f;
	public Vector3 right = new Vector3(1, 0, 0);

	public aCamera() {

	}

	public aCamera(aCameraController controller) {
		this.controller = controller;

	}

	public void update() {
		// Override

		this.camera.update();
		this.right = this.camera.direction.cpy().crs(this.camera.up.cpy()).nor();
		this.view = new Rect(this.getOrigin().x, this.getOrigin().y, this.getSize().x, this.getSize().y);
	}

	public void resize(int width, int height) {
		Log("<<<<<<<<<<<<<<<<<<");
		this.viewport.update(width, height);
		this.viewport.setScreenWidth(width);
		this.viewport.setScreenHeight(width);
		this.camera.viewportWidth = this.viewport.getScreenWidth();
		this.camera.viewportHeight = this.viewport.getScreenHeight();
		this.update();

	}

	// WorldPosition

	public Vector3 getSize() {
		return new Vector3((int) this.camera.viewportWidth, (int) this.camera.viewportHeight, 1);
	}

	// CENTER
	public Vector3 getPosition() {
		return this.camera.position;

	}

	public Vector3 getOfficialPosition() {
		return this.camera.position;
	}

	public Vector3 getOrigin() {
		Vector3 size = this.getSize();
		Vector3 pos = this.getPosition();
		return new Vector3(pos.x - (size.x / 2), pos.y - (size.y / 2), 0);
		// return new Vector3(this.getPosition().x-(this.camera.viewportWidth/2),
		// this.getPosition().y-(this.camera.viewportHeight/2),0);

	}

	// CENTER
	public void setPosition(Vector2 newPos) {
		this.camera.position.set(new Vector3(newPos.x, newPos.y, 0));
	}

	public void setPosition(float x, float y) {
		this.camera.position.set(new Vector3(x, y, 0));
	}

	public void setPosition(Vector3 newPos) {
		this.camera.position.set(newPos);
	}

	public void setPosition(float x, float y, float z) {
		this.camera.position.set(new Vector3(x, y, z));
	}

	public void setPosition(float x, float y, float z, float w) {
		Vector3 v = new Vector3(x, y, z);
		this.camera.rotate(v, w * MathUtils.degRad);
		this.camera.position.set(v);
	}

	public void setPosition(Quaternion q) {
		Vector3 v = new Vector3(q.x, q.y, q.z);
		this.camera.rotate(v, q.w * MathUtils.degRad);
		this.camera.position.set(v);
	}

	public Vector3 getDirection() {
		if (this.camera == null)
			return new Vector3(0, -1f, 0);

		return this.camera.direction;
	}

	//
	public float getZoom() {
		return 1f;
	}

	public Rect getViewRect() {

		if (view == null)
			this.view = new Rect(this.getOrigin().x, this.getOrigin().y, this.camera.viewportWidth,
					this.camera.viewportHeight);
		
		return this.view;

	}

	public Matrix4 getProjection() {
		return this.camera.combined;
	}

	public Camera getBaseCamera() {
		return this.camera;
	}

	public void setController(aCameraController controller) {
		this.controller = controller;
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return this.controller.getTransform();
	}

	@Override
	public void transformUpdated() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toLog() {
		// TODO Auto-generated method stub
		return this.getName();
	}

}
