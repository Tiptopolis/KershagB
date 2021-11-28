package prime.TEST.ConTest3;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.Comparator;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.OroborosList;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.SYS.NIX._Entity;

public class LuciferParticle extends _Entity {

	private Camera reference;

	private PerspectiveCamera self;
	public Transform transform;
	public OroborosList<Transform> memory;
	public uVector flavor; // (age, zDepth)

	public LuciferParticle(lEnv environment) {
		this.reference = environment.observer;
		this.Environment = environment;
		this.Environment.Members.add(this);
		// this.self = new PerspectiveCamera(90,1,1);
		this.self = new PerspectiveCamera(33, 33, 33);
		this.memory = new OroborosList(1, 4, 1);
		this.transform = new Transform();
	}

	//////
	// TRANSFORM
	public void position(Vector3 position) {
		this.position(position, true);
	}

	public void position(Vector3 position, boolean local) {
		if (local)
			this.transform.SetLocalPosition(position);
		else
			this.transform.SetPosition(position);
	}

	public Vector3 position() {
		return this.position(true);
	}

	public Vector3 position(boolean local) {
		if (local)
			return this.transform.GetLocalPosition();
		else
			return this.transform.GetPosition();
	}

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
		this.scale(scl, true);
	}

	public void scale(Vector3 scl, boolean local) {
		if (local)
			this.transform.SetLocalScale(scl);
		else
			this.transform.SetScale(scl);
	}

	// TRANSFORM
	//////

	public void render() {
		this.memory.add(this.transform.cpy());
		this.draw();
	}

	protected void draw() {

		Vector3 pos = this.reference.project(this.position().cpy());
		int m = this.memory.size() - 1;
		Color c = Color.RED;

		Vector3 unit = this.Environment.getUnit();
		float uSize = unit.len() / 3;

		for (int i = m; i > 0; i--) {
			float n = (1f / m) * 1f;
			// Log(n);
			Vector3 at = this.reference.project(this.memory.get(i).GetLocalPosition().cpy());
			Vector3 v = this.memory.get(i).GetLocalPosition();
			Vector3 s = this.memory.get(i).GetLocalScale();
			float z2 = (s.len() / reference.position.cpy().sub(v.cpy()).len());
			z2 = (z2 * (unit.len() / 3)) / 2;
			z2 = z2 * (unit.len() / 3);
			Vector3 Z2 = new Vector3(z2, z2, z2);
			c.a = 1 - n;

			Sketcher.setColor(c);
			Sketcher.Drawer.filledCircle(at.x, at.y, uSize * z2);
			Sketcher.setColor(new Color(0, 0, 0, z2));
			Sketcher.Drawer.circle(at.x, at.y, uSize * z2);
		}
	}

	public static Comparator<LuciferParticle> distanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<LuciferParticle>() {
			@Override
			public int compare(LuciferParticle p0, LuciferParticle p1) {
				double ds0 = VectorUtils.dst(p0.position(), finalP).len();
				double ds1 = VectorUtils.dst(p1.position(), finalP).len();
				return Double.compare(ds0, ds1);
			}

		};
	}
}
