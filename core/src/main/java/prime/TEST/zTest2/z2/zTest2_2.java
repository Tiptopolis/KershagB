package prime.TEST.zTest2.z2;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import static prime._METATRON.Metatron.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import prime._PRIME.C_O.Geom;
import prime._PRIME.C_O.VectorUtils;
import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.RAUM._GEOM_X.A_I.aGeosetX;
import prime._PRIME.RAUM._GEOM_X.A_I.aLineX;
import prime._PRIME.RAUM._GEOM_X.A_I.aVertexX;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class zTest2_2 extends uApp {

	public uFpsAdapter Explorer;
	Transform Prnt;
	Transform Chld;

	_Property<Float> time;
	_Property<Float> mooch;
	public static Color PHASE = Color.CLEAR;

	public zEnv Environment;

	aGeosetX G;

	@Override
	public void create() {
		super.create();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		this.Environment = new zEnv();

		this.Prnt = new Transform();
		this.Chld = new Transform();
		this.Chld.SetParent(this.Prnt);
		this.Chld.SetScale(new Vector3(2, 2, 2));

		time = new _Property<Float>("Time", 0f);
		mooch = new _Property<Float>("Time-er", 0f);
		time.connect("Listener", mooch);

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float dt = (float) this.time.get();
		dt += deltaTime;
		this.time.set(dt);
		if ((float) time.get() > MathUtils.PI)
			time.set(-MathUtils.PI);

		// Log("[}o" + time + "o{]");
		aGeosetX g = new aGeosetX();
		g.vertices.add(new aVertexX(0, 0, 0));
		g.vertices.add(new aVertexX(32, 0, 0));
		g.vertices.add(new aVertexX(0, 32, 0));
		g.lines.add(new aLineX(g.vertices.get(0), g.vertices.get(1)));
		g.lines.add(new aLineX(g.vertices.get(1), g.vertices.get(2)));
		g.lines.add(new aLineX(g.vertices.get(0), g.vertices.get(2)));

		G = g.cpy();
		Log(G.toLog());
		g.dispose();
		G.dispose();
	}

	@Override
	public void render() {
		super.render();
		Matrix4 mtx;
		// Log(1f / 0.5f);

		Sketcher.setProjectionMatrix(CAMERA.getProjection());
		Sketcher.begin();
		PHASE = Color.RED.cpy();
		this.debugRect();

		aLineX l = new aLineX(new Vector3(), this.Explorer.getCameraPosition().cpy()
				.add(this.Explorer.getCameraDirection().cpy().scl(this.Environment.getUnit())));
		drawDebugLine4(l);
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.line(VectorUtils.downcast(l.from.get), VectorUtils.downcast(l.to.get));

		Sketcher.end();

		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		PHASE = Color.BLUE.cpy();
		this.debugRect();
		Sketcher.setColor(Color.BLACK);
		l = new aLineX(new Vector3(), this.Explorer.getCameraPosition().cpy()
				.add(this.Explorer.getCameraDirection().cpy().scl(this.Environment.getUnit())));
		Sketcher.Drawer.line(VectorUtils.downcast(l.from.get), VectorUtils.downcast(l.to.get));
		drawDebugLine4(l);
		Sketcher.end();

		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		PHASE = Color.GREEN.cpy();
		this.debugRect();
		Sketcher.setColor(Color.BLACK);
		l = new aLineX(new Vector3(), this.Explorer.getCameraPosition().cpy()
				.add(this.Explorer.getCameraDirection().cpy().scl(this.Environment.getUnit())));
		Sketcher.Drawer.line(VectorUtils.downcast(l.from.get), VectorUtils.downcast(l.to.get));
		drawDebugLine4(l);
		Sketcher.end();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void debugRect() {
		Sketcher.setColor(PHASE);
		Sketcher.Drawer.filledRectangle(0, 0, 32, 32);
		Color c = PHASE.cpy();
		c.a = -.1f;
		// Line L = new Line(new Vector3(), this.Explorer.getCameraPosition().cpy());
		// L.setColor(c);
		// L.drawShape();
	}

	private void drawDebugLine1(aLineX l) {
		// Array<Vector3> ar = aLine.interpolatePoints(l);

		Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get, 0.1f, true,
				new Vector3(MathUtils.sin(time.get()), MathUtils.cos(time.get()), 1));
		// Array<Vector3> ar = Geom.interpolatePoints(l.from.get,l.to.get, 0.1f,true);

		Array<Vector3> AR = new Array<Vector3>(true, ar.size, Vector3.class);
		for (Vector3 v : ar) {
			int i = ar.indexOf(v, true);
			// Vector3 V = v.cpy().add(new Vector3(0,MathUtils.sin(time.get()),0));
			// Vector3 V = v.cpy().add(new
			// Vector3(MathUtils.cos(time.get()),MathUtils.sin(time.get())*i,0));
			Vector3 V = v.cpy().add(new Vector3(0, MathUtils.sin(v.x) * 2, 0));
			AR.add(V);

		}

		Color C = PHASE.cpy();
		C.a = 0.5f;
		Sketcher.setColor(C);
		for (Vector3 v : AR) {
			Sketcher.Drawer.filledCircle(v.x, v.y, 8);
		}
	}

	private void drawDebugLine2(aLineX l) {
		// Array<Vector3> ar = aLine.interpolatePoints(l);

		// Array<Vector3> ar = Geom.interpolatePoints(l.from.get,l.to.get, 0.1f,true,new
		// Vector3(MathUtils.sin(time.get()),MathUtils.cos(time.get()),1));
		Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get, Math.abs((float) time.get()), true);

		Array<Vector3> AR = new Array<Vector3>(true, ar.size, Vector3.class);
		for (Vector3 v : ar) {
			int i = ar.indexOf(v, true);
			// Vector3 V = v.cpy().add(new Vector3(0,MathUtils.sin(time.get()),0));
			// Vector3 V = v.cpy().add(new
			// Vector3(MathUtils.cos(time.get()),MathUtils.sin(time.get())*i,0));
			Vector3 V = v.cpy()
					.add(new Vector3(Math.abs((float) time.get()) * MathUtils.cos(v.x * Math.abs((float) time.get())),
							Math.abs((float) time.get()) * MathUtils.sin(v.x * Math.abs((float) time.get())), 0));
			AR.add(V);

		}

		Color C = PHASE.cpy();
		C.a = 0.5f;
		Sketcher.setColor(C);
		for (Vector3 v : AR) {
			Sketcher.Drawer.filledCircle(v.x, v.y, Math.abs((float) time.get()) * 0.2f);
		}
		Vector3 m = l.getMidpoint();
		Sketcher.setColor(Color.PURPLE);
		Sketcher.Drawer.circle(m.x, m.y, 4);

	}

	private void drawDebugLine3(aLineX l) {
		// Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get,
		// Math.abs((float) time.get()), true);

		// renders every 10th pt of the line, can also do continuous freq at 1/10
		Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get);

		Color C = PHASE.cpy();
		C.a = 0.5f;
		Sketcher.setColor(C);
		for (Vector3 v : ar) {
			int i = ar.indexOf(v, true);
			if (i == 0 || i % 10 == 0)
				Sketcher.Drawer.filledCircle(v.x, v.y, 8);
		}

		Vector3 m = l.getMidpoint();
		Sketcher.setColor(Color.MAGENTA);
		Sketcher.Drawer.circle(m.x, m.y, 4);
		Log(VectorUtils.dst(l.from.get, m));
		Log(VectorUtils.dst(m, l.to.get));

		Vector3 left = l.getNormalNegative();
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.circle(left.x, left.y, 2);

		Vector3 right = l.getNormalPositive();
		Sketcher.setColor(Color.YELLOW);
		Sketcher.Drawer.circle(right.x, right.y, 2);
	}

	private void drawDebugLine4(aLineX l) {

		// Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get);
		// divides line into units of size 32
		// Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get, 1f / 32f,
		// true);

		// matches line increment to Environment's UnitSize; fewer points, i think
		Array<Vector3> ar = Geom.interpolatePoints(l.from.get, l.to.get,
				1f / (this.Environment.getUnit().len() / 3) * 2, true);

		Color C = PHASE.cpy();
		C.a = 0.5f;
		Sketcher.setColor(C);
		for (Vector3 v : ar) {
			int i = ar.indexOf(v, true);
			// if (i == 0 || i % 10 == 0)
			Sketcher.Drawer.filledCircle(v.x, v.y, 8);
		}

		Vector3 m = l.getMidpoint();
		Sketcher.setColor(Color.MAGENTA);
		Sketcher.Drawer.circle(m.x, m.y, 4);
		Log(VectorUtils.dst(l.from.get, m));
		Log(VectorUtils.dst(m, l.to.get));

		Vector3 left = l.getNormalNegative();
		Sketcher.setColor(Color.TEAL);
		Sketcher.Drawer.circle(left.x, left.y, 2);

		Vector3 right = l.getNormalPositive();
		Sketcher.setColor(Color.YELLOW);
		Sketcher.Drawer.circle(right.x, right.y, 2);
	}

}
