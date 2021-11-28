package prime.TEST.a1DEFAULT;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import static prime._METATRON.Metatron.*;

import prime._PRIME.C_O.Prototype.Transform;
import prime._PRIME.SYS.uApp;
import prime._PRIME.SYS.NIX._Property;
import prime._PRIME.UI._Camera.uFpsAdapter;

public class a1DEFAULT_1 extends uApp {

	public uFpsAdapter Explorer;	
	Transform Prnt;
	Transform Chld;
	
	_Property<Float> time;
	_Property<Float> mooch;
	
	@Override
	public void create() {
		super.create();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.Explorer = new uFpsAdapter(CAMERA);
		this.Explorer.init();
		
		this.Prnt = new Transform();
		this.Chld = new Transform();
		this.Chld.SetParent(this.Prnt);
		this.Chld.SetScale(new Vector3(2,2,2));
		
		time = new _Property<Float>("Time", 0f);
		mooch = new _Property<Float>("Time-er", 0f);
		time.connect("Listener", mooch);
	}

	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float dt = (float)this.time.get();
		dt += deltaTime;
		this.time.set(dt);
		if((float)time.get()>MathUtils.PI)
			time.set(0f);
		
		Log("[}o"+time+"o{]");
	}

	@Override
	public void render() {
		super.render();
		Sketcher.setProjectionMatrix(CAMERA.getProjection());
		Sketcher.begin();
		this.renderTransformTest(Color.RED);
		Sketcher.end();
		
		Sketcher.setProjectionMatrix(this.Explorer.getProjection());
		Sketcher.begin();
		this.renderTransformTest(Color.BLUE);
		Sketcher.end();
		
		Sketcher.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Width, Height));
		Sketcher.begin();
		this.renderTransformTest(Color.GREEN);
		Sketcher.end();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	
	
	public void renderTransformTest(Color d)
	{
		float tX = MathUtils.sin(this.time.get());
		float tY = MathUtils.cos(this.time.get());
		this.Prnt.SetScale(new Vector3(tX,tY,1));
		this.Prnt.SetPosition(CAMERA.getCameraPosition().cpy());
		
		Log("P-\n"+this.Prnt);
		Log("C-\n"+this.Chld);
		Vector3 unit = new Vector3(32,32,32);
		
		Vector3 pPos = this.Prnt.GetPosition();
		Vector3 pScl = this.Prnt.GetScale();
		Vector3 cPos= this.Chld.GetPosition();
		Vector3 cScl = this.Chld.GetScale();
	
		Sketcher.setColor(Color.BLACK);
		Sketcher.Drawer.circle(pPos.x, pPos.y, pScl.x*32);
		Sketcher.setColor(Color.WHITE);
		Sketcher.Drawer.circle(cPos.x, cPos.y, cScl.x*32);
		Sketcher.setColor(d);
		Sketcher.Drawer.filledRectangle(pPos.x, pPos.y,  pScl.x*32,  pScl.y*32);
		Sketcher.setColor(d);
		Sketcher.Drawer.rectangle(cPos.x, cPos.y,  cScl.x*32,  cScl.y*32);
		
	}
}
