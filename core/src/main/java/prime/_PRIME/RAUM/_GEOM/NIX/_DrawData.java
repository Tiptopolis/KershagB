package prime._PRIME.RAUM._GEOM.NIX;

import com.badlogic.gdx.graphics.Color;

import prime._PRIME.SYS.NIX._Component;

public class _DrawData {

	public boolean stroke = true;
	public boolean fill = true;

	public Color inner = Color.CLEAR;
	public Color outter = Color.WHITE;

	public float lineSize = 1f;
	public float vertexSize = 1f;

	public _DrawData() {
		//super("DrawData", _Component.Component);
	}

	public Color fill() {
		return this.inner;
	}

	public void fill(boolean d) {
		this.fill = d;
	}

	public void fill(Color c) {
		this.inner = c;
		this.fill = true;
	}

	public void stroke(boolean d) {
		this.stroke = d;
	}

	public void stroke(Color c) {
		this.outter = c;
		this.stroke = true;
	}

	public Color stroke() {
		return this.outter;
	}

}
