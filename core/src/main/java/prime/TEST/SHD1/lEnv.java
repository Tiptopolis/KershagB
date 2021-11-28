package prime.TEST.SHD1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Rect;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.N_M.UTIL.N_Operator;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;
import squidpony.squidmath.RNG;

public class lEnv extends _Environment {

	public boolean running = false;

	public Camera observer;
	protected Thread thread;
	ShaderProgram shader;

	protected Array<LuciferParticle> perspectiveVisible = new Array<LuciferParticle>(true, 0, LuciferParticle.class);
	public SnapshotArray<LuciferParticle> currentVisible = new SnapshotArray<LuciferParticle>();
	public Array<LuciferParticle> dstSorted = new Array<LuciferParticle>(true, 0, LuciferParticle.class);
	public SnapshotArray<LuciferParticle> toDraw = new SnapshotArray<LuciferParticle>();
	public Array<LuciferParticle> toBind = new Array<LuciferParticle>(true, 0, LuciferParticle.class);

	private int maxVisible = 1024 * 5;
	private int batchSize = 512;

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

		Rect r = CAMERA.Camera.view;
		shader.bind();

		shader.setUniformMatrix("u_projTrans", this.observer.combined);		
		shader.setUniformf("u_resolution", new Vector2(r.width, r.height));
		shader.setUniformf("u_camP", (this.observer.position.cpy()));
		//shader.setUniformf("u_camD", this.observer.position.cpy().scl(this.observer.direction.cpy()));
		shader.setUniformf("u_camD", (this.observer.direction.cpy()));
		shader.setUniformf("u_time", deltaTime);

		Ray R = this.observer.getPickRay(Width/2, Height/2);
		shader.setUniformMatrix("_CameraInverseProjection", this.observer.invProjectionView);
		shader.setUniformMatrix("_CameraToWorld", this.observer.projection);
		//shader.setUniformMatrix("u_projView", this.observer.view);
		shader.setUniformf("u_cRayOrigin", R.origin);
		shader.setUniformf("u_cRayDirection", R.direction);
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

							Vector3 moveby = new Vector3().setToRandomDirection();
							E.position(E.position().cpy().add(moveby.scl(0.5f)));

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

					for (int i = 0; i < perspectiveVisible.size; i++) {

						sn.add(this.perspectiveVisible.get(i));
					}
					Collections.sort(sn, distanceComparator(this.observer.position));

					for (int i = 0; i < this.perspectiveVisible.size; i++) {
						this.dstSorted.add(sn.get(i));
					}

					this.toDraw = new SnapshotArray<LuciferParticle>(this.dstSorted);

					// sn.clear();
					int s = this.toDraw.size;
					int t = s;
					if (s > this.maxVisible) {
						t = s - maxVisible;
						// 0 being farthest
						this.toDraw.removeRange(0, t);
					}

					// Log("=== "+this.perspectiveVisible.size);
				}
				// Metatron.Console.post(this.currentVisible.size + " : "+this.dstMap.size() + "
				// <<<<");

			}
		}
	}

	public void render(boolean t) {

		// Log(this.toDraw.size);
		if (this.toDraw.size > 0) {
			int max = this.maxVisible;
			float b = this.batchSize;
			SnapshotArray<LuciferParticle> dr = new SnapshotArray<LuciferParticle>(this.toDraw);
			int s = dr.size;
			float mod = s % b;
			int rem = (int) ((s / b) + 1);

			for (int i = 0; i < (int) rem; i++) {
				if (t) {
					Sketcher.begin();

					for (int j = 0; j < b; j++) {

						if ((b * i) + j > Math.min(s - 1, max)) {
							break;
						}

						int f = (int) ((b * i) + j);

						LuciferParticle L = dr.get(f);
						// L.render();
						this.toBind.add(L);
					}
					this.bind(this.shader);
					Sketcher.Drawer.filledRectangle(0, 0, Width, Height);
					Sketcher.end();
				} else {
					Sketcher.begin();

					for (int j = 0; j < b; j++) {

						if ((b * i) + j > Math.min(s - 1, max)) {
							break;
						}

						int f = (int) ((b * i) + j);

						LuciferParticle L = dr.get(f);
						L.render();

					}
					Sketcher.end();
				}

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

	//////////
	public void bind(ShaderProgram s) {
		// Log("BINDING SHAPES TO SHADER:::");

		Vector3 unit = this.getUnit();
		int shpCnt = this.toBind.size;
		s.setUniformi("shpCnt", shpCnt);
		// Log(">" + shpCnt);

		for (int i = 0; i < shpCnt; i++) {
			LuciferParticle E = this.toBind.get(i);
			Vector3 p = E.position().cpy();
			Vector3 scl = E.scale().cpy();
			float z2 = (scl.len() / observer.position.cpy().sub(p.cpy()).len());
			z2 = (z2 * (unit.len() / 3)) / 2;
			z2 = z2 * (unit.len() / 3);

			this.observer.project(p);
			p.z = z2;
			//s.setUniformf("posAr[" + (i) + "]", p);

		}

		this.toBind.clear();
	}
}
