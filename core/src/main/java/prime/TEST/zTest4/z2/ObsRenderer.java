package prime.TEST.zTest4.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.SnapshotArray;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.SYS.NIX._Entity;

public class ObsRenderer {

	private ObserverKernel source;
	private int maxVisible = 1024 * 2;
	private int batchSize = 500;

	public ObsRenderer(ObserverKernel kernel) {
		this.source = kernel;
	}

	public void render() {
		Log(this.source.toDraw.size);
		SnapshotArray<_Entity> current = new SnapshotArray<_Entity>(this.source.toDraw);		
		int max = this.maxVisible;
		float b = this.batchSize;
		
		Vector3 unit = this.source.environment.getUnit();
		float uSize = unit.len()/3;
		
		if(current.size!=0)
		{
			int s = current.size;
			float mod = s % b;
			int rem = (int) ((s / b) + 1);
			
			for (int i = 0; i < (int) rem; i++) {
				Sketcher.begin();

				for (int j = 0; j < b; j++) {

					if ((b * i) + j > Math.min(s - 1, max)) {
						break;
					}

					int f = (int) ((b * i) + j);

					zEnt L = (zEnt)current.get(f);
					Vector3 reference = this.source.camera.position.cpy();
										
					Vector3 scl = L.scale(true).cpy();
					Vector3 pos = L.position().cpy();
					Vector3 prj = this.source.camera.project(pos.cpy());
					
					float z2 = (scl.len() / reference.cpy().sub(pos.cpy()).len());
					z2 = (z2 * (unit.len() / 3)) / 2;
					z2 = z2 * (unit.len() / 3);
					Vector3 Z2 = new Vector3(z2, z2, z2);
					
					
					
					if (L != null) {
						//L.render();
						Sketcher.setColor(Color.BLACK);
						Sketcher.Drawer.circle(prj.x,prj.y,uSize*z2);
					}

				}
				Sketcher.end();
			}
			
		}
	}
}
