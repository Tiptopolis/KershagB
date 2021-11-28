package prime._PRIME.SYS.Prototype;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M._N.Node;


public interface iGraph<T> extends Map<Node,T>{

	public Node Vertex();
	
	public HashMap<String, Node> Key(String k, Node v);

	public default Node Edge(Node a, Node b)
	{
		Edge E = new Edge(a,b);
		this.Edges().add(E);
		return E;
	}
	
	
	public default Vector3 getSize()
	{
		return new Vector3(1,1,1);
	}
	
	public default Vector3 getUnit()
	{
		return new Vector3(1,1,1);
	}

	public Array<Node> Vertices();
	
	public default boolean newVertex(Object at)
	{
		return newVertex(new Node(at));
	}
	
	public default boolean newVertex(String at)
	{
		return newVertex(new Node(at));
	}
	
	public boolean newVertex(Node at);

	public Array<Node> Edges();

	public void put(Node N);
	
	class Edge extends Node {
		public Node A;
		public Node B;

		public Edge(Node a, Node b) {
			this.A = a;
			this.B = b;
		}
	}

}
