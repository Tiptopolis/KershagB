package prime._PRIME.N_M.Prototype;

import static prime._PRIME.uAppUtils.*;


import java.util.Collection;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.N_M.NMP.Binary;
import prime._PRIME.N_M.NMP.Hex;
import prime._PRIME.N_M.UTIL.N_Operator;
import prime._PRIME.N_M.UTIL.N_Resolver;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;


public class uVector extends aVector<Number> {

	// static manipulation & creation

	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float w = 0;

	public uVector() {
		this(1, 0f, false);
	}

	public uVector(int indexLen, boolean z) {
		this(indexLen, 0f, z);
	}

	public uVector(int indexLen, Number type, boolean Z) {
		super(indexLen, type, Z);
		for (int i = 0; i < indexLen; i++) {
			if (i == 0) {
				x = (float) this.elements.get(0).floatValue();
				this.labels.set(i, "" + this.elements.get(0));
			}
			if (i == 1) {
				y = (float) this.elements.get(1).floatValue();
				this.labels.set(i, "" + this.elements.get(1));
			}
			if (i == 2) {
				z = (float) this.elements.get(2).floatValue();
				this.labels.set(i, "" + this.elements.get(2));
			}
			if (i == 3) {
				w = (float) this.elements.get(3).floatValue();
				this.labels.set(i, "" + this.elements.get(3));
			}
		}
	}

	public uVector(Number x, Number y) {
		super(2, x, false);
		this.x = x.floatValue();
		this.y = y.floatValue();;
		this.labels.set(0, "x");
		this.labels.set(1, "y");
		this.set(x, y);
	}

	public uVector(Number x, Number y, Number z) {
		super(3, x, false);
		this.x = x.floatValue();
		this.y = y.floatValue();
		this.z = z.floatValue();
		this.labels.set(0, "x");
		this.labels.set(1, "y");
		this.labels.set(2, "z");
		this.set(x, y, z);
	}

	public uVector(Number x, Number y, Number z, Number w) {
		super(4, x, false);
		this.x = x.floatValue();
		this.y = y.floatValue();;
		this.z = z.floatValue();;
		this.w = w.floatValue();;
		this.labels.set(0, "x");
		this.labels.set(1, "y");
		this.labels.set(2, "z");
		this.labels.set(3, "w");
		this.set(x, y, z, w);
	}
	
	public uVector(Vector2 vec)
	{
		this(vec.x,vec.y);
	}
	public uVector(Vector3 vec)
	{
		this(vec.x,vec.y,vec.z);
	}
	public uVector(Quaternion vec)
	{
		this(vec.x,vec.y,vec.z, vec.w);
	}

	public uVector(Number... values) {
		super(values);
		for (int i = 0; i < values.length; i++) {
			if (i == 0) {
				x = (float) values[0].floatValue();
				this.labels.set(i, "" + values[0]);
			}
			if (i == 1) {
				y = (float) values[1].floatValue();
				this.labels.set(i, "" + values[1]);
			}
			if (i == 2) {
				z = (float) values[2].floatValue();
				this.labels.set(i, "" + values[2]);
			}
			if (i == 3) {
				w = (float) values[3].floatValue();
				this.labels.set(i, "" + values[3]);
			}
		}

	}

	public uVector(aVector<Number> v) {
		this(v.elements.toArray());
	}

	public static aVector fromString(String s) {
		String[] sa = s.split(",");
		Array<Number> parse = new Array<Number>(true, sa.length, Number.class);
		for (int i = 0; i < sa.length; i++) {
			parse.add(N_Resolver.stringFlt(sa[i]));
		}
		return new aVector(parse.toArray());

	}
	
	public static aVector fromBinary(String s)
	{
		Array<Number> parse = new Array<Number>(true, s.length(), Number.class);
		for(int i = 0; i < s.length(); i++)
		{
			parse.add(N_Resolver.stringInt(""+s.charAt(i)));
		}
		return new aVector(parse.toArray());
	}

	public static aVector fromString(String s, Class type) {
		String[] sa = s.split(",");
		Array<Number> parse = new Array<Number>(true, sa.length, type);
		for (int i = 0; i < sa.length; i++) {
			if (type == Integer.class)
				parse.add(N_Resolver.stringInt(sa[i]));
			else
				parse.add(N_Resolver.stringFlt(sa[i]));
		}
		return new aVector(parse.toArray());

	}

