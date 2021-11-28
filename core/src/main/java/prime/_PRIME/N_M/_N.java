package prime._PRIME.N_M;

import static prime._PRIME.uAppUtils.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.google.common.collect.HashMultimap;

import prime._PRIME.SYS._Shell;
import prime._PRIME.SYS.NIX._Object;

public interface _N<N> {
	// name/number/node

	public static String A_N = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	public default Name Name(String name) {
		return new Name(name);
	}

	public String Name();

	public N Type();

	public Object Class();

	public default _N Symbol() {
		return this;
	}

	public default Number Index() {
		return 0;
	}

	public default boolean isOf(_N N) {

		return this.Symbol().toString().contains(N.Symbol().toString());
	}

	public default <O extends Object> O get(_N s) {
		return this.get();
	}

	public default <O extends Object> O get(String s) {
		return this.get();
	}

	public <O extends Object> O get();

	public default <I extends Object, O extends Object> O get(I in) {
		return null;
	}

	public default <I extends Object, O extends Object> O get(I... in) {

		return null;
	}

	public default <I extends Object, O extends Object> O set(I in) {
		return null;
	}

	public default <I extends Object, O extends Object> O set(I... in) {
		return null;
	}

	public default boolean message(String message) {
		// Log(this.toString() + " " + message);
		return message(this, message, true, null);
	}

	public default boolean message(_N source, String message, boolean callback, @Null Object data) {
		// Log(this.toString() + " " + "CALLBACK_FOR:"+message);
		source.message(this, "CALLBACK_FOR:" + message, false, null);
		return true;
	}

	public default Array<Object> getBits() {
		// return (O[]) new Object[] { this.Name(), this.Type(), this.Symbol() };
		return new Array<Object>(true, 0, Object.class);
	}

	public default <O extends Object> Array<O> properties() {
		// return (O[]) new Object[] { this.Name(), this.Type(), this.Symbol() };
		return new Array<O>(true, 0, Object.class);
	}

	public default String toLog() {
		return this.toString();
	}

	//// GENERALITIES

	public default <O extends Object> O as() {
		return null;
	}

	// isAs()
	// isAs(_N subject, Boolean...if)

	////////////////////////////////////////////////////////////////////

	public abstract class n_Symbol implements _N<String>, CharSequence {

		public String Reference = "";
		public String Value = "";
		public _N Node;

		public n_Symbol() {
			this.Value = "null";
			String N = this.Value.substring(0, 1).toUpperCase();
			String ame = this.Value.substring(1).toLowerCase();
			this.Reference = N + ame;
			this.Node = this;
		}

		public n_Symbol(String name) {
			this.Value = name;
			String N = "";
			String ame = "";
			if (name.length() > 0)
				N = this.Value.toString().substring(0, 1).toUpperCase();
			if (name.length() > 1)
				ame = this.Value.toString().substring(1).toLowerCase();
			this.Reference = N + ame;
			this.Node = this;
		}

		@Override
		public String Name() {

			return this.Reference;
		}

		@Override
		public String Type() {
			return this.Value;
		}

		@Override
		public <CharSequence> CharSequence get() {
			return (CharSequence) this;
		}

		@Override
		public char charAt(int arg0) {
			this.Value.charAt(arg0);
			return 0;
		}

		@Override
		public int length() {
			return this.Value.length();
		}

		@Override
		public CharSequence subSequence(int arg0, int arg1) {
			return this.Value.subSequence(arg0, arg1);
		}

		@Override
		public Array<Object> getBits() {
			Array<Object> bits = new Array<Object>(true, this.Value.length(), String.class);
			for (int i = 0; i < this.Value.length(); i++) {
				bits.add("" + this.charAt(i));
			}

			return bits;
		}

		@Override
		public Object Class() {
			return CharSequence.class;
		}

		@Override
		public String toString() {
			return this.Name();
		}

		@Override
		public boolean equals(Object other) {
			Log(">EQ=_N");
			if (!(other instanceof CharSequence) && !CharSequence.class.isAssignableFrom(other.getClass())) {
				return false;
			}

			return this.toString().equals(other.toString());
		}

	}

	public abstract class n_Number extends Number implements _N<Number>, CharSequence, Comparable<Number> {

		private static final long serialVersionUID = -2329365161682497929L;
		public String Label;
		public Number Value;
		public _N Node;
		protected Number[] digits;

