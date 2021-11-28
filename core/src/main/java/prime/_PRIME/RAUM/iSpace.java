package prime._PRIME.RAUM;

import java.util.Collection;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


public interface iSpace {
	public Vector3 getSize();

	public Vector3 getUnit();

	public boolean isEmpty();
	
	public default<O extends Object> boolean contains(O o)
	{
		return false;
	}
	
	public default Vector3 getRight()
	{
		return new Vector3(1,0,0);
	}
	
	public default Vector3 getUp() {
		return new Vector3(0, 1, 0);
	}
	
	public default Vector3 getForward()
	{
		return new Vector3(0,0,1);
	}
	
	
	public default Matrix4 getOrtho()
	{
		Vector3 scl = this.getSize().cpy().scl(this.getUnit());
		return new Matrix4().setToOrtho2D(0, 0, scl.x, scl.y);
	}
	
	public default Matrix4 getSizeOrtho() {
		return new Matrix4().setToOrtho2D(0, 0, this.getSize().x, this.getSize().y);
	}
	
	public default Matrix4 getUnitOrtho() {
		return new Matrix4().setToOrtho2D(0, 0, this.getUnit().x, this.getUnit().y);
	}
	
	public default Vector3 project(Vector3 point)
	{
		return this.spaceCam().project(point);
	}
	
	public default Vector3 unproject(Vector3 point)
	{
		return this.spaceCam().unproject(point);
	}
	
	
	
	public default Camera spaceCam()
	{
		Vector3 v = this.getSize();
		Camera c = new OrthographicCamera(v.x,v.y);
		c.near = this.getUnit().len()/3;
		c.far = v.z;
		return c;
	}

	
	public void update(float deltaTime);
	
	public default void create()
	{
		this.update(-1);
	}
	
	public default void create(Object object)
	{
		this.create();
	}
	
	public default void create(Object[] objects)
	{
		this.create();
	}
}
