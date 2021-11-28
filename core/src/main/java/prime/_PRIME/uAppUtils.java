package prime._PRIME;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import net.dermetfan.utils.Pair;
import prime._PRIME.C_O.RNG;

public class uAppUtils {

	// public static uApp appInstance;
	public static uAppUtils instance;
	public static Vector2 DEFAULT_RESOLUTION = new Vector2(640, 480);
	public static Vector2 altRatio = new Vector2(1, 1);

	static {
		uAppUtils.instance = new uAppUtils();
	}

	public static int Width = Gdx.graphics.getWidth();
	public static int Height = Gdx.graphics.getHeight();
	public static float AspectRatio;
	public static Matrix4 ScreenProjection;

	public static Vector2 MouseRaw = new Vector2(0, 0); // use this for camera projection
	public static Vector2 MouseScreenPos = new Vector2(0, 0); // move to a static UI handler?
	public static int MouseX = 0;
	public static int MouseY = 0;

	/*
	 * public uAppUtils(uApp app) { appInstance = app; instance = this; }
	 */

	public uAppUtils() {

		instance = this;
		ScreenProjection = new Matrix4().setToOrtho2D(0, 0, Width, Height);

	}

	public static void update(float deltaTime) {
		MouseRaw.set(Gdx.input.getX(), Gdx.input.getY());
		MouseScreenPos.set(Gdx.input.getX(), Height - Gdx.input.getY() - 1);
		MouseX = Gdx.input.getX();
		MouseY = Gdx.input.getY();

	}

	public static void resize() {
		Width = Gdx.graphics.getWidth();
		Height = Gdx.graphics.getHeight();

		if (Height == 0)
			Height = 1;
		AspectRatio = Width / Height;
		ScreenProjection = new Matrix4().setToOrtho2D(0, 0, Width, Height);
		altRatio = new Vector2(Width / DEFAULT_RESOLUTION.x, Height / DEFAULT_RESOLUTION.y);
	}

	public static float mod(float a, float b) {
		return a % b;
	}

	public static Vector3 mod(Vector3 a, float b) {
		return new Vector3(a.x % b, a.y % b, a.z % b);
	}

	// ??
	public static Vector3 mod(float a, Vector3 b) {
		return new Vector3(a % b.x, a % b.y, a % b.z);
	}

	public static Vector3 mod(Vector3 a, Vector3 b) {
		return new Vector3(a.x % b.x, a.y % b.y, a.z % b.z);
	}

	public static void Log() {
		Log("");
	}

	public static void Log(String s) {

		System.out.println(s);
	}

	public static void Log(Object s) {
		if (s != null)
			System.out.println(s.toString());
		else
			Log("null");
	}

	public static void Log(Object[] S) {

		for (int i = 0; i < S.length; i++) {
			if (S[i] == null)
				Log("S[i] is null :?" + i);
			// if(S[i]!= null)
			Log(i + "/" + (S.length - 1) + " :  " + S[i].toString());
		}
	}
	
	public static void Log(float[] s)
	{
		for (int i = 0; i < s.length; i++) {
			if (s[i] == Float.NaN)
				Log("S[i] is null :?" + i);
			// if(S[i]!= null)
			Log(i + "/" + (s.length - 1) + " :  " + s[i]);
		}
	}

	public static void Page() {
		String out = "";
		out += "\n";
		for (int i = 0; i < 180; i++) {
			out += ("_");
		}
		out += "\n";
		Log(out);
	}

	public static void print(String s) {
		Log(s);
	}

