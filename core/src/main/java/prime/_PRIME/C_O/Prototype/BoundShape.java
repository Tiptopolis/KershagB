package prime._PRIME.C_O.Prototype;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Plane.PlaneSide;
import com.badlogic.gdx.utils.Array;
import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;

public class BoundShape implements Iterable<Vector3> {

	// 2D
	// temporary, just-in-time 2d geometry, capturable
	public Transform transform;
	public Color fillColor = Color.WHITE;
	public Color lineColor = Color.GRAY;
	public int vertexNum = 0;
	public float vertexSize = 2;
	public float lineWidth = 1;

	public ArrayList<Vector3> vertices = new ArrayList<Vector3>();
	protected int OriginIndex = 0; // if 0, return position

	private boolean dirty = false;
	public boolean debug = true;
	public boolean fill = false;
	public boolean lines = true;
	public boolean linesOutter = true;
	public boolean linesInner = true;

	public BoundShape() {
		this.transform = new Transform();
	}

	//////
	// TRANSFORM

	// Position
	public Vector3 position() {
		return this.position(true);
	}

	public Vector3 position(boolean local) {
		if (local)
			return this.transform.GetLocalPosition();
		else
			return this.transform.GetPosition();
	}

	public void position(Vector3 pos) {
		this.position(pos, true);
	}

	public void position(Vector3 pos, boolean local) {
		if (local)
			this.transform.SetLocalPosition(pos);
		else
			this.transform.SetPosition(pos);
	}
	// Position

	// Rotation
	public Quaternion rotation() {
		return this.rotation(true);
	}

	public Quaternion rotation(boolean local) {
		if (local)
			return this.transform.GetLocalRotation();
		else
			return this.transform.GetRotation();
	}

	public void rotation(Vector3 dir) {
		this.rotation(dir, true);
	}

	public void rotation(Quaternion rot) {
		this.rotation(rot, true);
	}

	public void rotation(Vector3 dir, boolean local) {
		if (local)
			this.transform.SetLocalRotation(VectorUtils.upcast(dir, 1));
		else
			this.transform.SetRotation(VectorUtils.upcast(dir, 1));
	}

	public void rotation(Quaternion rot, boolean local) {
		if (local)
			this.transform.SetLocalRotation(rot);
		else
			this.transform.SetRotation(rot);
	}
	// Rotation

	// Scale
	public Vector3 scale() {
		return this.scale(true);
	}

	public Vector3 scale(boolean local) {
		if (local)
			return this.transform.GetLocalScale();
		else
			return this.transform.GetScale();
	}

	public void scale(Vector3 scl) {
		this.position(scl, true);
	}

	public void scale(Vector3 scl, boolean local) {
		if (local)
			this.transform.SetLocalScale(scl);
		else
			this.transform.SetScale(scl);
	}
	// Scale

	// TRANSFORM
	//////

	//////
	//VERTICES
	public int size()
	{
		return this.vertices.size();
	}
	
	public Vector3 get(int index)
	{
		return this.vertices.get(index);
	}
	
	public boolean isEmpty()
	{
		return this.vertices.isEmpty();
	}
	
	public void clear()
	{
		this.vertices.clear();
	}
	
	public void add(Vector3 ...V)
	{
		for(Vector3 v : V)
		{
			this.vertices.add(v);
		}
	}
	
	public int indexOf(Vector3 v)
	{
		return this.vertices.indexOf(v);
	}
	//VERTICES
	//////
	
	public float perimeter() {
		float sum = 0f;
		for (int i = 0; i < this.vertexNum; i++) {
			sum = sum + this.vertices.get(i).dst(this.vertices.get(i + 1));
		}
		return sum;
	}

	// return signed area of polygon
	public double area() {
		double sum = 0.0;
		for (int i = 0; i < this.vertexNum; i++) {
			sum = sum + (this.vertices.get(i).x * this.vertices.get(i + 1).y)
					- (this.vertices.get(i).y * this.vertices.get(i + 1).x)
					- (this.vertices.get(i).z * this.vertices.get(i + 1).z);
		}
		return 0.5 * sum;
	}

	/** Returns whether an x, y pair is contained within the polygon. */

	public boolean contains(float x, float y) {

		return (this.toPolygon().contains(x, y));

	}

	public boolean contains(Vector2 v) {

		return contains(v.x, v.y);
	}

	public boolean contains(Vector3 v) {
		return this.contains(v.x, v.y, v.z);
	}

	public boolean contains(float x, float y, float z) {
		// bandaid solution
		Vector3 v = new Vector3(x, y, z);
		Vector3 pos = this.position();

		Array<Vector3> pts = new Array<Vector3>(true, this.vertexNum * 2, Vector3.class);
		for (int i = 0; i < this.vertices.size(); i++) {
			int n = i + 1;
			if (i == this.vertices.size() - 1)
				n = 0;

			Vector3 P = this.vertices.get(i);
			Vector3 N = this.vertices.get(n);

			Line l = new Line(P, N);
			Vector3 M = l.getMidpoint();

			pts.add(P);
			pts.add(M);
		}

		for (Vector3 p : pts) {
			// rig up a counter, testPt dst is greater than 2 points on the same side, its
			// outside
			if (VectorUtils.dst(pos, v).len() > VectorUtils.dst(pos, p).len())
				return false;
		}

		return true;
	}

