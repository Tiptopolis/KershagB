package prime._PRIME.SYS.NIX;

import static prime._PRIME.uAppUtils.*;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Null;

import prime._PRIME.N_M._N;
import prime._PRIME.N_M._N.*;
import prime._PRIME.SYS._VTS;
import prime._PRIME.SYS._Events.Prototype.Listener;

public class _Property<T> extends Node<T> {

	public static Type Property = new Type("Property", _Property.class);
	public static Type PropertyType = new Type("Property", _Property.class, Type.class);

	public Node header;
	public Type Node;

	protected ArrayList<Listener> listeners;

	public _Property() {
		this.Label = "aProperty";
		this.Of = Property;
		this.Data = (T) PropertyType;
		this.Node = Property;
	}

	public _Property(String name, T data) {
		this.Label = name;
		this.Of = new Type(data.getClass().getSimpleName(), data.getClass());
		this.Data = data;
		this.Node = Property;
	}

	public _Property(String label, Type data) {
		this.Label = label;
		this.Of = _VTS.getA(data.Type.Reference);
		this.Data = (T) data;
		// this.Node=data;
		this.Node = Property;
	}

	public _Property(_Property<T> p) {
		this.Label = p.Label;
		this.Of = p.Of;
		this.Data = p.Data;
		this.Node = Property;
	}

	@Override
	public <O extends Object, aProperty> aProperty set(O in) {
		this.Data = (T) in;

		this.msg("Listener", "Update");
		// this.message(this, "Update", true, this.get());
		return (aProperty) this;
	}

	@Override
	public void msg(String to, String message) {
		for (Entry<String, _N.Node> E : this.Connections.entries()) {
			String K = E.getKey();
			_N.Node V = E.getValue();
			// Log(K + " " + V.Name());
			if (K.contains(to)) {
				V.message(this, to + message, false, this.get());
				// Log(" =>>" + K + " :: " + V);
			}

		}
	}

	@Override
	public boolean message(_N source, String message, boolean callback, @Null Object data) {
		String c = "";
		String d = "";
		if (data != null) {

			c = "<" + data.getClass().getSimpleName() + ">:[";
			d = data.toString() + "]";

			if (this.isOf(data))
				this.set(data);
		}

		// Log(" =<<" + this.Name() + " " + data);

		if (callback) {
			source.message(this, "CALLBACK_FOR:" + message, false, this.get());
		}

		return true;
	}

	public _Property cpy() {
		return new _Property(this);
	}

	public String headString() {
		String aCap = "";
		String bCap = "";
		if (this.Data instanceof Type) {
			aCap = "(";
			bCap = ")";
		}
		return this.Name() + ":" + aCap + this.get() + bCap;
	}

	public String tailString() {
		return this.Class().getSimpleName() + this.Node + ":" + this.Type();
	}

	@Override
	public String toString() {

		return "[" + this.headString() + "]" + "[" + this.tailString() + "]";
	}

	@Override
	public String toLog() {
		return this.toString();
	}

}
