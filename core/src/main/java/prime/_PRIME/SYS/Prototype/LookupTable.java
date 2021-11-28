package prime._PRIME.SYS.Prototype;

import static prime._PRIME.uAppUtils.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M._N;
import prime._PRIME.N_M._N.*;


public class LookupTable<T> extends ArrayList<T> implements _N<Node<T>> {

	public String Label;
	public Node Node;
	public HashMap<String, Node<T>> Index = new HashMap<String, Node<T>>(8);
	public Type Type = Node.Node;
	public T Class;

	public LookupTable() {
		this.Label = "Lookup";
		this.Class = (T) new String[0];
		this.Node = new LookUp<T>().Node;

	}

	@Override
	public boolean add(T thing) {
		boolean added = false;
		if (super.add(thing))
			added = true;

		if (added) {
			Index.put(thing.toString(), new Node(Node.Node.Name(), thing));

		}

		return added;
	}

	public boolean add(String label, Node N) {
		boolean added = false;
		if (super.add((T) N))
			added = true;

		if (added) {
			Index.put(label, N);

		}

		return added;
	}

	public boolean add(String label, Type type) {
		boolean added = false;
		Node<T> N = new Node<T>(type.Name(), type);
		if (super.add((T) type))
			added = true;

		if (added) {
			Index.put(label, N);

		}

		return added;
	}

	@Override
	public Node<T> get(String n) {

		for (Node<T> N : this.Index.values()) {

			if (N.Label.contains(n) || N.Data.toString().contains(n)) {
				return (Node<T>) N.get();

			}
		}

		return null;

	}

	public Node<T> get(String n, String v) {
		for (Node<T> N : this.Index.values()) {

			if (N.Label.contains(n) && N.Data.toString().contains(v)) {
				return (Node<T>) N.get();

			}
		}

		return null;
	}

	public Node<T> get(Object item) {

		if (this.Index.containsKey(item))
			return this.Index.get(item);

		return null;
	}

	public Array<Node> getAll(String n) {
		Array<Node> res = new Array<Node>();
		for (Node<T> N : this.Index.values()) {

			if (N.Label.contains(n) || N.Data.toString().contains(n)) {
				res.add(N);

			}
		}
		return res;
	}

	@Override
	public String Name() {
		// TODO Auto-generated method stub
		return "[Lookup]" + this.Type + "{[" + this.Class().getSimpleName() + "]}";
	}

	@Override
	public Node<T> Type() {

		return new Node(this.Type);
	}

	@Override
	public Class Class() {

		return this.Class.getClass();
	}

	@Override
	public <O> O get() {
		// TODO Auto-generated method stub
		return null;
	}

	public Array<Node> properties() {
		return this.Node.properties();
	}

	@Override
	public String toLog() {
		return this.toString() + "   " + this.Node + " " + this.Name();
	}
}