		public n_Number() {
			this.Label = "#";
			this.Value = 0;
			this.Node = this;
			this.digits = new Number[0];
		}

		public n_Number(Number n) {
			this.Label = "" + n;
			this.Value = n;
			this.Node = this;

			this.digits = new Number[n.toString().length()];
		}

		@Override
		public String Name() {
			return this.Label;
		}

		@Override
		public Number Type() {
			return this.Value;
		}

		@Override
		public Number Index() {
			return this;
		}

		@Override
		public <Number> Number get() {
			return (Number) this;
		}

		@Override
		public int length() {
			return this.digits.length;
		}

		@Override
		public char charAt(int index) {
			String res = "" + this.Value;
			return res.charAt(index);
		}

		@Override
		public CharSequence subSequence(int from, int to) {
			String res = "" + this.Value;
			return res.subSequence(from, to);
		}

		public Number valueAt(int index) {
			if (index <= this.digits.length)
				return this.digits[index];
			else
				return Float.NaN;
		}

		@Override
		public double doubleValue() {
			return this.Value.doubleValue();
		}

		@Override
		public float floatValue() {
			return this.Value.floatValue();
		}

		@Override
		public int intValue() {
			return this.Value.intValue();
		}

		@Override
		public long longValue() {
			return this.Value.longValue();
		}

		public String stringValue() {
			return this.Value.toString();
		}

		@Override
		public String toString() {
			return this.stringValue();
		}

		public boolean greaterThan(Number other) {
			if (this.compareTo(other) > 0)
				return true;
			else
				return false;
		}

		public boolean lessThan(Number other) {
			if (this.compareTo(other) < 0)
				return true;
			else
				return false;
		}

		@Override
		public int compareTo(Number other) {

			if (this.Value.floatValue() < other.floatValue())
				return -1;
			if (this.Value.floatValue() > other.floatValue())
				return 1;
			if (this.Value.floatValue() == other.floatValue())
				return 0;

			return 0;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Number) && !Number.class.isAssignableFrom(other.getClass())) {
				return false;
			}

			Number n = (Number) other;
			if (MathUtils.isEqual(this.floatValue(), n.floatValue())) {
				return true;
			}

			if (Integer.class.isAssignableFrom(other.getClass()))
				return this.intValue() == n.intValue();
			else

