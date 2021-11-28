package prime._PRIME.RAUM._GEOM;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import java.util.Map.Entry;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM.NIX._BoundShape;
import prime._PRIME.RAUM._GEOM.Prototype.aFace;
import prime._PRIME.RAUM._GEOM.Prototype.aLine;
import prime._PRIME.RAUM._GEOM.Prototype.aVertex;
import prime._PRIME.RAUM._GEOM.UTIL.ThingCounter;
import prime._PRIME.SYS.NIX._Property;

public class aGeoset {

	_BoundShape B;

	public aVertex origin;
	public Array<aVertex> vertices;
	public Array<aLine> diag; // linesInner
	public Array<aLine> hull; // linesOutter
	public Array<aLine> lines;// allLines
	public Array<aFace> faces;

	public aGeoset() {
		this.origin = new aVertex(new Transform());
		this.vertices = new Array<aVertex>(true, 0, aVertex.class);

		this.diag = new Array<aLine>(true, 0, aLine.class);
		this.hull = new Array<aLine>(true, 0, aLine.class);
		this.lines = new Array<aLine>(true, 0, aLine.class);

		this.faces = new Array<aFace>(true, 0, aFace.class);
	}

	public aGeoset(aVertex... verts) {
		this.origin = new aVertex(new Transform());
		this.vertices = new Array<aVertex>(true, 0, aVertex.class);

		this.diag = new Array<aLine>(true, 0, aLine.class);
		this.hull = new Array<aLine>(true, 0, aLine.class);
		this.lines = new Array<aLine>(true, 0, aLine.class);

		this.faces = new Array<aFace>(true, 0, aFace.class);

		for (aVertex v : verts) {
			this.vertices.add(v);
		}
	}

	public aGeoset(Vector3... verts) {
		this.origin = new aVertex(new Transform());
		this.vertices = new Array<aVertex>(true, 0, aVertex.class);

		this.diag = new Array<aLine>(true, 0, aLine.class);
		this.hull = new Array<aLine>(true, 0, aLine.class);
		this.lines = new Array<aLine>(true, 0, aLine.class);

		this.faces = new Array<aFace>(true, 0, aFace.class);

		for (Vector3 v : verts) {
			this.vertices.add(new aVertex(v));
		}
	}
	// apply transform...

	public void addVertex(@Null Transform t, Vector3... vectors) {
		// verts are typically generated in world-space
		// need a more constructive method for adding 'local-space' vertices lol
		for (int i = 0; i < vectors.length; i++) {

			if (t == null) {
				t = new Transform();
				if (this.origin == null)
					this.origin = new aVertex(t.GetPosition());
				this.vertices.add(new aVertex(t, vectors[i], true));

			} else {
				if (this.origin == null)
					this.origin = new aVertex(t.GetPosition());
				this.vertices.add(new aVertex(t, vectors[i], false));
			}

		}
	}

	public void addVertex(Transform... transforms) {
		// assumes parent-child linkages already present lol
		for (int i = 0; i < transforms.length; i++) {
			this.vertices.add(new aVertex(transforms[i]));
			if (this.origin == null)
				this.origin = this.vertices.get(0);
		}
	}

	public void addLine(aLine... lines) {
		for (int i = 0; i < lines.length; i++) {
			this.lines.add(lines[i]);
			this.vertices.add(lines[i].from);
			this.vertices.add(lines[i].to);
		}
	}

	public void addLine(aVertex a, aVertex b) {
		this.addLine(new aLine(a, b));
	}

	public void addLine(Vector3 a, Vector3 b) {

		this.addLine(new aLine(a, b));

	}

	public void addFace(aVertex... verts) {
		this.faces.add(new aFace(verts));
	}

	public void addFace(aFace... faces) {
		for (int i = 0; i < faces.length; i++) {
			aFace F = faces[i];
			this.faces.add(F);
			for (int l = 0; l < F.lines.size; l++) {
				aLine L = F.lines.get(l);
				this.lines.add(L);
				this.vertices.add(L.from);

			}
		}
	}

	public void addFace(aLine... lines) {
		if (checkCircuit(lines)) {
			// if each vertex is represented twice, shape is closed-circuit
			this.faces.add(new aFace(lines));
		}
	}

	// if not a circuit, interpret as hull and convert to points?
	public boolean checkCircuit(aLine... lines) {

		Array<aVertex> verts = new Array<aVertex>(true, lines.length * 2, aVertex.class);

		for (int i = 0; i < lines.length; i++) {
			verts.add(lines[i].from);
			verts.add(lines[i].to);
		}

		ThingCounter c = new ThingCounter(verts.toArray());
		// Log(c.things.entrySet());

		for (aVertex v : verts) {
			// Log(c.getCountOf(v));
			if (c.getCountOf(v) < 2) {
				c.dispose();
				return false;
			}
		}
		verts.clear();
		c.dispose();
		return true;
	}

