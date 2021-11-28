package prime._PRIME.SYS._Events;

import static prime._PRIME.uAppUtils.*;
import static prime._PRIME.uSketcher.*;
import java.util.ArrayList;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Queue;

import prime._PRIME.SYS._Events.Prototype.Message;
import prime._PRIME.UI.iMonad;



public class aEventManager extends InputMultiplexer implements iEventProcessor, iMonad{

	protected aEventManager parent;
	protected int hDepth = 0;
	public boolean isProxy = false;

	protected ArrayList<iEventReciever> listeners = new ArrayList<iEventReciever>();
	public Queue<aEvent> pending = new Queue<aEvent>();

	public boolean LogInput = false;

	public aEventManager() {
		Log("-" + this.getClass().getSimpleName() + " created");
	}

	public aEventManager(aEventManager parent) {
		this.parent = parent;
		this.hDepth = parent.hDepth + 1;
		this.isProxy = true;
		parent.listeners.add(this);
		Log("\n-" + this.getClass().getSimpleName() + " created");
		
	}

	public void update(float deltaTime) {
		
		for (aEvent e : pending) {
			for (iEventReciever r : listeners) {
				r.handle(e);
			}
		}
	}




	


	////////////////
	@Override
	public boolean keyDown(int keycode) {
		// Log(this.getClass().getSimpleName() + " k->" + keycode);
		if (LogInput)
			Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.KeyDown + keycode);
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		// Log(this.getClass().getSimpleName() + " k<-" + keycode);
		if (LogInput)
		Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.KeyUp + keycode);
		return super.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		if (LogInput)
		Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.KeyTyped + character);
		return super.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (LogInput)
		Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.TouchDown + "[(" + screenX + "," + screenY + ")|"
				+ pointer + "|" + button + ")]");
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (LogInput)
		Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.TouchUp + "[(" + screenX + "," + screenY + ")|"
				+ pointer + "|" + button + ")]");
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (LogInput)
		Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.TouchDragged + "[(" + screenX + "," + screenY + ")|" + pointer
				+ ")]");
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		//if (this.logInput)
		// Log(this.getClass().getSimpleName()+hDepth+" t<->" + "[("+screenX + "," +
		// screenY+")]");
		return super.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		if (LogInput)
		Log(this.getClass().getSimpleName() + hDepth + StdInputEvents.Scrolled + "[(" + amountX + "," + amountY + ")]");
		return super.scrolled(amountX, amountY);
	}

	public static enum StdInputType {

		Touch("t"),
		Scroll("s"),
		Cursor("x"),
		Command("c"),
		Key("k");

		public final String get;

		private StdInputType(String symbol) {
			this.get = symbol;
		}
	}

	public static enum StdInputEvents {
		TouchDown(InputEvent.Type.touchDown,StdInputType.Touch, "->"),
		TouchUp(InputEvent.Type.touchUp,StdInputType.Touch, "<-"),
		TouchDragged(InputEvent.Type.touchDragged,StdInputType.Touch, "<_>"),
		KeyDown(InputEvent.Type.keyDown,StdInputType.Key, "->"),		
		KeyUp(InputEvent.Type.keyUp,StdInputType.Key, "<-"),
		KeyTyped(InputEvent.Type.keyTyped,StdInputType.Key, "<->"),
		Scrolled(InputEvent.Type.scrolled,StdInputType.Scroll, "<+>"),
		CursorMoved(InputEvent.Type.mouseMoved, StdInputType.Cursor,"<->");

		
		public final InputEvent.Type I;
		public final StdInputType T;
		public final String Symbol;

		private StdInputEvents(InputEvent.Type I,StdInputType T, String symbol) {
			this.T = T;
			this.Symbol = symbol;
			this.I = I;
		}

		@Override
		public String toString() {
			return " <[" + this.T.get + "|" + this.Symbol + "]> ";
		}



	}



	@Override
	public void transformUpdated() {
		// TODO Auto-generated method stub

	}

	public String logInput() {
		String log = "";
		return log;
	}

	@Override
	public String toLog() {
		return this.getClass().getSimpleName();
	}

	//////////////
	
	@Override
	public void addEventListener(String type, EventListener listener, boolean useCapture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEventListener(String type, EventListener listener, boolean useCapture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void broadcast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcast(Object at) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handle(Message m) {
		// TODO Auto-generated method stub
		return false;
	}





	



	

}
