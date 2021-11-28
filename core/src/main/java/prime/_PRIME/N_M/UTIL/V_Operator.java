package prime._PRIME.N_M.UTIL;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class V_Operator {

	public static Vector2 add(Vector2 a, Vector2 other) {
		return a.add(other.cpy());
	}

	public static Vector2 add(Vector2 a, Vector3 other) {
		return a.add(other.x, other.y);
	}

	public Vector3 add(Vector3 a, Vector2 other) {
		return a.add(other.x, other.y, 0);

	}

	public static Vector3 add(Vector3 a, Vector3 other) {
		return a.add(other.cpy());
	}

	public static Vector2 add(Vector2 a, Number n) {
		return a.add(n.floatValue(), n.floatValue());
	}

	public static Vector3 add(Vector3 a, Number n) {
		return a.add(n.floatValue());
	}

	//
	public static Vector2 sub(Vector2 a, Vector2 other) {
		return a.sub(other.cpy());
	}

	public static Vector2 sub(Vector2 a, Vector3 other) {
		return a.sub(other.x, other.y);
	}

	public Vector3 sub(Vector3 a, Vector2 other) {
		return a.sub(other.x, other.y, 0);

	}

	public static Vector3 sub(Vector3 a, Vector3 other) {
		return a.sub(other.cpy());
	}

	public static Vector2 sub(Vector2 a, Number n) {
		return a.sub(n.floatValue(), n.floatValue());
	}

	public static Vector3 sub(Vector3 a, Number n) {
		return a.sub(n.floatValue());
	}

	//
	public static Vector2 mul(Vector2 a, Vector2 other) {
		return a.scl(other.cpy());
	}

	public static Vector2 mul(Vector2 a, Vector3 other) {
		return a.scl(other.x, other.y);
	}

	public Vector3 mul(Vector3 a, Vector2 other) {
		return a.scl(other.x, other.y, 0);

	}

	public static Vector3 mul(Vector3 a, Vector3 other) {
		return a.scl(other.cpy());
	}

	public static Vector2 mul(Vector2 a, Number n) {
		return a.scl(n.floatValue(), n.floatValue());
	}

	public static Vector3 mul(Vector3 a, Number n) {
		return a.scl(n.floatValue());
	}

	//
	public static Vector2 div(Vector2 a, Vector2 other) {
		Vector2 A = new Vector2(a.x / other.x, a.y / other.y);

		return a.set(A);
	}

	public static Vector2 div(Vector2 a, Vector3 other) {
		Vector2 A = new Vector2(a.x / other.x, a.y / other.y);

		return a.set(A);
	}

	public Vector3 div(Vector3 a, Vector2 other) {
		Vector3 A = new Vector3(a.x / other.x, a.y / other.y, a.z);

		return a.set(A);

	}

	public static Vector3 div(Vector3 a, Vector3 other) {
		Vector3 A = new Vector3(a.x / other.x, a.y / other.y, a.z / other.z);

		return a.set(A);
	}

	public static Vector2 div(Vector2 a, Number n) {
		Vector2 A = new Vector2(a.x / n.floatValue(), a.y / n.floatValue());
		return a.set(A);
	}

	public static Vector3 div(Vector3 a, Number n) {
		Vector3 A = new Vector3(a.x / n.floatValue(), a.y / n.floatValue(), a.z / n.floatValue());
		return a.set(A);
	}
	
	////////
	
	public static float min(Vector2 of)
	{
		return Math.min(of.x, of.y);
	}
	
	public static float max(Vector2 of)
	{
		return Math.max(of.x, of.y); 
	}
	
	public static Vector2 min(Vector2 a, Vector2 b)
	{
		float x = Math.min(a.x, b.x);
		float y = Math.min(a.y, b.y);
		Vector2 r = new Vector2(x,y);
		return r;
	}
	
	public static Vector3 min(Vector3 a, Vector3 b)
	{
		float x = Math.min(a.x, b.x);
		float y = Math.min(a.y, b.y);
		float z = Math.min(a.z, b.z);
		Vector3 r = new Vector3(x,y,z);
		return r;
	}
	
	public static Vector2 max(Vector2 a, Vector2 b)
	{
		float x = Math.max(a.x, b.x);
		float y = Math.max(a.y, b.y);
		Vector2 r = new Vector2(x,y);
		return r;
	}
	
	public static Vector3 max(Vector3 a, Vector3 b)
	{
		float x = Math.max(a.x, b.x);
		float y = Math.max(a.y, b.y);
		float z = Math.max(a.z, b.z);
		Vector3 r = new Vector3(x,y,z);
		return r;
	}
	
	////////

	public static float[] toStream(Vector3[] vects) {

		Array<Float> F = new Array<Float>(true, vects.length * 3, Float.class);

		for (int i = 0; i < vects.length; i++) {
			F.add(vects[i].x);
			F.add(vects[i].y);
			F.add(vects[i].z);
		}

		float[] f = new float[F.size];
		for(int i = F.size-1; i >=0; i--)
			f[i]=F.get(i).floatValue();
		return f;
	}

	public static float[] toStream(Vector2[] vects) {

		Array<Float> F = new Array<Float>(true, vects.length * 2, Float.class);

		for (int i = 0; i < vects.length; i++) {
			F.add(vects[i].x);
			F.add(vects[i].y);

		}

		float[] f = new float[F.size];
		for(int i = F.size-1; i >=0; i--)
			f[i]=F.get(i).floatValue();
		return f;
	}

	public static Vector3[] fromStreamVect3(float[] values) {
		Array<Vector3> V = new Array<Vector3>(true, values.length / 3, Vector3.class);
		for (int i = 0; i < values.length - 1; i += 3) {

			V.add(new Vector3(values[i], values[i + 1], values[i + 2]));
		}

		return V.toArray();
	}

	public static Vector2[] fromStreamVect2(float[] values) {

		Array<Vector2> V = new Array<Vector2>(true, values.length / 2, Vector2.class);
		for (int i = 0; i < values.length - 1; i += 2) {

			V.add(new Vector2(values[i], values[i + 1]));
		}

		return V.toArray();
	}

	public static Vector2[] downcast(Vector3... values) {
		Array<Vector2> V = new Array<Vector2>(true, values.length, Vector2.class);
		for (int i = V.size - 1; i > 0; i--) {
			V.add(new Vector2(values[i].x, values[i].y));
		}

		return V.toArray();
	}
	
	public static Vector3[] upcast(Vector2... values)
	{
		Array<Vector3> V = new Array<Vector3>(true, values.length, Vector3.class);
		for (int i = V.size - 1; i > 0; i--) {
			V.add(new Vector3(values[i].x, values[i].y, 0));
		}

		return V.toArray();
	}
}