				return this.Value.toString().equals(other.toString());
		}

	}

	public static class Word extends n_Symbol implements _N<String>, CharSequence {

		public Word() {
			super();
		}

		public Word(String name) {
			super(name);
		}

		@Override
		public String Name() {
			return this.Reference;
		}

		@Override
		public String Type() {
			return this.toString();
		}

		@Override
		public String get() {

			return this.toString();
		}

		@Override
		public char charAt(int arg0) {
			return this.Reference.charAt(arg0);
		}

		@Override
		public int length() {
			return this.Reference.length();
		}

		@Override
		public CharSequence subSequence(int arg0, int arg1) {
			return this.subSequence(arg0, arg1);
		}

	}

	public class Name extends Word {
		public Name() {
			super();
		}

		public Name(String name) {
			super(name);
		}
	}

	public class Type implements _N<Class>, CharSequence {

		public int Count = 0;
		public String Reference = "null";
		public Class Value;
		public _N Node;

		protected boolean subclass;
		protected Type of;

		public static Type Type = new Type("Type", Type.class, Class.class);

		public static Type Number = new Type("Number", Number.class);
		public static Type NumberType = new Type("Number", Number.class, Type);

		public static HashMap<Type, String> Every = new HashMap<Type, String>();

		public Type() {
			this.Reference = "?";
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(String name) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Class val) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val;
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Object val) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(Object val) {
			String name = val.getClass().getSimpleName();
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;

			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg();
		}

		public Type(Number val) {
			String name = val.getClass().getSimpleName();
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg();
		}

		/////////////////////////

		public Type(String name, Class val, Class ext) {

			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val;
			this.of = new Type(ext.getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Object val, Object ext) {

			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Class val, Object ext) {

			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val;
			this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Object val, Class ext) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		public Type(String name, Type ext) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = ext.Value;
			this.of = ext;
			this.subclass = true;
			this.Node = this;
			this.reg();
		}

		///
		public Type(boolean global) {
			this.Reference = "?";
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = Object.class;
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Object val) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Class val) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val;
			this.of = this;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Object val, Object ext) {

			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = val.getClass();
			this.of = new Type(ext.getClass().getSimpleName(), ext);
			this.subclass = true;
			this.Node = this;
			this.reg(global);
		}

		public Type(boolean global, String name, Type ext) {
			if (name.length() > 1 && !A_N.contains("" + name.charAt(0)))
				this.Reference = name.substring(1);
			else
				this.Reference = name;
			String N = this.Reference.substring(0, 1).toUpperCase();
			String ame = this.Reference.substring(1).toLowerCase();
			this.Value = ext.Value;
			this.of = ext;
			this.subclass = true;
			this.Node = this;
			this.reg(global);
		}

		public void reg() {
			this.reg(true);
		}

		private void reg(boolean global) {
			if (global) {
				if (Every == null)
					Every = new HashMap<Type, String>();

				if (!Every.containsKey(this) && !Every.containsValue(this.Reference))
					Every.put(this, this.Reference);

				_Shell.VTS.put(this);
			}

		}

		public static Type extend(Type n, String name) {
			Type k = new Type(name, n.Type(), n.Type());
			return k;
		}

		public static Type extendA(Type a, Type b) {
			b.of = a;
			b.subclass = true;
			b.reg();
			a.reg();

			return b;
		}

		@Override
		public String Name() {
			return this.Reference;
		}

		public Type Class() {
			if (this.subclass)
				return this.of;
			else
				return this;
		}

		@Override
		public Class Type() {
			return Class.class;
		}

		@Override
		public Class get() {
			return this.Value;
		}

		public boolean subclass() {
			return (this.subclass);
		}

		public Type subclass(String name, Type t) {
			return new Type(name, t);
		}

		public boolean isOf(Class c) {
			// For some reason, equality returns change when i remove the Log()s
			if (c.equals(Object.class) || c.equals(_Object.class)) {
				// Log(" ==<<1");
				return true;
			}

			if (c.equals(String.class)) {
				// Log(" ==<<2 " + c.toString());
				return this.isOf(c.toString());
			}

			if (this.getClass().isAssignableFrom(c)) {
				// Log(" ==<<3");
				return true;
			}
			if (this.subclass) {
				boolean a = false;
				boolean b = false;

				if (this.of.Value.equals(c)) {
					// Log(" ==<<4_1"); //keep these seperate for the moment, for debugging Node
					// Types
					a = true;
				}
				if (this.of.Value.isAssignableFrom(c)) {
					// Log(" ==<<4_2");
					b = true;
				}
				if (this.of.Reference.equals(this.Reference)) {
					// Log(" ==<<4_3");
				}
				return a && b;
			}
			if (Number.class.isAssignableFrom(c) && Number.class.isAssignableFrom(this.Value))
				return true;
			// Log(" ==<<5");
			return false;

		}

		public boolean isOf(String o) {
			// Log(" >str>" + o);

			if (o.equals("" + Object.class.getSimpleName()) || o.equals("" + _Object.class.getSimpleName()))
				return true;
			if (this.subclass && (this.of.Name().toUpperCase().equals(o)
					|| this.of.Value.getSimpleName().toUpperCase().equals(o)))
				return true;

			return false;
		}

		public boolean isOf(Type o) {

			if (o.equals(this)) {
				// Log(" --<0" + o + " " + this);
				return true;
			}

			if (this.isOf(o.Type())) {
				// Log(" --<1" + o + " " + o.Type());
				return true;
			}

			if (this.isOf(o.Class().Reference)) {
				// Log(" --<2");
				return true;
			}

			if (this.isOf(o.Value)) {
				// Log(" --<3");
				return true;
			}

			if (this.Type.toString().contains(o.of.toString())) {
				// Log(" --<4");
				return true;
			}

			if (this.isOf(o.Value.toString()) || this.isOf(o.Type().toString())) {
				// Log(" --<5");
				return true;
			}
			// Log("--<6");
			return this.isOf(o.Value.getSimpleName());
		}

		public boolean isOf(Object o) {
			return this.isOf(o.getClass());
		}

		@Override
		public boolean equals(Object other) {

			if (other instanceof Class) {
				Class O = (Class) other;
				// Log(" >EQ=1[T] " + this.Name() + " = " + O.getSimpleName());
				return this.Name().equals(O.getSimpleName());
			}
			if (other instanceof Type) {
				// Log(" >EQ=2<T> " + this.Name() + " = " + ((Type) other).Name());
				return this.Name().equals(((Type) other).Name());
			}

			if (other instanceof CharSequence) {
				String s = other.toString();
				// Log(" >EQ=3[C] " + this.Name() + " = " + s);
				return (this.Name().equals(s));
			}

			return false;
		}

		@Override
		public String toString() {
			String ext = "";
			if (this.subclass || (this.of != null && this.Value.getClass().equals(this.of)))
				ext = ":" + this.of;
			return "<" + this.Reference + ">" + ext;
		}

		@Override
		public String toLog() {
			String ext = "";
			String base = this.Type().getSimpleName();

			if (this.subclass || (this.of != null && this.Value.getClass().equals(this.of))) {
				base = this.of.Name();
				ext = ":<" + this.of.Type().getSimpleName() + ">";
			}

			return "[" + this.toString() + "][(<" + base + ">" + ext + ")]";
		}

		@Override
		public char charAt(int arg0) {
			return this.Name().charAt(arg0);
		}

		@Override
		public int length() {
			return this.Name().length();
		}

		@Override
		public CharSequence subSequence(int arg0, int arg1) {
			return this.Name().subSequence(arg0, arg1);
		}

	}

	public class Node<T> implements _N<Object> {

		public boolean State = true;

		public static Type Node = new Type("Node", Node.class);
		public static Type NodeType = new Type("Node", Node.class, Type.class);
		public String Label;
		public Type Of;
		public T Data;

		public Array<Node> Properties; // from Data
		// public HashMap<String, Node> Connections;
		public HashMultimap<String, Node> Connections;

		public int depth = 0;

		public Node() {
			this.Label = "NULL";
			this.Data = (T) "NULL";
			this.Of = Node;
			this.Properties = new Array<Node>();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);
		}

		public Node(T data) {
			this.Label = data.toString();
			this.Of = new Type(false, this.Label, data.getClass());
			this.Data = data;
			this.Properties = new Array<Node>();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);

		}

		public Node(String label, T data) {
			this.Label = label;
			this.Of = new Type(false, this.Label, data.getClass());
			this.Data = data;
			this.Properties = new Array<Node>();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);

		}

		public Node(_N data) {
			this.Label = data.Name();
			this.Of = new Type(false, this.Label, data.getClass());
			this.Data = (T) data;
			this.Properties = new Array<Node>();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);

		}

		public Node(String label, _N data) {
			this.Label = label;
			this.Of = new Type(false, this.Label, data.Class());
			this.Data = (T) data;
			this.Properties = new Array<Node>();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);

		}

		public Node(String label, Type data) {
			this.Label = label;
			this.Of = data;
			this.Data = (T) data;
			this.Properties = data.properties();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);

		}

		public Node(String label, Node data) {
			this.Label = label;
			this.Of = data.Of;
			this.Data = (T) data.Data;
			this.Properties = data.properties();
			// this.Connections = new HashMap<String, Node>(1);
			this.Connections = HashMultimap.create();
			this.Connections.put("Self", this);

		}

		public boolean connect(String con, Node other) {

			if (!this.Connections.containsKey(con) && !this.Connections.containsValue(other)) {
				this.Connections.put(con, other);
				return true;
			} else
				return false;
		}

		// public Node thatConnection = this;

		public Set<Node> thatConnection;

		public boolean hasConnection(String conName) {
			if (this.Connections.containsKey(conName.toUpperCase())) {
				this.thatConnection = this.Connections.get(conName);
				return true;
			} else
				return false;
		}

		public Node getConnected(String conName) {
			Node N = null;
			// for (Entry<String, Node> E : this.Connections.entrySet()) {
			for (Entry<String, Node> E : this.Connections.entries()) {
				if (E.getKey().equals(conName.toUpperCase()))
					N = E.getValue();
			}

			if (N == null)
				return this;
			else
				return N;

		}

		public Node getConnected(String conName, String conType) {
			Node N = null;
			// for (Entry<String, Node> E : this.Connections.entrySet()) {
			for (Entry<String, Node> E : this.Connections.entries()) {
				if (E.getKey().equals(conName.toUpperCase()) && E.getValue().Type().Name().equals(conType))
					N = E.getValue();
			}
			if (N == null)
				return this;
			else
				return N;
		}

		public boolean isConnected(Node N) {

			// for (Entry<String, Node> E : this.Connections.entrySet()) {
			for (Entry<String, Node> E : this.Connections.entries()) {
				if (E.getValue().equals(N) || E.getValue().toString().equals(N.toString())) {
					// this.thatConnection = E.getValue();
					this.thatConnection = new HashSet<Node>();
					this.thatConnection.add(E.getValue());
					return true;
				}
			}
			return false;
		}

		public boolean isConnected(_Object N) {
			// for (Entry<String, Node> E : this.Connections.entrySet()) {
			for (Entry<String, Node> E : this.Connections.entries()) {

				if (E.getValue().equals(N) || E.getValue().toString().equals(N.toString())) {
					// this.thatConnection = E.getValue();
					this.thatConnection = new HashSet<Node>();
					this.thatConnection.add(E.getValue());
					return true;
				}
				if (E.getValue().get().equals(N) || E.getValue().get().toString().equals(N.toString())) {
					// this.thatConnection = E.getValue();
					this.thatConnection = new HashSet<Node>();
					this.thatConnection.add(E.getValue());
					return true;
				}
			}

			return false;
		}

		@Override
		public String Name() {
			return this.Label;
		}

		@Override
		public Type Type() {
			return this.Of;
		}

		@Override
		public Class Class() {
			return Node.Class().Value;
		}

		@Override
		public <T> T get() {
			return (T) this.Data;
		}

		@Override
		public <O extends Object, Node> Node set(O in) {
			this.Data = (T) in;

			// for (Entry<String, _N.Node> E : this.Connections.entrySet()) {
			for (Entry<String, _N.Node> E : this.Connections.entries()) {
				String K = E.getKey();
				_N.Node V = E.getValue();
				if (K.contains("Listener")) {
					V.message("ListenerUpdate");
				}

			}

			return (Node) this;
		}

		public void msg(String to, String message) {
			this.msg(to, message, false, null);
		}

		public void msg(String to, String message, boolean callback, @Null Object data) {

			for (Entry<String, _N.Node> E : this.Connections.entries()) {
				String K = E.getKey();
				_N.Node V = E.getValue();
				if (K.contains(to)) {
					V.message(to + message);
				}

			}
		}

		@Override
		public boolean message(String message) {
			return message(this, message, true, this.get());
		}

		@Override
		public boolean message(_N source, String message, boolean callback, @Null Object data) {
			String c = "";
			String d = "";
			if (data != null) {

				c = "<" + data.getClass().getSimpleName() + ">:[";
				d = data.toString() + "]";
				if (data.getClass().isAssignableFrom(this.get().getClass())
						|| this.get().getClass().isAssignableFrom(data.getClass()))
					this.set(data);
			}
			Log(this.toString() + " :: " + source.toString() + " __ " + message + "     " + this.Data + c + d);

			if (callback) {
				source.message(this, "CALLBACK_FOR:" + message, false, data);
			}

			return true;
		}

		@Override
		public _N Symbol() {
			return this.Type();
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

			return false;

		}

		public boolean isOf(String n) {
			if (this.get().toString().contains(n))
				return true;
			return false;
		}
		
		public boolean isOf(Object o)
		{
			
			if(this.get().getClass().isAssignableFrom(o.getClass()))
				return true;
			
			else
				return this.isOf(o.toString());
		}

		public Node cpy() {
			Node N = new Node(this.Label);
			N.Data = this.Data;
			if (this.Of != null)
				N.Of = this.Of;

			return N;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Node) {
				Node N = (Node) other;
				if (this.Data.equals(N.Data))
					return true;
			}

			if (other instanceof Type) {
				return this.Type().isOf(((Type) other));
			}

			if (other instanceof String) {
				String o = "" + other;
				if (this.Name().toString().equals(o))
					return true;

				if (this.Type().toString().equals(o))
					return true;
			}

			return false;
		}

		@Override
		public Array<Node> properties() {
			return this.Properties;
		}

		public String lhString() {
			return "[" + this.Name() + ":" + this.get() + "]";
		}

		public String rhString() {
			return "[" + this.Class().getSimpleName() + ":" + this.Type() + "]";
		}

		@Override
		public String toString() {
			return "[" + this.Name() + ":" + this.get() + "]|[" + this.Class().getSimpleName() + ":" + this.Type()
					+ "]";
		}

		@Override
		public String toLog() {
			return this.toString() + this.Properties + " {::} " /* + this.Components */;
		}
	}

}