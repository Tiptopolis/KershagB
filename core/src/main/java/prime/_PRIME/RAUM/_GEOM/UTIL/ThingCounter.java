package prime._PRIME.RAUM._GEOM.UTIL;

import java.util.HashMap;

import prime._PRIME.SYS.NIX._Property;

public class ThingCounter {

	
	
	public HashMap<Object, _Property<Integer>> things;
	
	
	
	public ThingCounter(Object...things)
	{
	
		this.things = new HashMap<Object, _Property<Integer>>();
		for(int i =0; i < things.length; i++)
		{
			if(!this.things.containsKey(things[i]))
				this.things.put(things[i], new _Property<Integer>("Count",1));
			else {
				int v = this.things.get(things[i]).get();
				this.things.get(things[i]).set(v+1);
			}
		}
	}
	
	public static ThingCounter countThings(Object...things)
	{
		return new ThingCounter(things);
	}
	
	public int getCountOf(Object o)
	{
		if(this.things==null || !this.things.containsKey(o))
			return -1;
		return this.things.get(o).get();
	}
	
	public void dispose()
	{
		this.things.clear();
		this.things = null;
	}
}
