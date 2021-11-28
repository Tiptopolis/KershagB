package prime.TEST.ConTest2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.N_M.UTIL.N_Operator;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;
import squidpony.squidmath.RNG;

public class lEnv extends _Environment {

	public boolean running = false;

	public Camera observer;
	protected Thread thread;

	protected Array<LuciferParticle> perspectiveVisible = new Array<LuciferParticle>(true, 0, LuciferParticle.class);
	public SnapshotArray<LuciferParticle> currentVisible = new SnapshotArray<LuciferParticle>();
	public Array<LuciferParticle> dstSorted = new Array<LuciferParticle>(true, 0, LuciferParticle.class);
	public SnapshotArray<LuciferParticle> toDraw = new SnapshotArray<LuciferParticle>();

	public lEnv(Camera obs, Vector3 size, int ents) {
		super("lEnv");
		this.observer = obs;
		this.Dimension = new uVector(size);
		this.gen(ents);

		this.running = true;
		this.thread = new Thread("SimThread") {
			@Override
			public void run() {
				if (running) {
					try {
						lEnv.this.simLoop();
					} catch (Throwable t) {
						if (t instanceof RuntimeException)
							throw (RuntimeException) t;
						else
							throw new GdxRuntimeException(t);
					}
				}
			}
		};
		this.running = true;
		this.thread.start();

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		this.currentVisible.clear();

	}

	public void simLoop() {

		while (running) {
			// Camera C = this.copy(this.perspective.perspective.camera);
			Camera C = this.observer;
			synchronized (this.perspectiveVisible) {
				synchronized (this.dstSorted) {

					this.perspectiveVisible.clear();
					this.dstSorted.clear();

					// raw visible
					for (int i = 0; i < this.Members.size(); i++) {
						_Entity e = this.Members.get(i);

						if (e instanceof LuciferParticle) {
							LuciferParticle E = (LuciferParticle) e;
							boolean b = (C.frustum.sphereInFrustum(((LuciferParticle) E).position(),
									this.getUnit().len() * 2))
									&& C.frustum.planes[0].testPoint(E.position()) == Plane.PlaneSide.Front;
							if (b && !this.perspectiveVisible.contains(E, true)) {

								this.perspectiveVisible.add(E);

							}
						}
					}

					// accessible cache
					this.currentVisible = new SnapshotArray<LuciferParticle>(this.perspectiveVisible.toArray());
					ArrayList<LuciferParticle> sn = new ArrayList<LuciferParticle>(this.perspectiveVisible.size);

					for (int i = 0; i < perspectiveVisible.size - 1; i++) {

						sn.add(this.perspectiveVisible.get(i));
					}
					Collections.sort(sn, distanceComparator(this.observer.position));

					for (int i = 0; i < this.perspectiveVisible.size - 1; i++) {
						this.dstSorted.add(sn.get(i));
					}
					this.toDraw = new SnapshotArray<LuciferParticle>(this.dstSorted);
					// sn.clear();
				}
				// Metatron.Console.post(this.currentVisible.size + " : "+this.dstMap.size() + "
				// <<<<");

			}
		}
	}

	public void render() {
		// Log(this.toDraw.size);
		if (this.toDraw.size > 0) {
			int max = 16000;
			SnapshotArray<LuciferParticle> dr = new SnapshotArray<LuciferParticle>(this.toDraw);
			int s = dr.size - 1;

			int t = s;
			if (s > max) {
				t = s - max - 1;
				// 0 being farthest
				dr.removeRange(0, t);
			}
			s = dr.size - 1;

			// ~8000 max batch size...
			//dropped to 256 for shadering
			float b = 256;
			float mod = s % b;
			float rem = (s / b) + 1;

			//can comp this into a batch-group that gets calculated & filled by simLoop????
			
			//Log(s + ">" + t);
			for (int i = 0; i < (int) rem; i++) {
				// Log("== " + b*i +" => "+ b*(i+1));//index range
				
				//shader & rect go here...
				Sketcher.begin();
				for (int j = 0; j < b; j++) {
					if ((b * i) + j > s || (b * i) + j > max)
						break;

					// Log("("+b+"*"+i+")"+"+"+j);
					// Log((b*i)+j + "/" + s);
					int f = (int) ((b * i) + j);

					// Log(">" + f + " / " + s);
					LuciferParticle L = dr.get(f);
					L.render();
				}
				Sketcher.end();
			}
		}
	}

	@Override
	public Vector3 getSize() {
		return this.Dimension.V3();
	}

	@Override
	public Vector3 getUnit() {
		return new Vector3(8, 8, 8);
	}

	public void gen(int num) {
		RNG RN = new RNG();
		Vector3 size = this.getSize();

		for (int i = 0; i < num; i++) {
			float nX = RN.nextFloat(size.x);
			float nY = RN.nextFloat(size.y);
			float nZ = RN.nextFloat(size.z);

			this.genNew(new Vector3(nX, nY, nZ));
		}

	}

	public LuciferParticle genNew(Vector3 at) {
		Vector3 pos = at.scl(this.getUnit());
		LuciferParticle L = new LuciferParticle(this);
		L.position(pos);

		return L;
	}

	public void dispose() {
		this.currentVisible.clear();
		this.Members.clear();
	}

	//////
	public static Comparator<LuciferParticle> distanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<LuciferParticle>() {
			@Override
			public int compare(LuciferParticle p0, LuciferParticle p1) {
				double ds0 = VectorUtils.dst(p0.position(), finalP).len();
				double ds1 = VectorUtils.dst(p1.position(), finalP).len();
				return Double.compare(ds1, ds0);
			}

		};
	}
}
