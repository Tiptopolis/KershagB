package prime.TEST.zTest4.z4.Componentx;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.SYS.NIX._Component;

public class Scalar extends _Component{

	private Transform affected;
	
	private int direction = 1;
	public float min = -1;
	public float max = 1;
	public float index = 0;
	public float theta = 0.1f;
	
	public Scalar(Transform target)
	{
		super("Scalar",_Component.Component);
	}
	
	@Override
	public void update(float delta)
	{
		Vector3 current = this.affected.GetLocalScale();
		if(index >= max || index<=min)
			direction*=-1;
		
		affected.GetLocalScale().add(direction*(theta*delta));
	}
	
}
