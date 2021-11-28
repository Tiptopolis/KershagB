package prime._PRIME.SYS.Prototype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M._N;
import prime._PRIME.N_M._N.Node;
import prime._PRIME.SYS._VTS;


public class Tree<T> extends Node<T> implements iGraph<T> {

	public Name Name;
	public Node<Tree> Root;

	public static Type ANY = new Type("Any", _VTS.getA("?"));
	public static Type ROOT = new Type("Root", _N.Node.NodeType);
	// public static Type LEAF = new Type("Leaf", _N.Node.NodeType);
	// public static Type BRANCH = new Type("Branch", _N.Node.NodeType);

	public ArrayList<Node> Any = new ArrayList<Node>();
	public HashMap<String, Node> All = new HashMap<String, Node>();
	public HashMap<Type, Node> Every = new HashMap<Type, Node>();

	public HashMap<Type, Edge> Edges = new HashMap<Type, Edge>();

	private T type;

	public Tree() {
		super();
		this.Name = this.Name("Tree");
		this.Data = (T) ANY;
		this.Root = new Node<Tree>("Root", ANY);
		this.Any.add(this.Root);
		this.All.put("Root", this.Root);
		this.Every.put(ROOT, this.Root);
		this.Edges.put(_VTS.getA("?"), new Edge(this.Root, this.Root));
	}

	@Override
	public Node<T> Vertex() {

		return new Node<T>();
	}

	@Override
	public HashMap<String, Node> Key(String k, Node v) {
		return null;
	}

	@Override
	public Array<Node> Vertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array<Node> Edges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Node N) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean newVertex(Object at) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean newVertex(String at) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean newVertex(Node at) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T put(Node key, T value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Node, ? extends T> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		this.Any.clear();
		this.All.clear();

	}

	@Override
	public Set<Node> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<T> values() {

		return null;
	}

	@Override
	public Set<Entry<Node, T>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String Name() {
		return this.Name.Value;
	}

	@Override
	public String toString() {
		return "[" + this.Name() + ":" + this.get() + "]|[" + this.Class().getSimpleName() + ":" + this.Type() + "]";
	}

	@Override
	public String toLog() {
		return this.toLog();
	}

	public class Edge extends Node {

		String label = "Edge";
		Node a;
		Node b;
		protected Type NodeType;

		public Edge(Node a, Node b) {
			this.a = a;
			this.b = b;
		}

	}

}
