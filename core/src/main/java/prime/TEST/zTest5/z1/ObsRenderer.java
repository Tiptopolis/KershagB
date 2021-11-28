package prime.TEST.zTest5.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.SYS.NIX._Entity;

public class ObsRenderer {

	public ObserverKernel perspective;
	private SnapshotArray<_Entity> members;

	public ObsRenderer(ObserverKernel kernel) {
		this.perspective = kernel;
		this.members = new SnapshotArray<_Entity>();
	}

	public void update() {
		this.members.clear();
		this.members = new SnapshotArray<_Entity>(this.perspective.toDraw.toArray());
	}

	public void render() {

		Vector3 unit = this.perspective.environment.getUnit();
		float uSize = unit.len()/3;
		Vector3 reference = this.perspective.observer.position;
		
		Log(">" + this.members.size);
		int max = this.perspective.maxVisible;
		float b = this.perspective.batchSize;
		int s = this.members.size;
		float mod = s % b;
		int rem = (int) ((s / b) + 1);
		Sketcher.setColor(0,0,0,1f);
		for (int i = 0; i < (int) rem; i++) {
			{
				Sketcher.begin();

				for (int j = 0; j < b; j++) {

					if ((b * i) + j > Math.min(s - 1, max)) {
						break;
					}

					int f = (int) ((b * i) + j);

					zEnt L = (zEnt) this.members.get(f);
					if (L != null) {
						// L.render();

						Vector3 pos = L.position();
						Vector3 prj = this.perspective.observer.project(pos.cpy());
						float z2 = (uSize / reference.cpy().sub(pos.cpy()).len());
						z2 = (z2 * (unit.len() / 3)) / 2;
						z2 = z2 * (unit.len() / 3);

						Sketcher.Drawer.filledCircle(prj.x, prj.y, z2);
					}

				}
				Sketcher.end();
			}

			//this.perspective.toDraw.clear();
		}
	}
}