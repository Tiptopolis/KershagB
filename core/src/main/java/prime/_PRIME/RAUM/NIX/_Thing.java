package prime._PRIME.RAUM.NIX;

import java.util.Map.Entry;

import prime._PRIME.N_M._N.Type;
import prime._PRIME.SYS.NIX._Object;
import prime._PRIME.SYS.NIX._Property;



public class _Thing extends _Object {

	// a "Thing" is meant to be a transitional step between
	// multitudinous type & property defs & a singular entity or component
	// un-fulfilled components are marked with <?>:<*Type>
	// upon instantiation of a 'Thing', one must ensure that all <?> fields are
	// filled with either their defaults or a pre-registered listing
	//
	// basically, represents a template default
	//
	// _Archytype + _Dexes = thing factory

	public _Archetype Of;
	public Type Kind;
	//public Flags Flags;
	//public Tags Tags;
	public _Property ID;

	public Node that;

	public _Thing(String name) {
		super(name);
		this.Of = new _Archetype(name);
		//this.Flags = new Flags("Flags");
		//this.Tags = new Tags("Tags");
		//this.addProperty(this.Flags);
		//this.addProperty(this.Tags);
	}

	public _Thing(String name, _Archetype base) {
		super(name);
		this.Of = base;

		// this.addProperty(new aProperty("Of",this.Of)); //shift to Tags

		//this.Flags = new Flags("Flags");
		//this.Tags = new Tags("Tags");
		//this.addProperty(this.Flags);
		//this.addProperty(this.Tags);
		// base.variant(new Type(false, name, base.Kind));
		for (_Property P : base.required()) {
			this.addProperty(P.cpy());
		}

	}

	public boolean has(String prop) {

		for (Node N : this.Properties) {
			if (N.toString().contains(prop) || N.Name().equals(prop) || N.Label.equals(prop)) {
				that = N;
				return true;
			}
			if (N.isOf(prop)) {
				that = N;
				return true;
			}
		}
		return false;
	}

	public boolean isOf(Node other) {
		return this.isOf(other.Node);
	}

	public boolean isOf(Type type) {

		if (this.Type().toString().equals(type.toString()))
			return true;
		if (this.Type().toString().contains(type.Node.toString()))
			return true;
		if (this.toString().contains(type.toString()))
			return true;
		if (this.Of != null) {
			if (this.Of.isOf(type))
				return true;
			if (this.Of.Kind.isOf(type))
				return true;
			if (this.Of.Kind.isOf(type))
				return true;
		}

		return false;

	}

	public boolean isOf(String n) {

		if (this.Of.isOf(n))
			return true;
		if (this.get().toString().contains(n))
			return true;
		return false;
	}

	@Override
	public _Thing clone(String newName, boolean strict) {
		_Thing copy = new _Thing(newName);

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

	@Override
	public _Thing cpy() {
		_Thing copy = new _Thing(this.Name());
		copy.Properties.clear();
		for (Node N : this.Properties) {
			if (N instanceof _Property)
				copy.addProperty((_Property) N.cpy());
		}

		//copy.Tags = this.Tags.cpy();
		//copy.Flags = this.Flags.cpy();

		for (Entry<String, Node> E : this.Connections.entries()) {
			copy.connect(E.getKey(), E.getValue());
		}

		return copy;
	}

	@Override
	public boolean equals(Object other) {
		// return super.equals(other);

		if (this.toString().equals(other.toString()))
			return true;
		else
			return false;
	}

	public void destroy() {
		this.exists = false;
		// this.Components.clear();
		for (Node N : this.Properties) {
			// N.Components.clear();
			N.Connections.clear();
			N.Properties.clear();
			N.Data = null;
		}
		this.Properties.clear();
		this.Connections.clear();
	}

	@Override
	public String toString() {
		return this.Name() + "_" + this.Node + " @" + this.Class().getSimpleName() + "[" + this.Type() + "]";
		// REFERENCE NODE INDEX SYMBOL
	}

	@Override
	public String toLog() {
		String log = "";
		String id = "";
		if (this.ID != null)
			id = " in " + this.ID.Data.toString();
		log += this.toString() + id + "\n";
		// log += this.Of.Kind + "_:_" + this.Of.Base + "\n";
		log += this.Of + "\n";
		log += "\n";
		log += "Properties: " + this.properties();
		log += "\n";
		for (Node N : this.Properties) {
			log += ("*" + N + "\n");
		}

		return log;
	}

}
