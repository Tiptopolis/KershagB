package prime._PRIME.UI._GFX;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer.FrameBufferBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer;

import prime._PRIME.C_O.Prototype.Rect;

public class DrawBuffer implements iFrameBuffer {

	public boolean active = true;

	public FrameBufferManager manager;

	public FrameBuffer buffer = null;
	public TextureRegion view = null; // whole image area
	public Texture cache;
	private float m_fboScaler = 1.00f; //
	private boolean m_fboEnabled = true;
	public Matrix4 drawMatrix;

	// offset, for adding a buffer to your buffer
	public Rect area = new Rect();

	public boolean doRefresh = true;
	public boolean doCache = true;

	public DrawBuffer() {

	}

	public DrawBuffer(Vector2 size) {

		this.area = new Rect(new Vector2(0, 0), size);
		this.withManager(FrameBufferManager.instance);
		this.refresh();
		this.activate();
	}

	public DrawBuffer(Rect r) {
		this.area = r;
		this.withManager(FrameBufferManager.instance);
		this.refresh();
		this.activate();
	}

	public DrawBuffer withManager(FrameBufferManager manager) {
		this.manager = manager;
		this.manager.registerBuffer(this);
		return this;
	}

	public static DrawBuffer unmanagedBuffer(Vector2 size) {
		DrawBuffer newBuffer = new DrawBuffer(size);
		return newBuffer;
	}

	public static DrawBuffer unmanagedBuffer(Rect r, boolean activate) {
		DrawBuffer newBuffer = new DrawBuffer(r);
		newBuffer.area = r;
		newBuffer.refresh();
		if (activate)
			return newBuffer.activate();
		else
			return newBuffer;
	}

	@Override
	public void buffer() {
		// buffer contents
		// Override this
		this.begin();
		this.render();
		this.end();
	}
	
	public void render()
	{
		
	}

	@Override
	public void refresh() {
		// size of desired virtual area
		this.drawMatrix = this.area.getMatrix();

		int w = 0;
		int h = 0;
		if (this.area != null) {
			w = (int) area.width;
			h = (int) area.height;
		}

		buffer = new FrameBuffer(Format.RGBA8888, w, h, true);
		view = new TextureRegion(this.buffer.getColorBufferTexture(), (int) this.area.minX, (int) this.area.minY,
				(int) this.area.width, (int) this.area.height);
		if (doCache)
			this.cache = view.getTexture();

		view.flip(false, true);
		this.doRefresh = false;

	}

	public DrawBuffer activate() {
		this.active = true;
		this.refresh();
		return this;
	}

	//draw rendered contents to 'screen' 
	@Override
	public void draw() {
		

		this.draw(new Vector2(this.area.minX, this.area.minY));

	}

	public void draw(Vector2 v) {
		if (this.doRefresh)
			this.refresh();
		//toScreen
		Sketcher.getBatch().draw(this.view, v.x, v.y, this.area.width, this.area.height);

	}
	
	public void draw(Rect area) {
		if (this.doRefresh)
			this.refresh();
		//toScreen
		Sketcher.getBatch().draw(this.view, area.minX, area.minY, area.width, area.height);

	}

	public void begin() {
		if (this.buffer == null || this.view == null)
			this.refresh();

		this.buffer.begin();
	}

	public void end() {
		this.buffer.end();
	}

	@Override
	public void dispose() {
		this.buffer.dispose();
		if (this.cache != null)
			this.cache.dispose();
	}

}
