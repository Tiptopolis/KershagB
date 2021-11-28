package prime._PRIME.UI._Camera;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._PRIME.DefaultResources.*;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.UI._Camera.A_I.aCameraController;
import prime._PRIME.UI._Camera.A_I.pCameraAdapter;


public class xFpsAdapter  extends pCameraAdapter{

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

	public xFpsAdapter(aCameraController primary) {
		super(primary.root, ": FPS_Controller");
		primary.subadapters.add(this);
		this.primary = primary;
	}

	@Override
	public void init() {
		super.init();
		this.setCameraPosition(this.primary.Camera.getOfficialPosition().add(0, 0, 16));
		this.zoomLock = true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for (Entry e : CommandMap.entrySet()) {
			if (Gdx.input.isKeyPressed((int) e.getValue())) {
				Log("FPS_CAM-: " + e.getKey());
				if (this.isMoving)
					Log("isMoving");
			}
		}
		Vector3 pos = this.getCameraPosition();
		
		this.primary.setCameraPosition(pos.x, pos.y);
		

		
	}

	@Override
	public void updateInput() {

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
			
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT))
		{
	
			
			Vector3 angle = VectorUtils.dir(new Vector3(MouseX, Height - MouseY, 32), new Vector3(Width / 2, Height / 2, 32));			
			this.setCameraDirection(angle.cpy());
			this.perspective.getUp().set(0,0,1);
			this.getBaseCamera().up.set(VectorUtils.up);//-<BAD NEWS BEARS
		}

		if (CmdStateMap.get(CommandMap.get("FORWARD"))) {
			Log("->");
			tmp.set(this.perspective.getDirection()).nor().scl(deltaTime * realVelocity);
			this.perspective.setPosition(this.perspective.getPosition().add(tmp));
		}

		if (CmdStateMap.get(CommandMap.get("REVERSE"))) {
			tmp.set(this.perspective.getDirection()).nor().scl(-deltaTime * realVelocity);
			this.perspective.setPosition(this.perspective.getPosition().add(tmp));
		}

		if (CmdStateMap.get(CommandMap.get("LEFT"))) {
			tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor().scl(-deltaTime * realVelocity);
			this.perspective.setPosition(this.perspective.getPosition().add(tmp));
		}

		if (CmdStateMap.get(CommandMap.get("RIGHT"))) {
			tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor().scl(deltaTime * realVelocity);
			this.perspective.setPosition(this.perspective.getPosition().add(tmp));
		}

		if (CmdStateMap.get(CommandMap.get("UP"))) {
			tmp.set(this.perspective.getUp()).scl(deltaTime * realVelocity).nor();
			this.perspective.setPosition(this.perspective.getPosition().add(tmp));
		}
		if (CmdStateMap.get(CommandMap.get("DOWN"))) {
			tmp.set(this.perspective.getUp()).nor().scl(-deltaTime * realVelocity);
			this.perspective.setPosition(this.perspective.getPosition().add(tmp));
		}

		if (CmdStateMap.get(CommandMap.get("LOOK_UP"))) {
			float theta = realVelocity * deltaTime;
			this.perspective.camera.rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()), -theta);
			this.perspective.getUp().rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()), -theta);
	
		}

		if (CmdStateMap.get(CommandMap.get("LOOK_DOWN"))) {
			float theta = deltaTime * realVelocity;
			this.perspective.camera.rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()), theta);
			this.perspective.getUp().rotate(this.perspective.getUp().cpy().crs(this.perspective.getDirection()), theta);

		}

		// Yaw
		if (CmdStateMap.get(CommandMap.get("LOOK_LEFT"))) {
			float theta = deltaTime * realVelocity;
			this.perspective.camera.rotate(this.perspective.getUp(), theta);
			this.perspective.getUp().rotate(this.perspective.getUp(), theta);

		}

		if (CmdStateMap.get(CommandMap.get("LOOK_RIGHT"))) {
			float theta = deltaTime * realVelocity;
			this.perspective.camera.rotate(this.perspective.getUp(), -theta);
			this.perspective.getUp().rotate(this.perspective.getUp(), -theta);
		}

		// Roll
		if (CmdStateMap.get(CommandMap.get("ROLL_RIGHT"))) {
			float theta = deltaTime * realVelocity;
			this.perspective.camera.rotate(this.perspective.getDirection(), theta);
			this.perspective.getUp().rotate(this.perspective.getDirection(), theta);

		}

		if (CmdStateMap.get(CommandMap.get("ROLL_LEFT"))) {
			float theta = deltaTime * realVelocity;
			this.perspective.camera.rotate(this.perspective.getDirection(), -theta);
			this.perspective.getUp().rotate(this.perspective.getDirection(), -theta);
		}

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 tmp = new Vector3();

		float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
		float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;
		this.perspective.getDirection().rotate(this.perspective.getUp(), deltaX);
		this.perspective.getUp().rotate(tmp, deltaX);//turn off to limit to ortho/gimble-locked?
		tmp.set(this.perspective.getDirection()).crs(this.perspective.getUp()).nor();//local right
		//tmp.set(this.perspective.getDirection()).crs(VectorUtils.up).nor();//global right? vectors mis-labled
		this.perspective.getDirection().rotate(tmp, deltaY);
		this.perspective.getUp().rotate(tmp, deltaY);
		

		return true;
	}

	public String toLog() {
		String log = "";
		log += "\n";
		log += "Direction: " +this.perspective.getDirection().cpy();
		log += "\n";
		log += "Up: " +this.perspective.getUp().cpy();
		log += "\n";
		log += ("dt: " + VectorUtils.dir(this.perspective.getPosition().cpy(), this.perspective.getDirection().cpy()));
		log += "\n";
		
		return log;
	}
	
}
