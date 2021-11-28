package prime._PRIME.N_M.Prototype;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.N_M.Prototype.uVector.*;
import static prime._PRIME.N_M._N.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Maths;
import prime._PRIME.N_M.UTIL.N_Operator;
import prime._PRIME.N_M.UTIL.N_Resolver;

public class aVector<T extends Number> extends aNumber
		implements Vector<aVector<T>>, Iterable<Number>, CharSequence, Comparable<Number> {

	private static final long serialVersionUID = 981966534342301741L;
	public Array<Number> elements;
	public Array<String> labels;
	public T type;

	// public static Type Vector = new Type("Vector", aVector.class,
	// n_Number.Number);

	public aVector() {
		super(1);
		this.elements = new Array<Number>(true, 1, Number.class);
		this.labels = new Array<String>(true, 1, String.class);
		this.type = (T) new aNumber(0f);
		this.elements.add(0);
		this.labels.add("0");
	}

	public aVector(Number n) {
		super(n);
		this.elements = new Array<Number>(true, 1, Number.class);
		this.labels = new Array<String>(true, 1, String.class);
		this.type = (T) N_Resolver.resolve(n);
		this.elements.add(n);
		this.labels.add("" + n.toString());
	}

	public aVector(int indexLen, T type, boolean z) {
		super(indexLen);
		this.elements = new Array<Number>(true, indexLen, type.getClass());
		this.labels = new Array<String>(true, indexLen, String.class);
		Number Z = 0;
		if (z)
			Z = 1;

		this.type = (T) N_Operator.add(N_Operator.mul(N_Resolver.resolve(type), 0), Z);
		for (int i = 0; i < indexLen; i++) {
			this.elements.add((T) this.type);
			this.labels.add("" + i);
		}
	}

	public aVector(Number... N) {
		// N can be different types of numbers, which may throw off your calculations
		// using uVector sets all N values to the same type as N[0]
		super(N.length);
		this.elements = new Array<Number>(true, N.length, Number.class);
		this.labels = new Array<String>(true, N.length, String.class);
		this.type = (T) N_Operator.add(N_Operator.mul(N_Resolver.resolve(N[0]), 0), 1f);

		for (int i = 0; i < N.length; i++) {
			Number n = N[i];
			this.elements.add(n);
			this.labels.add("" + i);
		}

	}

	public aVector(aVector v) {
		super(v.elements.size);
		this.type = (T) v.getType();
		this.elements = new Array(v.elements);
		this.labels = new Array(v.labels);
	}

	public aVector(uVector v) {
		super(v.elements.size);
		this.type = (T) v.getType();
		this.elements = new Array(v.elements);
		this.labels = new Array(v.labels);
	}

	public T getType() {
		return this.type;
	}

	public aVector type(Number n) {
		this.type = (T) N_Resolver.resolveTo(n, this.type);

		return this.mul(N_Resolver.resolveTo(this.type, 1));
	}

	public Number get(int index) {
		if (index <= this.elements.size - 1)
			return this.elements.get(index);
		else
			return Float.NaN;
	}

	@Override
	public Number get(String label) {
		Array<Number> comp = new Array<Number>(true, 0, Number.class);
		Array<String> labs = new Array<String>(true, 0, String.class);
		boolean alt = false;

		for (String s : this.labels) {
			int i = this.labels.indexOf(s, false);
			if (s.equals(label))
				return this.elements.get(i);

			if (s.contains(label)) {
				if (!alt)
					alt = true;
				int l = s.length();
				String sub = s.substring(label.length() + 1, l);// seperator character
				comp.add(this.elements.get(i));
				labs.add(sub);
			}

		}
		if (alt) {
			aVector Comp = new aVector(comp.size);
			Comp.labels.clear();
			Comp.elements.clear();
			Comp.Label = label;
			Log(comp);
			for (int i = 0; i < comp.size; i++) {
				Comp.append(comp.get(i), labs.get(i));
			}
			return Comp;
		}
		return Float.NaN;
	}

	public aVector append(Number n) {
		return this.append(n, (this.elements.size - 1) + "");
	}

	public aVector append(Number n, String label) {
		this.elements.add(N_Resolver.resolveTo(this.type, n));
		this.labels.add(label);
		this.Value = this.elements.size;
		return this;
	}

	public aVector setAll(Number t) {
		this.type = (T) t;
		for (Number n : this.elements) {
			n = t;
		}
		return this;
	}

	public aVector setAt(int index, Number n) {
		if (index <= this.elements.size)
			this.elements.set(index, N_Resolver.resolveTo(this.Type(), n));
		else
			this.append(n);

		return this;
	}

	public aVector set(Number... N) {

		for (int i = 0; i < this.elements.size; i++) {
			Number r = 0;
			if (i < N.length && N[i] != null)
				r = N[i];

			this.elements.set(i, N_Resolver.resolveTo(this.type, r));
		}

		return this;
	}

	public aVector set(int indexLen, boolean z, Number... N) {

		this.elements.clear();
		this.elements = new Array<Number>(true, indexLen, Number.class);
		for (int i = 0; i < this.elements.size; i++) {
			Number r = 0;
			if (i < N.length && N[i] != null)
				r = N[i];

			this.elements.set(i, N_Resolver.resolveTo(this.type, r));
		}
		return this;
	}

	// Sets this.elements values to v.elements
	@Override
	public aVector<T> set(aVector<T> v) {

		for (int i = 0; i < this.elements.size; i++) {
			Number r = 0;
			if (i < v.elements.size && v.elements.get(i) != null)
				r = v.elements.get(i);

			this.elements.set(i, r);
		}

		return this;
	}

	///////////////////////////

	// adds [b] to all [this.elements]
	@Override
	public aVector add(Number b) {

		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.add(a, b));

		}
		return this;
	}

	// adds [v.elements[i]] to [this.elements[i]]
	public aVector add(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.add(a, b));
		}
		return this;

	}

	// subtracts [b] from all [this.elements]
	@Override
	public aVector sub(Number b) {

		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.sub(a, b));

		}
		return this;
	}

	// subtracts v.elements[i] from this.elements[i]
	public aVector sub(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.sub(a, b));
		}
		return this;
	}

	public aVector subtract(aVector v) {
		return this.sub(v);
	}

	// multiplies all [this.elements] by [b]
	public aVector mul(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.mul(a, b));

		}
		return this;
	}

	// multiplies [this.elements[i]] by [v.elements[i]]
	public aVector mul(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.mul(a, b));
		}
		return this;
	}

	/*
	 * public aVector mul(aMatrix m) {
	 * 
	 * for (aVector col : m.columns()) { this.mul(col.cpy()); } return this; }
	 */

	public aVector multiply(aVector v) {
		return this.mul(v);
	}

	// divides all [this.elements] by [b]
	public aVector div(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.div(a, b));

		}
		return this;
	}

	// divides [this.elements[i]] by [v.elements[i]]
	public aVector div(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 1;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.div(a, b));
		}
		return this;
	}

	public aVector divide(aVector v) {
		return this.div(v);
	}

	public aVector pow(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, (T) N_Resolver.resolveTo(this.type, N_Operator.pow(a, b)));

		}
		return this;
	}

	public aVector pow(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.pow(a, b));
		}
		return this;
	}

	public aVector root(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.root(a, b));

		}
		return this;
	}

	public aVector root(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 1;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.root(a, b));
		}
		return this;
	}

	public aVector mod(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.mod(a, b));

		}
		return this;
	}

	public aVector mod(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.mod(a, b));
		}
		return this;
	}

	public aVector rem(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.rem(a, b));

		}
		return this;
	}

	public aVector rem(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.rem(a, b));
		}
		return this;
	}

	public aVector abs() {

		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.abs(a));
		}

		return this;
	}

	public aVector min(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.min(a, b));

		}
		return this;
	}

	public aVector min(aVector v) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.min(a, b));
		}
		return this;
	}

	public aVector max(Number b) {
		for (int i = 0; i < this.elements.size; i++) {
			Number a = this.elements.get(i);
			this.elements.set(i, N_Operator.max(a, b));

		}
		return this;
	}

	public aVector max(aVector v) {
		for (int i = 0; i < this.elements.size - 1; i++) {
			Number a = this.elements.get(i);
			Number b = 0;

			if (i < v.elements.size && v.elements.get(i) != null) {
				b = (Number) v.elements.get(i);
			}
			this.elements.set(i, N_Operator.max(a, b));
		}
		return this;
	}

	//////////
	// Stats
	public float sum() {
		// Sum
		// sigma integral direct sum of all elements
		float c = 0f;
		for (int u = 0; u < this.elements.size; u++) {

			Object o = this.elements.get(u);
			if ((Number.class.isAssignableFrom(o.getClass()))) {
				c += N_Resolver.resolve(o).floatValue();
			}
		}
		return c;
	}

	public float dif() {
		// Difference
		// sigma anti-sum of all elements
		float c = 0f;
		for (int u = 0; u < this.elements.size; u++) {

			Object o = this.elements.get(u);
			if ((Number.class.isAssignableFrom(o.getClass()))) {
				c -= N_Resolver.resolve(o).floatValue();
			}
		}
		return c;
	}

	public float pro() {
		// Product
		float c = 1f;
		for (int u = 0; u < this.elements.size; u++) {

			Object o = this.elements.get(u);
			if ((Number.class.isAssignableFrom(o.getClass()))) {
				if (N_Resolver.resolve(o).floatValue() != 0)
					c *= N_Resolver.resolve(o).floatValue();

			}
		}
		return c;
	}

	public float quo() {
		// Quotient
		float c = 1f;
		for (int u = 0; u < this.elements.size; u++) {

			Object o = this.elements.get(u);
			if ((Number.class.isAssignableFrom(o.getClass()))) {
				if (N_Resolver.resolve(o).floatValue() != 0)
					c /= N_Resolver.resolve(o).floatValue();

			}
		}
		return c;

	}

	public float avg() {
		// average
		float c = 0f;
		for (int u = 0; u < this.elements.size; u++) {
			Object o = this.elements.get(u);
			if ((Number.class.isAssignableFrom(o.getClass()))) {
				c += N_Resolver.resolve(o).floatValue();
			}
		}
		return (c / (this.elements.size - 1));
	}

	public float med() {
		// median value, even odd
		int i = this.elements.size;
		float c = 0f;
		if (i == 0)
			return 0;
		if (i == 1)
			return this.Value.floatValue();

		int m = i;
		if (Maths.isEven(m))
			c = i / 2f;
		if (Maths.isOdd(m))
			c = Math.round((i) / 2);

		Number result = (Number) this.elements.get((int) c);
		return result.floatValue();
	}

	public float mode() {
		// mode
		// useful in finding the 'degree' of a polynomial lol
		Number c = 0f;
		HashMap<Number, Integer> counter = new HashMap<Number, Integer>();
		// count
		for (Object o : this.elements) {
			if ((Number.class.isAssignableFrom(o.getClass()))) {

				c = N_Resolver.resolve(o);
				if (!counter.containsKey(c))
					counter.putIfAbsent(c, 0);
				else {
					// map.put(key, map.get(key) + 1);
					counter.put(c, counter.getOrDefault(c, 0) + 1);
				}
			}
		}

		// compare
		Iterator<Map.Entry<Number, Integer>> it = counter.entrySet().iterator();
		int count = -1;
		float mode = 0;
		while (it.hasNext()) {
			Map.Entry<Number, Integer> e = (Map.Entry<Number, Integer>) it.next();
			if (e.getValue().intValue() >= count) {
				count = e.getValue().intValue();
				mode = e.getKey().floatValue();
			}
			it.remove();
		}

		return mode;
	}

	//////////

	public aVector idt() {

		this.set(0);
		Number n = this.elements.get(this.elements.size - 1);
		n = 1;

		return this;
	}

	/**
	 * @param vector The other vector
	 * @return Whether this and the other vector are equal
	 */
	public boolean idt(final aVector vector) {

		boolean is = true;
		for (int i = 0; i < this.elements.size; i++) {
			if (i < vector.elements.size) {
				if (!MathUtils.isEqual(this.elements.get(i).floatValue(),
						((Number) vector.elements.get(i)).floatValue()))
					is = false;
			} else
				is = false;
		}

		return is;
	}

	// transpose
	public aVector tra() {

		aVector<Number> mirror = new aVector<Number>(this.length(), this.Type(), false);
		for (int i = 0; i < this.length(); i++) {
			int j = (mirror.length() - 1) - i;
			mirror.setAt(j, this.get(i));
		}
		return this.set(mirror.toArray());
	}

	public aVector inv() {
		return this.tra().neg();
	}

	// idt, ignoring dimensional differences
	public boolean demi(final aVector vector) {
		boolean is = true;
		for (int i = 0; i < this.elements.size; i++) {
			if (i < vector.elements.size) {
				if (!MathUtils.isEqual(this.elements.get(i).floatValue(),
						((Number) vector.elements.get(i)).floatValue()))
					is = false;
			}
		}

		return is;
	}

	public aVector neg() {
		return this.mul(-1);
	}

	@Override
	public aVector<T> cpy() {

		return (aVector<T>) new aVector(this);
	}

	@Override
	public float len() {
		if (this.elements == null)
			return 0;
		if (this.elements.isEmpty())
			return 1;
		if (this.elements.size == 1)
			return this.Value.floatValue();

		return (float) Math.sqrt(this.len2());
	}

	@Override
	public float len2() {

		return this.pow(2).sum();
	}

	public float lenF(int f) {
		return N_Operator.root(this.lenX(f), f).floatValue();
	}

	public float lenX(int x) {
		return this.pow(x).sum();
	}

	@Override
	public aVector nor() {

		final float len2 = this.cpy().len2();
		if (len2 == 0f || len2 == 1f)
			return this;

		float v = 1f / (float) Math.sqrt(len2);
		return this.mul(N_Resolver.resolveTo(this.type, v));
	}

	@Override
	public float dot(aVector v) {
		// outter product

		int count = this.elements.size; //
		float result = 0f;
		for (int i = 0; i < count; i++) {
			if (v.elements.get(i) != null)
				result += N_Resolver.resolveF(N_Operator.mul(N_Resolver.resolve(this.elements.get(i)),
						N_Resolver.resolve(v.elements.get(i))));
			else
				result += 0f;
		}
		return result;

	}

	public aVector mulLeft(aVector v) {
		uVector other = new uVector(v);
		uVector T = new uVector(this);
		final float newX = other.w * T.x + other.x * T.w + other.y * T.z - other.z * T.y;
		final float newY = other.w * T.y + other.y * T.w + other.z * T.x - other.x * T.z;
		final float newZ = other.w * T.z + other.z * T.w + other.x * T.y - other.y * T.x;
		final float newW = other.w * T.w - other.x * T.x - other.y * T.y - other.z * T.z;
		T.x = newX;
		T.y = newY;
		T.z = newZ;
		T.w = newW;
		return this.set(T);
	}

	// 3d only
	public aVector crs(aVector other) {
		/*
		 * Number[] IndexA = N_Resolver.scanFrom(this.elements.toArray()); Number[]
		 * IndexB = N_Resolver.scanFrom(other.elements.toArray());
		 * 
		 * Number[] crsRes = new Number[3]; crsRes[0] = ((IndexA[1].floatValue() *
		 * IndexB[2].floatValue()) - (IndexA[2].floatValue() * IndexB[1].floatValue()));
		 * crsRes[1] = ((IndexA[2].floatValue() * IndexB[0].floatValue()) -
		 * (IndexA[0].floatValue() * IndexB[2].floatValue())); crsRes[2] =
		 * ((IndexA[0].floatValue() * IndexB[1].floatValue()) - (IndexA[1].floatValue()
		 * * IndexB[0].floatValue())); this.set(crsRes);
		 * 
		 * return this;
		 */

		aVector A = new aVector(N_Resolver.scanFrom(this.elements.toArray()));
		aVector B = new aVector(N_Resolver.scanFrom(other.elements.toArray()));

		aVector IndexA = new aVector(3, 1f, false).add(A);
		aVector IndexB = new aVector(3, 1f, false).add(B);

		Number[] crsRes = new Number[3];
		crsRes[0] = ((IndexA.get(1).floatValue() * IndexB.get(2).floatValue())
				- (IndexA.get(2).floatValue() * IndexB.get(1).floatValue()));
		crsRes[1] = ((IndexA.get(2).floatValue() * IndexB.get(0).floatValue())
				- (IndexA.get(0).floatValue() * IndexB.get(2).floatValue()));
		crsRes[2] = ((IndexA.get(0).floatValue() * IndexB.get(1).floatValue())
				- (IndexA.get(1).floatValue() * IndexB.get(0).floatValue()));
		this.set(crsRes);

		return this;

	}

	@Override
	public aVector<T> scl(float scalar) {
		return this.mul(scalar);
	}

	public aVector<T> scl(Number scalar) {
		return this.mul(scalar);
	}

	@Override
	public aVector<T> scl(aVector<T> v) {
		return this.mul(v);
	}

	@Override
	public float dst(aVector<T> v) {

		return (float) Math.sqrt(this.dst2(v));
	}

	@Override
	public float dst2(aVector<T> v) {
		// pow(2).sum
		aVector a = v.cpy().sub(this.cpy()).pow(2);
		return a.sum();
	}

	public aVector dir(aVector b) {
		return this.sub(b.cpy()).nor();
	}

	/*
	 * public aVector prj(aMatrix m) { aVector vec = new aVector(this.length(),
	 * this.type, false);
	 * 
	 * float inv_w = 1.0f / (this.cpy().mul(m.columns()[m.columns().length -
	 * 1]).sum());
	 * 
	 * for (int i = 0; i < vec.length(); i++) { aVector col = new
	 * aVector(this.length(), this.type, true); if (i <= m.columns().length) col =
	 * m.columns()[i].cpy();
	 * 
	 * vec.setAt(i, this.cpy().mul(col.cpy()).sum() * inv_w); } return
	 * this.set(vec); }
	 */

	public aVector rot(final Matrix4 matrix) {
		final float l_mat[] = matrix.val;
		uVector v = new uVector(this);
		return this.set(v.x * l_mat[Matrix4.M00] + v.y * l_mat[Matrix4.M01] + v.z * l_mat[Matrix4.M02],
				v.x * l_mat[Matrix4.M10] + v.y * l_mat[Matrix4.M11] + v.z * l_mat[Matrix4.M12],
				v.x * l_mat[Matrix4.M20] + v.y * l_mat[Matrix4.M21] + v.z * l_mat[Matrix4.M22]);
	}

	/*
	 * public aVector rot(aMatrix m) { aVector vec = new aVector(this.length(),
	 * this.type, false); for (int i = 0; i < vec.length(); i++) { aVector col = new
	 * aVector(this.length(), this.type, true); if (i <= m.columns().length) col =
	 * m.columns()[i].cpy();
	 * 
	 * vec.setAt(i, this.cpy().mul(col.cpy()).sum()); } return this.set(vec); }
	 */

	@Override
	public aVector<T> setLength(float len) {
		return setLength2(len * len);
	}

	@Override
	public aVector<T> setLength2(float len2) {

		float oldLen2 = len2();
		return (oldLen2 == 0 || oldLen2 == len2) ? this : mul((float) Math.sqrt(len2 / oldLen2));

	}

	@Override
	public aVector<T> limit(float limit) {
		return limit2(limit * limit);
	}

	@Override
	public aVector<T> limit2(float limit2) {
		float len2 = this.cpy().len2();
		if (len2 > limit2) {
			mul((float) Math.sqrt(limit2 / len2));
		}
		return this;
	}

	@Override
	public aVector<T> clamp(float min, float max) {
		for (Number e : this.elements) {

			int i = this.elements.indexOf(e, true);
			if (e.floatValue() > max)
				this.elements.set(i, max);
			if (e.floatValue() < min)
				this.elements.set(i, min);
		}

		return this;
	}

	public aVector negate() {
		return this.neg();
	}

	public aVector subtract(Number n) {
		return this.sub(n);
	}

	public aVector multiply(Number n) {
		return this.mul(n);
	}

	public aVector divide(Number n) {
		return this.div(n);
	}

	@Override
	public aVector<T> lerp(aVector<T> target, float alpha) {
		aVector dst = target.cpy().sub(this.cpy()).mul(alpha);
		return this.add(dst);
	}

	@Override
	public aVector<T> interpolate(aVector<T> target, float alpha, Interpolation interpolator) {
		return lerp(target, interpolator.apply(0f, 1f, alpha));
	}

	/*
	 * public aVector rotate(final aVector axis, float degrees) { aMatrix tmpMat =
	 * new aMatrix(4); tmpMat.setToRotation(axis, degrees); return this.mul(tmpMat);
	 * }
	 */

	/*
	 * public aVector<T> rotate(float degrees, float axisX, float axisY, float
	 * axisZ) { aMatrix tmpMat = new aMatrix(4); return
	 * this.mul(tmpMat.setToRotation(axisX, axisY, axisZ, degrees));
	 * 
	 * }
	 */

	public aVector<T> transform(aVector V) {
		uVector v = new uVector(V);
		uVector tmp2 = new uVector(0, 0, 0, 1);
		uVector tmp1 = new uVector();
		tmp2.set(this);
		tmp2.neg();
		tmp2.mulLeft(tmp1.set(v.x, v.y, v.z, 0)).mulLeft(this);

		v.x = tmp2.x;
		v.y = tmp2.y;
		v.z = tmp2.z;
		return this.set(v);
	}

	@Override
	public aVector<T> setToRandomDirection() {
		float u = MathUtils.random();
		float v = MathUtils.random();

		float theta = MathUtils.PI2 * u; // azimuthal angle
		float phi = (float) Math.acos(2f * v - 1f); // polar angle

		return this.setFromSpherical(theta, phi);
	}

	/**
	 * Sets the components from the given spherical coordinate
	 * 
	 * @param azimuthalAngle The angle between x-axis in radians [0, 2pi]
	 * @param polarAngle     The angle between z-axis in radians [0, pi]
	 * @return This vector for chaining
	 */
	public aVector setFromSpherical(float azimuthalAngle, float polarAngle) {
		float cosPolar = MathUtils.cos(polarAngle);
		float sinPolar = MathUtils.sin(polarAngle);

		float cosAzim = MathUtils.cos(azimuthalAngle);
		float sinAzim = MathUtils.sin(azimuthalAngle);

		return this.set(cosAzim * sinPolar, sinAzim * sinPolar, cosPolar);
	}

	public aVector setFromCross(final float x1, final float y1, final float z1, final float x2, final float y2,
			final float z2) {
		final float dot = MathUtils.clamp(Vector3.dot(x1, y1, z1, x2, y2, z2), -1f, 1f);
		final float angle = (float) Math.acos(dot);
		return this.setFromAxisRad(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2, angle);
	}

	public aVector setFromCross(final Vector3 v1, final Vector3 v2) {
		final float dot = MathUtils.clamp(v1.dot(v2), -1f, 1f);
		final float angle = (float) Math.acos(dot);
		return setFromAxisRad(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x, angle);
	}

	public aVector setFromAxis(final Vector3 axis, final float degrees) {
		return setFromAxis(axis.x, axis.y, axis.z, degrees);
	}

	public aVector setFromAxis(final uVector axis, final float degrees) {
		return setFromAxis(axis.x, axis.y, axis.z, degrees);
	}

	public aVector setFromAxisRad(final Vector3 axis, final float radians) {
		return setFromAxisRad(axis.x, axis.y, axis.z, radians);
	}

	public aVector setFromAxisRad(final uVector axis, final float radians) {
		return setFromAxisRad(axis.x, axis.y, axis.z, radians);
	}

	public aVector setFromAxis(final float x, final float y, final float z, final float degrees) {
		return setFromAxisRad(x, y, z, degrees * MathUtils.degreesToRadians);
	}

	public aVector setFromAxisRad(final float x, final float y, final float z, final float radians) {
		float d = uVector.len(x, y, z);
		if (d == 0f)
			return (uVector) this.idt();
		d = 1f / d;
		float l_ang = radians < 0 ? MathUtils.PI2 - (-radians % MathUtils.PI2) : radians % MathUtils.PI2;
		float l_sin = (float) Math.sin(l_ang / 2);
		float l_cos = (float) Math.cos(l_ang / 2);
		return (uVector) this.set(d * x * l_sin, d * y * l_sin, d * z * l_sin, l_cos).nor();
	}

	/*
	 * public aVector setFromMatrix(aMatrix matrix) { return
	 * this.setFromMatrix(true, matrix); }
	 */

	/*
	 * public aVector setFromMatrix(aMatrix matrix, int len) { return
	 * this.setFromMatrix(true, matrix, len); }
	 */

	/*
	 * public aVector setFromMatrix(boolean normalizeAxes, aMatrix matrix, int len)
	 * { aMatrix M = uMatrix.boxTo(len, matrix);
	 * 
	 * return uVector.setFromAxes(this, normalizeAxes, M.get("M00").floatValue(),
	 * M.get("M01").floatValue(), M.get("M02").floatValue(),
	 * M.get("M10").floatValue(), M.get("M11").floatValue(),
	 * M.get("M12").floatValue(), M.get("M20").floatValue(),
	 * M.get("M21").floatValue(), M.get("M22").floatValue()); }
	 */

	/*
	 * public aVector setFromMatrix(boolean normalizeAxes, aMatrix matrix) { //
	 * culls it to a 3x3 from the origin return this.setFromMatrix(normalizeAxes,
	 * matrix, 3); }
	 */

	public aVector setFromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy,
			float zz) {
		return uVector.setFromAxes(this, false, xx, xy, xz, yx, yy, yz, zx, zy, zz);
	}

	public aVector setFromCross(final uVector v1, final uVector v2) {
		final float dot = MathUtils.clamp(v1.dot(v2), -1f, 1f);
		final float angle = (float) Math.acos(dot);
		return setFromAxisRad(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x, angle);
	}

	public aVector setEulerAngles(float yaw, float pitch, float roll) {
		return this.setEulerAnglesRad(yaw * MathUtils.degreesToRadians, pitch * MathUtils.degreesToRadians,
				roll * MathUtils.degreesToRadians);
	}

	public aVector setEulerAnglesRad(float yaw, float pitch, float roll) {

		uVector quat = new uVector(0f, 0f, 0f, 1f);
		final float hr = roll * 0.5f;
		final float shr = (float) Math.sin(hr);
		final float chr = (float) Math.cos(hr);
		final float hp = pitch * 0.5f;
		final float shp = (float) Math.sin(hp);
		final float chp = (float) Math.cos(hp);
		final float hy = yaw * 0.5f;
		final float shy = (float) Math.sin(hy);
		final float chy = (float) Math.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		quat.x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) *
													// cos(pitch/2) * sin(roll/2)
		quat.y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) *
													// sin(pitch/2) * sin(roll/2)
		quat.z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) *
													// sin(pitch/2) * cos(roll/2)
		quat.w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) *
													// sin(pitch/2) * sin(roll/2)
		return this.set(quat);

	}

	///////////////

	@Override
	public boolean isUnit() {
		return isUnit(0.000000001f);
	}

	@Override
	public boolean isUnit(final float margin) {
		return Math.abs(len2() - 1f) < margin;
	}

	@Override
	public boolean isZero() {
		for (Number n : this.elements) {
			if (n.floatValue() != 0f)
				return false;
		}

		return true;
	}

	@Override
	public boolean isZero(final float margin) {
		return len2() < margin;
	}

	@Override
	public aVector<T> setZero() {
		for (Number n : this.elements) {
			n = 0;
		}
		return this;
	}

	@Override
	public boolean hasSameDirection(aVector<T> other) {
		return this.cpy().dot(other) > 0;
	}

	@Override
	public boolean hasOppositeDirection(aVector<T> other) {

		return this.cpy().dot(other) < 0;
	}

	@Override
	public boolean isOnLine(aVector<T> v, float epsilon) {
		uVector other = new uVector(v);
		uVector This = new uVector(this.cpy());

		float a = This.y * other.z - This.z * other.y;
		float b = This.z * other.x - This.x * other.z;
		float c = This.x * other.y - This.y * other.x;
		return uVector.len2(a, b, c) <= epsilon;
	}

	@Override
	public boolean isOnLine(aVector<T> v) {

		return this.isOnLine(v, MathUtils.FLOAT_ROUNDING_ERROR);

	}

	@Override
	public boolean isCollinear(aVector<T> other, float epsilon) {

		return isOnLine(other, epsilon) && hasSameDirection(other);
	}

	@Override
	public boolean isCollinear(aVector<T> other) {

		return isOnLine(other) && hasSameDirection(other);
	}

	@Override
	public boolean isCollinearOpposite(aVector<T> other, float epsilon) {

		return isOnLine(other, epsilon) && hasOppositeDirection(other);
	}

	@Override
	public boolean isCollinearOpposite(aVector<T> other) {
		return isOnLine(other) && hasOppositeDirection(other);
	}

	@Override
	public boolean isPerpendicular(aVector<T> other) {

		return MathUtils.isZero(this.cpy().dot(other));
	}

	@Override
	public boolean isPerpendicular(aVector<T> other, float epsilon) {

		return MathUtils.isZero(this.cpy().dot(other), epsilon);
	}

	@Override
	public boolean epsilonEquals(aVector<T> other, float epsilon) {

		return MathUtils.isEqual(this.len(), other.len(), epsilon);
	}

	@Override
	public aVector<T> mulAdd(aVector<T> v, float scalar) {

		return this.add(v.cpy().scl(scalar));
	}

	@Override
	public aVector<T> mulAdd(aVector<T> v, aVector<T> mulVec) {

		return this.add(v.cpy().scl(mulVec.cpy()));
	}

	/////////////////////////////

	public aVector getRange() {
		return new aVector(this.getMin(), this.getMax());
	}

	public Number getMin() {
		// returns smallest element
		float z = this.getMax().floatValue();
		for (Number n : this.elements) {
			if (n.floatValue() < z)
				z = n.floatValue();
		}
		return z;
	}

	public Number getMax() {
		// returns largest element

		float z = 0f;
		for (Number n : this.elements) {
			if (n.floatValue() > z)
				z = n.floatValue();
		}
		return z;
	}

	public int countZeros() {
		int c = 0;
		for (Number e : this.elements) {
			if (e.floatValue() == 0.0) {
				c++;
			}
		}
		return c;
	}

	public Number[] toArray() {
		Number[] values = new Number[this.elements.size];
		for (int i = 0; i < this.elements.size; i++) {
			values[i] = this.elements.get(i);
		}
		return values;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof aVector) /* && !(other instanceof aMatrix) */) {
			if (other instanceof Number) {
				return MathUtils.isEqual(this.len(), ((Number) other).floatValue());
			}
			return false;
		} else {
			aVector<Number> v = (aVector<Number>) other;
			return this.Equals(v);
		}

	}

	public boolean Equals(Number other) {
		for (Number n : this.elements) {
			Number O = N_Resolver.resolveTo(n, other);
			if (n != O)
				return false;
		}
		return true;
	}

	public boolean Equals(aVector other) {
		for (int i = 0; i < this.elements.size; i++) {

			Number a = Float.NaN;
			Number b = Float.NaN;
			if (i <= this.elements.size)
				a = N_Resolver.resolveTo(this.type, this.get(i));

			if (i <= other.elements.size)
				b = N_Resolver.resolveTo(this.type, other.get(i));

			if (a != b || a.equals(Float.NaN) || b.equals(Float.NaN))
				return false;
		}
		return true;
	}

	public String[] toStings() {
		Array<String> vals = new Array<String>(true, this.elements.size, String.class);
		for (int i = 0; i < this.elements.size; i++) {
			vals.add("" + this.elements.get(i));
		}
		return vals.toArray();
	}

	@Override
	public String toString() {
		String vals = "";
		for (int i = 0; i <= this.elements.size - 1; i++) {
			vals += this.elements.get(i);
			if (i != this.elements.size - 1)
				vals += ",";
		}

		return "(" + vals + ")";
	}

	public String logLabels() {
		String vals = "";
		for (int i = 0; i <= this.labels.size - 1; i++) {
			vals += this.labels.get(i);
			if (i != this.labels.size - 1)
				vals += ",";
		}

		return "{" + vals + "}";
	}

	@Override
	public String toLog() {
		String log = "";
		log += this.getClass().getSimpleName() + this.Value + "<" + this.type + ":"
				+ this.type.getClass().getSimpleName() + ":" + this.Value + ">" + this.toString() + this.logLabels();

		return log;
	}

	@Override
	public Iterator<Number> iterator() {
		return this.elements.iterator();
	}

	@Override
	public char charAt(int arg0) {
		return 'v';
	}

	@Override
	public int length() {
		return this.elements.size;
	}

	@Override
	public CharSequence subSequence(int start, int end) {

		if (end < start)
			end = start;
		if (end == start)
			return new aVector(this.elements.get(start));

		Number[] subseq = new Number[end - start + 1];
		for (int i = 0; i < subseq.length; i++) {
			subseq[i] = this.elements.get(start + i);
		}

		return new aVector<Number>(subseq);
	}

	@Override
	public int compareTo(Number other) {
		if (!(other instanceof aVector)) {
			if (this.Equals(other))
				return 0;
			return (int) (this.len() - other.floatValue());
		} else {
			aVector v = (aVector) other;
			if (this.Equals(v))
				return 0;
			return (int) this.sub(v.cpy()).len();

		}

	}

}
