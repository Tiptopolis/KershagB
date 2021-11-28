package prime.TEST.zTest6.z2;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class VisKernel {

	// derived from zTest6 et al ObserverKernel
	// 3d-cull: frst,frnt
	// sort
	// project
	// 2d-cull: face,line

	public Transform transform;
	public Camera observer;
	public _Environment environment;

	public boolean running = false;
	protected Thread thread;

	protected Array<_Entity> perspectiveVisible = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> currentVisible = new SnapshotArray<_Entity>();
	public Array<_Entity> dstSorted = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> toDraw = new SnapshotArray<_Entity>();

	// public int maxVisible = 1024 * 2;
	// public int batchSize = 500;

	public int maxVisible = 256 * 2;
	public int batchSize = 100;

	public VisKernel(_Environment e, Transform t, Camera c) {
		this.transform = t;
		this.observer = c;
		this.environment = e;
		this.thread = new Thread("SimThread") {
			@Override
			public void run() {
				if (running) {
					try {
						VisKernel.this.simLoop();
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

			// cull frustum & forward
			// dist-sort

			// cull max-vis

			// copy-flay-sort-project
			// cull overlapped polys

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
