package prime.TEST.ConTest1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;
import java.util.Collections;

import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;
import squidpony.squidmath.RNG;

public class lEnv extends _Environment {

	public Camera observer;
	public Array<LuciferParticle> toDraw = new Array<LuciferParticle>(true, 0, LuciferParticle.class);

	public lEnv(Camera obs, Vector3 size, int ents) {
		super("lEnv");
		this.observer = obs;
		this.Dimension = new uVector(size);
		this.gen(ents);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		this.toDraw.clear();
		for (_Entity E : this.Members) {
			if (E instanceof LuciferParticle) {
				LuciferParticle L = (LuciferParticle) E;
				if(this.observer.frustum.sphereInFrustum(L.position(), 2))
				{
					this.toDraw.add(L);
				}
			}
		}
	}

	
	public void render()
	{
		Log(this.toDraw.size);
		ArrayList<LuciferParticle> sn = new ArrayList<LuciferParticle>();
		for (LuciferParticle E : this.toDraw) {
			sn.add(E);
		}
		Collections.sort(sn, LuciferParticle.distanceComparator(this.observer.position));
		for(LuciferParticle L : sn)
		{
			L.render();
		}
		sn.clear();
	}
	

	
	@Override
	public Vector3 getSize() {
		return this.Dimension.V3();
	}
	
	@Override
	public Vector3 getUnit()
	{
		return new Vector3(8,8,8);
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

	
	
	public void dispose()
	{
		this.toDraw.clear();
		this.Members.clear();
	}
}
