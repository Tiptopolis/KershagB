package prime._PRIME.SYS;

import java.util.ArrayList;

import prime._PRIME.RAUM.NIX._Thing;
import prime._PRIME.SYS.NIX._Entity;
import prime._PRIME.SYS.NIX._Property;



public class _ECS extends _Shell implements iSystem {

	// public _Entity ent;

	public static ArrayList<iSystem> Running = new ArrayList<iSystem>();

	public static EntityFactory Spawner = new EntityFactory();

	public static class EntityFactory {
		// link _Archetype <?><[TYPE]> to registered properties
		public _Entity iEnt(_Thing from) {
			_Entity newEnt = new _Entity(from.Name());
			for (Node n : from.Properties) {
				newEnt.addProperty(((_Property)n).cpy());
			}

			return newEnt;
		}

	}
	
	public void dispose()
	{
		Running.clear();
		Running = null;
		Spawner = null;
	}

}