	public BoundShape cpy() {
		BoundShape newShape = new BoundShape();
		newShape.scale(this.scale().cpy());
		newShape.rotation(this.rotation().cpy());
		newShape.position(this.position().cpy());

		newShape.vertices = (ArrayList<Vector3>) this.vertices.clone();
		newShape.vertexNum = this.vertexNum;
		newShape.fill = this.fill;
		newShape.fillColor = this.fillColor;
		newShape.lines = this.lines;
		newShape.linesOutter = this.linesOutter;
		newShape.linesInner = this.linesInner;
		newShape.lineColor = this.lineColor;

		return newShape;
	}

	public void setColor(Color color) {
		this.lineColor = color;
		this.fillColor = color;
	}

	public void setColor(Color line, Color fill) {
		this.lineColor = line;
		this.fillColor = fill;
	}

	public void drawShape() {
		int o = 0;
		int n = 0;
		int s = this.vertices.size() - 1;
		Vector3 next;
		Vector3 last;
		Vector3 pos = this.position().cpy();
		

		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pos.x, pos.y, 8);

		for (int i = 0; i < s + 1; i++) {
			Vector3 v = this.vertices.get(i);
			Sketcher.setColor(this.lineColor);

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			next = this.vertices.get(n);

			if (this.lines) {
				if (this.linesOutter) {
					Sketcher.Drawer.line(new Vector2(next.x, next.y), new Vector2(v.x, v.y), this.lineColor,
							this.lineWidth);
				}
				if (this.linesInner) {
					Sketcher.Drawer.line(new Vector2(v.x, v.y), new Vector2(pos.x, pos.y), this.lineColor,
							this.lineWidth);
				}
			}

			Sketcher.setColor(this.fillColor);
			Sketcher.Drawer.filledCircle(v.x, v.y, this.vertexSize + (this.vertexSize % v.z));

		}

		if (!this.vertices.isEmpty()) {
			last = this.vertices.get(0);
			Sketcher.setColor(new Color(1, 1, 1, 0.5f));
			Sketcher.Drawer.filledCircle(last.x, last.y, this.vertexSize);
		}
	}

	public void rebind(Vector3 at) {
		this.position(at);
		this.rebind();
	}

	public void rebind() {
		if (this.transform != null) {
			Transform t = this.getTransform();
			this.position(t.GetLocalPosition());
			Vector3 s = this.getTransform().GetScale();
			this.scale(s);
			this.rotation(t.GetLocalRotation());
			this.vertices.clear();
			this.vertices = Geom.generatePolygon(t, this.vertexNum);
		} else {
			this.rebind();
		}
	}

	public BoundShape project(Camera c) {

		Array<Vector3> tmp = new Array<Vector3>(true, this.vertices.size(), Vector3.class);
		for (Vector3 v : this.vertices) {
			// if (c.frustum.planes[0].testPoint(v) == PlaneSide.Front)
			tmp.add(v);
		}

		for (Vector3 v : tmp) {
			boolean b = false;
			if (!(c.frustum.planes[0].testPoint(v) == PlaneSide.Front))
				b = true;

			c.project(v);

		}
		this.vertices.clear();
		for (int i = 0; i < tmp.size; i++) {

			this.vertices.add(tmp.get(i));
		}
		this.position(c.project(this.position()));
		return this;
	}

	////
	//

	public Transform getTransform() {

		return this.transform;
	}

	public BoundShape fromPoly(Polygon p) {

		ArrayList<Vector2> vert = VectorUtils.assembleVect2(p.getTransformedVertices());

		Vector2 pos = new Vector2(p.getX(), p.getY());
		Vector2 size = new Vector2(p.getScaleX(), p.getScaleY());
		Vector3 position = VectorUtils.upcast(pos);
		Vector3 scale = VectorUtils.upcast(size);
		ArrayList<Vector3> shape = new ArrayList<Vector3>();
		for (int i = 0; i < vert.size(); i++) {
			shape.add(VectorUtils.upcast(vert.get(i)));
		}

		BoundShape newShape = new BoundShape();

		newShape.scale(scale);
		newShape.position(position);
		newShape.rotation(new Vector3(0, 1, 0));
		newShape.vertices = shape;

		return newShape;
	}
	////////////////

	public Polygon toPolygon() {

		ArrayList<Vector2> tmpVerts = new ArrayList<Vector2>();
		for (Vector3 v : this.vertices) {
			tmpVerts.add(VectorUtils.downcast(v.cpy()));
		}

		Polygon result = new Polygon(VectorUtils.disassembleVects(tmpVerts.toArray()));

		if (this.transform != null) {
			Vector3 scl = this.transform.GetLocalScale();
			result.setOrigin(this.transform.GetLocalPosition().x, this.transform.GetLocalPosition().y);
			// result.setScale(scl.x, scl.y);
			Quaternion rot = this.transform.GetLocalRotation();
			result.setRotation((float) Math.atan2(rot.x, rot.y));
			result.setPosition(this.transform.GetLocalPosition().x, this.transform.GetLocalPosition().y);
			// result.setOrigin(this.transform.GetLocalPosition().x,
			// this.transform.GetLocalPosition().y);
		}

		return result;
	}

	@Override
	public String toString() {
		String out = "";
		out += this.vertexNum + "";
		out += "\n";

		out += "" + this.position();
		out += "\n";
		out += "" + this.rotation();
		out += "\n";
		out += "" + this.scale();
		out += "\n";

		return out;
	}

	@Override
	public Iterator<Vector3> iterator() {
		Iterator<Vector3> it = new Iterator<Vector3>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < vertices.size() && vertices.get(currentIndex) != null;
			}

			@Override
			public Vector3 next() {
				return vertices.get(currentIndex++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		return it;
	}

}
