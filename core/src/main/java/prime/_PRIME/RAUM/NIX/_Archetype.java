package prime._PRIME.RAUM.NIX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M._N.Type;
import prime._PRIME.SYS._VTS;
import prime._PRIME.SYS.NIX._Object;
import prime._PRIME.SYS.NIX._Property;


public class _Archetype extends Type {

	public Type Base;
	public Type Kind;

	public ArrayList<Type> Variants = new ArrayList<Type>();
	public HashMap<String, _Property> Required = new HashMap<String, _Property>(0);

	public _Archetype() {
		super(false, "Thing", _Object._C);
		this.Base = _VTS.getA("Type>:<Class");
		this.Kind = new Type(false, "Thing", _VTS.getA("Type>:<Object"));

	}

	public _Archetype(String name) {
		super(false, name, _Object._C);
		this.Base = _VTS.getA("Type>:<Class");
		this.Kind = new Type(false, name, _VTS.getA("Type>:<Object"));
	}

	public _Archetype(String name, Type base) {
		super(false, name, base);
		this.Base = base;
		this.Kind = new Type(false, name, base);

	}

	public _Archetype(String name, _Archetype ext) {
		super(false, name, ext.Kind);
		this.Base = ext.Base;
		this.Kind = new Type(false, name, ext.Kind);
		ext.Variants.add(this);
	}

	@Override
	public Type get(String n) {
		for (Type T : this.Variants) {
			if (T.equals(n))
				return T;
		}

		return this.Base;
	}

	public void variant(Type t) {
		if (!this.Variants.contains(t))
			this.Variants.add(t);
	}

	public boolean includes(Type t) {
		if (this.Variants.contains(t))
			return true;
		for (Type T : this.Variants) {
			if (T.isOf(t))
				return true;
			if (T instanceof _Archetype) {
				if ((((_Archetype) T).includes(t) || t.isOf(((_Archetype) T).Base)))
					return true;
			}

		}

		return t.isOf(this);
	}

	public boolean includes(String s) {
		for (Type T : this.Variants) {
			if (T.equals(s) || T.isOf(s))
				return true;
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
		if (this.Base.toString().equals(type.toString()) || this.Base.equals(type))
			return true;
		if (this.Kind.toString().equals(type.toString()) || this.Kind.equals(type))
			return true;

		return false;

	}

	public boolean isOf(String n) {

		if (this.get().toString().contains(n))
			return true;
		if (this.Base.toString().contains(n))
			return true;
		if (this.Kind.toString().contains(n))
			return true;

		return false;
	}

	public Array<_Property> required() {
		Array<_Property> a = new Array<_Property>(true, 0, _Property.class);
		for (_Property p : this.Required.values()) {
			a.add(p);
		}
		return a;
	}

	public void require(_Property p)
	{
		this.Required.put(p.Label, p);
	}
	
	////

	public _Thing getA(int variant) {
		Type k = this.Variants.get(variant);

		return new _Thing(k.Reference, this);

	}

	public _Thing getA(String variant) {
		for (Type k : this.Variants) {
			if (k.Reference.equals(variant))
				return new _Thing(k.Reference, this);
		}
		return this.getA(0);
	}
	
	@Override
	public boolean equals(Object other)
	{
		return super.equals(other);
	}

	@Override
	public String toString() {
		return "[" + this.Name() + ":" + this.get().getSimpleName() + "]|[" + this.Class().Name() + ":"
				+ this.Type().getSimpleName() + "]";
	}

	@Override
	public String toLog() {
		String log = this.toString();
		log += "\n" + this.Kind + " | " + this.Base + "\n";
		if (!this.Variants.isEmpty()) {
			for (Type T : this.Variants) {
				int i = this.Variants.indexOf(T);
				log += i + "=" + T + "\n";
			}
		}
		log += "\n";
		if (!this.Required.isEmpty()) {
			for (Entry<String, _Property> E : this.Required.entrySet()) {

				log += " *" + E.getValue().headString() + "\n";
			}
		}
		return log;
	}
}
