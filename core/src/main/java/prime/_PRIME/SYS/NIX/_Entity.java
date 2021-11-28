package prime._PRIME.SYS.NIX;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import prime._PRIME.N_M._N;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.Prototype.LookupTable;



public class _Entity extends _Object implements EventListener{


	public int ID;
	public _Environment Environment;
	
	
	public static Type Entity = new Type("Entity", _Entity.class);	
	public static Type EntityType = new Type("Entity", _Entity.class, Type.class);	
	
	
	
	
	public HashMap<String, _Property> Components;
	
	public HashMap<_Environment, Integer> of;
	public HashMap<Integer, _Environment> in;
	
	public LookupTable<Node<EventListener>> Sensors;
	
	public _Entity() {
		super();
		this.Node = Entity;
		if(_Environment.ANY==null)
		{
			_Environment.ANY = new _Environment("Any");
		}
		this.Environment = _Environment.ANY;
		this.of = new HashMap<_Environment, Integer>();
		this.in = new HashMap<Integer,_Environment>();
	}

	public _Entity(String name) {
		super(name);
		this.Node = Entity;
		this.Name= new Name(name);
		if(_Environment.ANY==null)
		{
			_Environment.ANY = new _Environment("Any");
		}
		this.Environment = _Environment.ANY;
		this.of = new HashMap<_Environment, Integer>();
		this.in = new HashMap<Integer,_Environment>();

	}
	
	public _Entity(_Environment e, String name)
	{
		super(name);
		this.Node = Entity;
		this.Name= new Name(name);
		this.Environment=e;
		this.of = new HashMap<_Environment, Integer>();
		this.in = new HashMap<Integer,_Environment>();
	}
	

	public _Entity(_N n) {
		super(n.Name());
		this.Node = Entity;
		this.Name= new Name(n.toString());
		if(_Environment.ANY==null)
		{
			_Environment.ANY = new _Environment("Any");
		}
		this.Environment = _Environment.ANY;
		this.of = new HashMap<_Environment, Integer>();
		this.in = new HashMap<Integer,_Environment>();
	}

	//////////////////////
	@Override
	public void handleEvent(Event evt) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public String toString() {
		return this.Name() + "_" + this.Node+ " @" + this.Class().getSimpleName() + "[" + this.Type() + "]";
		// REFERENCE NODE INDEX SYMBOL
	}

	@Override
	public String toLog()
	{
		return super.toLog();
	}



}