	// CENTER=>VERTEX
	public aGeoset diag() {

		for (int i = 0; i < this.vertices.size; i++) {
			aVertex V = this.vertices.get(i);
			aLine newLine = new aLine(this.origin, V);
			this.lines.add(newLine);
			this.diag.add(newLine);
		}

		return this;
	}

	// VERTEX=>VERTEX
	public aGeoset hull() {
		int o = 0;
		int n = 0;
		int s = this.vertices.size - 1;
		aVertex next;
		aVertex last;

		for (int i = 0; i < s + 1; i++) {
			aVertex v = this.vertices.get(i);

			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);

			next = this.vertices.get(n);
			aLine l = new aLine(v, next);
			this.lines.add(l);
			this.hull.add(l);
		}
		return this;
	}

	public aGeoset skin() {
		int o = 0;
		int n = 0;
		int s = this.vertices.size - 1;
		for (int i = 0; i < this.vertices.size; i++) {
			int f = s + 1;
			n = i + 1;
			if (n >= f)
				n = (n % f);
			aLine d0 = this.diag.get(i);
			aLine d1 = this.diag.get(n);
			aLine h = this.hull.get(i);
			this.faces.add(new aFace(d0, h, d1));
		}

		return this;
	}

	public Vector3 getCentroid() {
		// barycenter; average of verts
		// sumX*(1/VertN)
		Vector3 c = new Vector3();

		for (aVertex V : this.vertices) {
			c.add(V.get.cpy());
		}
		c.scl(1 / this.vertices.size);

		return c;
	}

	public void origin(Vector3 at) {
		if (this.origin == null) {
			this.origin = new aVertex(new Transform());
		}
		this.origin.set(at);
	}

	public aVertex origin() {
		if (this.origin == null) {
			this.origin = new aVertex(new Transform());
		}
		return this.origin;
	}

	public void setParent(Transform t) {
		for (aVertex v : this.vertices) {
			v.getTransform().SetParent(t);
		}
	}

	public aGeoset cpy() {
		aGeoset newG = new aGeoset();
		newG.origin = this.origin.cpy();
		// need effective way of ensuring proper linkage between vertices & lines & such
		// to reduce weight

		// generate link maps
		// Log("!!");

		Array<LineLink> lMap = new Array<LineLink>(true, this.lines.size, LineLink.class);

		for (int v = 0; v < this.vertices.size; v++) {
			newG.vertices.add(this.vertices.get(v).cpy());
		}

		for (int l = 0; l < this.lines.size; l++) {
			aLine L = this.lines.get(l);
			int pfx = -1;

			if (this.diag.contains(L, true) || this.diag.contains(L, false))
				pfx = 0;
			if (this.hull.contains(L, true) || this.hull.contains(L, false))
				pfx = 1;

			int vA = this.vertices.indexOf(L.from, true);
			int vB = this.vertices.indexOf(L.to, true);

			LineLink lL = new LineLink(vA, vB, this.lines.indexOf(L, true), pfx);
			lMap.add(lL);

		}

		for (int l = 0; l < this.lines.size; l++) {
			LineLink L = lMap.get(l);
			int iA = L.indexA;
			int iB = L.indexB;
			aLine nL;
			// Log(iA + "_" + iB);
			if (iA != -1)
				nL = new aLine(newG.vertices.get(iA), newG.vertices.get(iB));
			else
				nL = new aLine(newG.origin, newG.vertices.get(iB));
			newG.lines.add(nL);
			if (L.prefix == 0)
				newG.diag.add(nL);
			if (L.prefix == 1)
				newG.hull.add(nL);
		}

		for (int f = 0; f < this.faces.size; f++) {
			Array<LineLink> fMap = new Array<LineLink>(true, this.vertices.size, LineLink.class);
			for (int l = 0; l < this.faces.get(f).lines.size; l++) {
				aLine L = this.faces.get(f).lines.get(l);
				int vA = this.vertices.indexOf(L.from, true);
				int vB = this.vertices.indexOf(L.to, true);

				LineLink lL = new LineLink(vA, vB, this.lines.indexOf(L, true), -1);
				fMap.add(lL);

			}
			Array<aLine> faceLines = new Array<aLine>(true, this.faces.get(f).lines.size, aLine.class);
			for (int l = 0; l < fMap.size; l++) {
				LineLink L = fMap.get(l);
				int iA = L.indexA;
				int iB = L.indexB;
				// Log(iA + "_" + iB);
				aLine nL = newG.lines.get(L.indexI);
				faceLines.add(nL);
			}
			newG.addFace(faceLines.toArray());
			faceLines.clear();
			fMap.clear();
		}

		lMap.clear();
		return newG;
	}

	public void dispose() {
		for (aVertex v : this.vertices) {
			v.clear();

		}
		this.vertices.clear();
		for (aLine l : this.lines) {
			l.clear();
		}
		this.lines.clear();
		this.hull.clear();
		this.diag.clear();
		for (aFace f : this.faces)
			f.clear();
		this.faces.clear();
	}

	public String whatLink(aLine l) {
		// indicates wether & how points are connected
		String result = "";
		String opn = "[";
		String cls = "]";

		int indexI = -1;
		int indexA = -1;
		int indexB = -1;
		String A = "?";
		String B = "?";
		String op = "_>";

		String isDiag = "D";
		String isShell = "S";
		String prefix = "G";

		boolean hasLine = false;
		// has a line
		if (this.lines.contains(l, true)) {
			hasLine = true;
			op = "=>";
			indexI = this.lines.indexOf(l, true);
			indexA = this.vertices.indexOf(l.from, true);
			indexB = this.vertices.indexOf(l.to, true);

		}
		// has line with same data as?
		else if (this.lines.contains(l, false)) {
			hasLine = true;
			op = "->";
			indexI = this.lines.indexOf(l, false);
			boolean hasA = this.vertices.contains(l.from, true);
			boolean hasB = this.vertices.contains(l.to, true);

			if (hasA)
				indexA = this.vertices.indexOf(l.from, true);
			if (hasB)
				indexB = this.vertices.indexOf(l.to, true);

		}

		if (hasLine) {
			if (this.diag.contains(l, true) || this.diag.contains(l, false))
				prefix = "{" + isDiag + this.diag.indexOf(l, false) + "}";
			if (this.hull.contains(l, true) || this.hull.contains(l, false))
				prefix = "{" + isShell + this.hull.indexOf(l, false) + "}";

			if (indexA != -1 && this.vertices.get(indexA) != null)
				A = this.vertices.get(indexA).get.toString();
			if (indexA == -1)
				A = this.origin.get.toString();
			if (indexB != -1 && this.vertices.get(indexB) != null)
				B = this.vertices.get(indexB).get.toString();
			if (indexB == -1)
				B = this.origin.get.toString();
			result += indexI + prefix + opn + indexA + op + indexB + cls + A + op + B + "\n";
			return result;
		}

		op = "_>_";

		if (this.vertices.contains(l.from, true)) {
			indexA = this.vertices.indexOf(l.from, true);
			opn = "[";
			A = l.from.get.toString();
		} else if (this.vertices.contains(l.from, false)) {
			indexA = this.vertices.indexOf(l.from, false);
			opn = "{*";
			A = l.from.get.toString();
		}

		if (this.vertices.contains(l.to, true)) {
			indexB = this.vertices.indexOf(l.to, true);
			cls = "]";
			B = l.to.get.toString();
		} else if (this.vertices.contains(l.to, false)) {
			indexB = this.vertices.indexOf(l.to, false);
			cls = "*}";
			B = l.to.get.toString();
		}

		result += indexI + opn + indexA + op + indexB + cls + A + op + B + "\n";
		return result;
	}

	public String toLog() {
		String log = "";

		log += "Verts: " + this.vertices.size + "\n";
		for (int v = 0; v < this.vertices.size; v++) {
			aVertex V = this.vertices.get(v);
			log += V.get+"\n";
		}

		log += "Lines: " + this.lines.size + " :: " + this.diag.size + "/" + this.hull.size + "\n";

		for (int l = 0; l < this.lines.size; l++) {
			aLine L = this.lines.get(l);
			int a = this.vertices.indexOf(L.from, true);
			int b = this.vertices.indexOf(L.to, true);
			// log += "[" + a + "->" + b + "]" + L.toString() + "\n";
			log += this.whatLink(L);
		}
		log += "\n";
		log += "Faces: " + this.faces.size + "\n";
		for (int f = 0; f < this.faces.size; f++) {
			log += this.faces.get(f).toLog(this);

		}
		return log;
	}

	// lineLink
	private class LineLink {

		int indexA;
		int indexB;

		int indexI;
		int prefix = -1;

		public LineLink(int a, int b, int i, int p) {
			this.indexA = a;
			this.indexB = b;
			this.indexI = i;
			this.prefix = p;
		}

		@Override
		public String toString() {
			String s = "";

			s += indexI + ":[" + indexA + "->" + indexB + "]";

			return s;

		}
	}

	//////////////////
	/////////////////

}
