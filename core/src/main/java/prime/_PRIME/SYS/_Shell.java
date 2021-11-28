package prime._PRIME.SYS;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._PRIME.N_M._N.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.badlogic.gdx.files.FileHandle;
import com.google.common.collect.HashMultimap;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.SYS.NIX._Object;
import prime._PRIME.SYS._Events.aEventManager;
import prime._PRIME.SYS._Events.Prototype.Message;


public class _Shell extends _Object implements iSystem {

	// DIAL M FOR METATRON lolol

	public aEventManager EventManager;

	public static _VTS VTS;
	public static _VFS VFS;
	public static _ECS ECS;

	public ArrayList<_Shell> Other = new ArrayList<_Shell>();
	public ArrayList<iSystem> Systems = new ArrayList<iSystem>();
	protected HashMap<UpdateType, iSystem> Running = new HashMap<UpdateType, iSystem>();

	public static Type shell = new Type("Shell");
	public static Type type = new Type("Shell", _Shell.class, Type.Type);
	// VFS, VTS, & VFS all doubly link their entries
	// protected HashMap<_VFS, Lookup> Files; // everything in here will be
	// accessible as a File
	// protected HashMap<_ECS, Lookup> Entities; // everything in here will be
	// accessible as an Entity
	// protected HashMap<_VTS, Lookup> Types; // everything in here will be
	// accessible as a Type/Class

	public enum UpdateType {
		Fixed, Dynamic, Reactive;
	}

	public _Shell() {
		super();
		this.Name = new Name("Shell");
		this.Node = shell;
		this.Class = shell;
		this.Of = type;
		this.Environment = this;
		this.Other.add(this);
		//this.Connections = new HashMap<String, Node>();
		this.Connections=HashMultimap.create();

	}

	public _Shell(String name) {
		this.Name = new Name(name.toUpperCase());
		this.Node = shell;
		this.Class = shell;
		this.Of = type;
		this.Environment = this;
		this.Other.add(this);
		//this.Connections = new HashMap<String, Node>();
		this.Connections=HashMultimap.create();
	}

	public _Shell(String name, iSystem env) {
		this(name);
		this.Node = shell;
		this.Class = shell;
		this.Of = type;
		this.Environment = env;
		//this.Connections = new HashMap<String, Node>();
		this.Connections=HashMultimap.create();
		// if(env.)
	}

	public static _Shell DomainShell() {
		_Shell n = new _Shell("Domain");
		return n;
	}

	public static _Shell DomainShell(String name) {
		_Shell n = new _Shell("Domain");
		n.Name = new Name(name);
		n.get("Name").set(name);
		return n;
	}

	@Override
	public void update(int deltaTime) {
		// Log(this.Name + ":I" + deltaTime);
		// check for pending changes & then do them

		for (Entry<UpdateType, iSystem> S : this.Running.entrySet()) {
			if (S.getKey().equals(UpdateType.Fixed))
				S.getValue().update(deltaTime);
		}
	}

	@Override
	public void update(float deltaTime) {
		// Log(this.Name + ":F" + deltaTime);
		for (Entry<UpdateType, iSystem> S : this.Running.entrySet()) {
			if (S.getKey().equals(UpdateType.Dynamic))
				S.getValue().update(deltaTime);
		}
	}

	public void pause(iSystem s) {
		this.Running.remove(s);
	}

	public void resume(UpdateType t, iSystem s) {
		this.Running.put(t, s);
	}

	public void launch(iApplet app) {
		this.Systems.add(app);

	}

	public void terminate() {
		this.Connections.clear();

	}

	////////////////
	///////////////

	@Override
	public boolean handle(Message m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Transform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transformUpdated() {
		// TODO Auto-generated method stub

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
	public void broadcast(java.lang.Object at) {
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

	@Override
	public String toLog() {
		String log = "";
		log += this.toString() + "\n";
		log += "Properties: " + this.properties().size;
		log += "\n";
		for (Node N : this.Properties) {
			log += ("*" + N + "\n");
		}

		log += "#Others: " + this.Other.size() + "\n";
		for (int i = 0; i < this.Other.size(); i++) {
			log += i + ": [" + this.Other.get(i) + "]\n";
		}
		log += "#Running " + this.Running.size() + "\n";
		for (int i = 0; i < this.Running.size(); i++) {
			UpdateType u = (UpdateType) this.Running.keySet().toArray()[i];
			iSystem s = (iSystem) this.Running.values().toArray()[i];
			log += "{" + u.toString() + " : " + s.getClass().getSimpleName() + "}";
		}

		return log;
	}

}