	/////////

	// doman k{[0,N-1]
	public static aVector<Number> Range(Number from, Number to) {
		aVector<Number> rng = new aVector<Number>(from, 0, to);
		rng.labels.set(0, "From");
		rng.labels.set(1, "At");
		rng.labels.set(2, "To");
		// FAT32 lol
		return rng;
	}

	public static aVector<Number> Byte() {
		return Byte(0);
	}

	public static aVector<Number> Byte(byte b) {
		return Byte((int) b);
	}

	public static aVector<Number> Byte(int b) {
		return new Binary(b);
	}

	public static aVector<Number> Hex() {
		return Hex(0);
	}

	public static aVector<Number> Hex(byte h) {
		return new Hex((int) h);
	}

	public static aVector<Number> Hex(int h) {
		return new Hex(h);
	}

	public static uVector Vect2() {
		return new uVector(0, 0);
	}

	public static uVector Vect3() {
		return new uVector(0, 0, 0);
	}

	public static uVector Vect4() {
		return new uVector(0, 0, 0, 0);
	}

	public static uVector Scl() {
		return new uVector(1, 1, 1, 1);
	}

	public static uVector Quat() {
		return new uVector(0, 0, 0, 1);
	}
	//////////////
	// STD VECTORS
	////

	public static aVector<Number> Vector2() {
		return Vector2(0, 0);
	}

	public static aVector<Number> Vector2(Number s) {
		return Vector2(s, s);
	}

	public static aVector<Number> Vector2(Number x, Number y) {
		y = N_Resolver.resolveTo(x, y);
		aVector<Number> V = new aVector<Number>(x, y);
		V.labels.set(0, "x");
		V.labels.set(1, "y");
		return V;
	}

	public static aVector<Number> Vector2(Vector2 v) {
		float x = v.x;
		float y = v.y;
		aVector<Number> V = new aVector<Number>(x, y);
		V.labels.set(0, "x");
		V.labels.set(1, "y");
		return V;
	}

	public static aVector<Number> Vector2(aVector<Number> v) {
		float x = 0;
		float y = 0;
		if (v.length() >= 0)
			x = (float) v.elements.get(0).floatValue();
		if (v.length() >= 1)
			y = (float) v.elements.get(1).floatValue();

		return Vector2(x, y);
	}

	public static aVector<Number> Vector3() {
		return Vector3(0, 0, 0);
	}

	public static aVector<Number> Vector3(Number s) {
		return Vector3(s, s, s);
	}

	public static aVector<Number> Vector3(Number x, Number y, Number z) {
		y = N_Resolver.resolveTo(x, y);
		z = N_Resolver.resolveTo(x, z);
		aVector<Number> V = new aVector<Number>(x, y, z);
		V.labels.set(0, "x");
		V.labels.set(1, "y");
		V.labels.set(2, "z");
		return V;
	}

	public static aVector<Number> Vector3(Vector3 v) {
		float x = v.x;
		float y = v.y;
		float z = v.z;
		aVector<Number> V = new aVector<Number>(x, y, z);
		V.labels.set(0, "x");
		V.labels.set(1, "y");
		V.labels.set(2, "z");
		return V;
	}

	public static aVector<Number> Vector3(aVector<Number> v) {

		float x = 0;
		float y = 0;
		float z = 0;
		if (v.length() >= 0)
			x = (float) v.elements.get(0).floatValue();
		if (v.length() >= 1)
			y = (float) v.elements.get(1).floatValue();
		if (v.length() >= 2)
			z = (float) v.elements.get(2).floatValue();

		return Vector3(x, y, z);
	}

	public static aVector<Number> Vector4() {
		return Vector4(0);
	}

	public static aVector<Number> Vector4(Number s) {
		return Vector4(s, s, s, s);
	}

	public static aVector<Number> Vector4(Number x, Number y, Number z, Number w) {
		return Quaternion(x, y, z, w);
	}

	public static aVector<Number> Quaternion() {
		return Quaternion(0, 0, 0, 1);
	}

	public static aVector<Number> Quaternion(Number s) {
		return Quaternion(s, s, s, s);
	}

