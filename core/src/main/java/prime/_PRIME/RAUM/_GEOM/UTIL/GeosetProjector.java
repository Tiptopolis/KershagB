package prime._PRIME.RAUM._GEOM.UTIL;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;

public class GeosetProjector {

	
	
	public static aGeoset project(Camera c, aGeoset g)
	{
		aGeoset result = g.cpy();
		//c.project(g.origin.get);
		
		
		result.origin.get = (c.project(g.origin.get.cpy()));
		for(aVertex v : result.vertices)
		{			
			c.project(v.get);			
		}
		
		
		return result;
	}
	
	
	
	
	public static Polygon projectPoly(Camera c, aGeoset g)
	{
		Array<Vector2> out = new Array<Vector2>();
		for(int i =0; i < g.vertices.size; i++)
		{
			Vector3 v = g.vertices.get(i).get.cpy();
			out.add(VectorUtils.downcast(c.project(v)));
		}
		Polygon result = new Polygon(VectorUtils.disassembleVects(out.toArray()));
		out.clear();
		return result;
	}
	public static Polygon projectPoly(Camera c, aFace f)
	{
		Array<Vector2> out = new Array<Vector2>();
		for(int i =0; i < f.vertices.size; i++)
		{
			Vector3 v = f.vertices.get(i).get;
			out.add(VectorUtils.downcast(c.project(v.cpy())));
		}
		Polygon result = new Polygon(VectorUtils.disassembleVects(out.toArray()));
		out.clear();
		return result;
	}
	
}
