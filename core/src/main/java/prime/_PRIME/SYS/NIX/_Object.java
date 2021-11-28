package prime._PRIME.SYS.NIX;

import static prime._PRIME.N_M._N.*;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.SYS.iSystem;
import prime._PRIME.UI.iMonad;



public class _Object extends _Struct implements iMonad {

	// object wrapper
	// constructed from one or more entities & their constituent components &
	// properties

	public static Type Object = new Type("Object", _Object.class);
	public static Type _Class = new Type("Class", Class.class);
	public static Type _C = new Type("?", Object.class);

	public iSystem Environment;
	public boolean exists = true;
	public Name Name;
	public Type Class = _C;

	public _Object() {
		super();
		this.Name = new Name("[?]");
		this.Node = Object;
		this.Of = (Type) this.Node;
		// this.addProperty(new aProperty("Object", Type.Type));
		this.addProperty(new _Property("Name", this.Name));
		this.addProperty(new _Property("Exists", true));
	}

	public _Object(String name) {
		super();
		this.Name = new Name(name);
		this.Node = Object;
		this.Of = (Type) this.Node;
		// this.Properties.add(new aProperty("Object", Type.Type));
		this.Properties.add(new _Property("Name", this.Name));
		this.Properties.add(new _Property("Exists", true));
	}

	public _Object(Name name) {
		this(name.Reference);
	}

	@Override
	public String Name() {
		return this.Name.Value;
	}

	@Override
	public Type Type() {
		return this.Of;
	}

	@Override
	public Class Class() {
		return this.Of.Type();
	}

	@Override
	public Object get() {
		return this;
	}

	public boolean exists() {
		return this.exists;
	}

	public void exists(boolean ex) {
		this.exists = ex;
		this.exists();
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

	public _Object clone(String newName, boolean strict) {
		_Object copy = new _Object(newName);

		for (Node N : this.Properties) {
			if (N instanceof _Property)
				if (strict)
					copy.addProperty((_Property) N);
				else
					copy.addProperty((_Property) N.cpy());
		}

		for (Entry<String, Node> E : this.Connections.entries()) {
			if (strict)
				copy.connect(E.getKey(), E.getValue());
			else
				copy.connect(E.getKey(), E.getValue().cpy());
		}

		return copy;
	}

	public _Object cpy() {
		_Object copy = new _Object(this.Name());

		for (Node N : this.Properties) {
			if (N instanceof _Property)
				copy.addProperty((_Property) N.cpy());
		}

		for (Entry<String, Node> E : this.Connections.entries()) {
			copy.connect(E.getKey(), E.getValue());
		}

		return copy;
	}

	@Override
	public String toLog() {
		String log = "";
		log += this.toString() + "\n";
		log += ":{Properties}: " + this.properties().size;
		log += "\n";
		for (Node N : this.Properties) {
			log += ("*" + N + "\n");
		}
		
		if(!this.Connections.isEmpty())
		{
			log += ":{Connections}:\n";
			for (Entry<String, Node> E : this.Connections.entries()) {
				log +="  " + E + "\n";
			}
		}

		return log;
	}
}
