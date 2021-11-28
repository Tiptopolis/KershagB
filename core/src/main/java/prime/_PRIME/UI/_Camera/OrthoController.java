package prime._PRIME.UI._Camera;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.UI.iMonad;
import prime._PRIME.UI._Camera.A_I.aCameraController;



public class OrthoController extends aCameraController {

	public boolean mousePanLock = true;
	public boolean dragPanLock = false;
	public boolean wheelDrag = true;
	public boolean rightDrag = false;
	public boolean leftDrag = false;

	// map stuff to here

	public OrthoController(iMonad root) {
		super(root);
	}

	@Override
	public void init() {
		super.init();
		this.Camera = new uCamera(this);
		this.Camera.resize(Width, Height);
		this.setCameraOrigin(0, 0);
		this.CollisionMode = CameraCollisionMode.CENTER_ENDGE;
	}

	@Override
	public Vector3 getCameraOrigin() // camera-world 0,0
	{
		Vector3 size = this.getCameraSize();
		Vector3 pos = this.getCameraPosition();
		return new Vector3(pos.x - (size.x / 2), pos.y - (size.y / 2), 0);
	}

	@Override
	public void setCameraOrigin(float x, float y) {
		Vector3 size = this.getCameraSize();
		this.Camera.setPosition(x + (size.x / 2), y + (size.y / 2));
	}
	