	public static aVector<Number> Quaternion(Number x, Number y, Number z, Number w) {
		y = N_Resolver.resolveTo(x, y);
		z = N_Resolver.resolveTo(x, z);
		w = N_Resolver.resolveTo(x, w);
		aVector<Number> V = new aVector<Number>(x, y, z, w);
		V.labels.set(0, "x");
		V.labels.set(1, "y");
		V.labels.set(2, "z");
		V.labels.set(3, "w");
		return V;
	}

	public static aVector<Number> Quaternion(Quaternion q) {
		float x = q.x;
		float y = q.y;
		float z = q.z;
		float w = q.w;
		aVector<Number> V = new aVector<Number>(x, y, z, w);
		V.labels.set(0, "x");
		V.labels.set(1, "y");
		V.labels.set(2, "z");
		V.labels.set(3, "w");
		return V;
	}

	public static aVector<Number> Quaternion(aVector<Number> v) {
		float x = 0;
		float y = 0;
		float z = 0;
		float w = 0;
		if (v.length() >= 0)
			x = (float) v.elements.get(0);
		if (v.length() >= 1)
			y = (float) v.elements.get(1);
		if (v.length() >= 2)
			z = (float) v.elements.get(2);
		if (v.length() >= 3)
			w = (float) v.elements.get(3);

		return Quaternion(x, y, z, w);
	}

	public float getAxisAngle(Vector3 axis) {
		return getAxisAngleRad(axis) * MathUtils.radiansToDegrees;
	}

	public float getAxisAngleRad(Vector3 axis) {
		if (this.w > 1)
			this.nor(); // if w>1 acos and sqrt will produce errors, this cant happen if quaternion is
						// normalised
		float angle = (float) (2.0 * Math.acos(this.w));
		double s = Math.sqrt(1 - this.w * this.w); // assuming quaternion normalised then w is
																				// less than 1, so term always positive.
		if (s < MathUtils.FLOAT_ROUNDING_ERROR) { // test to avoid divide by zero, s is always positive due to sqrt
			// if s close to zero then direction of axis not important
			axis.x = this.x; // if it is important that axis is normalised then replace with x=1; y=z=0;
			axis.y = this.y;
			axis.z = this.z;
		} else {
			axis.x = (float) (this.x / s); // normalise axis
			axis.y = (float) (this.y / s);
			axis.z = (float) (this.z / s);
		}

		return angle;
	}
	
	public static aVector setFromCross(aVector q, final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
		final float dot = MathUtils.clamp(Vector3.dot(x1, y1, z1, x2, y2, z2), -1f, 1f);
		final float angle = (float)Math.acos(dot);
		return q.setFromAxisRad(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2, angle);
	}

	public static aVector setFromAxes(aVector v, boolean normalizeAxes, float xx, float xy, float xz, float yx,
			float yy, float yz, float zx, float zy, float zz) {
		uVector V = new uVector(v.length(), v.Type(), false);
		V.add(v);

		return setFromAxes(V, normalizeAxes, xx, xy, xz, yx, yy, yz, zx, zy, zz);

	}

	public static aVector setFromAxes(uVector v, boolean normalizeAxes, float xx, float xy, float xz, float yx,
			float yy, float yz, float zx, float zy, float zz) {
		if (normalizeAxes) {
			final float lx = 1f / uVector.len(xx, xy, xz);
			final float ly = 1f / uVector.len(yx, yy, yz);
			final float lz = 1f / uVector.len(zx, zy, zz);
			xx *= lx;
			xy *= lx;
			xz *= lx;
			yx *= ly;
			yy *= ly;
			yz *= ly;
			zx *= lz;
			zy *= lz;
			zz *= lz;
		}
		// the trace is the sum of the diagonal elements; see
		// http://mathworld.wolfram.com/MatrixTrace.html
		final float t = xx + yy + zz;

		// we protect the division by s by ensuring that s>=1
		if (t >= 0) { // |w| >= .5
			float s = (float) Math.sqrt(t + 1); // |s|>=1 ...
			v.w = 0.5f * s;
			s = 0.5f / s; // so this division isn't bad
			v.x = (zy - yz) * s;
			v.y = (xz - zx) * s;
			v.z = (yx - xy) * s;
		} else if ((xx > yy) && (xx > zz)) {
			float s = (float) Math.sqrt(1.0 + xx - yy - zz); // |s|>=1
			v.x = s * 0.5f; // |x| >= .5
			s = 0.5f / s;
			v.y = (yx + xy) * s;
			v.z = (xz + zx) * s;
			v.w = (zy - yz) * s;
		} else if (yy > zz) {
			float s = (float) Math.sqrt(1.0 + yy - xx - zz); // |s|>=1
			v.y = s * 0.5f; // |y| >= .5
			s = 0.5f / s;
			v.x = (yx + xy) * s;
			v.z = (zy + yz) * s;
			v.w = (xz - zx) * s;
		} else {
			float s = (float) Math.sqrt(1.0 + zz - xx - yy); // |s|>=1
			v.z = s * 0.5f; // |z| >= .5
			s = 0.5f / s;
			v.x = (xz + zx) * s;
			v.y = (zy + yz) * s;
			v.w = (yx - xy) * s;
		}

		return dev((uVector) v.set(v.x, v.y, v.z, v.w));
	}
	
