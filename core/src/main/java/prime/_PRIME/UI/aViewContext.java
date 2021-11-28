package prime._PRIME.UI;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import prime._METATRON.Metatron;
import prime._PRIME.C_O.Prototype.TimeKey;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM.iSpace;
import prime._PRIME.SYS._Events.aEventManager;
import prime._PRIME.UI._GFX.FrameBufferManager;
import prime._PRIME.UI._Scene._Stage;
import prime._PRIME.UI._GFX.DrawBuffer;


public abstract class aViewContext extends aEventManager implements iMonad, iSpace {

	// Top-1Level Modal Window interface, consumes gdx window
	// transmits input from InputMultiplexer to stage & camera controller

	public FrameBufferManager BufferManager;
	public Environment Environment;
	public _Stage Stage;
	public TimeKey Time;
	public Vector3 Unit; // cell size
	// public aCamera[] staticCameras;

	// MenuWidget
	public Camera View;
	public DrawBuffer DrawBuffer;

	// public OroborosList<iDrawable> toDraw;
	// protected static final Vector3 Unit = new Vector3(1,1,1);

	public aViewContext() {
		super();
		Log(".aViewContext()");
		this.BufferManager = new FrameBufferManager();
		this.Time = new TimeKey();
		this.isProxy = true;
		this.Environment = new Environment();
	}

	public aViewContext(aEventManager manager) {
		super(manager);
		this.BufferManager = new FrameBufferManager();
		this.Time = new TimeKey();
		this.isProxy = true;
		this.Environment = new Environment();
	}

	public void create() {

		this.Stage = new _Stage();
		Metatron.TheMetatron.addProcessor(this.Stage);
		this.View = this.Stage.StageCamera;
	}

	public void update() {
		this.Time.a = Metatron.TheMetatron.DeltaTime.I.floatValue();
		this.Time.i = Metatron.TheMetatron.iTime.I.floatValue();
		this.update(this.Time.a.floatValue());
		this.render();
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		this.BufferManager.update(deltaTime);
		this.Stage.act(deltaTime);
	}

	public void render() {
		// stage.render
		// Log(""+this.getClass().getSimpleName()+".draw()");

		// this.Stage.draw();
	}

	public void resize(int width, int height) {
		// Log(this.getName() +":" + this.getClass().getSimpleName()+".resize()");
		this.Stage.resize(width, height);
	}

	public void resize(int width, int height, int depth) {
		this.Stage.resize(width, height, depth);
	}

	public void resize(float width, float height) {
		this.Stage.resize(width, height);
	}

	public void resize(float width, float height, float depth) {
		this.Stage.resize(width, height, depth);
	}

	public void enter() {
		Metatron.TheMetatron.addProcessor(this);
		this.Time = new TimeKey();
		Log("{=>" + this.getClass().getSimpleName());

	}

	public void exit() {
		Metatron.TheMetatron.removeProcessor(this);
		Log("<=}" + this.getClass().getSimpleName());
		this.exists(false);
	}

	@Override
	public Transform getTransform() {
		return super.getTransform();
	}

	@Override
	public void transformUpdated() {
		super.transformUpdated();
	}

	@Override
	public String toLog() {
		return super.toLog();
	}

	@Override
	public Vector3 getSize() {
		return new Vector3();
	}

	@Override
	public Vector3 getUnit() {
		return this.Unit;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
