package prime.TEST.zTest4.z2;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class ObserverKernel {

	public boolean running = false;
	protected Thread thread;

	public _Environment environment;
	public Transform transform;
	public Camera camera;
	
	
	protected Array<_Entity> perspectiveVisible = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> currentVisible = new SnapshotArray<_Entity>();
	public Array<_Entity> dstSorted = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> toDraw = new SnapshotArray<_Entity>();
	public Array<_Entity> toBind = new Array<_Entity>(true, 0, _Entity.class);

	private int maxVisible = 1024 * 2;
	private int batchSize = 500;

	public ObserverKernel(_Environment e, Transform t, Camera c) {
		this.environment = e;
		this.transform = t;
		this.camera = c;
		
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
			Camera C = this.camera;
			synchronized (this.perspectiveVisible) {
				synchronized (this.dstSorted) {

					this.perspectiveVisible.clear();
					this.dstSorted.clear();

					// raw visible
					for (int i = 0; i < this.environment.Members.size; i++) {
						_Entity e = this.environment.Members.get(i);

						if (e instanceof zEnt) {
							zEnt E = (zEnt) e;

							//Vector3 moveby = new Vector3().setToRandomDirection();
							// E.position(E.position().cpy().add(moveby.scl(0.5f)));

							//boolean b = (C.frustum.sphereInFrustum(((zEnt) E).position(),
							//		this.environment.getUnit().len() * 2));
							boolean b = (C.frustum.pointInFrustum(((zEnt) E).position()));
							if (b && !this.perspectiveVisible.contains(E, true)) {

								this.perspectiveVisible.add(E);

							}
						}
					}

					// accessible cache
					this.currentVisible = new SnapshotArray<_Entity>(this.perspectiveVisible.toArray());
					ArrayList<_Entity> sn = new ArrayList<_Entity>(this.perspectiveVisible.size);

					for (int i = 0; i < perspectiveVisible.size; i++) {

						sn.add(this.perspectiveVisible.get(i));
					}
					Collections.sort(sn, zEnv.distanceComparator(this.camera.position));

					for (int i = 0; i < this.perspectiveVisible.size; i++) {
						this.dstSorted.add(sn.get(i));
					}

					this.toDraw = new SnapshotArray<_Entity>(this.dstSorted);

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
	public void update(float deltaTime)
	{
		this.currentVisible.clear();
	}
	
	
	public void dispose() {
		this.currentVisible.clear();
		
	}
}