	public static aVector setFromEulerAnglesRad (aVector v,float yaw, float pitch, float roll) {
		
		uVector quat = new uVector(0f,0f,0f,1f);
		final float hr = roll * 0.5f;
		final float shr = (float)Math.sin(hr);
		final float chr = (float)Math.cos(hr);
		final float hp = pitch * 0.5f;
		final float shp = (float)Math.sin(hp);
		final float chp = (float)Math.cos(hp);
		final float hy = yaw * 0.5f;
		final float shy = (float)Math.sin(hy);
		final float chy = (float)Math.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		quat.x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
		quat.y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
		quat.z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
		quat.w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
		return v.set(quat);
		
	}

	public static aVector dev(uVector v) {
		return new aVector(v.elements.toArray());
	}

	/////////////////////
	
	public Vector2 V2()
	{
		return new Vector2(this.x, this.y);
	}
	
	
	public Vector3 V3()
	{
		return new Vector3(this.x, this.y, this.z);
	}
	
	public Quaternion Q()
	{
		return new Quaternion(this.x, this.y, this.z, this.w);
	}
	
	public static Array<aVector> toArray(Collection<aVector<Number>> vects) {
		Array<aVector> result = new Array<aVector>();
		for (aVector<Number> v : vects)
			result.add(v);
		return result;
	}

	public static Array<aVector> toArray(aVector... vects) {
		Array<aVector> result = new Array<aVector>();
		for (aVector<Number> v : vects)
			result.add(v);
		return result;
	}
	

	public static aVector toStream(Collection<aVector<Number>> vectors) {
		return toStream(toArray(vectors).toArray());
	}