	// can be used as a basic object parser
	@SuppressWarnings("unused")
	static public void printArray(Object what) {
		System.out.println("PRINT ARRAY: " + what + " " + what.getClass());
		System.out.println("           : " + what.getClass().getName());
		System.out.println("           : " + what.getClass().getSimpleName());
		System.out.println("           : " + what.getClass().getCanonicalName());
		System.out.println("");

		if (what == null) {
			// special case since this does fuggly things on > 1.1
			System.out.println("null");

		} else {
			String name = what.getClass().getName();
			if (name.charAt(0) == '[') {
				switch (name.charAt(1)) {
				case '[':
					// don't even mess with multi-dimensional arrays (case '[')
					// or anything else that's not int, float, boolean, char
					System.out.println(what);
					break;

				case 'L':
					// print a 1D array of objects as individual elements
					Object poo[] = (Object[]) what;
					for (int i = 0; i < poo.length; i++) {
						if (poo[i] instanceof String) {
							System.out.println("[" + i + "] \"" + poo[i] + "\"" + " -|String|");
						} else {
							System.out.println("[" + i + "] " + poo[i]);
						}
					}
					break;

				case 'Z': // boolean
					boolean zz[] = (boolean[]) what;
					for (int i = 0; i < zz.length; i++) {
						System.out.println("[" + i + "] " + zz[i] + " -|boolean|");
					}
					break;

				case 'B': // byte
					byte bb[] = (byte[]) what;
					for (int i = 0; i < bb.length; i++) {
						System.out.println("[" + i + "] " + bb[i] + " -|byte|");
					}
					break;

				case 'C': // char
					char cc[] = (char[]) what;
					for (int i = 0; i < cc.length; i++) {
						System.out.println("[" + i + "] '" + cc[i] + "'" + " -|char|");
					}
					break;

				case 'I': // int
					int ii[] = (int[]) what;
					for (int i = 0; i < ii.length; i++) {
						System.out.println("[" + i + "] " + ii[i] + " -|int|");
					}
					break;

				case 'J': // lonn int
					long jj[] = (long[]) what;
					for (int i = 0; i < jj.length; i++) {
						System.out.println("[" + i + "] " + jj[i] + " -|long|");
					}
					break;

				case 'F': // float
					float ff[] = (float[]) what;
					for (int i = 0; i < ff.length; i++) {
						System.out.println("[" + i + "] " + ff[i] + " -|float|");
					}
					break;

				case 'D': // double
					double dd[] = (double[]) what;
					for (int i = 0; i < dd.length; i++) {
						System.out.println("[" + i + "] " + dd[i] + " -|double|");
					}
					break;

				default:
					System.out.println(what);
				}
			} else { // not an array
				System.out.println(what.toString());
			}
		}
		System.out.flush();
	}

	public static <T> Pair<T[], T[]> splitArray(T[] array, int index) {

		if (index > array.length)
			return new Pair(array, array);

		int aLen = index;
		T[] a = (T[]) new Object[aLen];
		for (int i = 0; i < aLen; i++) {
			a[i] = array[i];
		}

		int bLen = array.length - index;
		T[] b = (T[]) new Object[bLen];
		for (int i = 0, o = 0; i < bLen; i++) {
			b[i] = array[i + index];
		}

		Pair<T[], T[]> result = new Pair(a, b);
		return result;
	}
	//
	// NUMBER STUFF
	//

	public static String isOddOrEven(Number n) {
		if ((n.byteValue() ^ 1) == n.byteValue() + 1) {
			return ("Even");
		} else
			return ("Odd");
	}

	// reduce vector to int -> (int nSeed = y << 16 | x;)
	////

	// CENTER->MOUSE CURSOR ROTATION in radians
	// Rotation = (float) Math.atan2((Height - MouseY) - (Height / 2), MouseX -
	// (Width / 2));
	////

	// DRAW A SPIRAL
	// float r = 0; // radius default increment +0.1
	// float theta = 0;// default increment +0.01
	// --updateFn
	// float x = (float) (r * Math.cos(theta));
	// float y = (float) (r * Math.sin(theta));
	// uSketcher.Drawer.setColor(COLOR);
	// uSketcher.Drawer.filledEllipse(x + center.x, y + center.y, 1, 1);
	////
	public static <T> boolean isInstanceOf(Class<T> clazz, Class<T> targetClass) {
		return clazz.isInstance(targetClass);
	}

	//
	////
	// @Annotation Reflection hax
	//
	// Instead of AccessibleObject.getAnnotation(Class annotationClass) use
	private static Annotation getAnnotation(AccessibleObject object, Class annotationClass) {
		for (Annotation a : object.getAnnotations()) {
			if (a.annotationType().getCanonicalName().equals(annotationClass.getCanonicalName()))
				return a;
		}
		return null;
	}

	// Instead of MyAnnotation.value() use
	private static Object getAnnotationFieldWithReflection(Annotation annotation, String fieldName)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		return annotation.annotationType().getMethod(fieldName).invoke(annotation);
	}

	//////////////////////
	public static Object getRandom(Object[] things) {
		int at = RNG.rndInt(0, things.length - 1);
		Log(" :: " + at);
		return things[at];
	}

	public static Object[] sample(Object[] things, int size) {
		if (size > things.length)
			size = things.length;
		ArrayList<Object> list = new ArrayList<Object>();
		int c = 0;
		while (c < size-1) {
			Object next = getRandom(things);
			Log(list.size()+ " <" + next + " " + next.getClass().getSimpleName());			
			if (!list.contains(next)) {
				list.add(next);
				c++;
			}
			Log(list.get(0).equals(next));
		}

		return list.toArray();
	}
	
	public static ArrayList<Object> sampleCollection(Object[] things, int size) {
		if (size > things.length)
			size = things.length;
		ArrayList<Object> list = new ArrayList<Object>();
		int c = 0;
		while (c < size-1) {
			Object next = getRandom(things);
			Log(list.size()+ " <" + next + " " + next.getClass().getSimpleName());			
			if (!list.contains(next)) {
				list.add(next);
				c++;
			}
			Log(list.get(0).equals(next));
		}

		return list;
	}

}
