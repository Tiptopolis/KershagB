package prime.TEST.zTest4.z1;

import java.util.Comparator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class ObserverKernel {

	public _Environment environment;
	public Transform transform;
	public Camera perspective;
	public Array<zEnt> visible;
	public zEnt body;

	public Thread thread;
	public boolean running = false;

	protected Array<_Entity> perspectiveVisible = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> currentVisible = new SnapshotArray<_Entity>();
	protected Array<_Entity> dstSorted = new Array<_Entity>(true, 0, _Entity.class);
	public SnapshotArray<_Entity> toDraw = new SnapshotArray<_Entity>();
	public Array<_Entity> toBind = new Array<_Entity>(true, 0, _Entity.class);

	private int maxVisible = 1024 * 2;
	private int batchSize = 500;// must cover the arrays being passed to shader :/ merge into a color?!?!

	public ObserverKernel(_Environment e, Transform t, Camera c) {

		this.environment = e;
		this.perspective = c;
		this.transform = t;
		this.visible = new Array<zEnt>();
		this.body = new zEnt(e, this.transform);

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
			Vector3 unit = this.environment.getUnit();
			synchronized (this.perspectiveVisible) {
				synchronized (this.dstSorted) {

					this.perspectiveVisible.clear();
					this.dstSorted.clear();

					// RawVisible
					SnapshotArray<_Entity> fromEnv = new SnapshotArray<_Entity>(this.environment.Members);
					for (_Entity E : fromEnv) {
						if (E instanceof zEnt) {
							zEnt e = (zEnt) E;
							Vector3 pos = e.transform.GetPosition().cpy();
							Vector3 dst = VectorUtils.dst(this.transform.GetPosition().cpy(), pos.cpy());

							boolean a = this.perspective.frustum.pointInFrustum(pos.cpy());
							boolean b = this.perspective.frustum.planes[0].testPoint(pos) == Plane.PlaneSide.Front;
							if (a && b && !this.perspectiveVisible.contains(E, true)) {
								this.perspectiveVisible.add(e);
							}
						}
					}
					// accessible cache
					// System.out.println(" __ "+this.perspectiveVisible.toArray().length);
					this.currentVisible = new SnapshotArray<_Entity>(this.perspectiveVisible.toArray());

				}
			}
			// System.out.println(">tick");
		}

	}

	public void update(float deltaTime) {
		// this.updateVisible();
		this.currentVisible.clear();
		// System.out.println("<tock");
	}

	// cull frustum, cull front, sort by dst, project, cull overlap
	private void updateVisible() {
		// get rawVisible from environment, sort & cull

		this.cullVisible();
		this.sortVisible();
	}

	// culls anything not in camera frustum or in forward direction
	private void cullVisible() {

	}

	// sorts by distance from observer
	private void sortVisible() {

	}

	private void projectVisible() {

	}

	private void cullOverlap() {

	}

	///////
	public static Comparator<Transform> distanceComparator(Vector3 point) {
		final Vector3 finalP = point.cpy();
		return new Comparator<Transform>() {
			@Override
			public int compare(Transform p0, Transform p1) {
				double ds0 = VectorUtils.dst(p0.GetPosition(), finalP).len();
				double ds1 = VectorUtils.dst(p1.GetPosition(), finalP).len();
				return Double.compare(ds0, ds1);
			}

		};
	}

}
