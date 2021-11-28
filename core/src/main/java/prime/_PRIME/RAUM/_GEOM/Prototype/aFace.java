package prime._PRIME.RAUM._GEOM.Prototype;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.aGeoset;

public class aFace {
	public Array<aVertex> vertices;
	public Array<aLine> lines;
	public boolean isSurface = false;

	public aFace(aVertex... vertices) {
		// forms a hull from the vertices, in insertion order
		this.vertices = new Array<aVertex>(vertices);
		this.genLines();
	}

	public aFace(aLine... lines) {

		this.vertices = new Array<aVertex>(true, lines.length * 2, aVertex.class);
		this.lines = new Array<aLine>(lines);
		for (int l = 0; l < lines.length; l++) {
			aLine L = lines[l];
			this.vertices.add(L.from);
			this.vertices.add(L.to);

		}

	}

	public aFace(Transform... transforms) {
		this.vertices = new Array<aVertex>(true, transforms.length, aVertex.class);
		for (int i = 0; i < transforms.length; i++) {
			aVertex v = new aVertex(transforms[i]);
			this.vertices.add(v);
		}
		this.genLines();
	}

	public Vector3 getCentroid() {
		// barycenter; average of verts
		// sumX*(1/VertN)
		Vector3 c = new Vector3(0, 0, 0);

		for (aVertex V : this.vertices) {
			c.add(V.get.cpy());
		}
		c.scl(1f / (this.vertices.size));

		return c;
	}

	// global normal???

	public Vector3 getNormalPositive(Vector3 dir) {

		Vector3 lP = this.getCentroid().cpy().add((dir.cpy().nor().scl(MathUtils.PI2)));

		return lP;
	}

	public Vector3 getNormalNegative(Vector3 dir) {

		Vector3 lN = this.getCentroid().cpy().add((dir.cpy().nor().scl(-1*MathUtils.PI2)));

		return lN;
	}

	// assumes points are the hull
	private void genLines() {
		this.lines = new Array<aLine>(true, this.vertices.size, aLine.class);

		for (int i = 0; i < this.vertices.size; i++) {
			int n = i + 1;
			if (i == this.vertices.size - 1)
				n = 0;
			this.lines.add(new aLine(this.vertices.get(i), this.vertices.get(n)));
		}
	}

	public void clear() {
		this.vertices.clear();
		this.lines.clear();
	}

	public Polygon toPoly() {
		ArrayList<Vector2> tmpVerts = new ArrayList<Vector2>();
		for (aVertex v : this.vertices) {
			tmpVerts.add(VectorUtils.downcast(v.get));
		}

		Polygon result = new Polygon(VectorUtils.disassembleVects(tmpVerts.toArray()));
		tmpVerts.clear();
		return result;
	}

	public String toLog() {
		String log = "";

		for (int i = 0; i < this.lines.size; i++) {
			aLine l = this.lines.get(i);
			log += "[" + i + "]" + l.toString() + "\n";
		}
		log += "\n";
		return log;
	}

	public String toLog(aGeoset owner) {
		String log = "";

		for (int i = 0; i < this.lines.size; i++) {
			aLine l = this.lines.get(i);
			log += "[" + i + "]||" + owner.lines.indexOf(l, true) + "" + l.toString() + "\n";
		}
		log += "\n";
		return log;
	}

	public Vector3 getNormalPositiveX() {
		Vector3 c = new Vector3(1, 1, 1);

		for (aLine L : this.lines) {
			Vector3 mid = L.getMidpoint();
			Vector3 dir = VectorUtils.dir(L.from.get.cpy(), L.to.get.cpy());
			Vector3 lP = mid.cpy().add((dir.cpy().crs(0, 1, 0).nor()).scl(MathUtils.PI2));

			c.add(lP);
		}
		c.scl(1f / (this.lines.size));

		return c;
	}

	public Vector3 getNormalNegativeX() {
		Vector3 c = new Vector3(1, 1, 1);

		for (aLine L : this.lines) {
			// L.getNormalizedNegative(); but with PI2 instead of PI
			Vector3 mid = L.getMidpoint();
			Vector3 dir = VectorUtils.dir(L.from.get.cpy(), L.to.get.cpy());
			Vector3 lN = mid.cpy().add(dir.cpy().crs(0, -1, 0).nor().scl(MathUtils.PI2));

			c.add(lN);
		}
		c.scl(1f / (this.lines.size));

		return c;
	}
}
