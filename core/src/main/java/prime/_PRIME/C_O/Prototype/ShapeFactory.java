package prime._PRIME.C_O.Prototype;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;

public class ShapeFactory {

	public static BoundShape bindVertex(Vector3 at, float radius, Color c) {
		BoundShape newShape = new BoundShape();

		newShape.scale(new Vector3().add(radius));
		newShape.position(at);
		newShape.vertexNum = 1;
		newShape.vertexSize = radius;
		newShape.lines = false;
		newShape.setColor(c);
		newShape.vertices.add(at);
		return newShape;
	}

	public static BoundShape bindVertex(Transform t, float radius, Color c) {
		BoundShape newShape = new BoundShape();
		newShape.scale(t.GetLocalScale());
		newShape.rotation(t.GetLocalRotation());
		newShape.position(t.GetLocalPosition());
		newShape.vertexNum = 1;
		newShape.vertexSize = radius;
		newShape.lines = false;
		newShape.setColor(c);
		newShape.vertices.add(t.GetLocalPosition());
		newShape.transform = t;
		return newShape;
	}

	public static BoundShape bindShape(Vector3 at, Vector3 radius, Color c, int n) {
		BoundShape newShape = new BoundShape();

		newShape.scale(radius);
		newShape.rotation(new Quaternion(0, 1, 0, -1));
		newShape.position(at);
		newShape.fillColor = c.cpy();
		newShape.lineColor = c.cpy();
		newShape.vertexNum = n;

		newShape.vertices = Geom.regPolygon(at, radius, n);
		return newShape;
	}
	
	public static BoundShape bindShape(Vector3 pos, Vector3 rad, Vector3 rot, int n)
	{
		return bindShape(pos, rad, rot,Color.BLACK, n);
	}

	// rebind to rotate
	public static BoundShape bindShape(Vector3 at, Vector3 radius, Vector3 rotation, Color c, int n) {
		BoundShape newShape = new BoundShape();

		newShape.scale(radius);
		newShape.rotation(VectorUtils.upcast(rotation));
		newShape.position(at);
		newShape.fillColor = c.cpy();
		newShape.lineColor = c.cpy();
		newShape.vertexNum = n;
		newShape.vertices = Geom.generatePolygon(at.cpy(), radius, rotation, n);

		return newShape;

	}

	public static BoundShape bindShape(Vector3 at, Vector3 radius, Vector3 rotation, Color c, int n, float vertSize) {
		BoundShape newShape = new BoundShape();
		
		newShape.scale(radius);
		newShape.rotation(VectorUtils.upcast(rotation));
		newShape.position(at);
		newShape.fillColor = c.cpy();
		newShape.lineColor = c.cpy();

		newShape.vertexNum = n;
		newShape.vertexSize = vertSize;

		newShape.vertices = Geom.generatePolygon(at, radius, rotation, n);
		return newShape;

	}

	public static BoundShape bindShape(Transform t, Color c, int n, float vertSize) {
		BoundShape newShape = new BoundShape();
		
		newShape.scale(t.GetScale());
		newShape.rotation(t.GetLocalRotation());
		newShape.position(t.GetLocalPosition());
		newShape.fillColor = c.cpy();
		newShape.lineColor = c.cpy();

		newShape.vertexNum = n;
		newShape.vertexSize = vertSize;

		newShape.vertices = Geom.generatePolygon(t, n);
		newShape.transform = t;
		// Log(t.of + " " + newShape.shape.size() + "/" + n);
		return newShape;

	}

	public static BoundShape bindShape(Transform t, Color c, Polygon p, float vertSize) {
		BoundShape newShape = new BoundShape();
		
		newShape.scale(t.GetScale());
		newShape.rotation(t.GetLocalRotation());
		newShape.position(t.GetLocalPosition());
		newShape.fillColor = c.cpy();
		newShape.lineColor = c.cpy();

		newShape.vertexNum = p.getVertices().length;
		newShape.vertexSize = vertSize;

		newShape.vertices = fromPoly(p.getVertices());
		newShape.transform = t;
		// Log(t.of + " " + newShape.shape.size() + "/" + n);
		return newShape;

	}

