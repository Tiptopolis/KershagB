package prime.TEST.zTest1.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Maths;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;

public class MapKernel {

	//graph kernel...
	
	public Texture mapBase;
	public Rect indexArea;// in cells
	public TextureRegion whole;
	public HashMap<VisKernel, TextureRegion> observers;
	public Pixmap mapPix;

	public rNode root;

	_Environment env;

	public Vector3 wrapAxes = new Vector3(1, 0, 0);

	public HashMap<Vector3, rNode> map;

	public MapKernel(_Environment e, Texture base) {
		this.root = new rNode(e, new Transform());
		this.root.body = new Rect(0, 0, base.getWidth(), base.getHeight());

		this.observers = new HashMap<VisKernel, TextureRegion>();

		this.mapBase = base;
		this.whole = new TextureRegion(mapBase);
		this.mapBase.getTextureData().prepare();
		this.mapPix = mapBase.getTextureData().consumePixmap();
		this.map = new HashMap<Vector3, rNode>();
		this.env = e;
		Vector3 unit = e.getUnit();
		float tX = Maths.roundToNearest(base.getWidth(), unit.x);
		float tY = Maths.roundToNearest(base.getHeight(), unit.y);
		this.indexArea = new Rect(0, 0, tX / unit.x, tY / unit.y);
		this.gen();
		this.env.Members.add(this.root);
	}

	public void connectObserver(VisKernel v) {
		this.observers.put(v, new TextureRegion());
	}

	public void update() {
		// Log("- " + mapBase);
		// Log("- " + whole);
		// Log("- " + observers);
		// Log("- " + mapPix);
		// Log("- " + indexArea);

		// Log(this.root.children.size);
		Vector3 unit = this.getUnit();
		for (Entry<VisKernel, TextureRegion> E : this.observers.entrySet()) {
			Vector3 Unit = this.getUnit();
			VisKernel V = E.getKey();
			Vector3 pos = V.root.transform.GetLocalPosition().cpy();
			Vector3 dir = V.c.direction;
			Vector3 mod = new Vector3((int) (pos.x % Unit.x), (int) (pos.y % Unit.y), (int) (pos.z % Unit.z));

			for (Entry<Vector3, rNode> C : this.root.children.entrySet()) {
				C.getValue().isVisible = false;
				C.getValue().wombo = false;
				Vector3 p = C.getValue().transform.GetLocalPosition().cpy();
				Vector3 dst = VectorUtils.dst(pos, p);
				Vector3 wumbo = VectorUtils.dst(pos.cpy().add(dir.cpy()), p);
				if (dst.len() < V.root.body.width) {
					C.getValue().isVisible = true;
					Log(dst.len() + " << =");

					if (dst.len() >= wumbo.len())
						C.getValue().wombo = true;
				}

			}
			// Log(" -> " + pos);
			// Log("___ " + this.projectIndex(pos.cpy()));

		}
		// Log(this.root.children.size());
	}

	// projects a world-space coord into CELL INDEX
	public Vector3 projectIndex(Vector3 p) {
		return this.projectIn(p, this.getUnit());
	}

	public Vector3 projectIn(Vector3 p, Vector3 Unit) {

		Vector3 pos = p.cpy();
		Vector3 mod = new Vector3((int) (pos.x % Unit.x), (int) (pos.y % Unit.y), (int) (pos.z % Unit.z));

		float aX = Maths.roundToNearest(pos.x - mod.x, Unit.x);
		float aY = Maths.roundToNearest(pos.y - mod.y, Unit.y);
		float aZ = Maths.roundToNearest(pos.z - mod.z, Unit.z);
		if (Math.signum(pos.x) < 0)
			aX -= Unit.x;
		if (Math.signum(pos.y) < 0)
			aY -= Unit.y;
		if (Math.signum(pos.z) < 0)
			aZ -= Unit.z;

		return pos.set(((int) aX / Unit.x), ((int) aY / Unit.y), ((int) aZ / Unit.z));

	}

	public Array<rNode> getRegion(Rect r) {
		// normalized, indicized rect
		Array<rNode> result = new Array<rNode>(true, (int) (r.width * r.height), rNode.class);
		for (int x = (int) r.minX; x < r.minX + r.width; x++) {
			for (int y = (int) r.minY; y < r.minY + r.height; y++) {
				result.add(this.root.getChildAt(new Vector3((int) x, (int) y, 0)));
			}
		}

		return result;
	}

	public Vector3 getUnit() {
		return this.env.getUnit();
	}

	public void dispose() {
		this.mapBase.dispose();
		this.observers.clear();
	}

	public void gen() {
		// generate & subdivide all nodes?
		// this.root.subdivide(1,3);
		this.root.mapTo(indexArea, true);

	}

}
