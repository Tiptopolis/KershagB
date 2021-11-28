package prime._PRIME.C_O.Prototype;

import static prime._PRIME.uAppUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.SnapshotArray;

public class OroborosList<T> extends ArrayList<T> {

	// %6 will fill a circle ?

	// waits to fill up before starting removal
	// feed in delta & increment time lol

	public float theta = 1; // increment size, points added/removed each frame
	public float maxTheta = 6; // will fill to maxTheta before removal happens
	public float scale = 1;

	public float I = 0;

	// s = iPointsG.size();
	// iN = (int) (360 * scale);

	public OroborosList() {
		super();
		this.theta = 1;
		this.maxTheta = 32;
		this.scale = 1;

	}

	public OroborosList(float theta, float max, float scale) {
		super();
		this.theta = theta;
		this.maxTheta = max;
		this.scale = scale;

	}

	@Override
	public boolean add(T t) {
		this.step();
		float s = this.size();		
		int iN = (int) (maxTheta * scale);
		float g = (this.theta * this.scale);
		// if(((currentSize-1)%(max)) + ((max)%(currentSize+1))<(max)) //generating set?
		if (((s - g) % iN) + (iN % (s + g)) < (iN)) {
			this.I++;
			this.I = (I % iN);
			return super.add(t);
		} else
			return false;

	}

	protected void step() {
		if (!this.isEmpty()) {
			if ((this.size() % this.maxTheta) == 0) {
				for (int i = 0; i < (this.theta * scale); i++) {
					T index = this.get(i);
					this.remove(index);
				}
			}
		}
	}

	@Override
	public boolean remove(Object index) {
		if (index instanceof Disposable) {
			((Disposable) index).dispose();
			 //Log("D*" + index.getClass().getSimpleName());
		}
		return super.remove(index);
	}

	@Override
	public void clear() {
		for (T item : this)
			if (item instanceof Disposable) {
				((Disposable) item).dispose();
				 Log("D*" + item.getClass().getSimpleName());
			}
		super.clear();
	}

	public boolean add(List<T> l) {
		for (T t : l) {
			this.add(t);
		}
		return true;
	}

	public T getFirst() {
		return this.get(0);
	}

	public T getLast() {
		int s = this.size() - 1;
		return this.get(s);
	}

	public ArrayList<T> All() {
		return this;
	}

}