	public static BoundShape bindShape(BoundShape s, Color c, int n, float vertSize) {
		BoundShape newShape = new BoundShape();
		
		newShape.scale(s.scale().cpy());
		newShape.rotation(s.rotation().cpy());
		newShape.position(s.position().cpy());
		newShape.fillColor = c.cpy();
		newShape.lineColor = c.cpy();

		newShape.vertexNum = n;
		newShape.vertexSize = vertSize;

		// newShape.shape = Geometry.generatePolygon(s.getTransform(), n);

		newShape.vertices = Geom.generatePolygon(newShape.position().cpy(), newShape.scale().cpy(),
				VectorUtils.downcast(newShape.rotation().cpy().nor()), n);
		// Log(t.of + " " + newShape.shape.size() + "/" + n);
		return newShape;

	}

/////////////////////
//////////////////////
/////////////////////
// Directed Line Segment, Ray
	public static BoundShape bindRadius(Vector3 at, Vector3 length, Vector3 rotation, Color color) {
		return bindShape(at, length, rotation, color, 2);

	}

// proper a-? line
	public static BoundShape bindRadian(Vector3 origin, Vector3 length, Vector3 dir, Color color) {
		// return bindShape(origin, length, dir, color, 1);
		return bindShape(origin, length, dir, color, 1);

	}

	public static BoundShape bindLine(Vector3 origin, Vector3 to) {
		return bindLine(origin,to,0);
	}
	
	public static BoundShape bindLine(Vector3 origin, Vector3 to, int div) {
		BoundShape newShape = new BoundShape();
		Vector3 dir = VectorUtils.dir(origin.cpy(), to.cpy());
		Vector3 dst = to.cpy().sub(origin.cpy());
		newShape.scale(dst);
		newShape.rotation(VectorUtils.upcast(dir));
		newShape.position(origin.cpy());
		newShape.vertices = Geom.lineTo(origin.cpy(), to.cpy(), div);
		newShape.vertexNum = newShape.vertices.size();
		return newShape;
	}
	
	
	
	

	//////////////////

	protected static ArrayList<Vector3> fromPoly(float[] values) {
		ArrayList<Vector3> vals = new ArrayList<Vector3>();
		for (int i = 0; i < values.length - 1; i += 2) {
			vals.add(new Vector3(values[i], values[i + 1], 0));
		}

		return vals;
	}

	public static Array<Vector3> generatePolygon(Vector3 origin, Vector3 radius, Vector3 dir, int n) {

		// Log("");
		Vector3 localForward = dir.cpy().nor();
		Vector3 localUp = localForward.cpy().crs(new Vector3(0, 1, 0)).nor();
		Vector3 localRight = localForward.cpy().crs(localUp.cpy().nor());

		if (dir.x == 0)
			dir.x = 0.01f;
		if (dir.y == 0)
			dir.y = 0.01f;

		ArrayList<Vector3> points = new ArrayList<Vector3>();
		points.ensureCapacity(n);
		float angle = MathUtils.PI2 / n;
		dir = dir.scl(MathUtils.radDeg);

		for (int i = 0; i < n; i++) {
			Vector3 r = (origin.cpy().add(radius.cpy()
					.scl(dir.cpy().nor().rot(
							new Matrix4().setToRotationRad((dir.cpy().crs(new Vector3(0, 1, 0))).nor(), (angle * i)))
							.nor())));
			points.add(r);

		}

		Array<Vector3> verts = new Array(true, n, Vector3.class);
		for (int i = 0; i < points.size(); i++)
			verts.add(points.get(i));
		return verts;
	}

	public static Array<Vector3> generatePolygon(Transform transform, int n) {
		ArrayList<Vector3> points = new ArrayList<Vector3>();
		Vector3 origin = transform.GetLocalPosition().cpy();
		Vector3 radius = transform.GetScale().cpy().scl(0.5f);
		// Vector3 dir = (transform.getDirection().cpy());
		Vector3 dir = VectorUtils.downcast(transform.GetLocalRotation());

		Vector3 localForward = dir.cpy().nor();
		Vector3 localUp = localForward.cpy().crs(new Vector3(0, 1, 0)).nor();
		Vector3 localRight = localForward.cpy().crs(localUp.cpy().nor());
		localUp = localForward.cpy().crs(localRight).nor();

		points.ensureCapacity(n);
		float angle = MathUtils.PI2 / n;

		for (int i = 0; i < n; i++) {
			Vector3 r = (origin.cpy().add(radius.cpy()
					.scl(dir.cpy().nor().rot(
							new Matrix4().setToRotationRad((dir.cpy().crs(new Vector3(0, 1, 0))).nor(), (angle * i)))
							.nor())));
			points.add(r);
		}

		Array<Vector3> verts = new Array(true, n, Vector3.class);
		for (int i = 0; i < points.size(); i++)
			verts.add(points.get(i));
		return verts;
	}

}
