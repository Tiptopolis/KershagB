package prime._PRIME.UI._Camera;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._PRIME.DefaultResources.*;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.UI._Camera.A_I.aCameraController;
import prime._PRIME.UI._Camera.A_I.pCameraAdapter;

public class uFpsAdapter extends pCameraAdapter {

	public aCameraController primary;

	public static final int Forward = Keys.W;
	public static final int Reverse = Keys.S;
	public static final int Left = Keys.A;
	public static final int Right = Keys.D;

	public static final int Up = Keys.SPACE;
	public static final int Down = Keys.CONTROL_LEFT;

	public static final int AltUp = Keys.R;
	public static final int AltDown = Keys.F;
	public static final int AltLeft = Keys.Q;
	public static final int AltRight = Keys.E;

	public static final HashMap<String, Integer> CommandMap = new HashMap<String, Integer>();
	public static final HashMap<Integer, Boolean> CmdStateMap = new HashMap<Integer, Boolean>();

	protected boolean isMoving;
	private float velocity = 5;
	private float degreesPerPixel = 0.5f;

	static {
		CommandMap.put("FORWARD", Keys.W);
		CmdStateMap.put(Keys.W, false);
		CommandMap.put("REVERSE", Keys.S);
		CmdStateMap.put(Keys.S, false);
		CommandMap.put("LEFT", Keys.A);
		CmdStateMap.put(Keys.A, false);
		CommandMap.put("RIGHT", Keys.D);
		CmdStateMap.put(Keys.D, false);

		CommandMap.put("UP", Keys.SPACE);
		CmdStateMap.put(Keys.SPACE, false);
		CommandMap.put("DOWN", Keys.CONTROL_LEFT);
		CmdStateMap.put(Keys.CONTROL_LEFT, false);

		CommandMap.put("LOOK_LEFT", Keys.Q);
		CmdStateMap.put(Keys.Q, false);
		CommandMap.put("LOOK_RIGHT", Keys.E);
		CmdStateMap.put(Keys.E, false);
		CommandMap.put("LOOK_UP", Keys.Z);
		CmdStateMap.put(Keys.Z, false);
		CommandMap.put("LOOK_DOWN", Keys.C);
		CmdStateMap.put(Keys.C, false);

		CommandMap.put("ROLL_LEFT", Keys.X);
		CmdStateMap.put(Keys.X, false);
		CommandMap.put("ROLL_RIGHT", Keys.V);
		CmdStateMap.put(Keys.V, false);
	}

	public uFpsAdapter(aCameraController primary) {
		super(primary.root, primary.getName());
		// primary.subadapters.add(this);
		primary.addAdapter(this);
		this.primary = primary;
	}

	@Override
	public void init() {
		super.init();
		// this.setCameraPosition(this.primary.Camera.getOfficialPosition().add(0, 0,
		// 16));
		this.isOn = true;
	}

	@Override
	public Transform getTransform() {
		return this.primary.getTransform();

	}

	public Vector3 getCameraOrigin() {
		return this.primary.getCameraOrigin();
	}

	@Override
	public void update() {
		if (this.exists && isOn) {
			super.update();

			if (this.logInput)
				for (Entry e : CommandMap.entrySet()) {
					if (Gdx.input.isKeyPressed((int) e.getValue())) {
						Log("FPS_CAM-: " + e.getKey());
						if (this.isMoving)
							Log("isMoving");
					}
				}

			Vector3 pos = this.getCameraPosition();
			this.primary.setCameraPosition(pos.x, pos.y);
			this.transformUpdated();
		}
	}

	@Override
	public void transformUpdated() {
		if (this.transform == null)
			this.transform = new Transform();

		this.transform.SetLocalPosition(this.getCameraPosition());
		this.transform.SetLocalScale(this.getCameraSize());
		this.transform.SetLocalRotation(VectorUtils.upcast(this.getCameraDirection()));
		// Log(" -->>>>>>>");
	}

