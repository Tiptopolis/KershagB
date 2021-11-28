package prime.TEST.zTest4.z1;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.Comparator;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.N_M.Prototype.uVector;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.SYS.NIX._Entity;

public class zEnv extends _Environment {

	protected Array<ObserverKernel> observers;
	public Vector3 size;

	
	
	
	
	
	public zEnv(Vector3 size, int numEnt) {
		super("zEnv");
		this.observers = new Array<ObserverKernel>(false, 0, ObserverKernel.class);
		this.Dimension = new uVector(1, 1, 1, 1);
		this.size = size;
		zEntGenerator.generate(this, numEnt);
	
	}
	
	
	
	
	
	
	
	

	public void registerObserver(ObserverKernel k) {
		if (!this.observers.contains(k, true)) {
			this.observers.add(k);
		}
	}

	@Override
	public Vector3 getSize() {
		return this.size;
	}

	@Override
	public Vector3 getUnit() {
		return new Vector3(32, 32, 32);
	}


	
	@Override
	public String toLog()
	{
		String log = super.toLog();
		
		log+= this.Name()+"\n";
		log += this.Members.size+"\n";
		
		return log;
	}
	

}