	public static aVector toStream(aVector... vectors) {
		Array<Number> result = new Array<Number>(true, 0, Number.class);
		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[i].length(); j++) {
				result.add(vectors[i].get(j));
			}
		}

		return new aVector<Number>(result.toArray());
	}

	public static aVector recombinate(int rank, aVector v) {
		// redistributes values (0->length()) % rank
		String[] recomb = new String[rank]; // rank# of values to pack into return vector
		aVector res = new aVector(rank, v.getType(), false);

		for (int i = 0; i < v.length(); i++) {
			int at = i % rank;
			recomb[at] = "" + v.get(i);

			res.append(N_Resolver.stringInt(recomb[at]));
		}

		return res;
	}

	public static aVector toSize(aVector v, int len) {
		if (v.length() == len)
			return v;
		aVector mirror = v.cpy();

		v.elements = new Array<Number>(true, len, v.getType().getClass());
		v.labels = new Array<String>(true, len, String.class);
		for (int i = 0; i < len; i++) {
			if (i < v.length()) {
				v.elements.add(mirror.elements.get(i));
				v.labels.add(mirror.labels.get(i));
			} else {
				v.append(0);
			}
		}
		return v;

	}

	public static Array<aVector> RectifyVectors(aVector... vectors) {
		int depth = HighestDimension(vectors);
		int width = vectors.length;
		Array<aVector> result = new Array<aVector>(true, vectors.length, Number.class);
		for (int i = 0; i < width; i++) {

			aVector n = new aVector(depth, vectors[i].getType(), false);
			n.add(vectors[i]);

			result.add(n);
		}
		return result;
	}

	public static Array<aVector> BoxifyVectors(aVector... vectors) {
		int width = vectors.length;
		Array<aVector> result = new Array<aVector>(true, vectors.length, Number.class);
		for (int i = 0; i < width; i++) {

			aVector n = new aVector(width, vectors[i].getType(), false);
			n.add(vectors[i]);

			result.add(n);
		}
		return result;
	}

	public static int HighestDimension(aVector... vectors) {
		return compare_HighestDimension(vectors).length();
	}

	public static aVector compare_HighestDimension(aVector... vectors) {
		aVector biggestD = vectors[0];
		for (aVector v : vectors) {
			if (v.length() > biggestD.length())
				biggestD = v;
		}

		return biggestD;
	}

	public static int LowestDimension(aVector... vectors) {
		return compare_LowestDimension(vectors).length();
	}

	public static aVector compare_LowestDimension(aVector... vectors) {
		aVector smallestD = vectors[0];
		for (aVector v : vectors) {
			if (v.length() > smallestD.length())
				smallestD = v;
		}

		return smallestD;
	}

	////////////////////

	///////////////////

	@Override
	public aVector set(aVector v) {
		super.set(v);
		for (int i = 0; i < v.elements.size; i++) {
			if (i == 0) {
				x = (float) N_Resolver.resolve(v.elements.get(0)).floatValue();
				this.labels.set(i, "x:" + v.elements.get(0));
			}
			if (i == 1) {
				y = (float) N_Resolver.resolve(v.elements.get(1)).floatValue();
				this.labels.set(i, "y:" + v.elements.get(1));
			}
			if (i == 2) {
				z = (float) N_Resolver.resolve(v.elements.get(2)).floatValue();
				this.labels.set(i, "z:" + v.elements.get(2));
			}
			if (i == 3) {
				w = (float) N_Resolver.resolve(v.elements.get(3)).floatValue();
				this.labels.set(i, "w:" + v.elements.get(3));
			}
		}
		return this;
	}

	@Override
	public aVector set(Number... values) {
		super.set(values);
		this.labels.clear();

		
		
		for (int i = 0; i < values.length; i++) {			
			if (i == 0) {
				x = (float) values[0].floatValue();
				this.labels.add("");
				this.labels.set(i, ("x:" + values[0]));
			}
			if (i == 1) {
				y = (float) values[1].floatValue();
				this.labels.add("");
				this.labels.set(i, ("y:" + values[1]));
			}
			if (i == 2) {
				z = (float) values[2].floatValue();
				this.labels.add("");
				this.labels.set(i, ("z:" + values[2]));
			}
			if (i == 3) {
				w = (float) values[3].floatValue();
				this.labels.add("");
				this.labels.set(i, ("w:" + values[3]));
			}
			
		}
		
		return this;
	}

	public static float len(Number... numbers) {
		aVector v = new aVector(numbers);
		return v.len();

	}

	/** @return The dot product between the two vectors */
	public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
		return x1 * x2 + y1 * y2 + z1 * z2;
	}

	public static float dot(aVector a, aVector b) {
		aVector r = a.cpy().mul(b.cpy());

		return r.sum();
	}

	public float dot(aVector v, int offset) {

		int count = this.elements.size; //
		float result = 0f;
		for (int i = 0; i < count; i++) {
			if (v.elements.get(offset + i) != null)
				result += N_Resolver.resolveF(N_Operator.mul(N_Resolver.resolve(this.elements.get(i)),
						N_Resolver.resolve(v.elements.get(offset + i))));
		}
		return result;

	}

	public static float dot(aVector a, Number[] data, int offset) {

		int count = a.elements.size; //
		float result = 0.0f;
		for (int i = 0; i < count; i++) {
			if (data[offset + i] != null)
				result += N_Resolver.resolveF(
						N_Operator.mul(N_Resolver.resolve(a.elements.get(i)), N_Resolver.resolve(data[offset + i])));
			else
				result += 0f;
		}
		return result;

	}

	public static float dot(aVector a, Number[] data) {

		int count = a.elements.size; //
		float result = 0.0f;
		for (int i = 0; i < count; i++) {
			if (data[i] != null)
				result += N_Resolver
						.resolveF(N_Operator.mul(N_Resolver.resolve(a.elements.get(i)), N_Resolver.resolve(data[i])));
			else
				result += 0f;
		}
		return result;

	}

	public static float len2(final float x, final float y, final float z) {
		return x * x + y * y + z * z;
	}
}
