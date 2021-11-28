package prime._PRIME.RAUM;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import java.util.ArrayList;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.N_M.Prototype.aVector;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.SYS._Shell;
import prime._PRIME.SYS.NIX._Entity;


public class _Environment extends _Shell implements iSpace {

	// an 'interaction layer', as it were...
	public static _Environment ANY;
	public uVector Dimension;
	public Array<_Entity> Members;
	

	public _Environment(String name) {
		super(name);
		this.Members = new Array<_Entity>(true,0,_Entity.class);
		
	}

	@Override
	public Vector3 getSize() {

		return new Vector3(1, 1, 1);
	}

	@Override
	public Vector3 getUnit() {

		return new Vector3(1, 1, 1);
	}

	@Override
	public boolean isEmpty() {
		return this.Members.isEmpty();
	}

	
	
	public void resize(int...size)
	{
		Array<Number> s = new Array<Number>(true, size.length, Number.class);
		for(int i =0; i < size.length; i++)
		{
			s.add(size[i]);
		}
		
		this.Dimension = new uVector(s.items);
		
	}
	
	public void resize(float...size)
	{
		Array<Number> s = new Array<Number>(true, size.length, Number.class);
		for(int i =0; i < size.length; i++)
		{
			s.add(size[i]);
		}
		this.Dimension = new uVector(s.items);
	}
	
	
	public void resize(aVector dim)
	{
		this.Dimension = new uVector(dim);
		
	}
	
	///////
	//zScalar
	public float apparentSize(Transform object, Camera observer)
	{
		Vector3 unit = this.getUnit();
		Vector3 u = VectorUtils.div(unit.cpy(), new Vector3(2, 2, 2));// unit radius
		Vector3 unScl = observer.unproject(new Vector3(1, 1, 1));
		float uSize = unit.len() / 3f;
		float uRad = u.len() / 3f;
		float uScl = unScl.len() / 3f;
		
		Vector3 scl = object.GetLocalScale();
		Vector3 p = object.GetLocalPosition();
		float z2 = (float) ((scl.len() + (1f / MathUtils.PI2)) / observer.position.cpy().sub(p.cpy()).len());
		z2 = z2 / (uRad);
		z2 = z2 / (uSize);
		
		
		return z2;
	}
	
	

}