	@Override
	public void updateInput() {

		if (exists && isOn) {

			Vector3 tmp = new Vector3();
			float deltaTime = Gdx.graphics.getDeltaTime();
			float realVelocity = this.velocity;

			this.isMoving = false;
			// update StateMap
			for (Entry<Integer, Boolean> e : CmdStateMap.entrySet()) {
				e.setValue(Gdx.input.isKeyPressed((int) e.getKey()));
				if (e.getValue() == true)
					this.isMoving = true;
			}

			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				realVelocity = velocity * 10;

			if (Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
				Vector3 angle = VectorUtils
						.dir(new Vector3(MouseX, Height - MouseY, 32), new Vector3(Width / 2, Height / 2, 32)).nor();
				this.setCameraDirection(angle.cpy().scl(-1));
				this.setCameraDirection(this.getCameraDirection().add(0, 0, -0.01f));
				this.getBaseCamera().up.set(VectorUtils.up);

			}

			if (CmdStateMap.get(CommandMap.get("FORWARD"))) {
				if (this.logInput)
					Log("->");
				tmp.set(this.perspective.getDirection()).nor().scl(deltaTime * realVelocity);
				this.perspective.setPosition(this.perspective.getPosition().add(tmp));
			}

			if (CmdStateMap.get(CommandMap.get("REVERSE"))) {
				tmp.set(this.perspective.getDirection()).nor().scl(-deltaTime * realVelocity);
				this.perspective.setPosition(this.perspective.getPosition().add(tmp));
			}

			if (CmdStateMap.get(CommandMap.get("LEFT"))) {
				tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor()
						.scl(-deltaTime * realVelocity);
				this.perspective.setPosition(this.perspective.getPosition().add(tmp));
			}

			if (CmdStateMap.get(CommandMap.get("RIGHT"))) {
				tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor()
						.scl(deltaTime * realVelocity);
				this.perspective.setPosition(this.perspective.getPosition().add(tmp));
			}

			if (CmdStateMap.get(CommandMap.get("UP"))) {
				tmp.set(this.perspective.getUp()).nor().scl(deltaTime * realVelocity);
				this.perspective.setPosition(this.perspective.getPosition().add(tmp));
			}
			if (CmdStateMap.get(CommandMap.get("DOWN"))) {
				tmp.set(this.perspective.getUp()).nor().scl(-deltaTime * realVelocity);
				this.perspective.setPosition(this.perspective.getPosition().add(tmp));
			}

			// Pitch
			if (CmdStateMap.get(CommandMap.get("LOOK_UP"))) {
				// float deltaY = deltaTime * realVelocity;
				// tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor();
				// this.perspective.getDirection().rotate(tmp, deltaY);
				float theta = realVelocity * deltaTime;
				this.perspective.camera.rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()),
						-theta);
				// this.perspective.getUp().rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()),
				// -theta);
			}

			if (CmdStateMap.get(CommandMap.get("LOOK_DOWN"))) {
				// float deltaY = deltaTime * realVelocity;
				// tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor();
				// this.perspective.getDirection().rotate(tmp, -deltaY);
				float theta = realVelocity * deltaTime;
				this.perspective.camera.rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()),
						theta);
				// this.perspective.getUp().rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()),
				// theta);
			}

			// Yaw
			if (CmdStateMap.get(CommandMap.get("LOOK_LEFT"))) {
				// float deltaX = deltaTime * realVelocity;
				// this.perspective.getDirection().rotate(this.perspective.getUp(), deltaX);
				// tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor();

				float theta = deltaTime * realVelocity;
				this.perspective.camera.rotate(this.perspective.getUp(), theta);
				// this.perspective.getUp().rotate(this.perspective.getUp(), theta);

			}

			if (CmdStateMap.get(CommandMap.get("LOOK_RIGHT"))) {
				// float deltaX = -deltaTime * realVelocity;
				// this.perspective.getDirection().rotate(this.perspective.getUp(), deltaX);
				// tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor();

				float theta = deltaTime * realVelocity;
				this.perspective.camera.rotate(this.perspective.getUp(), -theta);
				// this.perspective.getUp().rotate(this.perspective.getUp(), -theta);

			}

			// Roll
			if (CmdStateMap.get(CommandMap.get("ROLL_RIGHT"))) {
				// float deltaX = deltaTime * realVelocity;
				// this.perspective.getUp().rotate(this.rollCamera(1, 1), deltaX);
				float theta = deltaTime * realVelocity;
				this.perspective.camera.rotate(this.perspective.getDirection(), theta);
				// this.perspective.getUp().rotate(this.perspective.getDirection(), theta);

			}

			if (CmdStateMap.get(CommandMap.get("ROLL_LEFT"))) {
				// float deltaX = deltaTime * realVelocity;
				// this.perspective.getUp().rotate(this.rollCamera(1, 1), -deltaX);
				float theta = deltaTime * realVelocity;
				this.perspective.camera.rotate(this.perspective.getDirection(), -theta);
				// this.perspective.getUp().rotate(this.perspective.getDirection(), -theta);
			}
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (!exists || !isOn)
			return false;

		Vector3 tmp = new Vector3();
		Vector3 tmp2 = new Vector3();
		Vector3 tmp3 = new Vector3();
		Camera camera = this.getBaseCamera();

		float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
		float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;

		Vector3 dir = camera.direction.cpy();
		Vector3 up = camera.up.cpy();

		//Log();
		// Log("> "+dir); //if up.y<dir.y
		// Log("^ "+up);
		// if(up.z<0.5f)
		// deltaY*=-2f;

		camera.direction.rotate(camera.up.cpy(), deltaX);

		// { vertical limiter
		Vector3 oldPitchAxis = tmp.set(camera.direction).crs(camera.up).nor();
		Vector3 newDirection = tmp2.set(camera.direction).rotate(tmp, deltaY);
		Vector3 newPitchAxis = tmp3.set(tmp2).crs(camera.up);
		if (!newPitchAxis.hasOppositeDirection(oldPitchAxis))
			camera.direction.set(newDirection);

		// {vertical de-limiter lol
		// this.perspective.camera.rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()),
		// -deltaY);

		return true;
	}

}
