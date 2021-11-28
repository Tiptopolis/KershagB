package prime._PRIME.UI._Scene;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import prime.uChumpEngine;
import prime._PRIME.RAUM._Environment;
import prime._PRIME.RAUM.iSpace;
import prime._PRIME.SYS.NIX._Entity;
import prime._PRIME.SYS.Prototype.Tree;
import prime._PRIME.SYS._Events.iEventProcessor;
import prime._PRIME.UI.iMonad;

public class _Stage extends _Environment implements iSpace, iEventProcessor, iMonad {

	// Environment component of aViewContext, for _Widget Entities & _InputEvents
	public Stage s;
	public OrthographicCamera StageCamera;
	public Tree<Node<Actor>> SceneGraph;

	//public _Group root;

	public _Stage() {
		super("Stage");
		// TODO Auto-generated constructor stub
		this.StageCamera = new OrthographicCamera(Width, Height);
		uChumpEngine.METATRON.addProcessor(this);
		this.init();
	}

	public void init() {

	}

	public void act(float deltaTime) {
		for (_Entity E : this.Members) {
			/*if (E instanceof _Actor) {
				_Actor A = (_Actor) E;
				A.act(deltaTime);
			}*/
		}
	}

	public void render() {

		Color c = Color.PURPLE;
		Sketcher.setColor(c);
		Sketcher.Drawer.rectangle(0, 0, this.Dimension.x, this.Dimension.y);
		Vector3 m = new Vector3(MouseX, MouseY, 1);
		Sketcher.Drawer.filledCircle(m.x, m.y, 3);
		// Log(" ***");
		c.a = 0.5f;
		Sketcher.setColor(c);
		for (_Entity E : this.Members) {
			/*if (E instanceof iDrawable) {
				// Log(" ___");
				iDrawable D = (iDrawable) E;
				D.draw();
			}*/

		}

		Sketcher.setColor(Color.BLACK);
		m = new Vector3(MouseX, MouseY, 1);
		this.StageCamera.unproject(m);
		Sketcher.Drawer.filledCircle(m.x, m.y, 3);

	}

	public void resize(int width, int height) {
		this.resize(new int[] { width, height });
	}

	public void resize(int... size) {
		super.resize(size);
		this.StageCamera.setToOrtho(true, Width, Height);
	}

	public void resize(float... size) {
		int[] i = new int[size.length];
		for (int f = 0; f < size.length; f++) {
			i[f] = (int) size[f];
		}
		this.resize(i);
	}

	@Override
	public Camera spaceCam() {
		return this.StageCamera;
	}

	@Override
	public String toLog() {
		String log = "";
		log += this.toString();
		log += "\n";
		log += this.Members.size;

		return log;
	}
}
