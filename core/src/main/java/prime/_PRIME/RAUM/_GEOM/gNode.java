package prime._PRIME.RAUM._GEOM;

import com.badlogic.gdx.math.Vector3;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.RAUM._GEOM.NIX._DrawData;
import prime._PRIME.RAUM._GEOM.NIX._Transform;
import prime._PRIME.SYS.NIX._Component;

public class gNode extends _Transform {

	public _BoundShape ShapeData;
	public _DrawData DrawData;
	// DrawData

	public gNode() {
		super(new Transform());
		this.Label = "gNode";
		this.ShapeData = new _BoundShape();
		this.DrawData = new _DrawData();

	}

	public gNode(Transform t) {
		super(t);
		this.Label = "gNode";
		this.ShapeData = new _BoundShape(t, new aGeoset());
		this.DrawData = new _DrawData();
	}

	public gNode(Transform t, aGeoset shape) {
		super(t);
		this.Label = "gNode";
		this.ShapeData = new _BoundShape(t, shape);
		this.DrawData = new _DrawData();
	}
	
	public void bindShape(aGeoset shapeData)
	{
		this.ShapeData=new _BoundShape(this.get,shapeData);
	}
	
	public void bindShape(_BoundShape shape)
	{
		this.ShapeData = shape;
	}

	public _BoundShape shape() {
		return this.ShapeData;
	}
	
	public _DrawData drawData()
	{
		return this.DrawData;
	}

	@Override
	public String toLog() {
		String log = "";
		log += this.toString() + "\n";
		log += "*" + this.position() + "\n";
		log += "_ " + this.position(true) + "\n";
		log += "*" + this.rotation() + "\n";
		log += "_ " + this.rotation(true) + "\n";
		log += "*" + this.scale() + "\n";
		log += "_ " + this.scale(true) + "\n";
		log += this.ShapeData.geom.toLog();

		return log;
	}

}