	public Rect getViewRect()
	{
		Vector3 size = this.getCameraSize();
		Vector3 pos = this.getCameraPosition();
		float z = this.Camera.zoom;
		return new Rect(pos.x-((size.x/2)*z),pos.y-((size.y/2)*z),size.x*z,size.y*z);
	}


	
	@Override
	protected void updateInput() {
		if (!this.isOn)
			return;
		this.CameraForce.set(0, 0);
		this.mouseScreenPrev = new Vector2(this.mouseScreenPos);

		if (this.mouseFocus && !this.moveLock) {
			if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				CameraForce.x -= CameraSpeed.x * this.Camera.zoom;
				// Log("*A " + CameraForce.x);

			}
			if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				CameraForce.x += CameraSpeed.x * this.Camera.zoom;
				// Log("*D " + CameraForce.x);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				CameraForce.y -= CameraSpeed.y * this.Camera.zoom;
				// Log("*S " + CameraForce.y);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
				CameraForce.y += CameraSpeed.y * this.Camera.zoom;
				// Log("*W " + CameraForce.y);
			}
			if (this.mousePanLock == false) {
				this.mouseEdgePan();
			}
			this.checkCollision();
		}
	}

	@Override
	protected void updateMouseProjection() {
		// override
	}

	protected void checkCollision() {
		// if camera bigger than map, lock to center

		// Log("" + this.root.CurrentMap.Size);
		// Log("" + this.getCameraPosition());

		if (this.CollisionMode == CameraCollisionMode.CENTER_ENDGE && this.viewedSpace != null) {
			Vector2 camCenter = this.Camera.view.getCenter(); // this.MainCamera.getPosition();
			Vector2 force = this.CameraForce;
			// Log(">>>CameraControllerCollision");

			if (camCenter.x < force.x && force.x < 0) {
				this.CameraForce.x = 0;
			}

			if (camCenter.x > (this.viewedSpace.getSize().x * this.viewedSpace.getUnit().x) - force.x && force.x > 0) {
				this.CameraForce.x = 0;
			}

			if (camCenter.y < force.y && force.y < 0) {
				this.CameraForce.y = 0;
			}

			if (camCenter.y > (this.viewedSpace.getSize().y * this.viewedSpace.getUnit().y) - force.y && force.y > 0) {
				this.CameraForce.y = 0;
			}
		}
	}

	protected void mouseEdgePan() {
		Vector2 center = new Vector2(Width / 2, Height / 2);

		if (MouseScreenPos.y > center.y && (Height - MouseScreenPos.y < 25))
			this.CameraForce.y += 5;

		if (MouseScreenPos.y < center.y && (0 + MouseScreenPos.y < 25))
			this.CameraForce.y -= 5;

		if (MouseScreenPos.x > center.x && (Width - MouseScreenPos.x < 25))
			this.CameraForce.x += 5;

		if (MouseScreenPos.x < center.x && (0 + MouseScreenPos.x < 25))
			this.CameraForce.x -= 5;

		// lerp cameraPos to mouseWorldPos <<--
	}

	protected void updateMouseTranslation() {
		// Vector3 mouseProj = this.MainCamera.camera.unproject(new Vector3(MouseX,
		// MouseY, 0));
		// Vector3 cellProj = new Vector3((int) (mouseProj.x /
		// this.root.defaultUnitSize),(int) (mouseProj.y / this.root.defaultUnitSize),
		// 0);

		this.mouseScreenPos = new Vector2(MouseX, MouseY);
		if (this.viewedSpace != null) {
			Vector3 mouseProj = this.Camera.camera
					.unproject(new Vector3(this.mouseScreenPrev.x, this.mouseScreenPrev.y, 0));
			Vector3 cellProj = new Vector3((int) (mouseProj.x / this.viewedSpace.getUnit().x),
					(int) (mouseProj.y / this.viewedSpace.getUnit().y), 0);
			this.mouseWorldPrev = new Vector2(mouseProj.x, mouseProj.y);

			mouseProj = this.Camera.camera.unproject(new Vector3(this.mouseScreenPos.x, this.mouseScreenPos.y, 0));
			cellProj = new Vector3((int) (mouseProj.x / this.viewedSpace.getUnit().x),
					(int) (mouseProj.y / this.viewedSpace.getUnit().y), 0);
			this.mouseWorldPos = new Vector2(mouseProj.x, mouseProj.y);

			this.mouseWorldDelta = new Vector2(mouseWorldPos.x - mouseWorldPrev.x, mouseWorldPos.y - mouseWorldPrev.y);
		}
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		if (!this.zoomLock) {
			Camera camera = this.Camera.camera;

			Vector3 prev = new Vector3();
			Vector3 d = new Vector3();
			Vector3 mPos = new Vector3();

			camera.unproject(prev.set(MouseX, MouseY, 0));

			this.Camera.zoom += amountY * Camera.zoom * 0.1f;
			this.Camera.update();
			this.update(0);

			camera.unproject(mPos.set(MouseX, MouseY, 0));
			d.set(prev.sub(mPos));
			this.moveCameraBy(new Vector2(d.x, d.y));
			this.Camera.update();
			this.update(0);

			// Log("" +this.name + " : " + this.Camera.zoom + " ||| " + amountX + " ; " +
			// amountY);
			this.updateMouseTranslation();

			return true;
		} else
			return super.scrolled(amountX, amountY);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// Log(""+Gdx.input.isButtonPressed(0));
		// Log(""+Gdx.input.isButtonPressed(Input.Buttons.MIDDLE));

		if (!this.dragPanLock && !this.moveLock) {

			if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE) && this.wheelDrag)
				this.exDrag(x, y);
			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && this.rightDrag)
				this.exDrag(x, y);
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.leftDrag)
				this.exDrag(x, y);			
		}
		return super.touchDragged(x, y, pointer);
	}

	private void exDrag(int x, int y) {
		Camera camera = this.Camera.camera;
		camera.unproject(curr.set(x, y, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			// camera.position.add(delta.x, delta.y, 0);
			this.moveCameraBy(new Vector2(delta.x, delta.y));
			this.Camera.update();
			this.update(0);
		}
		last.set(x, y, 0);
		this.updateMouseTranslation();
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// used for touchDragged
		last.set(-1, -1, -1);
		for (InputProcessor a : this.subadapters) {
			a.touchUp(x, y, pointer, button);
		}

		return super.touchUp(x, y, pointer, button);
	}


}
