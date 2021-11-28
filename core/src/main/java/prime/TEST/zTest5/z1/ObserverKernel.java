package prime.TEST.zTest5.z1;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class ObserverKernel {

	public Transform transform;
	public Camera observer;
	public _Environment environment;

	public boolean running = false;
	protected Thread thread;

	protected Array<_Entity> perspectiveVisible = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> currentVisible = new SnapshotArray<_Entity>();
	public Array<_Entity> dstSorted = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> toDraw = new SnapshotArray<_Entity>();

	public int maxVisible = 1024 * 2;
	public int batchSize = 500;

	public ObserverKernel(_Environment e, Transform t, Camera c) {
		this.transform = t;
		this.observer = c;
		this.environment = e;
		this.thread = new Thread("SimThread") {
			@Override
			public void run() {
				if (running) {
					try {
						ObserverKernel.this.simLoop();
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

	public void simLoop() {

		while (running) {
			// Camera C = this.copy(this.perspective.perspective.camera);
			Camera C = this.observer;
			synchronized (this.perspectiveVisible) {
				synchronized (this.dstSorted) {

					this.perspectiveVisible.clear();
					this.dstSorted.clear();
					SnapshotArray<_Entity> Members = new SnapshotArray<_Entity>(this.environment.Members);

					// raw visible
					for (int i = 0; i < Members.size; i++) {
						_Entity e = Members.get(i);

						if (e instanceof zEnt) {
							zEnt E = (zEnt) e;
							// Vector3 moveby = new Vector3().setToRandomDirection();
							// E.position(E.position().cpy().add(moveby.scl(0.5f)));

							boolean a = C.frustum.planes[0].testPoint(E.position()) == Plane.PlaneSide.Front;
							boolean b = (C.frustum.sphereInFrustum(E.position(), this.environment.getUnit().len() * 3));
							// boolean b = (C.frustum.pointInFrustum(((zEnt) E).position()));

							if (a && b && !this.perspectiveVisible.contains(E, true)) {

								this.perspectiveVisible.add(E);

							}
						}
					}

					// System.out.println(">" + this.perspectiveVisible.size);
					// accessible cache
					this.currentVisible = new SnapshotArray<_Entity>(this.perspectiveVisible.toArray());
					ArrayList<_Entity> sn = new ArrayList<_Entity>(this.perspectiveVisible.size);

					for (int i = 0; i < perspectiveVisible.size; i++) {

						sn.add(this.perspectiveVisible.get(i));
					}
					Collections.sort(sn, zEnv.distanceComparator(this.observer.position));

					for (int i = 0; i < this.perspectiveVisible.size; i++) {
						this.dstSorted.add(sn.get(i));
					}

					this.toDraw = new SnapshotArray<_Entity>(this.dstSorted);
					// System.out.println("<" + this.toDraw.size);
					// sn.clear();
					int s = this.toDraw.size;
					int t = s;
					if (s > this.maxVisible) {
						t = s - maxVisible;
						// 0 being farthest
						this.toDraw.removeRange(0, t);
					}
					// System.out.println("<" + this.toDraw.size);
					// Log("=== "+this.perspectiveVisible.size);
				}
				// Metatron.Console.post(this.currentVisible.size + " : "+this.dstMap.size() + "
				// <<<<");

			}
		}
	}

	public void update() {
		this.currentVisible.clear();
	}

	public void dispose() {
		this.currentVisible.clear();
		this.toDraw.clear();

	}

}
